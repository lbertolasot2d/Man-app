package com.example.man_app.ui.scaricatrice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.man_app.data.model.*
import com.example.man_app.ui.fermate.AutoCompleteTextField
import com.example.man_app.ui.mattoniera.*
import com.example.man_app.ui.components.CommonDatePicker
import com.example.man_app.ui.components.CommonTimePicker
import com.example.man_app.util.formatDate
import com.example.man_app.util.formatTime
import com.example.man_app.util.formatInputDate
import com.example.man_app.util.formatInputTime
import com.example.man_app.util.getNowMillis
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant

private val DarkGreen = Color(0xFF1B5E20)
private val Orange = Color(0xFFFFA500)

@Composable
fun ScaricatriceScreen(
    viewModel: ScaricatriceViewModel,
    lineId: String,
    machineId: String
) {
    val activeReport by viewModel.activeReport.collectAsState()
    val validationError by viewModel.validationError.collectAsState()
    val preFillData by viewModel.preFillData.collectAsState()
    val operatorSuggestions by viewModel.operators.collectAsState()
    val availableShifts by viewModel.availableShifts.collectAsState()
    
    LaunchedEffect(lineId) {
        viewModel.loadShifts(lineId)
    }

    val products by viewModel.getMattonieraProducts(lineId).collectAsState(emptyList())
    val sigle by viewModel.getMattonieraSigle(lineId).collectAsState(emptyList())

    var selectedTab by remember { mutableStateOf(0) }
    var showEditShiftDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        if (activeReport == null) {
            ScaricatriceStartShiftScreen(
                initialData = preFillData,
                products = products,
                sigle = sigle,
                operatorSuggestions = operatorSuggestions,
                availableShifts = availableShifts.map { it.name },
                onStart = { shift, op, prod, sigla, cTime ->
                    viewModel.startShift(shift, op, prod, sigla, lineId, machineId, cTime)
                }
            )
        } else {
            ScaricatriceShiftHeader(
                report = activeReport!!,
                onEndShift = viewModel::closeShift,
                onChangeProduction = viewModel::changeProduction,
                onEditShift = { showEditShiftDialog = true }
            )
            TabRow(selectedTabIndex = selectedTab, modifier = Modifier.height(44.dp)) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) { Text("Produzione", fontSize = 13.sp) }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) { Text("Qualità", fontSize = 13.sp) }
            }
            when (selectedTab) {
                0 -> ScaricatriceProductionTab(viewModel = viewModel, report = activeReport!!)
                1 -> ScaricatriceQualityTab(viewModel = viewModel, report = activeReport!!)
            }
            if (showEditShiftDialog) {
                ScaricatriceEditShiftDialog(
                    report = activeReport!!,
                    operatorSuggestions = operatorSuggestions,
                    availableShifts = availableShifts.map { it.name },
                    onDismiss = { showEditShiftDialog = false },
                    onConfirm = { updatedReport ->
                        viewModel.updateReport(updatedReport)
                        showEditShiftDialog = false
                    }
                )
            }
        }
    }

    validationError?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Dati Incompleti") },
            text = { Text(error) },
            confirmButton = { Button(onClick = { viewModel.clearError() }) { Text("Ho capito") } }
        )
    }
}

@Composable
fun ScaricatriceShiftHeader(report: ScaricatriceReport, onEndShift: () -> Unit, onChangeProduction: () -> Unit, onEditShift: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "SCARICATRICE - Linea: ${report.lineId}", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                Text(text = report.sigla ?: "-", style = MaterialTheme.typography.bodySmall, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = onEditShift, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Edit, "", modifier = Modifier.size(14.dp)) }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Op: ${report.operatorId}", fontSize = 11.sp); Text("Art: ${report.productId}", fontSize = 11.sp); Text("Inizio: ${formatTime(report.startTime)}", fontSize = 11.sp)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 2.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = onChangeProduction, modifier = Modifier.weight(1f).height(28.dp), contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)) { Text("Cambio Prod.", fontSize = 9.sp) }
                Button(onClick = onEndShift, modifier = Modifier.weight(1f).height(28.dp), contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("Fine Turno", fontSize = 9.sp) }
            }
        }
    }
}

@Composable
fun ScaricatriceEditShiftDialog(report: ScaricatriceReport, operatorSuggestions: List<String>, availableShifts: List<String>, onDismiss: () -> Unit, onConfirm: (ScaricatriceReport) -> Unit) {
    var operator by remember { mutableStateOf(report.operatorId) }
    var product by remember { mutableStateOf(report.productId) }
    var sigla by remember { mutableStateOf(report.sigla ?: "") }
    var shift by remember { mutableStateOf(report.shift) }
    val focusManager = LocalFocusManager.current
    var currentStartTime by remember { mutableStateOf(report.startTime) }
    val (f1, f2, f3, f4, f5) = remember { FocusRequester.createRefs() }
    
    var showDatePicker by remember { mutableStateOf(false) }; var showTimePicker by remember { mutableStateOf(false) }
    var dateStr by remember(currentStartTime) { mutableStateOf(formatDate(currentStartTime)) }
    var timeStr by remember(currentStartTime) { mutableStateOf(formatTime(currentStartTime)) }
    
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Modifica Turno") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
            OutlinedTextField(dateStr, { dateStr = formatInputDate(it) }, label={Text("Data")}, modifier=Modifier.fillMaxWidth().focusRequester(f1), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), trailingIcon={Row { IconButton({ showDatePicker = true }) { Icon(Icons.Default.DateRange, "") }; IconButton({ f2.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
            OutlinedTextField(timeStr, { timeStr = formatInputTime(it) }, label={Text("Ora")}, modifier=Modifier.fillMaxWidth().focusRequester(f2), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), trailingIcon={Row { IconButton({ showTimePicker = true }) { Icon(Icons.Default.Timer, "") }; IconButton({ f3.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
            
            ShiftSelectionDropdown(selected = shift, options = availableShifts, onSelect = { shift = it })

            AutoCompleteTextField(
                label = "Op",
                initialValue = operator,
                suggestions = operatorSuggestions,
                onTextChange = { operator = it.uppercase() },
                onConfirm = { operator = it.uppercase() },
                onCheck = { f4.requestFocus() },
                modifier = Modifier.focusRequester(f3)
            )
            OutlinedTextField(product, { product = it.uppercase() }, label={Text("Art")}, modifier=Modifier.fillMaxWidth().focusRequester(f4), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Next))
            OutlinedTextField(sigla, { sigla = it.uppercase() }, label={Text("Sigla")}, modifier=Modifier.fillMaxWidth().focusRequester(f5), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Done))
        }
    }, confirmButton = { Button(onClick = { onConfirm(report.copy(operatorId = operator, productId = product, sigla = sigla, startTime = currentStartTime, shift = shift)) }) { Text("Salva") } })

    if (showDatePicker) {
        CommonDatePicker(initialEpochMillis = currentStartTime, onDismiss = { showDatePicker = false }, onConfirm = { 
            val oldTime = Instant.fromEpochMilliseconds(currentStartTime).toLocalDateTime(TimeZone.currentSystemDefault())
            val newDate = Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            currentStartTime = LocalDateTime(newDate.year, newDate.monthNumber, newDate.dayOfMonth, oldTime.hour, oldTime.minute)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            showDatePicker = false 
        })
    }
    if (showTimePicker) {
        val dt = Instant.fromEpochMilliseconds(currentStartTime).toLocalDateTime(TimeZone.currentSystemDefault())
        CommonTimePicker(initialMinutes = dt.hour * 60 + dt.minute, onDismiss = { showTimePicker = false }, onConfirm = { 
            currentStartTime = LocalDateTime(dt.year, dt.monthNumber, dt.dayOfMonth, it / 60, it % 60)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            showTimePicker = false 
        })
    }
}

@Composable
fun ScaricatriceStartShiftScreen(
    initialData: ShiftPreFillData,
    products: List<String>,
    sigle: List<String>,
    operatorSuggestions: List<String>,
    availableShifts: List<String>,
    onStart: (String, String, String, String, Long?) -> Unit
) {
    var operator by remember(initialData) { mutableStateOf(initialData.operator) }
    var product by remember(initialData) { mutableStateOf(initialData.product) }
    var sigla by remember(initialData) { mutableStateOf(initialData.sigla) }
    var shift by remember(initialData) { mutableStateOf(initialData.shift) }
    
    val focusManager = LocalFocusManager.current; var currentStartTime by remember { mutableStateOf(com.example.man_app.util.getNowMillis()) }
    val (f1, f2, f3, f4, f5) = remember { FocusRequester.createRefs() }
    
    var dateStr by remember(currentStartTime) { mutableStateOf(formatDate(currentStartTime)) }
    var timeStr by remember(currentStartTime) { mutableStateOf(formatTime(currentStartTime)) }
    var showDatePicker by remember { mutableStateOf(false) }; var showTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("Avvio Scaricatrice", style = MaterialTheme.typography.titleLarge)
        
        OutlinedTextField(
            value = dateStr,
            onValueChange = { dateStr = formatInputDate(it) },
            label = { Text("Data") },
            modifier = Modifier.fillMaxWidth().focusRequester(f1),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { f2.requestFocus() }),
            trailingIcon = {
                Row {
                    IconButton(onClick = { showDatePicker = true }) { Icon(Icons.Default.DateRange, null) }
                    IconButton(onClick = { f2.requestFocus() }) { Icon(Icons.Default.Check, null) }
                }
            }
        )

        OutlinedTextField(
            value = timeStr,
            onValueChange = { timeStr = formatInputTime(it) },
            label = { Text("Ora") },
            modifier = Modifier.fillMaxWidth().focusRequester(f2),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { f3.requestFocus() }),
            trailingIcon = {
                Row {
                    IconButton(onClick = { showTimePicker = true }) { Icon(Icons.Default.Timer, null) }
                    IconButton(onClick = { f3.requestFocus() }) { Icon(Icons.Default.Check, null) }
                }
            }
        )

        ShiftSelectionDropdown(selected = shift, options = availableShifts, onSelect = { shift = it })

        com.example.man_app.ui.fermate.AutoCompleteTextField(
            label = "Operatore",
            initialValue = operator,
            suggestions = operatorSuggestions,
            onTextChange = { operator = it.uppercase() },
            onConfirm = { operator = it.uppercase() },
            onCheck = { f4.requestFocus() },
            modifier = Modifier.focusRequester(f3)
        )

        com.example.man_app.ui.fermate.AutoCompleteTextField(
            label = "Articolo",
            initialValue = product,
            suggestions = products,
            onTextChange = { product = it },
            onConfirm = { product = it },
            onCheck = { f5.requestFocus() },
            modifier = Modifier.focusRequester(f4)
        )

        com.example.man_app.ui.fermate.AutoCompleteTextField(
            label = "Sigla",
            initialValue = sigla,
            suggestions = sigle,
            onTextChange = { sigla = it },
            onConfirm = { sigla = it },
            onCheck = { focusManager.clearFocus() },
            modifier = Modifier.focusRequester(f5)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {
                onStart(shift, operator, product, sigla, currentStartTime)
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("AVVIA TURNO")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
    if (showDatePicker) {
        CommonDatePicker(initialEpochMillis = currentStartTime, onDismiss = { showDatePicker = false }, onConfirm = { 
            val oldTime = Instant.fromEpochMilliseconds(currentStartTime).toLocalDateTime(TimeZone.currentSystemDefault())
            val newDate = Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            currentStartTime = LocalDateTime(newDate.year, newDate.monthNumber, newDate.dayOfMonth, oldTime.hour, oldTime.minute)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            showDatePicker = false 
        })
    }
    if (showTimePicker) {
        val dt = Instant.fromEpochMilliseconds(currentStartTime).toLocalDateTime(TimeZone.currentSystemDefault())
        CommonTimePicker(initialMinutes = dt.hour * 60 + dt.minute, onDismiss = { showTimePicker = false }, onConfirm = { 
            currentStartTime = LocalDateTime(dt.year, dt.monthNumber, dt.dayOfMonth, it / 60, it % 60)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            showTimePicker = false 
        })
    }
}

@Composable
fun ScaricatriceProductionTab(viewModel: ScaricatriceViewModel, report: ScaricatriceReport) {
    val kilnCars by viewModel.kilnCars.collectAsState()
    val kilnCarsHourly by viewModel.kilnCarsHourlyData.collectAsState()
    val scrapHourly by viewModel.scrapHourlyData.collectAsState()
    
    var showCarDialog by remember { mutableStateOf(false) }
    var carToEdit by remember { mutableStateOf<ScaricatriceKilnCar?>(null) }
    var showScrapDialog by remember { mutableStateOf(false) }

    // Local states for inputs to avoid laggy typing
    var p1Text by remember(report.id) { mutableStateOf(if (report.pacchi1aScelta == 0) "" else report.pacchi1aScelta.toString()) }
    var p2Text by remember(report.id) { mutableStateOf(if (report.pacchi2aScelta == 0) "" else report.pacchi2aScelta.toString()) }
    var nylonText by remember(report.id) { mutableStateOf(if (report.bobineNylon == 0) "" else report.bobineNylon.toString()) }
    var reggiaText by remember(report.id) { mutableStateOf(if (report.bobineReggia == 0) "" else report.bobineReggia.toString()) }

    // Update DB only when typing stops (debouncing)
    LaunchedEffect(p1Text, p2Text, nylonText, reggiaText) {
        kotlinx.coroutines.delay(500)
        val p1 = p1Text.toIntOrNull() ?: 0
        val p2 = p2Text.toIntOrNull() ?: 0
        val n = nylonText.toIntOrNull() ?: 0
        val r = reggiaText.toIntOrNull() ?: 0
        
        if (p1 != report.pacchi1aScelta || p2 != report.pacchi2aScelta || 
            n != report.bobineNylon || r != report.bobineReggia) {
            viewModel.updateReport(report.copy(
                pacchi1aScelta = p1, 
                pacchi2aScelta = p2, 
                bobineNylon = n, 
                bobineReggia = r
            ))
        }
    }

    Column(modifier = Modifier.padding(2.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // PRODUZIONE PACCHI
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("PRODUZIONE PACCHI", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = p1Text,
                        onValueChange = { if (it.all { c -> c.isDigit() }) p1Text = it },
                        label = { Text("1a Scelta") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = p2Text,
                        onValueChange = { if (it.all { c -> c.isDigit() }) p2Text = it },
                        label = { Text("2a Scelta") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("TOTALE PACCHI: ", fontWeight = FontWeight.Bold)
                    Text("${(p1Text.toIntOrNull() ?: 0) + (p2Text.toIntOrNull() ?: 0)}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }

                HorizontalDivider(thickness = 0.5.dp)
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nylonText,
                        onValueChange = { if (it.all { c -> c.isDigit() }) nylonText = it },
                        label = { Text("Bobine Nylon") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = reggiaText,
                        onValueChange = { if (it.all { c -> c.isDigit() }) reggiaText = it },
                        label = { Text("Bobine Reggia") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }

        // PRODUZIONE CARRI
        Card {
            Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Produzione Carri", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Button(
                    onClick = { showCarDialog = true },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("INIZIO CARRO", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                HourlyLineGraph(data = kilnCarsHourly.map { it.toDouble() }, label = "Carri/h", color = MaterialTheme.colorScheme.primary)
            }
        }

        Text("Storia Carri", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(start = 4.dp))
        kilnCars.reversed().forEach { car ->
            ScaricatriceKilnCarItem(
                car = car,
                article = report.productId,
                onEdit = { carToEdit = it },
                onDelete = { viewModel.deleteKilnCar(it) }
            )
        }
        
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Scarto PLC (minuti)", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showScrapDialog = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Add, "", modifier = Modifier.size(16.dp)) }
                }
                HourlyLineGraph(data = scrapHourly, label = "Scarto/h", color = Color(0xFFFFA000))
            }
        }
    }
    
    if (showCarDialog) {
        ScaricatriceKilnCarDialog(
            onDismiss = { showCarDialog = false },
            onConfirm = { cl, rO, s, sc, sa, scn, sb, h, la, t, w, f, ch, chN, ef, efN, stR, stC, stN, crR, crN, haR, haN, brR, brN, nt ->
                viewModel.startKilnCar(cl, rO, s, sc, sa, scn, sb, h, la, t, w, f, ch, chN, ef, efN, stR, stC, stN, crR, crN, haR, haN, brR, brN, nt)
                showCarDialog = false
            },
            defaultSigla = kilnCars.lastOrNull()?.sigla ?: report.sigla
        )
    }

    if (carToEdit != null) {
        ScaricatriceKilnCarDialog(
            car = carToEdit,
            onDismiss = { carToEdit = null },
            onConfirm = { cl, rO, s, sc, sa, scn, sb, h, la, t, w, f, ch, chN, ef, efN, stR, stC, stN, crR, crN, haR, haN, brR, brN, nt ->
                viewModel.updateKilnCar(carToEdit!!.copy(
                    isCleaned = cl, 
                    refractoriesOk = rO,
                    sigla = s,
                    scelta = sc,
                    soundAltoOk = sa, soundCentroOk = scn, soundBassoOk = sb,
                    height = h, width = la, thickness = t, weight = w,
                    firingRating = f,
                    chipsOk = ch, chipsNotes = chN,
                    efflorescenceRating = ef, efflorescenceNotes = efN,
                    stainsRating = stR, stainsColor = stC, stainsNotes = stN,
                    cracksRating = crR, cracksNotes = crN,
                    hairlinesRating = haR, hairlinesNotes = haN,
                    breaksRating = brR, breaksNotes = brN,
                    qualityNotes = nt
                ))
                carToEdit = null
            }
        )
    }

    if (showScrapDialog) ScrapDialog(onDismiss = { showScrapDialog = false }, onConfirm = viewModel::addScrapRecord)
}

@Composable
fun ScaricatriceQualityTab(viewModel: ScaricatriceViewModel, report: ScaricatriceReport) {
    val kilnCars by viewModel.kilnCars.collectAsState()
    var carToEdit by remember { mutableStateOf<ScaricatriceKilnCar?>(null) }

    Column(modifier = Modifier.padding(4.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // QUALITÀ PACCHI (Globale)
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f))) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("CONTROLLO QUALITÀ PACCHI", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Squadratura:", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        FilterChip(selected = report.pacchiSquadratura == "Buona", onClick = { viewModel.updateReport(report.copy(pacchiSquadratura = "Buona")) }, label = { Text("Buona", fontSize = 10.sp) })
                        FilterChip(selected = report.pacchiSquadratura == "Non buona", onClick = { viewModel.updateReport(report.copy(pacchiSquadratura = "Non buona")) }, label = { Text("Non buona", fontSize = 10.sp) })
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Imballo:", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        FilterChip(selected = report.pacchiImballo == "Buono", onClick = { viewModel.updateReport(report.copy(pacchiImballo = "Buono")) }, label = { Text("Buono", fontSize = 10.sp) })
                        FilterChip(selected = report.pacchiImballo == "Non buono", onClick = { viewModel.updateReport(report.copy(pacchiImballo = "Non buono")) }, label = { Text("Non buono", fontSize = 10.sp) })
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Centratura Pallet:", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Switch(checked = report.pacchiCentraturaPallet, onCheckedChange = { viewModel.updateReport(report.copy(pacchiCentraturaPallet = it)) }, modifier = Modifier.scale(0.7f))
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Etichetta presente/leggibile:", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Switch(checked = report.pacchiEtichettaOk, onCheckedChange = { viewModel.updateReport(report.copy(pacchiEtichettaOk = it)) }, modifier = Modifier.scale(0.7f))
                }
            }
        }

        // ANALISI PEZZO (Storico Carri)
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Analisi Pezzo (Storico Carri)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                kilnCars.reversed().take(5).forEach { car ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Carro ${formatTime(car.startTime)}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("H:${car.height} LA:${car.width} T:${car.thickness} | P:${car.weight}g", fontSize = 10.sp, color = Color.Gray)
                            if (car.scelta != null) {
                                Text("Scelta: ${car.scelta}", fontSize = 10.sp, color = if(car.scelta == "Scarto") Color.Red else Orange)
                            }
                        }
                        IconButton(onClick = { carToEdit = car }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.Edit, "", modifier = Modifier.size(16.dp))
                        }
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.2f))
                }
            }
        }
    }

    if (carToEdit != null) {
        ScaricatriceKilnCarDialog(
            car = carToEdit,
            onDismiss = { carToEdit = null },
            onConfirm = { cleaned, refrOk, s, sc, sAlto, sCentro, sBasso, h, la, t, w, firing, chips, chipsN, effl, efflN, stains, stainsC, stainsN, cracks, cracksN, hair, hairN, breaks, breaksN, notes ->
                viewModel.updateKilnCar(carToEdit!!.copy(
                    isCleaned = cleaned, 
                    refractoriesOk = refrOk,
                    sigla = s,
                    scelta = sc,
                    soundAltoOk = sAlto, soundCentroOk = sCentro, soundBassoOk = sBasso,
                    height = h, width = la, thickness = t, weight = w,
                    firingRating = firing,
                    chipsOk = chips, chipsNotes = chipsN,
                    efflorescenceRating = effl, efflorescenceNotes = efflN,
                    stainsRating = stains, stainsColor = stainsC, stainsNotes = stainsN,
                    cracksRating = cracks, cracksNotes = cracksN,
                    hairlinesRating = hair, hairlinesNotes = hairN,
                    breaksRating = breaks, breaksNotes = breaksN,
                    qualityNotes = notes
                ))
                carToEdit = null
            }
        )
    }
}

@Composable
fun ScaricatriceKilnCarItem(
    car: ScaricatriceKilnCar,
    article: String,
    onEdit: (ScaricatriceKilnCar) -> Unit,
    onDelete: (ScaricatriceKilnCar) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatTime(car.startTime),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(4.dp))
            Text("Art: $article", fontSize = 10.sp, color = Color.DarkGray)
            car.sigla?.let {
                Spacer(Modifier.width(4.dp))
                Text("($it)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            }
            
            Spacer(Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                StatusIndicator(label = "P", isOk = car.isCleaned)
                StatusIndicator(label = "R", isOk = car.refractoriesOk)
                if (!car.scelta.isNullOrBlank()) {
                    Badge(containerColor = if(car.scelta == "Scarto") Color.Red else Orange) {
                        Text(car.scelta ?: "", fontSize = 8.sp, color = Color.White)
                    }
                }
            }
            
            Spacer(Modifier.weight(1f))
            
            Row {
                IconButton(onClick = { onEdit(car) }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Edit, "", modifier = Modifier.size(14.dp), tint = Color.Gray)
                }
                IconButton(onClick = { onDelete(car) }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Delete, "", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(14.dp))
                }
            }
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.2f))
    }
}

@Composable
fun StatusIndicator(label: String, isOk: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            if (isOk) Icons.Default.CheckCircle else Icons.Default.Cancel,
            null,
            tint = if (isOk) DarkGreen else Color.Red,
            modifier = Modifier.size(10.dp)
        )
        Spacer(Modifier.width(2.dp))
        Text(label, fontSize = 9.sp, color = Color.Gray)
    }
}

@Composable
fun ScaricatriceKilnCarDialog(
    car: ScaricatriceKilnCar? = null,
    onDismiss: () -> Unit,
    onConfirm: (Boolean, Boolean, String?, String?, Boolean, Boolean, Boolean, Double, Double, Double, Double, String, Boolean, String, String, String, String, String, String, String, String, String, String, String, String, String) -> Unit,
    defaultSigla: String? = null
) {
    var cleaned by remember { mutableStateOf(car?.isCleaned ?: true) }
    var refrOk by remember { mutableStateOf(car?.refractoriesOk ?: true) }
    var sigla by remember { mutableStateOf(car?.sigla ?: defaultSigla ?: "") }
    var scelta by remember { mutableStateOf(car?.scelta ?: "") }
    
    // Qualità Pezzo
    var sAlto by remember { mutableStateOf(car?.soundAltoOk ?: true) }
    var sCentro by remember { mutableStateOf(car?.soundCentroOk ?: true) }
    var sBasso by remember { mutableStateOf(car?.soundBassoOk ?: true) }
    
    var h by remember { mutableStateOf(car?.height?.toString() ?: "0.0") }
    var la by remember { mutableStateOf(car?.width?.toString() ?: "0.0") }
    var t by remember { mutableStateOf(car?.thickness?.toString() ?: "0.0") }
    var w by remember { mutableStateOf(car?.weight?.toString() ?: "0.0") }
    
    var firing by remember { mutableStateOf(car?.firingRating ?: "Cotto") }
    
    var chips by remember { mutableStateOf(!car?.chipsOk.let { it ?: true }) } // UI uses Si/No, data uses chipsOk
    var chipsN by remember { mutableStateOf(car?.chipsNotes ?: "") }
    
    var effl by remember { mutableStateOf(car?.efflorescenceRating ?: "Nulla") }
    var efflN by remember { mutableStateOf(car?.efflorescenceNotes ?: "") }
    
    var stainsR by remember { mutableStateOf(car?.stainsRating ?: "Nulla") }
    var stainsC by remember { mutableStateOf(car?.stainsColor ?: "Verdi") }
    var stainsN by remember { mutableStateOf(car?.stainsNotes ?: "") }
    
    var cracksR by remember { mutableStateOf(car?.cracksRating ?: "Nulla") }
    var cracksN by remember { mutableStateOf(car?.cracksNotes ?: "") }
    
    var hairR by remember { mutableStateOf(car?.hairlinesRating ?: "Nulla") }
    var hairN by remember { mutableStateOf(car?.hairlinesNotes ?: "") }
    
    var breaksR by remember { mutableStateOf(car?.breaksRating ?: "Nulla") }
    var breaksN by remember { mutableStateOf(car?.breaksNotes ?: "") }
    
    var notes by remember { mutableStateOf(car?.qualityNotes ?: "") }

    val ratingOptions = listOf("Nulla", "Media", "Forte")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (car == null) "Dati Nuovo Carro" else "Modifica Carro") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text("Stato Carro", style = MaterialTheme.typography.titleSmall)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(cleaned, { cleaned = it })
                        Text("Pulizia OK", fontSize = 12.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(refrOk, { refrOk = it })
                        Text("Refrattari OK", fontSize = 12.sp)
                    }
                }

                HorizontalDivider()
                Text("Classificazione", style = MaterialTheme.typography.titleSmall)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = scelta == "", onClick = { scelta = "" }, label = { Text("1a Scelta") })
                    FilterChip(selected = scelta == "2a", onClick = { scelta = "2a" }, label = { Text("2a Scelta") })
                    FilterChip(selected = scelta == "Scarto", onClick = { scelta = "Scarto" }, label = { Text("Scarto") })
                }

                HorizontalDivider()
                Text("Qualità Pezzo", style = MaterialTheme.typography.titleSmall)
                
                Text("Suono", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                SoundToggle("Alto", sAlto) { sAlto = it }
                SoundToggle("Centro", sCentro) { sCentro = it }
                SoundToggle("Basso", sBasso) { sBasso = it }

                Spacer(modifier = Modifier.height(4.dp))
                Text("Misure", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(h, {h=it}, label={Text("H")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                    OutlinedTextField(la, {la=it}, label={Text("L")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(t, {t=it}, label={Text("T")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                    OutlinedTextField(w, {w=it}, label={Text("Peso")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                }

                RatingSelector("Cottura", listOf("Cotto", "Poco cotto"), firing) { firing = it }
                
                RatingSelectorWithNote("Sbeccature", listOf("No", "Si"), if(chips) "Si" else "No", { chips = it == "Si" }, chipsN) { chipsN = it }
                RatingSelectorWithNote("Efflorescenza", ratingOptions, effl, { effl = it }, efflN) { efflN = it }

                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text("Macchie", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        ratingOptions.forEach { r -> FilterChip(selected = stainsR == r, onClick = { stainsR = r }, label = { Text(r, fontSize = 10.sp) }) }
                    }
                    if (stainsR != "Nulla") {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            FilterChip(selected = stainsC == "Verdi", onClick = { stainsC = "Verdi" }, label = { Text("Verdi", fontSize = 10.sp) })
                            FilterChip(selected = stainsC == "Nere", onClick = { stainsC = "Nere" }, label = { Text("Nere", fontSize = 10.sp) })
                        }
                        OutlinedTextField(stainsN, { stainsN = it }, label = { Text("Note Macchie", fontSize = 9.sp) }, modifier = Modifier.fillMaxWidth().height(48.dp), textStyle = MaterialTheme.typography.bodySmall)
                    }
                }

                RatingSelectorWithNote("Fessurazioni", ratingOptions, cracksR, { cracksR = it }, cracksN) { cracksN = it }
                RatingSelectorWithNote("Filature", ratingOptions, hairR, { hairR = it }, hairN) { hairN = it }
                RatingSelectorWithNote("Rotture", ratingOptions, breaksR, { breaksR = it }, breaksN) { breaksN = it }

                OutlinedTextField(
                    value = sigla,
                    onValueChange = { sigla = it.uppercase() },
                    label = { Text("Sigla Carro") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Note Generali") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { 
                onConfirm(
                    cleaned, refrOk, sigla.ifBlank { null }, scelta.ifBlank { null },
                    sAlto, sCentro, sBasso,
                    h.toDoubleOrNull() ?: 0.0, la.toDoubleOrNull() ?: 0.0, t.toDoubleOrNull() ?: 0.0, w.toDoubleOrNull() ?: 0.0,
                    firing, !chips, chipsN, // chipsOk is inverse of "Si"
                    effl, efflN,
                    stainsR, stainsC, stainsN,
                    cracksR, cracksN,
                    hairR, hairN,
                    breaksR, breaksN,
                    notes
                ) 
            }) {
                Text("Salva")
            }
        },
        dismissButton = { TextButton(onDismiss) { Text("Annulla") } }
    )
}

@Composable
fun SoundToggle(label: String, isOk: Boolean, onToggle: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, fontSize = 12.sp, modifier = Modifier.width(60.dp))
        FilterChip(selected = isOk, onClick = { onToggle(true) }, label = { Text("OK", fontSize = 10.sp) })
        FilterChip(selected = !isOk, onClick = { onToggle(false) }, label = { Text("KO", fontSize = 10.sp) })
    }
}

@Composable
fun RatingSelectorWithNote(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            options.forEach { option ->
                FilterChip(
                    selected = selected == option,
                    onClick = { onSelect(option) },
                    label = { Text(option, fontSize = 10.sp) }
                )
            }
        }
        if (selected != options.first()) {
             OutlinedTextField(
                value = note,
                onValueChange = onNoteChange,
                label = { Text("Note $label", fontSize = 9.sp) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                textStyle = MaterialTheme.typography.bodySmall
            )
        }
    }
}
@Composable
fun RatingSelector(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            options.forEach { option ->
                FilterChip(
                    selected = selected == option,
                    onClick = { onSelect(option) },
                    label = { Text(option, fontSize = 10.sp) }
                )
            }
        }
    }
}
