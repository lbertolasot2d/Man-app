package com.example.man_app.ui.selection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.man_app.data.model.ImpilatriceReport
import com.example.man_app.data.model.MattonieraReport
import com.example.man_app.data.model.ScaricatriceReport
import com.example.man_app.ui.fermate.StopFilter
import com.example.man_app.util.formatTime
import com.example.man_app.ui.components.CommonDatePicker
import com.example.man_app.util.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportListScreen(
    title: String,
    reports: List<Any>,
    isLoading: Boolean,
    filter: StopFilter,
    onFilterChange: (StopFilter, Long?) -> Unit,
    onBack: () -> Unit,
    onReportSelected: (String) -> Unit,
    onNewReport: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // FILTER BAR
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                FilterChip(selected = filter == StopFilter.CURRENT_SHIFT, onClick = { onFilterChange(StopFilter.CURRENT_SHIFT, null) }, label = { Text("Turno", fontSize = 10.sp) })
                FilterChip(selected = filter == StopFilter.LAST_24H, onClick = { onFilterChange(StopFilter.LAST_24H, null) }, label = { Text("24h", fontSize = 10.sp) })
                FilterChip(selected = filter == StopFilter.LAST_WEEK, onClick = { onFilterChange(StopFilter.LAST_WEEK, null) }, label = { Text("Sett.", fontSize = 10.sp) })
                FilterChip(
                    selected = filter == StopFilter.CUSTOM_DATE,
                    onClick = { showDatePicker = true },
                    label = { Text("Data", fontSize = 10.sp) }
                )
            }

            Button(onClick = onNewReport, modifier = Modifier.fillMaxWidth().height(48.dp), shape = MaterialTheme.shapes.medium) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("NUOVO RAPPORTINO")
            }

            Text("Rapportini Esistenti", style = MaterialTheme.typography.titleMedium)

            if (isLoading) {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            } else if (reports.isEmpty()) {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) { Text("Nessun rapportino trovato.", color = Color.Gray) }
            } else {
                reports.forEach { report ->
                    when (report) {
                        is MattonieraReport -> MattonieraReportItem(report, onClick = { onReportSelected(report.id) })
                        is ImpilatriceReport -> ImpilatriceReportItem(report, onClick = { onReportSelected(report.id) })
                        is ScaricatriceReport -> ScaricatriceReportItem(report, onClick = { onReportSelected(report.id) })
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }

    if (showDatePicker) {
        CommonDatePicker(
            initialEpochMillis = com.example.man_app.util.getNowMillis(),
            onDismiss = { showDatePicker = false },
            onConfirm = { 
                onFilterChange(StopFilter.CUSTOM_DATE, it)
                showDatePicker = false
            }
        )
    }
}

@Composable
fun MattonieraReportItem(report: MattonieraReport, onClick: () -> Unit) {
    ReportItemBase(
        date = report.date,
        isClosed = report.isClosed,
        op = report.operatorId,
        prod = report.productId,
        sigla = report.sigla,
        startTime = report.startTime,
        onClick = onClick
    )
}

@Composable
fun ImpilatriceReportItem(report: ImpilatriceReport, onClick: () -> Unit) {
    ReportItemBase(
        date = report.date,
        isClosed = report.isClosed,
        op = report.operatorId,
        prod = report.productId,
        sigla = report.sigla,
        startTime = report.startTime,
        onClick = onClick
    )
}

@Composable
fun ScaricatriceReportItem(report: ScaricatriceReport, onClick: () -> Unit) {
    ReportItemBase(
        date = report.date,
        isClosed = report.isClosed,
        op = report.operatorId,
        prod = report.productId,
        sigla = report.sigla,
        startTime = report.startTime,
        onClick = onClick
    )
}

@Composable
fun ReportItemBase(date: Long, isClosed: Boolean, op: String, prod: String, sigla: String?, startTime: Long, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = if (isClosed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = formatDate(date), fontWeight = FontWeight.Bold)
                Text(text = if (isClosed) "CHIUSO" else "APERTO", color = if (isClosed) Color.Gray else Color.Blue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text("Op: $op | Art: $prod", fontSize = 14.sp)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Sigla: ${sigla ?: "-"}", style = MaterialTheme.typography.bodySmall)
                Text("Inizio: ${formatTime(startTime)}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
