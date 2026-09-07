package com.example.man_app.ui.personnel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.man_app.data.webdb.PersonnelDao
import com.example.man_app.data.model.Personnel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.man_app.SyncTrigger

class PersonnelViewModel(
    private val personnelDao: com.example.man_app.data.webdb.PersonnelDao,
    private val syncTrigger: SyncTrigger? = null
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _lineFilter = MutableStateFlow<String?>(null)
    val lineFilter = _lineFilter.asStateFlow()

    private val _deptFilter = MutableStateFlow<String?>(null)
    val deptFilter = _deptFilter.asStateFlow()

    val personnelList: StateFlow<List<Personnel>> = personnelDao.getAll()
        .combine(_searchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter { 
                it.firstName.contains(query, ignoreCase = true) || 
                it.lastName.contains(query, ignoreCase = true) ||
                it.id.contains(query, ignoreCase = true)
            }
        }
        .combine(_lineFilter) { list, line ->
            if (line == null) list
            else list.filter { it.line == line }
        }
        .combine(_deptFilter) { list, dept ->
            if (dept == null) list
            else list.filter { it.department == dept }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableLines: StateFlow<List<String>> = personnelDao.getAll()
        .map { list -> list.map { it.line }.distinct().filter { it.isNotBlank() }.sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableDepts: StateFlow<List<String>> = personnelDao.getAll()
        .map { list -> list.map { it.department }.distinct().filter { it.isNotBlank() }.sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearchQuery(query: String) { _searchQuery.value = query }
    fun setLineFilter(line: String?) { _lineFilter.value = line }
    fun setDeptFilter(dept: String?) { _deptFilter.value = dept }

    fun addPersonnel(p: Personnel) {
        viewModelScope.launch {
            personnelDao.insert(p.copy(syncStatus = com.example.man_app.data.model.SyncStatus.PENDING))
            syncTrigger?.triggerSync()
        }
    }

    fun updatePersonnel(p: Personnel) {
        viewModelScope.launch {
            personnelDao.update(p.copy(syncStatus = com.example.man_app.data.model.SyncStatus.PENDING, lastUpdated = com.example.man_app.util.getNowMillis()))
            syncTrigger?.triggerSync()
        }
    }

    fun deletePersonnel(p: Personnel) {
        viewModelScope.launch {
            // Soft delete or real delete? For sync, soft delete is better.
            // For now, just real delete but it won't sync the deletion easily without extra logic.
            personnelDao.delete(p)
            syncTrigger?.triggerSync()
        }
    }
}
