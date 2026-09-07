package com.example.man_app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.man_app.data.webdb.AppDatabase
import com.example.man_app.data.model.WorkstationContext
import com.example.man_app.ui.mattoniera.MattonieraScreen
import com.example.man_app.ui.mattoniera.MattonieraViewModel
import com.example.man_app.ui.fermate.FermateScreen
import com.example.man_app.ui.fermate.FermateViewModel
import com.example.man_app.ui.impilatrice.ImpilatriceScreen
import com.example.man_app.ui.impilatrice.ImpilatriceViewModel
import com.example.man_app.ui.personnel.PersonnelScreen
import com.example.man_app.ui.personnel.PersonnelViewModel
import com.example.man_app.ui.home.HomeScreen
import com.example.man_app.ui.selection.SelectionScreen
import com.example.man_app.ui.selection.ReportListScreen
import com.example.man_app.ui.theme.ManappTheme
import com.example.man_app.ui.scaricatrice.ScaricatriceScreen
import com.example.man_app.ui.scaricatrice.ScaricatriceViewModel
import com.example.man_app.ui.shifts.ShiftManagementScreen
import com.example.man_app.ui.shifts.ShiftManagementViewModel
import kotlinx.serialization.Serializable

@Serializable class HomeRoute
@Serializable class SelectionRoute
@Serializable class PersonnelRoute
@Serializable data class ShiftManagementRoute(val line: String)
@Serializable data class ReportListRoute(val line: String, val machine: String)
@Serializable data class MainDashboardRoute(val line: String, val machine: String, val reportId: String? = null)
@Serializable class ProductionRoute
@Serializable class FermateRoute
@Serializable class ChecklistRoute

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

@Composable
fun App(database: AppDatabase, syncTrigger: SyncTrigger? = null) {
    ManappTheme {
        val rootNavController = rememberNavController()
        
        // ViewModels shared via standard viewModel() or composition locals
        // For simplicity, we create them here or use a factory pattern
        
        val mattonieraViewModel: MattonieraViewModel = viewModel {
            MattonieraViewModel(database.mattonieraDao(), database.productDao(), database.personnelDao(), database.shiftDao(), syncTrigger)
        }
        val impilatriceViewModel: ImpilatriceViewModel = viewModel {
            ImpilatriceViewModel(database.impilatriceDao(), database.mattonieraDao(), database.personnelDao(), database.shiftDao(), syncTrigger)
        }
        val scaricatriceViewModel: ScaricatriceViewModel = viewModel {
            ScaricatriceViewModel(database.scaricatriceDao(), database.mattonieraDao(), database.personnelDao(), database.shiftDao(), syncTrigger)
        }
        val personnelViewModel: PersonnelViewModel = viewModel {
            PersonnelViewModel(database.personnelDao(), syncTrigger)
        }
        val shiftManagementViewModel: ShiftManagementViewModel = viewModel {
            ShiftManagementViewModel(database.shiftDao())
        }

        NavHost(navController = rootNavController, startDestination = HomeRoute()) {
            composable<HomeRoute> {
                HomeScreen(
                    onNavigateToPersonnel = { rootNavController.navigate(PersonnelRoute()) },
                    onNavigateToReports = { rootNavController.navigate(SelectionRoute()) },
                    onNavigateToChecklists = { rootNavController.navigate(ChecklistRoute()) }
                )
            }
            composable<PersonnelRoute> {
                PersonnelScreen(
                    viewModel = personnelViewModel,
                    onBack = { rootNavController.popBackStack() }
                )
            }
            composable<SelectionRoute> {
                SelectionScreen(
                    onConfirm = { context ->
                        rootNavController.navigate(ReportListRoute(context.line, context.machine))
                    },
                    onManageShifts = { line ->
                        rootNavController.navigate(ShiftManagementRoute(line))
                    },
                    onBack = { rootNavController.popBackStack() }
                )
            }
            composable<ShiftManagementRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<ShiftManagementRoute>()
                ShiftManagementScreen(
                    lineId = route.line,
                    viewModel = shiftManagementViewModel,
                    onBack = { rootNavController.popBackStack() }
                )
            }
            composable<ChecklistRoute> {
                Scaffold(
                    topBar = {
                        @OptIn(ExperimentalMaterial3Api::class)
                        CenterAlignedTopAppBar(
                            title = { Text("Checklist di Sicurezza") },
                            navigationIcon = {
                                IconButton(onClick = { rootNavController.popBackStack() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                                }
                            }
                        )
                    }
                ) { padding ->
                    Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Modulo Checklist in arrivo...")
                    }
                }
            }
            composable<ReportListRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<ReportListRoute>()
                
                if (route.machine == "MATTONIERA") {
                    val reports by mattonieraViewModel.reportsArchive.collectAsState()
                    val filter by mattonieraViewModel.archiveFilter.collectAsState()
                    val isLoading by mattonieraViewModel.isArchiveLoading.collectAsState()
                    
                    LaunchedEffect(route.line, route.machine) { mattonieraViewModel.loadArchive(route.line, route.machine) }
                    
                    ReportListScreen(
                        title = "Archivio Mattoniera - ${route.line}",
                        reports = reports,
                        isLoading = isLoading,
                        filter = filter,
                        onFilterChange = mattonieraViewModel::setArchiveFilter,
                        onBack = { rootNavController.popBackStack() },
                        onReportSelected = { id ->
                            mattonieraViewModel.setReport(id)
                            rootNavController.navigate(MainDashboardRoute(route.line, route.machine, id))
                        },
                        onNewReport = {
                            mattonieraViewModel.setReport(null)
                            rootNavController.navigate(MainDashboardRoute(route.line, route.machine, null))
                        }
                    )
                } else if (route.machine == "IMPILATRICE") {
                    val reports by impilatriceViewModel.reportsArchive.collectAsState()
                    val filter by impilatriceViewModel.archiveFilter.collectAsState()
                    
                    LaunchedEffect(route.line, route.machine) { impilatriceViewModel.loadArchive(route.line, route.machine) }
                    
                    ReportListScreen(
                        title = "Archivio Impilatrice - ${route.line}",
                        reports = reports,
                        isLoading = false,
                        filter = filter,
                        onFilterChange = impilatriceViewModel::setArchiveFilter,
                        onBack = { rootNavController.popBackStack() },
                        onReportSelected = { id ->
                            impilatriceViewModel.setReport(id)
                            rootNavController.navigate(MainDashboardRoute(route.line, route.machine, id))
                        },
                        onNewReport = {
                            impilatriceViewModel.setReport(null)
                            rootNavController.navigate(MainDashboardRoute(route.line, route.machine, null))
                        }
                    )
                } else if (route.machine == "SCARICATRICE") {
                    val reports by scaricatriceViewModel.reportsArchive.collectAsState()
                    val filter by scaricatriceViewModel.archiveFilter.collectAsState()
                    
                    LaunchedEffect(route.line, route.machine) { scaricatriceViewModel.loadArchive(route.line, route.machine) }
                    
                    ReportListScreen(
                        title = "Archivio Scaricatrice - ${route.line}",
                        reports = reports,
                        isLoading = false,
                        filter = filter,
                        onFilterChange = scaricatriceViewModel::setArchiveFilter,
                        onBack = { rootNavController.popBackStack() },
                        onReportSelected = { id ->
                            scaricatriceViewModel.setReport(id)
                            rootNavController.navigate(MainDashboardRoute(route.line, route.machine, id))
                        },
                        onNewReport = {
                            scaricatriceViewModel.setReport(null)
                            rootNavController.navigate(MainDashboardRoute(route.line, route.machine, null))
                        }
                    )
                } else {
                    PlaceholderScreen("Archivio ${route.machine} in arrivo...")
                }
            }
            composable<MainDashboardRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<MainDashboardRoute>()
                MainScreen(
                    database = database,
                    syncTrigger = syncTrigger,
                    mattonieraViewModel = mattonieraViewModel,
                    impilatriceViewModel = impilatriceViewModel,
                    scaricatriceViewModel = scaricatriceViewModel,
                    context = WorkstationContext(route.line, route.machine),
                    onBack = { rootNavController.popBackStack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    database: AppDatabase,
    syncTrigger: SyncTrigger?,
    mattonieraViewModel: MattonieraViewModel,
    impilatriceViewModel: ImpilatriceViewModel,
    scaricatriceViewModel: ScaricatriceViewModel,
    context: WorkstationContext,
    onBack: () -> Unit
) {
    val navController = rememberNavController()
    val routes = listOf(
        TopLevelRoute("Produzione", ProductionRoute(), Icons.AutoMirrored.Filled.List),
        TopLevelRoute("Fermate", FermateRoute(), Icons.Default.Warning)
    )

    val activeReportMattoniera by mattonieraViewModel.activeReport.collectAsState()
    val activeReportImpilatrice by impilatriceViewModel.activeReport.collectAsState()
    val activeReportScaricatrice by scaricatriceViewModel.activeReport.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${context.machine} - ${context.line}", style = MaterialTheme.typography.titleMedium)
                        if (context.machine == "MATTONIERA") {
                            activeReportMattoniera?.let { Text("Sigla: ${it.sigla ?: "-"} | Op: ${it.operatorId}", style = MaterialTheme.typography.labelSmall) }
                        } else if (context.machine == "IMPILATRICE") {
                            activeReportImpilatrice?.let { Text("Sigla: ${it.sigla ?: "-"} | Op: ${it.operatorId}", style = MaterialTheme.typography.labelSmall) }
                        } else if (context.machine == "SCARICATRICE") {
                            activeReportScaricatrice?.let { Text("Sigla: ${it.sigla ?: "-"} | Op: ${it.operatorId}", style = MaterialTheme.typography.labelSmall) }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Indietro")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                routes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                        label = { Text(topLevelRoute.name) },
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ProductionRoute(),
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<ProductionRoute> { 
                when (context.machine) {
                    "MATTONIERA" -> MattonieraScreen(mattonieraViewModel, context.line, context.machine)
                    "IMPILATRICE" -> ImpilatriceScreen(impilatriceViewModel, context.line, context.machine)
                    "SCARICATRICE" -> ScaricatriceScreen(scaricatriceViewModel, context.line, context.machine)
                    else -> PlaceholderScreen("Report ${context.machine} - ${context.line}")
                }
            }
            composable<FermateRoute> { 
                val fermateViewModel: FermateViewModel = viewModel {
                    FermateViewModel(database.stopDao())
                }
                val currentOp = when (context.machine) {
                    "MATTONIERA" -> activeReportMattoniera?.operatorId
                    "IMPILATRICE" -> activeReportImpilatrice?.operatorId
                    "SCARICATRICE" -> activeReportScaricatrice?.operatorId
                    else -> null
                }
                val currentProd = when (context.machine) {
                    "MATTONIERA" -> activeReportMattoniera?.productId
                    "IMPILATRICE" -> activeReportImpilatrice?.productId
                    "SCARICATRICE" -> activeReportScaricatrice?.productId
                    else -> null
                }
                val currentSigla = when (context.machine) {
                    "MATTONIERA" -> activeReportMattoniera?.sigla
                    "IMPILATRICE" -> activeReportImpilatrice?.sigla
                    "SCARICATRICE" -> activeReportScaricatrice?.sigla
                    else -> null
                }
                val currentShift = when (context.machine) {
                    "MATTONIERA" -> activeReportMattoniera?.shift
                    "IMPILATRICE" -> activeReportImpilatrice?.shift
                    "SCARICATRICE" -> activeReportScaricatrice?.shift
                    else -> null
                }
                
                FermateScreen(
                    viewModel = fermateViewModel,
                    machineId = context.machine,
                    lineId = context.line,
                    currentOperator = currentOp,
                    currentProduct = currentProd,
                    currentSigla = currentSigla,
                    currentShift = currentShift
                )
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title)
    }
}
