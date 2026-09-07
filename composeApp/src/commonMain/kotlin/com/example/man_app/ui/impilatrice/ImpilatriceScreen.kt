package com.example.man_app.ui.impilatrice

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
import androidx.compose.ui.focus.FocusDirection
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
import com.example.man_app.util.*
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant

private val DarkGreen = Color(0xFF1B5E20)

@Composable
fun ImpilatriceScreen(
    viewModel: ImpilatriceViewModel,
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

    val mattonieraProducts by viewModel.getMattonieraProducts(lineId).collectAsState(emptyList())
    val mattonieraSigle by viewModel.getMattonieraSigle(lineId).collectAsState(emptyList())

    var selectedTab by remember { mutableStateOf(0) }
    var showEditShiftDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        if (activeReport == null) {
            ImpilatriceStartShiftScreen(
                initialData = preFillData,
                products = mattonieraProducts,
                sigle = mattonieraSigle,
                operatorSuggestions = operatorSuggestions,
                availableShifts = availableShifts.map { it.name },
                onStart = { shift, op, prod, sigla, cTime ->
                    viewModel.startShift(shift, op, prod, sigla, lineId, machineId, cTime)
                }
            )
        } else {
            ImpilatriceShiftHeader(
                report = activeReport!!,
                onEndShift = viewModel::closeShift,
                onChangeProduction = viewModel::changeProduction,
                onEditShift = { showEditShiftDialog = true }
            )
            TabRow(selectedTabIndex = selectedTab, modifier = Modifier.height(44.dp)) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) { Text("Qualità", fontSize = 13.sp) }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) { Text("Produzione", fontSize = 13.sp) }
            }
            when (selectedTab) {
                0 -> ImpilatriceQualityTab(viewModel = viewModel, report = activeReport!!)
                1 -> ImpilatriceProductionTab(viewModel = viewModel)
            }
            if (showEditShiftDialog) {
                ImpilatriceEditShiftDialog(
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

    // Error Dialog for validation
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
fun ImpilatriceShiftHeader(report: ImpilatriceReport, onEndShift: () -> Unit, onChangeProduction: () -> Unit, onEditShift: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "IMPILATRICE - Linea: ${report.lineId}", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
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
fun ImpilatriceEditShiftDialog(report: ImpilatriceReport, operatorSuggestions: List<String>, availableShifts: List<String>, onDismiss: () -> Unit, onConfirm: (ImpilatriceReport) -> Unit) {
    var operator by remember { mutableStateOf(report.operatorId) }; var product by remember { mutableStateOf(report.productId) }; var sigla by remember { mutableStateOf(report.sigla ?: "") }; var shift by remember { mutableStateOf(report.shift) }
    val focusManager = LocalFocusManager.current; var currentStartTime by remember { mutableStateOf(report.startTime) }
    val (f1, f2, f3, f4, f5) = remember { FocusRequester.createRefs() }
    var showDatePicker by remember { mutableStateOf(false) }; var showTimePicker by remember { mutableStateOf(false) }
    var dateStr by remember(currentStartTime) { mutableStateOf(formatDate(currentStartTime)) }
    var timeStr by remember(currentStartTime) { mutableStateOf(formatTime(currentStartTime)) }
    
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Modifica Turno") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
            OutlinedTextField(dateStr, { dateStr = formatInputDate(it) }, label={Text("Data")}, modifier=Modifier.fillMaxWidth().focusRequester(f1), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f2.requestFocus()}), trailingIcon={Row { IconButton({ showDatePicker = true }) { Icon(Icons.Default.DateRange, "") }; IconButton({ f2.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
            OutlinedTextField(timeStr, { timeStr = formatInputTime(it) }, label={Text("Ora")}, modifier=Modifier.fillMaxWidth().focusRequester(f2), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f3.requestFocus()}), trailingIcon={Row { IconButton({ showTimePicker = true }) { Icon(Icons.Default.Timer, "") }; IconButton({ f3.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
            
            com.example.man_app.ui.mattoniera.ShiftSelectionDropdown(selected = shift, options = availableShifts, onSelect = { shift = it })

            com.example.man_app.ui.fermate.AutoCompleteTextField(
                label = "Op",
                initialValue = operator,
                suggestions = operatorSuggestions,
                onTextChange = { operator = it.uppercase() },
                onConfirm = { operator = it.uppercase() },
                onCheck = { f4.requestFocus() },
                modifier = Modifier.focusRequester(f3)
            )
            OutlinedTextField(product, { product = it.uppercase() }, label={Text("Art")}, modifier=Modifier.fillMaxWidth().focusRequester(f4), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f5.requestFocus()}), trailingIcon={IconButton({f5.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(sigla, { sigla = it.uppercase() }, label={Text("Sigla")}, modifier=Modifier.fillMaxWidth().focusRequester(f5), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Done), keyboardActions=KeyboardActions(onDone={focusManager.clearFocus()}), trailingIcon={IconButton({focusManager.clearFocus()}){Icon(Icons.Default.Check,"")}})
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
fun ImpilatriceStartShiftScreen(
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
        Text("Avvio Impilatrice", style = MaterialTheme.typography.titleLarge)
        
        OutlinedTextField(
            value = dateStr,
            onValueChange = { dateStr = formatInputDate(it) },
            label = { Text("Data") },
            modifier = Modifier.fillMaxWidth().focusRequester(f1),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { f2.requestFocus() }),
            trailingIcon = { Row { IconButton({ showDatePicker = true }) { Icon(Icons.Default.DateRange, "") }; IconButton({ f2.requestFocus() }) { Icon(Icons.Default.Check, "") } } }
        )

        OutlinedTextField(
            value = timeStr,
            onValueChange = { timeStr = formatInputTime(it) },
            label = { Text("Ora") },
            modifier = Modifier.fillMaxWidth().focusRequester(f2),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { f3.requestFocus() }),
            trailingIcon = { Row { IconButton({ showTimePicker = true }) { Icon(Icons.Default.Timer, "") }; IconButton({ f3.requestFocus() }) { Icon(Icons.Default.Check, "") } } }
        )

        com.example.man_app.ui.mattoniera.ShiftSelectionDropdown(selected = shift, options = availableShifts, onSelect = { shift = it })

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
fun ImpilatriceQualityTab(viewModel: ImpilatriceViewModel, report: ImpilatriceReport) {
    val kilnCars by viewModel.kilnCars.collectAsState()
    var carToEdit by remember { mutableStateOf<ImpilatriceKilnCar?>(null) }

    Column(modifier = Modifier.padding(4.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Card {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Analisi Gabbia", style = MaterialTheme.typography.titleSmall)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Materiale distribuito:", fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        FilterChip(
                            selected = report.cageMaterialDistribution == "Bene",
                            onClick = { viewModel.updateReport(report.copy(cageMaterialDistribution = "Bene")) },
                            label = { Text("Bene", fontSize = 10.sp) }
                        )
                        FilterChip(
                            selected = report.cageMaterialDistribution == "Male",
                            onClick = { viewModel.updateReport(report.copy(cageMaterialDistribution = "Male")) },
                            label = { Text("Male", fontSize = 10.sp) }
                        )
                    }
                }
                
                OutlinedTextField(
                    value = report.cageNotes,
                    onValueChange = { viewModel.updateReport(report.copy(cageNotes = it)) },
                    label = { Text("Note Gabbia", fontSize = 10.sp) },
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    textStyle = MaterialTheme.typography.bodySmall
                )
            }
        }

        Card {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Analisi Pezzo (Storico Carri)", style = MaterialTheme.typography.titleSmall)
                kilnCars.reversed().take(5).forEach { car ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Carro ${formatTime(car.startTime)}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("H:${car.height} LA:${car.width} T:${car.thickness} | P:${car.weight}g", fontSize = 10.sp, color = Color.Gray)
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
        KilnCarDialog(
            car = carToEdit,
            onDismiss = { carToEdit = null },
            onConfirm = { cleaned, load, refrOk, newStart, newEnd, h, la, t, w, dOk, drying, chips, cracks, hair, breaks, notes, s ->
                viewModel.updateKilnCar(carToEdit!!.copy(
                    isCleaned = cleaned, 
                    loadPercentage = load, 
                    refractoriesOk = refrOk,
                    startTime = newStart ?: carToEdit!!.startTime,
                    endTime = newEnd ?: carToEdit!!.endTime,
                    height = h, width = la, thickness = t, weight = w, isDiagonalOk = dOk,
                    dryingRating = drying, chipsRating = chips, cracksRating = cracks,
                    hairlinesRating = hair, breaksRating = breaks, qualityNotes = notes,
                    sigla = s
                ))
                carToEdit = null
            }
        )
    }
}

@Composable
fun ImpilatriceProductionTab(viewModel: ImpilatriceViewModel) {
    val report by viewModel.activeReport.collectAsState()
    val kilnCars by viewModel.kilnCars.collectAsState()
    val kilnCarsHourly by viewModel.kilnCarsHourlyData.collectAsState()
    val scrapHourly by viewModel.scrapHourlyData.collectAsState()
    val cageEvents by viewModel.cageEvents.collectAsState()
    val cagesHourly by viewModel.cagesHourlyData.collectAsState()
    
    var showCarDialog by remember { mutableStateOf(false) }
    var carToEdit by remember { mutableStateOf<ImpilatriceKilnCar?>(null) }
    var showScrapDialog by remember { mutableStateOf(false) }
    var showCageHistory by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(2.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        /*
        Card {
            Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Gabbie Prodotte", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showCageHistory = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.AutoMirrored.Filled.List, "", modifier = Modifier.size(16.dp)) }
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = viewModel::removeCage, colors = ButtonDefaults.buttonColors(containerColor = Color.Red), modifier = Modifier.size(36.dp), contentPadding = PaddingValues(0.dp)) { Text("-1", fontSize = 12.sp) }
                    Text(report?.cagesProduced?.toString() ?: "0", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Button(onClick = viewModel::addCage, modifier = Modifier.size(36.dp), contentPadding = PaddingValues(0.dp)) { Text("+1", fontSize = 12.sp) }
                }
                HourlyLineGraph(data = cagesHourly.map { it.toDouble() }, label = "Gabbie/h", color = MaterialTheme.colorScheme.primary)
            }
        }
        */

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
            KilnCarHistoryItem(
                car = car,
                article = report?.productId ?: "",
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
        KilnCarDialog(
            onDismiss = { showCarDialog = false },
            onConfirm = { cleaned, load, refrOk, _, _, h, la, t, w, dOk, drying, chips, cracks, hair, breaks, notes, s ->
                viewModel.finishKilnCar(cleaned, load, refrOk, h, la, t, w, dOk, drying, chips, cracks, hair, breaks, notes, s)
                showCarDialog = false
            },
            defaultSigla = kilnCars.lastOrNull()?.sigla ?: report?.sigla
        )
    }

    if (carToEdit != null) {
        KilnCarDialog(
            car = carToEdit,
            onDismiss = { carToEdit = null },
            onConfirm = { cleaned, load, refrOk, newStart, newEnd, h, la, t, w, dOk, drying, chips, cracks, hair, breaks, notes, s ->
                viewModel.updateKilnCar(carToEdit!!.copy(
                    isCleaned = cleaned, 
                    loadPercentage = load, 
                    refractoriesOk = refrOk,
                    startTime = newStart ?: carToEdit!!.startTime,
                    endTime = newEnd ?: carToEdit!!.endTime,
                    height = h, width = la, thickness = t, weight = w, isDiagonalOk = dOk,
                    dryingRating = drying, chipsRating = chips, cracksRating = cracks,
                    hairlinesRating = hair, breaksRating = breaks, qualityNotes = notes,
                    sigla = s
                ))
                carToEdit = null
            }
        )
    }

    if (showScrapDialog) ScrapDialog(onDismiss = { showScrapDialog = false }, onConfirm = viewModel::addScrapRecord)
    // if (showCageHistory) EventHistoryDialog(title = "Storia Gabbie", events = cageEvents.map { it.timestamp to "${if(it.change > 0) "+1" else "-1"} - ${report?.productId ?: ""}" }, onDismiss = { showCageHistory = false }, onDelete = { ts -> cageEvents.find { it.timestamp == ts }?.let { viewModel.deleteCageEvent(it) } }, onUpdate = { ts, nTs -> cageEvents.find { it.timestamp == ts }?.let { viewModel.updateCageEvent(it.copy(timestamp = nTs)) } })
}

@Composable
fun KilnCarHistoryItem(
    car: ImpilatriceKilnCar,
    article: String,
    onEdit: (ImpilatriceKilnCar) -> Unit,
    onDelete: (ImpilatriceKilnCar) -> Unit
) {
    val duration = (car.endTime ?: com.example.man_app.util.getNowMillis()) - car.startTime
    val durationMin = duration / 60000

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${formatTime(car.startTime)}-${car.endTime?.let { formatTime(it) } ?: "..."}",
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
            Spacer(Modifier.width(6.dp))
            Text(
                text = "${durationMin}m",
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
            Spacer(Modifier.width(6.dp))
            Text(text = "${car.loadPercentage}%", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                StatusIndicator(label = "P", isOk = car.isCleaned)
                StatusIndicator(label = "R", isOk = car.refractoriesOk)
            }
            
            Spacer(Modifier.weight(1f))
            
            Row {
                IconButton(onClick = { onEdit(car) }, modifier = Modifier.size(20.dp)) {
                    Icon(Icons.Default.Edit, "", modifier = Modifier.size(12.dp), tint = Color.Gray)
                }
                IconButton(onClick = { onDelete(car) }, modifier = Modifier.size(20.dp)) {
                    Icon(Icons.Default.Delete, "", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(12.dp))
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
fun KilnCarDialog(
    car: ImpilatriceKilnCar? = null,
    onDismiss: () -> Unit,
    onConfirm: (Boolean, Int, Boolean, Long?, Long?, Double, Double, Double, Double, Boolean, String, String, String, String, String, String, String?) -> Unit,
    defaultSigla: String? = null
) {
    var cleaned by remember { mutableStateOf(car?.isCleaned ?: true) }
    var load by remember { mutableStateOf(car?.loadPercentage?.toString() ?: "100") }
    var refrOk by remember { mutableStateOf(car?.refractoriesOk ?: true) }
    var startTime by remember { mutableStateOf(car?.startTime) }
    var endTime by remember { mutableStateOf(car?.endTime) }
    var sigla by remember { mutableStateOf(car?.sigla ?: defaultSigla ?: "") }

    // Analisi Pezzo
    var h by remember { mutableStateOf(car?.height?.toString() ?: "0.0") }
    var la by remember { mutableStateOf(car?.width?.toString() ?: "0.0") }
    var t by remember { mutableStateOf(car?.thickness?.toString() ?: "0.0") }
    var w by remember { mutableStateOf(car?.weight?.toString() ?: "0.0") }
    var dOk by remember { mutableStateOf(car?.isDiagonalOk ?: true) }
    
    var drying by remember { mutableStateOf(car?.dryingRating ?: "Essiccato") }
    var chips by remember { mutableStateOf(car?.chipsRating ?: "Nessuna") }
    var cracks by remember { mutableStateOf(car?.cracksRating ?: "Nessuna") }
    var hair by remember { mutableStateOf(car?.hairlinesRating ?: "Nessuna") }
    var breaks by remember { mutableStateOf(car?.breaksRating ?: "Nessuna") }
    var notes by remember { mutableStateOf(car?.qualityNotes ?: "") }

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }
    val ratingOptions = listOf("Nessuna", "Poche", "Molte")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (car == null) "Dati Nuovo Carro" else "Modifica Carro") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (car != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Inizio: ${formatTime(startTime ?: 0)}", modifier = Modifier.weight(1f))
                        IconButton(onClick = { showStartPicker = true }) { Icon(Icons.Default.Timer, "") }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Fine: ${endTime?.let { formatTime(it) } ?: "-"}", modifier = Modifier.weight(1f))
                        IconButton(onClick = { showEndPicker = true }) { Icon(Icons.Default.Timer, "") }
                    }
                }

                Text("Stato Carro", style = MaterialTheme.typography.titleSmall)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = cleaned, onCheckedChange = { cleaned = it })
                        Text("Pulizia OK", fontSize = 12.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(checked = refrOk, onCheckedChange = { refrOk = it })
                        Text("Refrattari OK", fontSize = 12.sp)
                    }
                }
                OutlinedTextField(
                    value = load,
                    onValueChange = { if (it.all { c -> c.isDigit() }) load = it },
                    label = { Text("% Carico") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider()
                Text("Analisi Pezzo", style = MaterialTheme.typography.titleSmall)
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(h, {h=it}, label={Text("H")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                    OutlinedTextField(la, {la=it}, label={Text("LA")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(t, {t=it}, label={Text("T")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                    OutlinedTextField(w, {w=it}, label={Text("Peso")}, modifier=Modifier.weight(1f), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(dOk, {dOk=it})
                    Text("Squadratura OK")
                }

                RatingSelector("Essiccazione", listOf("Essiccato", "Umido"), drying) { drying = it }
                RatingSelector("Sbeccature", ratingOptions, chips) { chips = it }
                RatingSelector("Fessurazione", ratingOptions, cracks) { cracks = it }
                RatingSelector("Filature", ratingOptions, hair) { hair = it }
                RatingSelector("Rotture", ratingOptions, breaks) { breaks = it }

                OutlinedTextField(
                    value = sigla,
                    onValueChange = { sigla = it.uppercase() },
                    label = { Text("Sigla Carro") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Note Qualità") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { 
                onConfirm(
                    cleaned, load.toIntOrNull() ?: 100, refrOk, startTime, endTime,
                    h.toDoubleOrNull() ?: 0.0, la.toDoubleOrNull() ?: 0.0, t.toDoubleOrNull() ?: 0.0, w.toDoubleOrNull() ?: 0.0, dOk,
                    drying, chips, cracks, hair, breaks, notes, sigla.ifBlank { null }
                ) 
            }) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annulla") }
        }
    )

    if (showStartPicker) {
        val dt = Instant.fromEpochMilliseconds(startTime ?: com.example.man_app.util.getNowMillis()).toLocalDateTime(TimeZone.currentSystemDefault())
        CommonTimePicker(initialMinutes = dt.hour * 60 + dt.minute, onDismiss = { showStartPicker = false }, onConfirm = { 
            startTime = LocalDateTime(dt.year, dt.monthNumber, dt.dayOfMonth, it / 60, it % 60)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            showStartPicker = false 
        })
    }
    if (showEndPicker) {
        val dt = Instant.fromEpochMilliseconds(endTime ?: com.example.man_app.util.getNowMillis()).toLocalDateTime(TimeZone.currentSystemDefault())
        CommonTimePicker(initialMinutes = dt.hour * 60 + dt.minute, onDismiss = { showEndPicker = false }, onConfirm = { 
            endTime = LocalDateTime(dt.year, dt.monthNumber, dt.dayOfMonth, it / 60, it % 60)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            showEndPicker = false 
        })
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
