package com.example.man_app.ui.mattoniera

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.man_app.data.model.*
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
fun MattonieraScreen(
    viewModel: MattonieraViewModel,
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

    var selectedTab by remember { mutableStateOf(0) }
    var showEditShiftDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        if (activeReport == null) {
            StartShiftScreen(
                initialData = preFillData,
                operatorSuggestions = operatorSuggestions,
                availableShifts = availableShifts.map { it.name },
                onStart = { shift, op, prod, die, sigla, cTime ->
                    viewModel.startShift(shift, op, prod, die, sigla, lineId, machineId, cTime)
                }
            )
        } else {
            ShiftHeader(
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
                0 -> QualityTab(viewModel = viewModel, report = activeReport!!)
                1 -> ProductionTab(viewModel = viewModel, report = activeReport!!)
            }
            if (showEditShiftDialog) {
                EditShiftDialog(
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

    // Error Snackbar
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
fun ShiftHeader(report: MattonieraReport, onEndShift: () -> Unit, onChangeProduction: () -> Unit, onEditShift: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "MATTONIERA - Linea: ${report.lineId}", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.weight(1f))
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
fun EditShiftDialog(report: MattonieraReport, operatorSuggestions: List<String>, availableShifts: List<String>, onDismiss: () -> Unit, onConfirm: (MattonieraReport) -> Unit) {
    var operator by remember { mutableStateOf(report.operatorId) }; var product by remember { mutableStateOf(report.productId) }; var die by remember { mutableStateOf(report.dieId) }; var sigla by remember { mutableStateOf(report.sigla ?: "") }; var shift by remember { mutableStateOf(report.shift) }
    val focusManager = LocalFocusManager.current; var currentStartTime by remember { mutableStateOf(report.startTime) }; val (f1, f2, f3, f4, f5, f6) = remember { FocusRequester.createRefs() }
    var showDatePicker by remember { mutableStateOf(false) }; var showTimePicker by remember { mutableStateOf(false) }
    var dateStr by remember(currentStartTime) { mutableStateOf(formatDate(currentStartTime)) }; var timeStr by remember(currentStartTime) { mutableStateOf(formatTime(currentStartTime)) }
    
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Modifica Turno") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.verticalScroll(rememberScrollState())) {
            OutlinedTextField(dateStr, { dateStr = formatInputDate(it) }, label={Text("Data")}, modifier=Modifier.fillMaxWidth().focusRequester(f1), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f2.requestFocus()}), trailingIcon={Row { IconButton({ showDatePicker = true }) { Icon(Icons.Default.DateRange, "") }; IconButton({ f2.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
            OutlinedTextField(timeStr, { timeStr = formatInputTime(it) }, label={Text("Ora")}, modifier=Modifier.fillMaxWidth().focusRequester(f2), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f3.requestFocus()}), trailingIcon={Row { IconButton({ showTimePicker = true }) { Icon(Icons.Default.Timer, "") }; IconButton({ f3.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
            
            ShiftSelectionDropdown(selected = shift, options = availableShifts, onSelect = { shift = it })

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
            OutlinedTextField(die, { die = it.uppercase() }, label={Text("Filiera")}, modifier=Modifier.focusRequester(f5), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f6.requestFocus()}), trailingIcon={IconButton({f6.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(sigla, { sigla = it.uppercase() }, label={Text("Sigla")}, modifier=Modifier.fillMaxWidth().focusRequester(f6), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Done), keyboardActions=KeyboardActions(onDone={focusManager.clearFocus()}), trailingIcon={IconButton({focusManager.clearFocus()}){Icon(Icons.Default.Check,"")}})
        }
    }, confirmButton = { Button(onClick = { onConfirm(report.copy(operatorId = operator, productId = product, dieId = die, sigla = sigla, startTime = currentStartTime, shift = shift)) }) { Text("Salva") } })

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
fun StartShiftScreen(initialData: ShiftPreFillData, operatorSuggestions: List<String>, availableShifts: List<String>, onStart: (String, String, String, String, String, Long?) -> Unit) {
    var operator by remember(initialData) { mutableStateOf(initialData.operator) }
    var product by remember(initialData) { mutableStateOf(initialData.product) }
    var die by remember(initialData) { mutableStateOf(initialData.die) }
    var sigla by remember(initialData) { mutableStateOf(initialData.sigla) }
    var shift by remember(initialData) { mutableStateOf(initialData.shift) }
    
    val focusManager = LocalFocusManager.current; var currentStartTime by remember { mutableStateOf(com.example.man_app.util.getNowMillis()) }; val (f1, f2, f3, f4, f5, f6) = remember { FocusRequester.createRefs() }
    var showDatePicker by remember { mutableStateOf(false) }; var showTimePicker by remember { mutableStateOf(false) }
    var dateStr by remember(currentStartTime) { mutableStateOf(formatDate(currentStartTime)) }; var timeStr by remember(currentStartTime) { mutableStateOf(formatTime(currentStartTime)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp) // Reduced from 6
    ) {
        Text("Avvio Rapportino", style = MaterialTheme.typography.titleMedium) // Smaller
        OutlinedTextField(dateStr, { dateStr = formatInputDate(it) }, label = { Text("Data") }, modifier = Modifier.fillMaxWidth().focusRequester(f1), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next), keyboardActions = KeyboardActions(onNext = { f2.requestFocus() }), trailingIcon = { Row { IconButton({ showDatePicker = true }) { Icon(Icons.Default.DateRange, "") }; IconButton({ f2.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
        OutlinedTextField(timeStr, { timeStr = formatInputTime(it) }, label = { Text("Ora") }, modifier = Modifier.fillMaxWidth().focusRequester(f2), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next), keyboardActions = KeyboardActions(onNext = { f3.requestFocus() }), trailingIcon = { Row { IconButton({ showTimePicker = true }) { Icon(Icons.Default.Timer, "") }; IconButton({ f3.requestFocus() }) { Icon(Icons.Default.Check, "") } } })
        
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
        OutlinedTextField(product, { product = it.uppercase() }, label={Text("Art")}, modifier=Modifier.fillMaxWidth().focusRequester(f4), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f5.requestFocus()}), trailingIcon={IconButton({f5.requestFocus()}){Icon(Icons.Default.Check,"")}})
        OutlinedTextField(die, { die = it.uppercase() }, label={Text("Filiera")}, modifier=Modifier.fillMaxWidth().focusRequester(f5), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f6.requestFocus()}), trailingIcon={IconButton({f6.requestFocus()}){Icon(Icons.Default.Check,"")}})
        OutlinedTextField(sigla, { sigla = it.uppercase() }, label={Text("Sigla")}, modifier=Modifier.fillMaxWidth().focusRequester(f6), keyboardOptions=KeyboardOptions(imeAction=ImeAction.Done), keyboardActions=KeyboardActions(onDone={focusManager.clearFocus()}), trailingIcon={IconButton({focusManager.clearFocus()}){Icon(Icons.Default.Check,"")}})
        
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { onStart(shift, operator, product, die, sigla, currentStartTime) },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) { 
            Text("AVVIA TURNO") 
        }
        Spacer(modifier = Modifier.height(16.dp)) // Extra space at bottom of scroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftSelectionDropdown(selected: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val displayOptions = if (options.isEmpty()) listOf("0", "1", "2", "3") else options

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }, modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = "Turno $selected",
            onValueChange = {},
            readOnly = true,
            label = { Text("Turno") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true).fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            displayOptions.forEach { option ->
                DropdownMenuItem(text = { Text("Turno $option") }, onClick = { onSelect(option); expanded = false })
            }
        }
    }
}

@Composable
fun QualityTab(viewModel: MattonieraViewModel, report: MattonieraReport) {
    val exitChecks by viewModel.exitChecks.collectAsState(); val measurements by viewModel.measurements.collectAsState(); val cleanings by viewModel.rollerCleanings.collectAsState(); val cloggings by viewModel.cloggingRemovals.collectAsState(); val hourlyData by viewModel.cloggingHourlyData.collectAsState(); val cloggingReasons by viewModel.cloggingReasons.collectAsState(); val cloggingStats by viewModel.cloggingStats.collectAsState()
    var showExitDialog by remember { mutableStateOf(false) }; var showMeasureDialog by remember { mutableStateOf(false) }; var showCloggingDialog by remember { mutableStateOf(false) }; var showCloggingHistory by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(2.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Text("Dima", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(modifier = Modifier.scale(0.7f), checked = report.dimaCheckStart, onCheckedChange = { viewModel.updateReport(report.copy(dimaCheckStart = it)) })
                    Text("Inizio", fontSize = 10.sp); Spacer(Modifier.width(4.dp)); Checkbox(modifier = Modifier.scale(0.7f), checked = report.dimaCheckMid, onCheckedChange = { viewModel.updateReport(report.copy(dimaCheckMid = it)) }); Text("Metà", fontSize = 10.sp)
                }
            }
        }
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Controllo Uscita", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showExitDialog = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Add, "", modifier = Modifier.size(16.dp)) }
                }
                exitChecks.takeLast(3).forEach { check -> Row(verticalAlignment = Alignment.CenterVertically) { Icon(if (check.isOk) Icons.Default.Check else Icons.Default.Close, "", tint = if (check.isOk) DarkGreen else Color.Red, modifier = Modifier.size(10.dp)); Text(" ${formatTime(check.timestamp)} T:${check.temperature}° P:${check.pressure}", fontSize = 9.sp) } }
            }
        }
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Filature", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showCloggingHistory = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.AutoMirrored.Filled.List, "", modifier = Modifier.size(16.dp)) }
                    IconButton({ viewModel.addRollerCleaning() }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Refresh, "", modifier = Modifier.size(16.dp)) }
                    IconButton({ showCloggingDialog = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Add, "", modifier = Modifier.size(16.dp)) }
                }
                Row { DotGrid(cleanings.size); Spacer(Modifier.width(8.dp)); DotGrid(cloggings.size) }
                HourlyLineGraph(data = hourlyData.map { it.toDouble() }, label = "Filature/h", color = Color.Red)
                if (cloggingStats.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    cloggingStats.forEach { (reason, percentage) ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(reason, style = MaterialTheme.typography.bodySmall, fontSize = 9.sp); Text("${percentage.toInt()}%", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                        }
                    }
                }
            }
        }
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Misure e Peso", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showMeasureDialog = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Add, "", modifier = Modifier.size(16.dp)) }
                }
                measurements.takeLast(2).forEach { m -> Row(verticalAlignment = Alignment.CenterVertically) { Icon(if (m.isDiagonalOk) Icons.Default.Check else Icons.Default.Close, "", tint = if (m.isDiagonalOk) DarkGreen else Color.Red, modifier = Modifier.size(10.dp)); Text(" ${formatTime(m.timestamp)}: H:${m.height} LA:${m.width} T:${m.thickness} | P:${m.weight}g", fontSize = 9.sp) } }
            }
        }
    }
    if (showExitDialog) ExitCheckDialog(onDismiss = { showExitDialog = false }, onConfirm = viewModel::addExitCheck)
    if (showMeasureDialog) MeasurementDialog(onDismiss = { showMeasureDialog = false }, onConfirm = viewModel::addMeasurement)
    if (showCloggingDialog) CloggingDialog(reasons = cloggingReasons, onDismiss = { showCloggingDialog = false }, onConfirm = viewModel::addCloggingRemoval)
    if (showCloggingHistory) EventHistoryDialog(title = "Storia Filature", events = cloggings.map { it.timestamp to it.reason }, onDismiss = { showCloggingHistory = false }, onDelete = { ts -> cloggings.find { it.timestamp == ts }?.let { viewModel.deleteCloggingRemoval(it) } }, onUpdate = { ts, nTs -> cloggings.find { it.timestamp == ts }?.let { viewModel.updateCloggingRemoval(it.copy(timestamp = nTs)) } })
}

@Composable
fun HourlyLineGraph(data: List<Double>, label: String, color: Color) {
    val maxVal = data.maxOrNull()?.coerceAtLeast(1.0) ?: 1.0
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontSize = 8.sp, color = Color.Gray)
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                data.forEach { value -> if (value > 0) { Text(text = value.toInt().toString(), fontSize = 7.sp, color = color, fontWeight = FontWeight.Bold); Text(text = "|", fontSize = 7.sp, color = Color.LightGray) } }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(35.dp).padding(top = 2.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stepX = size.width / 7; val path = Path()
                data.forEachIndexed { i, value ->
                    val x = i * stepX; val y = size.height - (value.toFloat() / maxVal.toFloat() * (size.height - 4.dp.toPx())) - 2.dp.toPx()
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                    drawCircle(color, 2.dp.toPx(), Offset(x, y))
                }
                drawPath(path, color, style = Stroke(width = 1.dp.toPx()))
            }
        }
    }
}

@Composable
fun DotGrid(count: Int) {
    val purples = count / 100; val reds = (count % 100) / 10; val blues = count % 10
    Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) { repeat(purples) { Dot(Color(0xFF9C27B0), 14.dp) }; repeat(reds) { Dot(Color.Red, 10.dp) }; repeat(blues) { Dot(Color.Blue, 6.dp) } }
}

@Composable
fun Dot(color: Color, size: androidx.compose.ui.unit.Dp) { Canvas(modifier = Modifier.size(size)) { drawCircle(color) } }

@Composable
fun ProductionTab(viewModel: MattonieraViewModel, report: MattonieraReport) {
    val prodData by viewModel.productionDataList.collectAsState(); val scrapHourly by viewModel.scrapHourlyData.collectAsState(); val cagesHourly by viewModel.cagesHourlyData.collectAsState(); val cageEvents by viewModel.cageEvents.collectAsState()
    var showProdDialog by remember { mutableStateOf(false) }; var showScrapDialog by remember { mutableStateOf(false) }; var showCageHistory by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(2.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Card {
            Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Gabbie Prodotte", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showCageHistory = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.AutoMirrored.Filled.List, "", modifier = Modifier.size(16.dp)) }
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = viewModel::removeCage, colors = ButtonDefaults.buttonColors(containerColor = Color.Red), modifier = Modifier.size(36.dp), contentPadding = PaddingValues(0.dp)) { Text("-1", fontSize = 12.sp) }
                    Text(report.cagesProduced.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Button(onClick = viewModel::addCage, modifier = Modifier.size(36.dp), contentPadding = PaddingValues(0.dp)) { Text("+1", fontSize = 12.sp) }
                }
                HourlyLineGraph(data = cagesHourly.map { it.toDouble() }, label = "Gabbie/h", color = MaterialTheme.colorScheme.primary)
            }
        }
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Dosatore / Assorbimento", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showProdDialog = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Add, "", modifier = Modifier.size(16.dp)) }
                }
                prodData.takeLast(3).forEach { d -> Row(verticalAlignment = Alignment.CenterVertically) { Text("${formatTime(d.timestamp)} ", fontSize = 9.sp); Icon(if (d.isDoserOk) Icons.Default.Check else Icons.Default.Close, "", tint = if (d.isDoserOk) DarkGreen else Color.Red, modifier = Modifier.size(10.dp)); Text(" Dos:${d.doserGiri} | ", fontSize = 9.sp); Icon(if (d.isAbsorptionOk) Icons.Default.Check else Icons.Default.Close, "", tint = if (d.isAbsorptionOk) DarkGreen else Color.Red, modifier = Modifier.size(10.dp)); Text(" Ass:${d.absorptionAmpere}A", fontSize = 9.sp) } }
            }
        }
        Card {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Scarto PLC", style = MaterialTheme.typography.titleSmall, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    IconButton({ showScrapDialog = true }, modifier = Modifier.size(24.dp)) { Icon(Icons.Default.Add, "", modifier = Modifier.size(16.dp)) }
                }
                HourlyLineGraph(data = scrapHourly, label = "Scarto/h", color = Color(0xFFFFA000))
            }
        }
    }
    if (showProdDialog) ProductionDataDialog(onDismiss = { showProdDialog = false }, onConfirm = viewModel::addProductionData)
    if (showScrapDialog) ScrapDialog(onDismiss = { showScrapDialog = false }, onConfirm = viewModel::addScrapRecord)
    if (showCageHistory) EventHistoryDialog(title = "Storia Gabbie", events = cageEvents.map { it.timestamp to "${if(it.change > 0) "+1" else "-1"} - ${report.productId}" }, onDismiss = { showCageHistory = false }, onDelete = { ts -> cageEvents.find { it.timestamp == ts }?.let { viewModel.deleteCageEvent(it) } }, onUpdate = { ts, nTs -> cageEvents.find { it.timestamp == ts }?.let { viewModel.updateCageEvent(it.copy(timestamp = nTs)) } })
}

@Composable
fun EventHistoryDialog(title: String, events: List<Pair<Long, String>>, onDismiss: () -> Unit, onDelete: (Long) -> Unit, onUpdate: (Long, Long) -> Unit) {
    var showTimePickerFor by remember { mutableStateOf<Long?>(null) }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(title) }, text = {
        Column(modifier = Modifier.heightIn(max = 400.dp).verticalScroll(rememberScrollState())) {
            events.reversed().forEach { (ts, label) ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Text("${formatTime(ts)} ($label)", modifier = Modifier.weight(1f), fontSize = 14.sp)
                    IconButton({ showTimePickerFor = ts }) { Icon(Icons.Default.Timer, "", modifier = Modifier.size(18.dp)) }
                    IconButton({ onDelete(ts) }) { Icon(Icons.Default.Delete, "", tint = Color.Red, modifier = Modifier.size(18.dp)) }
                }
            }
        }
    }, confirmButton = { TextButton(onDismiss) { Text("Chiudi") } })

    if (showTimePickerFor != null) {
        val dt = Instant.fromEpochMilliseconds(showTimePickerFor!!).toLocalDateTime(TimeZone.currentSystemDefault())
        CommonTimePicker(initialMinutes = dt.hour * 60 + dt.minute, onDismiss = { showTimePickerFor = null }, onConfirm = { 
            val newTs = LocalDateTime(dt.year, dt.monthNumber, dt.dayOfMonth, it / 60, it % 60)
                .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            onUpdate(showTimePickerFor!!, newTs)
            showTimePickerFor = null 
        })
    }
}

@Composable
fun ProductionDataDialog(onDismiss: () -> Unit, onConfirm: (Int, Boolean, Int, Boolean) -> Unit) {
    var giri by remember { mutableStateOf("") }; var giriOk by remember { mutableStateOf(true) }; var amp by remember { mutableStateOf("") }; var ampOk by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current; val (f1, f2) = remember { FocusRequester.createRefs() }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Dati Macchina") }, text = {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) { OutlinedTextField(giri, {if(it.all{c->c.isDigit()})giri=it}, label={Text("Giri")}, modifier=Modifier.weight(1f).focusRequester(f1), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f2.requestFocus()}), trailingIcon={IconButton({f2.requestFocus()}){Icon(Icons.Default.Check,"")}}); Checkbox(giriOk, {giriOk=it}) }
            Row(verticalAlignment = Alignment.CenterVertically) { OutlinedTextField(amp, {if(it.all{c->c.isDigit()})amp=it}, label={Text("A")}, modifier=Modifier.weight(1f).focusRequester(f2), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Done), keyboardActions=KeyboardActions(onDone={focusManager.clearFocus()}), trailingIcon={IconButton({focusManager.clearFocus()}){Icon(Icons.Default.Check,"")}}); Checkbox(ampOk, {ampOk=it}) }
        }
    }, confirmButton = { Button({ onConfirm(giri.toIntOrNull() ?: 0, giriOk, amp.toIntOrNull() ?: 0, ampOk); onDismiss() }) { Text("Salva") } })
}

@Composable
fun ScrapDialog(onDismiss: () -> Unit, onConfirm: (Double) -> Unit) {
    var valStr by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Scarto PLC") }, text = { OutlinedTextField(valStr, {valStr=it}, label={Text("Valore")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal)) }, confirmButton = { Button({ onConfirm(valStr.toDoubleOrNull() ?: 0.0); onDismiss() }) { Text("Salva") } })
}

@Composable
fun ExitCheckDialog(onDismiss: () -> Unit, onConfirm: (Boolean, Int, Int) -> Unit) {
    var isOk by remember { mutableStateOf(true) }; var temp by remember { mutableStateOf("") }; var pressure by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current; val (f1, f2) = remember { FocusRequester.createRefs() }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Controllo Uscita") }, text = {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(isOk, {isOk=true}); Text("OK"); RadioButton(!isOk, {isOk=false}); Text("KO") }
            OutlinedTextField(temp, {if(it.all{c->c.isDigit()})temp=it}, label={Text("Temp")}, modifier=Modifier.focusRequester(f1), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f2.requestFocus()}), trailingIcon={IconButton({f2.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(pressure, {if(it.all{c->c.isDigit()})pressure=it}, label={Text("Durometro")}, modifier=Modifier.focusRequester(f2), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number, imeAction=ImeAction.Done), keyboardActions=KeyboardActions(onDone={focusManager.clearFocus()}), trailingIcon={IconButton({focusManager.clearFocus()}){Icon(Icons.Default.Check,"")}})
        }
    }, confirmButton = { Button({ onConfirm(isOk, temp.toIntOrNull() ?: 0, pressure.toIntOrNull() ?: 0); onDismiss() }) { Text("Salva") } })
}

@Composable
fun MeasurementDialog(onDismiss: () -> Unit, onConfirm: (Double, Double, Double, Double, Double, Double, Boolean) -> Unit) {
    var h by remember { mutableStateOf("") }; var la by remember { mutableStateOf("") }; var t by remember { mutableStateOf("") }; var d1 by remember { mutableStateOf("") }; var d2 by remember { mutableStateOf("") }; var weight by remember { mutableStateOf("") }; var diagOk by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current; val (f1, f2, f3, f4, f5, f6) = remember { FocusRequester.createRefs() }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Misure") }, text = {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            OutlinedTextField(h, {h=it}, label={Text("H")}, modifier=Modifier.focusRequester(f1), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f2.requestFocus()}), trailingIcon={IconButton({f2.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(la, {la=it}, label={Text("LA")}, modifier=Modifier.focusRequester(f2), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f3.requestFocus()}), trailingIcon={IconButton({f3.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(t, {t=it}, label={Text("T")}, modifier=Modifier.focusRequester(f3), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f4.requestFocus()}), trailingIcon={IconButton({f4.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(d1, {d1=it}, label={Text("D1")}, modifier=Modifier.focusRequester(f4), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f5.requestFocus()}), trailingIcon={IconButton({f4.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(d2, {d2=it}, label={Text("D2")}, modifier=Modifier.focusRequester(f5), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal, imeAction=ImeAction.Next), keyboardActions=KeyboardActions(onNext={f6.requestFocus()}), trailingIcon={IconButton({f5.requestFocus()}){Icon(Icons.Default.Check,"")}})
            OutlinedTextField(weight, {weight=it}, label={Text("Peso")}, modifier=Modifier.focusRequester(f6), keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Decimal, imeAction=ImeAction.Done), keyboardActions=KeyboardActions(onDone={focusManager.clearFocus()}), trailingIcon={IconButton({f6.freeFocus()}){Icon(Icons.Default.Check,"")}})
            Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(diagOk, {diagOk=it}); Text("Squad. OK") }
        }
    }, confirmButton = { Button({ onConfirm(h.toDoubleOrNull() ?: 0.0, la.toDoubleOrNull() ?: 0.0, t.toDoubleOrNull() ?: 0.0, d1.toDoubleOrNull() ?: 0.0, d2.toDoubleOrNull() ?: 0.0, weight.toDoubleOrNull() ?: 0.0, diagOk); onDismiss() }) { Text("Salva") } })
}

@Composable
fun CloggingDialog(reasons: List<CloggingReason>, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var selectedReason by remember { mutableStateOf("") }; var customReason by remember { mutableStateOf("") }; var showCustomInput by remember { mutableStateOf(false) }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Filatura") }, text = {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            reasons.forEach { r -> Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(selectedReason == r.reasonText && !showCustomInput, {selectedReason=r.reasonText; showCustomInput=false}); Text(r.reasonText) } }
            Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(showCustomInput, {showCustomInput=true}); Text("Altro") }
            if (showCustomInput) OutlinedTextField(customReason, {customReason=it}, label={Text("Nuovo Motivo")}, modifier=Modifier.fillMaxWidth())
        }
    }, confirmButton = { Button({ val fR = if (showCustomInput) customReason else selectedReason; if (fR.isNotEmpty()) { onConfirm(fR); onDismiss() } }) { Text("Salva") } })
}
