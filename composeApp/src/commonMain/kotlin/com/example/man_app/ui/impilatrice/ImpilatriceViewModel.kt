package com.example.man_app.ui.impilatrice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.man_app.data.webdb.ImpilatriceDao
import com.example.man_app.data.webdb.MattonieraDao
import com.example.man_app.data.model.*
import com.example.man_app.ui.fermate.StopFilter
import com.example.man_app.ui.mattoniera.ShiftPreFillData
import com.example.man_app.util.getEndOfDay
import com.example.man_app.util.getStartOfDay
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ImpilatriceViewModel(
    private val impilatriceDao: ImpilatriceDao,
    private val mattonieraDao: MattonieraDao,
    private val personnelDao: com.example.man_app.data.webdb.PersonnelDao,
    private val shiftDao: com.example.man_app.data.webdb.ShiftDao,
    private val syncTrigger: com.example.man_app.SyncTrigger? = null
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
    val activeReport: StateFlow<ImpilatriceReport?> = _currentReportId
        .flatMapLatest { id ->
            if (id != null) impilatriceDao.getReportById(id)
            else flowOf(null)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError = _validationError.asStateFlow()

    private val _preFillData = MutableStateFlow(ShiftPreFillData())
    val preFillData = _preFillData.asStateFlow()

    private val _archiveContext = MutableStateFlow<Pair<String, String>?>(null)
    private val _archiveFilter = MutableStateFlow(StopFilter.CURRENT_SHIFT)
    val archiveFilter = _archiveFilter.asStateFlow()

    private val _archiveCustomDate = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val reportsArchive: StateFlow<List<ImpilatriceReport>> = _archiveContext
        .flatMapLatest { context ->
            if (context == null) flowOf(emptyList())
            else impilatriceDao.getReportsByContext(context.first, context.second)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val kilnCars: StateFlow<List<ImpilatriceKilnCar>> = activeReport
        .flatMapLatest { report ->
            if (report != null) impilatriceDao.getKilnCars(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val cageEvents: StateFlow<List<ImpilatriceCageEvent>> = activeReport
        .flatMapLatest { report ->
            if (report != null) impilatriceDao.getCageEvents(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val kilnCarsHourlyData: StateFlow<List<Int>> = combine(activeReport, kilnCars) { report, cars ->
        if (report == null || cars.isEmpty()) List(8) { 0 }
        else {
            val start = report.startTime
            val hourlySums = MutableList(8) { 0 }
            cars.forEach { car ->
                val ts = car.endTime ?: car.startTime
                val hourIndex = ((ts - start) / 3600000).toInt()
                if (hourIndex in 0..7) {
                    hourlySums[hourIndex]++
                }
            }
            hourlySums
        }
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
    val scrapRecords: StateFlow<List<ImpilatriceScrapRecord>> = activeReport
        .flatMapLatest { report ->
            if (report != null) impilatriceDao.getScrapRecords(report.id) else flowOf(emptyList())
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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

    fun loadArchive(lineId: String, machineId: String) {
        _archiveContext.value = lineId to machineId
    }

    fun getMattonieraProducts(lineId: String): Flow<List<String>> = mattonieraDao.getUniqueProductsForLine(lineId)
    fun getMattonieraSigle(lineId: String): Flow<List<String>> = mattonieraDao.getUniqueSigleForLine(lineId).map { it.filterNotNull() }

    fun setReport(id: String?) { _currentReportId.value = id }
    fun setArchiveFilter(f: StopFilter, d: Long? = null) { _archiveFilter.value = f; _archiveCustomDate.value = d }
    fun clearError() { _validationError.value = null }

    fun updateReport(report: ImpilatriceReport) {
        viewModelScope.launch {
            impilatriceDao.updateReport(report)
            syncTrigger?.triggerSync()
        }
    }

    fun startShift(shift: String, op: String, prod: String, sigla: String, line: String, machine: String, cTime: Long? = null) {
        viewModelScope.launch {
            val now = com.example.man_app.util.getNowMillis()
            val newReport = ImpilatriceReport(
                date = now,
                startTime = cTime ?: now,
                operatorId = op,
                productId = prod,
                shift = shift,
                sigla = sigla,
                lineId = line,
                machineId = machine
            )
            impilatriceDao.insertReport(newReport)
            _currentReportId.value = newReport.id
            syncTrigger?.triggerSync()
        }
    }

    private fun validateCurrentReport(): Boolean {
        val report = activeReport.value ?: return false
        if (kilnCars.value.isEmpty()) {
            _validationError.value = "Inserire almeno un Carro prodotto."
            return false
        }
        return true
    }

    fun closeShift() {
        val current = activeReport.value ?: return
        viewModelScope.launch {
            if (validateCurrentReport()) {
                impilatriceDao.updateReport(current.copy(isClosed = true, endTime = com.example.man_app.util.getNowMillis()))
                // Fine turno: mantiene Articolo/Filiera, resetta il resto
                _preFillData.value = ShiftPreFillData(product = current.productId, die = "")
                _currentReportId.value = null
                syncTrigger?.triggerSync()
            }
        }
    }

    fun changeProduction() {
        val current = activeReport.value ?: return
        viewModelScope.launch {
            if (validateCurrentReport()) {
                impilatriceDao.updateReport(current.copy(isClosed = true, endTime = com.example.man_app.util.getNowMillis()))
                // Cambio Prod: mantiene Op/Turno/Sigla, resetta il resto
                _preFillData.value = ShiftPreFillData(operator = current.operatorId, shift = current.shift, sigla = current.sigla ?: "")
                _currentReportId.value = null
                syncTrigger?.triggerSync()
            }
        }
    }

    fun finishKilnCar(
        cleaned: Boolean, 
        load: Int, 
        refrOk: Boolean,
        height: Double = 0.0,
        width: Double = 0.0,
        thickness: Double = 0.0,
        weight: Double = 0.0,
        diagOk: Boolean = true,
        drying: String = "Essiccato",
        chips: String = "Nessuna",
        cracks: String = "Nessuna",
        hairlines: String = "Nessuna",
        breaks: String = "Nessuna",
        notes: String = "",
        sigla: String? = null
    ) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            val cars = kilnCars.value
            val now = com.example.man_app.util.getNowMillis()
            
            val startTime = if (cars.isEmpty()) report.startTime else cars.last().endTime ?: report.startTime
            
            val newCar = ImpilatriceKilnCar(
                reportId = report.id,
                startTime = startTime,
                endTime = now,
                sigla = sigla,
                isCleaned = cleaned,
                loadPercentage = load,
                refractoriesOk = refrOk,
                height = height,
                width = width,
                thickness = thickness,
                weight = weight,
                isDiagonalOk = diagOk,
                dryingRating = drying,
                chipsRating = chips,
                cracksRating = cracks,
                hairlinesRating = hairlines,
                breaksRating = breaks,
                qualityNotes = notes
            )
            impilatriceDao.insertKilnCar(newCar)
            
            impilatriceDao.updateReport(report.copy(cagesProduced = cars.size + 1))
            syncTrigger?.triggerSync()
        }
    }

    fun updateKilnCar(car: ImpilatriceKilnCar) {
        viewModelScope.launch {
            impilatriceDao.updateKilnCar(car)
        }
    }

    fun deleteKilnCar(car: ImpilatriceKilnCar) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            impilatriceDao.deleteKilnCar(car)
            val updatedCars = impilatriceDao.getKilnCars(report.id).first()
            impilatriceDao.updateReport(report.copy(cagesProduced = updatedCars.size))
        }
    }

    fun addScrapRecord(plcValue: Double) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            impilatriceDao.insertScrapRecord(ImpilatriceScrapRecord(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis(), plcValue = plcValue))
            impilatriceDao.updateReport(report.copy(scrapMinutes = plcValue.toInt()))
            syncTrigger?.triggerSync()
        }
    }

    fun addCage() {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            impilatriceDao.insertCageEvent(ImpilatriceCageEvent(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis(), change = 1))
            impilatriceDao.updateReport(report.copy(cagesProduced = report.cagesProduced + 1))
            syncTrigger?.triggerSync()
        }
    }

    fun removeCage() {
        val report = activeReport.value ?: return
        if (report.cagesProduced > 0) {
            viewModelScope.launch {
                impilatriceDao.insertCageEvent(ImpilatriceCageEvent(reportId = report.id, timestamp = com.example.man_app.util.getNowMillis(), change = -1))
                impilatriceDao.updateReport(report.copy(cagesProduced = report.cagesProduced - 1))
                syncTrigger?.triggerSync()
            }
        }
    }

    fun updateCageEvent(event: ImpilatriceCageEvent) {
        viewModelScope.launch {
            impilatriceDao.updateCageEvent(event)
        }
    }

    fun deleteCageEvent(event: ImpilatriceCageEvent) {
        val report = activeReport.value ?: return
        viewModelScope.launch {
            impilatriceDao.deleteCageEvent(event)
            val events = impilatriceDao.getCageEvents(report.id).first()
            val total = events.sumOf { it.change }
            impilatriceDao.updateReport(report.copy(cagesProduced = total))
        }
    }
}
