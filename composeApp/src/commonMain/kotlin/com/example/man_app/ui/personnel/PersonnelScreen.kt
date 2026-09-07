package com.example.man_app.ui.personnel

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
import com.example.man_app.data.model.Personnel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonnelScreen(
    viewModel: PersonnelViewModel,
    onBack: () -> Unit
) {
    val personnelList by viewModel.personnelList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val lineFilter by viewModel.lineFilter.collectAsState()
    val deptFilter by viewModel.deptFilter.collectAsState()
    
    val lines by viewModel.availableLines.collectAsState()
    val depts by viewModel.availableDepts.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var personnelToEdit by remember { mutableStateOf<Personnel?>(null) }
    var personnelToDelete by remember { mutableStateOf<Personnel?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Anagrafica Personale") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Aggiungi Dipendente")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::setSearchQuery,
                label = { Text("Cerca per nome o ID") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filters Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterDropdown(
                    label = "Linea",
                    options = lines,
                    selected = lineFilter,
                    onSelect = viewModel::setLineFilter,
                    modifier = Modifier.weight(1f)
                )
                FilterDropdown(
                    label = "Reparto",
                    options = depts,
                    selected = deptFilter,
                    onSelect = viewModel::setDeptFilter,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Personnel List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(personnelList) { person ->
                    PersonnelItem(
                        person = person,
                        onEdit = { personnelToEdit = person },
                        onDelete = { personnelToDelete = person }
                    )
                }
            }
            
            if (personnelList.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nessun dipendente trovato", color = Color.Gray)
                }
            }
        }
    }

    if (showAddDialog) {
        EmployeeDialog(
            title = "Aggiungi Dipendente",
            onDismiss = { showAddDialog = false },
            onConfirm = { 
                viewModel.addPersonnel(it)
                showAddDialog = false
            }
        )
    }

    if (personnelToEdit != null) {
        EmployeeDialog(
            title = "Modifica Dipendente",
            person = personnelToEdit,
            onDismiss = { personnelToEdit = null },
            onConfirm = {
                viewModel.updatePersonnel(it)
                personnelToEdit = null
            }
        )
    }

    if (personnelToDelete != null) {
        AlertDialog(
            onDismissRequest = { personnelToDelete = null },
            title = { Text("Conferma Eliminazione") },
            text = { Text("Sei sicuro di voler eliminare ${personnelToDelete?.lastName} ${personnelToDelete?.firstName}?") },
            confirmButton = {
                Button(
                    onClick = {
                        personnelToDelete?.let { viewModel.deletePersonnel(it) }
                        personnelToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Elimina")
                }
            },
            dismissButton = {
                TextButton(onClick = { personnelToDelete = null }) {
                    Text("Annulla")
                }
            }
        )
    }
}

@Composable
fun PersonnelItem(
    person: Personnel,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${person.lastName} ${person.firstName}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${person.id} | ${person.role}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 8.dp)) {
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text(person.department, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 4.dp))
                    }
                    if (person.line.isNotBlank()) {
                        Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer, modifier = Modifier.padding(top = 2.dp)) {
                            Text(person.line, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 4.dp))
                        }
                    }
                }
                
                IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Edit, "Modifica", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Delete, "Elimina", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun EmployeeDialog(
    title: String,
    person: Personnel? = null,
    onDismiss: () -> Unit,
    onConfirm: (Personnel) -> Unit
) {
    var id by remember { mutableStateOf(person?.id ?: "") }
    var firstName by remember { mutableStateOf(person?.firstName ?: "") }
    var lastName by remember { mutableStateOf(person?.lastName ?: "") }
    var role by remember { mutableStateOf(person?.role ?: "") }
    var line by remember { mutableStateOf(person?.line ?: "") }
    var department by remember { mutableStateOf(person?.department ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("ID Dipendente") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = person == null // ID non modificabile se in modalità modifica
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Cognome") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Ruolo") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = line,
                    onValueChange = { line = it },
                    label = { Text("Linea") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    label = { Text("Reparto") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (id.isNotBlank() && lastName.isNotBlank() && firstName.isNotBlank()) {
                        onConfirm(Personnel(id, firstName, lastName, role, line, department))
                    }
                }
            ) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    label: String,
    options: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected ?: "Tutti",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
            textStyle = MaterialTheme.typography.bodySmall
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Tutti") },
                onClick = {
                    onSelect(null)
                    expanded = false
                }
            )
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
