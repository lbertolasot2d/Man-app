package com.example.man_app.ui.mattoniera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.man_app.SyncTrigger
import com.example.man_app.data.webdb.MattonieraDao
import com.example.man_app.data.webdb.ProductDao
import com.example.man_app.data.model.*
import com.example.man_app.ui.fermate.StopFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import com.example.man_app.util.getStartOfDay
import com.example.man_app.util.getEndOfDay

data class ShiftPreFillData(
    val operator: String = "",
    val product: String = "",
    val die: String = "",
    val sigla: String = "",
    val shift: String = "Mattina"
)

class MattonieraViewModel(
    private val mattonieraDao: MattonieraDao,
    private val productDao: ProductDao,
    private val personnelDao: com.example.man_app.data.webdb.PersonnelDao,
    private val shiftDao: com.example.man_app.data.webdb.ShiftDao,
    private val syncTrigger: SyncTrigger? = null
) : ViewModel() {

    val operators: StateFlow<List<String>> = personnelDao.getAll()
        .map { list -> list.filter { it.department.contains("Produzione", ignoreCase = true) }.map { "${it.lastName} ${it.firstName}" }.sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _availableShifts = MutableStateFlow<List<ShiftConfiguration>>(emptyList())
    val availableShifts = _availableShifts.asStateFlow()

    fun loadShifts(lineId: String) {
        viewModelScope.launch {
            shiftDao.getShiftsForLine(lineId).collect {
                _availableShifts.value = it
                updateSuggestedShift()
            }
        }
    }

    private fun updateSuggestedShift() {
        val suggested = com.example.man_app.ui.shifts.ShiftUtils.suggestShift(_availableShifts.value, com.example.man_app.util.getNowMillis())
        _preFillData.value = _preFillData.value.copy(shift = suggested)
    }

    private val _currentReportId = MutableStateFlow<String?>(null)
    
    @OptIn(ExperimentalCoroutinesApi::class)
    val activeReport: StateFlow<MattonieraReport?> = _currentReportId
        .flatMapLatest { id ->
            if (id != null) mattonieraDao.getReportById(id)
            else mattonieraDao.getActiveReports().map { it.firstOrNull() }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError = _validationError.asStateFlow()

    private val _preFillData = MutableStateFlow(ShiftPreFillData())
    val preFillData = _preFillData.asStateFlow()

    private val _archiveFilter = MutableStateFlow(StopFilter.CURRENT_SHIFT)
    val archiveFilter = _archiveFilter.asStateFlow()

    private val _archiveCustomDate = MutableStateFlow<Long?>(null)

    private val _archiveContext = MutableStateFlow<Pair<String, String>?>(null)

    private val _isArchiveLoading = MutableStateFlow(false)
    val isArchiveLoading = _isArchiveLoading.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val reportsArchive: StateFlow<List<MattonieraReport>> = _archiveContext
        .flatMapLatest { context ->
            if (context == null) {
                _isArchiveLoading.value = false
                flowOf(emptyList())
            } else {
                _isArchiveLoading.value = true
                mattonieraDao.getReportsByContext(context.first, context.second)
                    .onEach { _isArchiveLoading.value = false }
            }
        }
        .combine(_archiveFilter) { list, filter -> list to filter }
        .combine(_archiveCustomDate) { (list, filter), customDate ->
            val now = com.example.man_app.util.getNowMillis()
            when (filter) {
                StopFilter.CURRENT_SHIFT -> list.filter { it.date > now - 8 * 3600000 }
                StopFilter.LAST_24H -> list.filter { it.date > now - 24 * 3600000 }
                StopFilter.LAST_WEEK -> list.filter { it.date > now - 7 * 24 * 3600000 }
                StopFilter.CUSTOM_DATE -> {
                    if (customDate == null) list
                    else {
                        val start = getStartOfDay(customDate)
                        val end = getEndOfDay(customDate)
                        list.filter { it.date in start..end }
                    }
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun loadArchive(lineId: String, machineId: String) {
        _archiveContext.value = lineId to machineId
    }

    init {
        viewModelScope.launch {
            productDao.getAll().first().let {
                if (it.isEmpty()) {
                    productDao.insert(Product("ART001", "Mattone 25x12x5", 2500.0, 25.0, 12.0, 5.0, 27.7, 10.0))
                }
            }
            mattonieraDao.getAllCloggingReasons().first().let {
                if (it.isEmpty()) {
                    listOf("Sasso", "Legno", "Ferro", "Plastica").forEach { reason ->
                        mattonieraDao.insertCloggingReason(CloggingReason(reason))
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val exitChecks: StateFlow<List<ExitQualityCheck>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getExitChecks(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val measurements: StateFlow<List<MeasurementCheck>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getMeasurements(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val rollerCleanings: StateFlow<List<RollerCleaning>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getRollerCleanings(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val cloggingRemovals: StateFlow<List<CloggingRemoval>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getCloggingRemovals(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val productionDataList: StateFlow<List<MattonieraProductionData>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getProductionData(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val scrapRecords: StateFlow<List<ScrapRecord>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getScrapRecords(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val cageEvents: StateFlow<List<CageEvent>> = activeReport
        .flatMapLatest { report ->
            if (report != null) mattonieraDao.getCageEvents(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cloggingReasons: StateFlow<List<CloggingReason>> = mattonieraDao.getAllCloggingReasons()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cloggingStats: StateFlow<List<Pair<String, Double>>> = cloggingRemovals.map { removals ->
        if (removals.isEmpty()) emptyList()
        else {
            val total = removals.size.toDouble()
            removals.groupBy { it.reason }
                .map { (reason, list) -> reason to (list.size / total) * 100.0 }
                .sortedByDescending { it.second }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val cloggingHourlyData: StateFlow<List<Int>> = combine(activeReport, cloggingRemovals) { report, removals ->
        calculateHourlyCounts(report, removals.map { it.timestamp })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), List(8) { 0 })

    @OptIn(ExperimentalCoroutinesApi::class)
    val cagesHourlyData: StateFlow<List<Int>> = combine(activeReport, cageEvents) { report, events ->
        if (report == null || events.isEmpty()) List(8) { 0 }
        else {
            val start = report.startTime
            val hourlySums = MutableList(8) { 0 }
            events.forEach { event ->
                val hourIndex = ((event.timestamp - start) / 3600000).toInt()
                if (hourIndex in 0..7) {
                    hourlySums[hourIndex] += event.change
                }
            }
            hourlySums
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), List(8) { 0 })

    @OptIn(ExperimentalCoroutinesApi::class)
    val scrapHourlyData: StateFlow<List<Double>> = combine(activeReport, scrapRecords) { report, records ->
        if (report == null) List(8) { 0.0 }
        else {
            val start = report.startTime
            val hourlyMax = MutableList(8) { 0.0 }
            records.forEach { record ->
                val hourIndex = ((record.timestamp - start) / 3600000).toInt()
                if (hourIndex in 0..7) {
                    hourlyMax[hourIndex] = maxOf(hourlyMax[hourIndex], record.plcValue)
                }
            }
            for (i in 1 until hourlyMax.size) {
                if (hourlyMax[i] == 0.0) {
                    hourlyMax[i] = hourlyMax[i - 1]
                }
            }
            hourlyMax
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), List(8) { 0.0 })

    private fun calculateHourlyCounts(report: MattonieraReport?, timestamps: List<Long>): List<Int> {
        if (report == null || timestamps.isEmpty()) return List(8) { 0 }
        val start = report.startTime
        val hourlyCounts = MutableList(8) { 0 }
        timestamps.forEach { ts ->
            val hourIndex = ((ts - start) / 3600000).toInt()
            if (hourIndex in 0..7) {
                hourlyCounts[hourIndex]++
            }
        }
        return hourlyCounts
    }

    fun setReport(reportId: String?) {
        _currentReportId.value = reportId
    }

    fun clearError() {
        _validationError.value = null
    }

    fun setArchiveFilter(filter: StopFilter, date: Long? = null) {
        _archiveFilter.value = filter
        _archiveCustomDate.value = date
    }

    fun startShift(shift: String, operatorId: String, productId: String, dieId: String, sigla: String, lineId: String, machineId: String, customStartTime: Long? = null) {
        viewModelScope.launch {
            val now = com.example.man_app.util.getNowMillis()
            val startTime = customStartTime ?: now
            val newReport = MattonieraReport(
                date = now,
                shift = shift,
                operatorId = operatorId,
                productId = productId,
                dieId = dieId,
                sigla = sigla,
                startTime = startTime,
                lineId = lineId,
                machineId = machineId
            )
            mattonieraDao.insertReport(newReport)
            _currentReportId.value = newReport.id
            syncTrigger?.triggerSync()
        }
    }

    private suspend fun validateCurrentReport(): Boolean {
        val report = activeReport.value ?: return false
        
        if (!report.dimaCheckStart) {
            _validationError.value = "Manca il controllo Dima Inizio."
            return false
        }
        if (exitChecks.value.isEmpty()) {
            _validationError.value = "Inserire almeno un Controllo Uscita."
            return false
        }
        if (measurements.value.isEmpty()) {
            _validationError.value = "Inserire almeno una Misura e Peso."
            return false
        }
        if (report.cagesProduced <= 0) {
            _validationError.value = "Inserire almeno una Gabbia prodotta."
            return false
        }
        if (productionDataList.value.isEmpty()) {
            _validationError.value = "Inserire almeno un dato Dosatore/Assorbimento."
            return false
        }
        
        return true
    }

    fun closeShift() {
        val current = activeReport.value ?: return
        viewModelScope.launch {
            if (validateCurrentReport()) {
                mattonieraDao.updateReport(current.copy(
                    isClosed = true,
                    endTime = com.example.man_app.util.getNowMillis()
                ))
                // Fine turno: mantiene Articolo/Filiera, resetta il resto
                _preFillData.value = ShiftPreFillData(
                    product = current.productId,
                    die = current.dieId
                )
                _currentReportId.value = null
            }
        }
    }

    fun changeProduction() {
        val current = activeReport.value ?: return
        viewModelScope.launch {
            if (validateCurrentReport()) {
                mattonieraDao.updateReport(current.copy(
                    isClosed = true,
                    endTime = com.example.man_app.util.getNowMillis()
                ))
                // Cambio Prod: mantiene Op/Turno/Sigla, resetta il resto
                _preFillData.value = ShiftPreFillData(
                    operator = current.operatorId,
                    shift = current.shift,
                    sigla = current.sigla ?: ""
                )
                _currentReportId.value = null
            }
        }
    }

    fun updateReport(report: MattonieraReport) {
        viewModelScope.launch {
            mattonieraDao.updateReport(report)
        }
    }

    fun addExitCheck(isOk: Boolean, temp: Int, pressure: Int) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertExitCheck(
                ExitQualityCheck(
                    reportId = report.id,
                    timestamp = com.example.man_app.util.getNowMillis(),
                    isOk = isOk,
                    temperature = temp,
                    pressure = pressure
                )
            )
        }
    }

    fun addMeasurement(h: Double, la: Double, t: Double, d1: Double, d2: Double, weight: Double, isDiagOk: Boolean) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertMeasurement(
                MeasurementCheck(
                    reportId = report.id,
                    timestamp = com.example.man_app.util.getNowMillis(),
                    height = h,
                    width = la,
                    thickness = t,
                    diagonal1 = d1,
                    diagonal2 = d2,
                    weight = weight,
                    isDiagonalOk = isDiagOk
                )
            )
        }
    }

    fun addRollerCleaning() {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertRollerCleaning(RollerCleaning(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis()))
        }
    }

    fun addCloggingRemoval(reasonText: String) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertCloggingReason(CloggingReason(reasonText))
            mattonieraDao.insertCloggingRemoval(CloggingRemoval(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis(), reason = reasonText))
        }
    }

    fun updateCloggingRemoval(removal: CloggingRemoval) {
        viewModelScope.launch {
            mattonieraDao.updateCloggingRemoval(removal)
        }
    }

    fun deleteCloggingRemoval(removal: CloggingRemoval) {
        viewModelScope.launch {
            mattonieraDao.deleteCloggingRemoval(removal)
        }
    }

    fun addProductionData(giri: Int, giriOk: Boolean, ampere: Int, ampOk: Boolean) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertProductionData(
                MattonieraProductionData(
                    reportId = report.id,
                    timestamp = com.example.man_app.util.getNowMillis(),
                    doserGiri = giri,
                    isDoserOk = giriOk,
                    absorptionAmpere = ampere,
                    isAbsorptionOk = ampOk
                )
            )
        }
    }

    fun addScrapRecord(plcValue: Double) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertScrapRecord(
                ScrapRecord(
                    reportId = report.id,
                    timestamp = com.example.man_app.util.getNowMillis(),
                    plcValue = plcValue
                )
            )
            updateReport(report.copy(scrapMinutes = plcValue.toInt()))
        }
    }

    fun addCage() {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.insertCageEvent(CageEvent(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis(), change = 1))
            mattonieraDao.updateReport(report.copy(cagesProduced = report.cagesProduced + 1))
        }
    }

    fun removeCage() {
        val report = activeReport.value ?: return
        if (report.cagesProduced > 0) {
            viewModelScope.launch {
                mattonieraDao.insertCageEvent(CageEvent(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis(), change = -1))
                mattonieraDao.updateReport(report.copy(cagesProduced = report.cagesProduced - 1))
            }
        }
    }

    fun updateCageEvent(event: CageEvent) {
        viewModelScope.launch {
            mattonieraDao.updateCageEvent(event)
        }
    }

    fun deleteCageEvent(event: CageEvent) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            mattonieraDao.deleteCageEvent(event)
            // Re-sync the main report counter
            val events = mattonieraDao.getCageEvents(report.id).first()
            val total = events.sumOf { it.change }
            mattonieraDao.updateReport(report.copy(cagesProduced = total))
        }
    }
}
