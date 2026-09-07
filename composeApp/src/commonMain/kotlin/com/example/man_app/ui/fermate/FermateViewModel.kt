package com.example.man_app.ui.fermate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.man_app.data.webdb.StopDao
import com.example.man_app.data.model.StopEvent
import com.example.man_app.data.model.StopSuggestion
import com.example.man_app.util.getEndOfDay
import com.example.man_app.util.getStartOfDay
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class StopFilter {
    CURRENT_SHIFT, LAST_24H, LAST_WEEK, CUSTOM_DATE
}

class FermateViewModel(private val stopDao: StopDao) : ViewModel() {

    private val _machineId = MutableStateFlow<String?>(null)
    private val _lineId = MutableStateFlow<String?>(null)

    private val _filter = MutableStateFlow(StopFilter.CURRENT_SHIFT)
    val filter = _filter.asStateFlow()

    private val _customDate = MutableStateFlow<Long?>(null)
    val customDate = _customDate.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeStop: StateFlow<StopEvent?> = combine(_machineId, _lineId) { m, l -> m to l }
        .flatMapLatest { (m, l) ->
            if (m == null || l == null) flowOf(null)
            else stopDao.getActiveStop().map { stop ->
                if (stop?.machineId == m && stop.lineId == l) stop else null
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val stopHistory: StateFlow<List<StopEvent>> = combine(
        stopDao.getAllStops(),
        _filter,
        _customDate,
        _machineId,
        _lineId
    ) { allStops, filter, customDate, mId, lId ->
        if (mId == null || lId == null) return@combine emptyList<StopEvent>()
        
        val now = com.example.man_app.util.getNowMillis()
        val contextStops = allStops.filter { it.machineId == mId && it.lineId == lId }
        val finishedStops = contextStops.filter { it.isFinished }
        
        when (filter) {
            StopFilter.CURRENT_SHIFT -> {
                // For now, assume current shift is since last 8 hours if we don't have report context here
                // In production, we'd filter by the actual reportId
                finishedStops.filter { it.startTime > now - 8 * 3600000 }
            }
            StopFilter.LAST_24H -> {
                finishedStops.filter { it.startTime > now - 24 * 3600000 }
            }
            StopFilter.LAST_WEEK -> {
                finishedStops.filter { it.startTime > now - 7 * 24 * 3600000 }
            }
            StopFilter.CUSTOM_DATE -> {
                if (customDate == null) finishedStops
                else {
                    val startOfDay = getStartOfDay(customDate)
                    val endOfDay = getEndOfDay(customDate)
                    finishedStops.filter { it.startTime in startOfDay..endOfDay }
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val typeSuggestions: StateFlow<List<String>> = stopDao.getSuggestions("TYPE")
        .map { list -> list.map { it.text } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activitySuggestions: StateFlow<List<String>> = stopDao.getSuggestions("ACTIVITY")
        .map { list -> list.map { it.text } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setFilter(newFilter: StopFilter, date: Long? = null) {
        _filter.value = newFilter
        _customDate.value = date
    }

    fun setContext(machineId: String, lineId: String) {
        _machineId.value = machineId
        _lineId.value = lineId
    }

    fun startStop(machineId: String, lineId: String, operator: String?, product: String?, sigla: String?, shift: String?) {
        viewModelScope.launch {
            val newStop = StopEvent(
                startTime = com.example.man_app.util.getNowMillis(),
                machineId = machineId,
                lineId = lineId,
                operatorId = operator,
                productId = product,
                sigla = sigla,
                shiftName = shift
            )
            stopDao.insertStop(newStop)
        }
    }

    fun updateStop(updated: StopEvent) {
        viewModelScope.launch {
            stopDao.updateStop(updated)
            // If it's being finished, save suggestions
            if (updated.isFinished) {
                if (updated.stopType.isNotBlank()) {
                    stopDao.insertSuggestion(StopSuggestion(updated.stopType.uppercase(), "TYPE"))
                }
                if (updated.activities.isNotBlank()) {
                    stopDao.insertSuggestion(StopSuggestion(updated.activities.uppercase(), "ACTIVITY"))
                }
            }
        }
    }

    fun deleteStop(stop: StopEvent) {
        viewModelScope.launch {
            stopDao.deleteStop(stop)
        }
    }

    fun finishStop(finalType: String? = null, finalActivities: String? = null) {
        activeStop.value?.let { stop ->
            val updatedStop = stop.copy(
                stopType = finalType ?: stop.stopType,
                activities = finalActivities ?: stop.activities,
                endTime = com.example.man_app.util.getNowMillis(),
                isFinished = true
            )
            updateStop(updatedStop)
        }
    }
}
