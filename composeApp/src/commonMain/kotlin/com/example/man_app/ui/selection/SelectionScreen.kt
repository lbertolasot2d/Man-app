package com.example.man_app.ui.selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.man_app.data.model.SelectionData
import com.example.man_app.data.model.WorkstationContext

import com.example.man_app.util.getLogoPainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen(
    onConfirm: (WorkstationContext) -> Unit,
    onManageShifts: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedLine by remember { mutableStateOf<String?>(null) }
    var selectedMachine by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuovo Rapportino") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                tonalElevation = 4.dp,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { 
                        if (selectedLine != null && selectedMachine != null) {
                            onConfirm(WorkstationContext(selectedLine!!, selectedMachine!!))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .height(48.dp),
                    enabled = selectedLine != null && selectedMachine != null
                ) {
                    Text("Seleziona", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 2.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // LOGO
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .padding(vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = getLogoPainter(),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = "Nuovo Rapportino",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // SELEZIONE LINEA
            SelectionGroup(
                title = "1. SELEZIONA LINEA",
                options = SelectionData.lines,
                selectedOption = selectedLine,
                onOptionSelected = { 
                    selectedLine = it
                    selectedMachine = null 
                }
            )

            if (selectedLine != null) {
                OutlinedButton(
                    onClick = { onManageShifts(selectedLine!!) },
                    modifier = Modifier.fillMaxWidth().height(32.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.Settings, null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Gestione Turni", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp)
                }

                // SELEZIONE MACCHINA
                SelectionGroup(
                    title = "2. SELEZIONA MACCHINA",
                    options = SelectionData.machines,
                    selectedOption = selectedMachine,
                    onOptionSelected = { selectedMachine = it }
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SelectionGroup(
    title: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp) // Reduced from 4
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp // Smaller
        )
        
        options.chunked(2).forEach { rowOptions ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rowOptions.forEach { option ->
                    val isSelected = selectedOption == option
                    FilterChip(
                        modifier = Modifier.weight(1f).height(32.dp), // Tighter chips
                        selected = isSelected,
                        onClick = { onOptionSelected(option) },
                        label = { Text(option, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 11.sp) }
                    )
                }
                if (rowOptions.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
