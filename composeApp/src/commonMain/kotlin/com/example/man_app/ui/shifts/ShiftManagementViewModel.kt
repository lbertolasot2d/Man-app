package com.example.man_app.ui.shifts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.man_app.data.webdb.ShiftDao
import com.example.man_app.data.model.ShiftConfiguration
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShiftManagementViewModel(private val shiftDao: ShiftDao) : ViewModel() {

    private val _lineId = MutableStateFlow<String?>(null)
    
    val shifts: StateFlow<List<ShiftConfiguration>> = _lineId
        .flatMapLatest { line ->
            if (line == null) flowOf(emptyList())
            else shiftDao.getShiftsForLine(line)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setLine(line: String) {
        _lineId.value = line
    }

    fun saveShift(shift: ShiftConfiguration) {
        viewModelScope.launch {
            shiftDao.insert(shift)
        }
    }

    fun deleteShift(shift: ShiftConfiguration) {
        viewModelScope.launch {
            shiftDao.delete(shift)
        }
    }
}
