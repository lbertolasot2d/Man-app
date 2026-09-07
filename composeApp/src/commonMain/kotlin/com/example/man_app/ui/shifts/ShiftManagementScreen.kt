package com.example.man_app.ui.shifts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.man_app.data.model.ShiftConfiguration
import com.example.man_app.util.randomUUID
import com.example.man_app.util.formatMin
import com.example.man_app.ui.components.CommonTimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftManagementScreen(
    lineId: String,
    viewModel: ShiftManagementViewModel,
    onBack: () -> Unit
) {
    val shifts by viewModel.shifts.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var shiftToEdit by remember { mutableStateOf<ShiftConfiguration?>(null) }
    var shiftToDelete by remember { mutableStateOf<ShiftConfiguration?>(null) }

    LaunchedEffect(lineId) {
        viewModel.setLine(lineId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestione Turni - $lineId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "Aggiungi Turno")
            }
        }
    ) { padding ->
        if (shifts.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nessun turno configurato. Aggiungine uno con il tasto +", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(shifts) { shift ->
                    ShiftItem(
                        shift = shift,
                        onEdit = { shiftToEdit = it },
                        onDelete = { shiftToDelete = it }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        ShiftEditDialog(
            lineId = lineId,
            onDismiss = { showAddDialog = false },
            onConfirm = { 
                viewModel.saveShift(it)
                showAddDialog = false
            }
        )
    }

    if (shiftToEdit != null) {
        ShiftEditDialog(
            lineId = lineId,
            shift = shiftToEdit,
            onDismiss = { shiftToEdit = null },
            onConfirm = {
                viewModel.saveShift(it)
                shiftToEdit = null
            }
        )
    }

    if (shiftToDelete != null) {
        AlertDialog(
            onDismissRequest = { shiftToDelete = null },
            title = { Text("Conferma Eliminazione") },
            text = { Text("Sei sicuro di voler eliminare il turno ${shiftToDelete?.name}?") },
            confirmButton = {
                Button(
                    onClick = {
                        shiftToDelete?.let { viewModel.deleteShift(it) }
                        shiftToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Elimina") }
            },
            dismissButton = {
                TextButton(onClick = { shiftToDelete = null }) { Text("Annulla") }
            }
        )
    }
}

@Composable
fun ShiftItem(shift: ShiftConfiguration, onEdit: (ShiftConfiguration) -> Unit, onDelete: (ShiftConfiguration) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Turno ${shift.name}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { onEdit(shift) }) { Icon(Icons.Default.Edit, null) }
                IconButton(onClick = { onDelete(shift) }) { Icon(Icons.Default.Delete, null, tint = Color.Red) }
            }
            Spacer(Modifier.height(8.dp))
            val days = listOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
            val starts = listOf(shift.mondayStart, shift.tuesdayStart, shift.wednesdayStart, shift.thursdayStart, shift.fridayStart, shift.saturdayStart, shift.sundayStart)
            val ends = listOf(shift.mondayEnd, shift.tuesdayEnd, shift.wednesdayEnd, shift.thursdayEnd, shift.fridayEnd, shift.saturdayEnd, shift.sundayEnd)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                days.forEachIndexed { i, day ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(day, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        if (starts[i] != null && ends[i] != null) {
                            Text(formatMin(starts[i]!!), fontSize = 9.sp)
                            Text(formatMin(ends[i]!!), fontSize = 9.sp)
                        } else {
                            Text("-", fontSize = 9.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShiftEditDialog(lineId: String, shift: ShiftConfiguration? = null, onDismiss: () -> Unit, onConfirm: (ShiftConfiguration) -> Unit) {
    var name by remember { mutableStateOf(shift?.name ?: "") }
    var monS by remember { mutableStateOf(shift?.mondayStart) }; var monE by remember { mutableStateOf(shift?.mondayEnd) }
    var tueS by remember { mutableStateOf(shift?.tuesdayStart) }; var tueE by remember { mutableStateOf(shift?.tuesdayEnd) }
    var wedS by remember { mutableStateOf(shift?.wednesdayStart) }; var wedE by remember { mutableStateOf(shift?.wednesdayEnd) }
    var thuS by remember { mutableStateOf(shift?.thursdayStart) }; var thuE by remember { mutableStateOf(shift?.thursdayEnd) }
    var friS by remember { mutableStateOf(shift?.fridayStart) }; var friE by remember { mutableStateOf(shift?.fridayEnd) }
    var satS by remember { mutableStateOf(shift?.saturdayStart) }; var satE by remember { mutableStateOf(shift?.saturdayEnd) }
    var sunS by remember { mutableStateOf(shift?.sundayStart) }; var sunE by remember { mutableStateOf(shift?.sundayEnd) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (shift == null) "Nuovo Turno" else "Modifica Turno") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome Turno (es. 1, 2, 3, 0)") }, modifier = Modifier.fillMaxWidth())
                
                DayRow("Lunedì", monS, monE, { monS = it }, { monE = it }, onCopyToNext = {
                    tueS = monS; tueE = monE; wedS = monS; wedE = monE; thuS = monS; thuE = monE; friS = monS; friE = monE; satS = monS; satE = monE; sunS = monS; sunE = monE
                })
                DayRow("Martedì", tueS, tueE, { tueS = it }, { tueE = it }, onCopyToNext = {
                    wedS = tueS; wedE = tueE; thuS = tueS; thuE = tueE; friS = tueS; friE = tueE; satS = tueS; satE = tueE; sunS = tueS; sunE = tueE
                })
                DayRow("Mercoledì", wedS, wedE, { wedS = it }, { wedE = it }, onCopyToNext = {
                    thuS = wedS; thuE = wedE; friS = wedS; friE = wedE; satS = wedS; satE = wedE; sunS = wedS; sunE = wedE
                })
                DayRow("Giovedì", thuS, thuE, { thuS = it }, { thuE = it }, onCopyToNext = {
                    friS = thuS; friE = thuE; satS = thuS; satE = thuE; sunS = thuS; sunE = thuE
                })
                DayRow("Venerdì", friS, friE, { friS = it }, { friE = it }, onCopyToNext = {
                    satS = friS; satE = friE; sunS = friS; sunE = friE
                })
                DayRow("Sabato", satS, satE, { satS = it }, { satE = it }, onCopyToNext = {
                    sunS = satS; sunE = satE
                })
                DayRow("Domenica", sunS, sunE, { sunS = it }, { sunE = it }, onCopyToNext = {})
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onConfirm(ShiftConfiguration(
                        id = shift?.id ?: randomUUID(), name = name, lineId = lineId,
                        mondayStart = monS, mondayEnd = monE, tuesdayStart = tueS, tuesdayEnd = tueE,
                        wednesdayStart = wedS, wednesdayEnd = wedE, thursdayStart = thuS, thursdayEnd = thuE,
                        fridayStart = friS, fridayEnd = friE, saturdayStart = satS, saturdayEnd = satE,
                        sundayStart = sunS, sundayEnd = sunE
                    ))
                }
            }) { Text("Salva") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Annulla") } }
    )
}

@Composable
fun DayRow(day: String, start: Int?, end: Int?, onStart: (Int?) -> Unit, onEnd: (Int?) -> Unit, onCopyToNext: () -> Unit) {
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    if (showStartPicker) {
        CommonTimePicker(start, onDismiss = { showStartPicker = false }, onConfirm = { onStart(it); showStartPicker = false })
    }
    if (showEndPicker) {
        CommonTimePicker(end, onDismiss = { showEndPicker = false }, onConfirm = { onEnd(it); showEndPicker = false })
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(day, modifier = Modifier.weight(1f), fontSize = 14.sp)
        TextButton(onClick = { showStartPicker = true }) { Text(start?.let { formatMin(it) } ?: "Inizio") }
        Text("-", fontSize = 14.sp)
        TextButton(onClick = { showEndPicker = true }) { Text(end?.let { formatMin(it) } ?: "Fine") }
        
        Row {
            IconButton(onClick = onCopyToNext, modifier = Modifier.size(32.dp)) { 
                Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary) 
            }
            IconButton(onClick = { onStart(null); onEnd(null) }, modifier = Modifier.size(32.dp)) { 
                Icon(Icons.Default.Clear, null, modifier = Modifier.size(16.dp)) 
            }
        }
    }
}
