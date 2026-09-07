package com.example.man_app.ui.fermate

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.man_app.data.model.StopEvent
import com.example.man_app.ui.components.CommonDatePicker
import com.example.man_app.ui.components.CommonTimePicker
import com.example.man_app.util.formatTime
import com.example.man_app.util.getNowMillis
import kotlinx.datetime.*

@Composable
fun FermateScreen(
    viewModel: FermateViewModel,
    machineId: String,
    lineId: String,
    currentOperator: String?,
    currentProduct: String?,
    currentSigla: String?,
    currentShift: String?
) {
    val activeStop by viewModel.activeStop.collectAsState()
    val history by viewModel.stopHistory.collectAsState()
    val typeSuggestions by viewModel.typeSuggestions.collectAsState()
    val activitySuggestions by viewModel.activitySuggestions.collectAsState()
    val filter by viewModel.filter.collectAsState()
    
    var stopToEdit by remember { mutableStateOf<StopEvent?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(machineId, lineId) {
        viewModel.setContext(machineId, lineId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (activeStop == null) {
            Button(
                onClick = { 
                    viewModel.startStop(machineId, lineId, currentOperator, currentProduct, currentSigla, currentShift) 
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("INIZIO FERMATA", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            ActiveStopCard(
                stop = activeStop!!,
                typeSuggestions = typeSuggestions,
                activitySuggestions = activitySuggestions,
                onUpdate = viewModel::updateStop,
                onFinish = { type, activities -> viewModel.finishStop(type, activities) }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        
        Text("Filtra Storia:", style = MaterialTheme.typography.labelSmall)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FilterChip(
                selected = filter == StopFilter.CURRENT_SHIFT,
                onClick = { viewModel.setFilter(StopFilter.CURRENT_SHIFT) },
                label = { Text("Turno", fontSize = 10.sp) }
            )
            FilterChip(
                selected = filter == StopFilter.LAST_24H,
                onClick = { viewModel.setFilter(StopFilter.LAST_24H) },
                label = { Text("24h", fontSize = 10.sp) }
            )
            FilterChip(
                selected = filter == StopFilter.LAST_WEEK,
                onClick = { viewModel.setFilter(StopFilter.LAST_WEEK) },
                label = { Text("Sett.", fontSize = 10.sp) }
            )
            FilterChip(
                selected = filter == StopFilter.CUSTOM_DATE,
                onClick = { showDatePicker = true },
                label = { Text("Data", fontSize = 10.sp) }
            )
        }

        Text("Storia Fermate", style = MaterialTheme.typography.titleMedium)
        history.forEach { stop ->
            StopHistoryItem(
                stop = stop,
                onEdit = { stopToEdit = it },
                onDelete = { viewModel.deleteStop(it) }
            )
        }
    }

    if (showDatePicker) {
        CommonDatePicker(
            initialEpochMillis = getNowMillis(),
            onDismiss = { showDatePicker = false },
            onConfirm = { 
                viewModel.setFilter(StopFilter.CUSTOM_DATE, it)
                showDatePicker = false
            }
        )
    }

    if (stopToEdit != null) {
        EditStopDialog(
            stop = stopToEdit!!,
            typeSuggestions = typeSuggestions,
            activitySuggestions = activitySuggestions,
            onDismiss = { stopToEdit = null },
            onConfirm = { 
                viewModel.updateStop(it)
                stopToEdit = null
            }
        )
    }
}

@Composable
fun ActiveStopCard(
    stop: StopEvent,
    typeSuggestions: List<String>,
    activitySuggestions: List<String>,
    onUpdate: (StopEvent) -> Unit,
    onFinish: (String, String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var currentType by remember(stop.id) { mutableStateOf(stop.stopType) }
    var currentActivities by remember(stop.id) { mutableStateOf(stop.activities) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("FERMATA ATTIVA", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            Text("Iniziata alle: ${formatTime(stop.startTime)}", fontSize = 14.sp)

            AutoCompleteTextField(
                label = "Tipo Fermata",
                initialValue = currentType,
                suggestions = typeSuggestions,
                onTextChange = { currentType = it },
                onConfirm = { onUpdate(stop.copy(stopType = it)) },
                onCheck = { focusManager.moveFocus(FocusDirection.Down) }
            )

            AutoCompleteTextField(
                label = "Attività svolta",
                initialValue = currentActivities,
                suggestions = activitySuggestions,
                onTextChange = { currentActivities = it },
                onConfirm = { onUpdate(stop.copy(activities = it)) },
                onCheck = { focusManager.clearFocus() }
            )

            Button(
                onClick = { onFinish(currentType, currentActivities) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("FINE FERMATA", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    label: String,
    initialValue: String,
    suggestions: List<String>,
    onTextChange: (String) -> Unit,
    onConfirm: (String) -> Unit,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember(initialValue) { mutableStateOf(initialValue) }
    var expanded by remember { mutableStateOf(false) }
    
    val filteredSuggestions by remember(text, suggestions) {
        derivedStateOf {
            if (text.isBlank()) emptyList()
            else suggestions.filter { it.contains(text, ignoreCase = true) }.take(5)
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { 
                val upper = it.uppercase()
                text = upper
                onTextChange(upper)
                expanded = true
            },
            label = { Text(label) },
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                .onFocusChanged { if (!it.isFocused) onConfirm(text) },
            trailingIcon = {
                IconButton(onClick = { 
                    onConfirm(text)
                    onCheck() 
                }) {
                    Icon(Icons.Default.Check, contentDescription = "Conferma")
                }
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        if (filteredSuggestions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                filteredSuggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text(suggestion) },
                        onClick = {
                            text = suggestion
                            onTextChange(suggestion)
                            onConfirm(suggestion)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StopHistoryItem(
    stop: StopEvent,
    onEdit: (StopEvent) -> Unit,
    onDelete: (StopEvent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${formatTime(stop.startTime)}-${stop.endTime?.let { formatTime(it) } ?: "..."}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stop.stopType,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    maxLines = 1
                )
                if (stop.activities.isNotBlank()) {
                    Text(
                        text = " - ${stop.activities}",
                        fontSize = 10.sp,
                        maxLines = 1,
                        color = Color.DarkGray,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(Modifier.weight(1f))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().height(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Op: ${stop.operatorId ?: "-"} | Art: ${stop.productId ?: "-"}",
                    fontSize = 9.sp,
                    color = Color.Gray
                )
                Row {
                    IconButton(onClick = { onEdit(stop) }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Edit, "", modifier = Modifier.size(14.dp), tint = Color.Gray)
                    }
                    IconButton(onClick = { onDelete(stop) }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, "", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(14.dp))
                    }
                }
            }
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
        }
    }
}

@Composable
fun EditStopDialog(
    stop: StopEvent,
    typeSuggestions: List<String>,
    activitySuggestions: List<String>,
    onDismiss: () -> Unit,
    onConfirm: (StopEvent) -> Unit
) {
    var currentStop by remember { mutableStateOf(stop) }
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifica Fermata") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Inizio: ${formatTime(currentStop.startTime)}", modifier = Modifier.weight(1f))
                    IconButton(onClick = { showStartPicker = true }) { Icon(Icons.Default.Timer, "") }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Fine: ${currentStop.endTime?.let { formatTime(it) } ?: "-"}", modifier = Modifier.weight(1f))
                    IconButton(onClick = { showEndPicker = true }) { Icon(Icons.Default.Timer, "") }
                }
                
                AutoCompleteTextField(
                    label = "Tipo Fermata",
                    initialValue = currentStop.stopType,
                    suggestions = typeSuggestions,
                    onTextChange = { currentStop = currentStop.copy(stopType = it) },
                    onConfirm = { currentStop = currentStop.copy(stopType = it) },
                    onCheck = { }
                )
                AutoCompleteTextField(
                    label = "Attività svolta",
                    initialValue = currentStop.activities,
                    suggestions = activitySuggestions,
                    onTextChange = { currentStop = currentStop.copy(activities = it) },
                    onConfirm = { currentStop = currentStop.copy(activities = it) },
                    onCheck = { }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(currentStop) }) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annulla") }
        }
    )

    if (showStartPicker) {
        val startDateTime = Instant.fromEpochMilliseconds(currentStop.startTime).toLocalDateTime(TimeZone.currentSystemDefault())
        val initialMinutes = startDateTime.hour * 60 + startDateTime.minute
        CommonTimePicker(
            initialMinutes = initialMinutes,
            onDismiss = { showStartPicker = false },
            onConfirm = { totalMinutes ->
                val date = startDateTime.date
                val newDateTime = LocalDateTime(date.year, date.month, date.dayOfMonth, totalMinutes / 60, totalMinutes % 60)
                currentStop = currentStop.copy(startTime = newDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
                showStartPicker = false
            }
        )
    }

    if (showEndPicker) {
        val endTimeToUse = currentStop.endTime ?: getNowMillis()
        val endDateTime = Instant.fromEpochMilliseconds(endTimeToUse).toLocalDateTime(TimeZone.currentSystemDefault())
        val initialMinutes = endDateTime.hour * 60 + endDateTime.minute
        CommonTimePicker(
            initialMinutes = initialMinutes,
            onDismiss = { showEndPicker = false },
            onConfirm = { totalMinutes ->
                val date = endDateTime.date
                val newDateTime = LocalDateTime(date.year, date.month, date.dayOfMonth, totalMinutes / 60, totalMinutes % 60)
                currentStop = currentStop.copy(endTime = newDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
                showEndPicker = false
            }
        )
    }
}
