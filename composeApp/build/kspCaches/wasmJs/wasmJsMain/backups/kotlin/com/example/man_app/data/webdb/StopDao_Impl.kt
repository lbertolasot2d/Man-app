package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.StopEvent
import com.example.man_app.`data`.model.StopSuggestion
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL", "MemberExtensionConflict"])
internal class StopDao_Impl(
  __db: RoomDatabase,
) : StopDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfStopEvent: EntityInsertAdapter<StopEvent>

  private val __insertAdapterOfStopSuggestion: EntityInsertAdapter<StopSuggestion>

  private val __deleteAdapterOfStopEvent: EntityDeleteOrUpdateAdapter<StopEvent>

  private val __updateAdapterOfStopEvent: EntityDeleteOrUpdateAdapter<StopEvent>
  init {
    this.__db = __db
    this.__insertAdapterOfStopEvent = object : EntityInsertAdapter<StopEvent>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `stop_events` (`id`,`startTime`,`endTime`,`stopType`,`activities`,`machineId`,`lineId`,`isFinished`,`operatorId`,`productId`,`sigla`,`shiftName`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StopEvent) {
        statement.bindText(1, entity.id)
        statement.bindLong(2, entity.startTime)
        val _tmpEndTime: Long? = entity.endTime
        if (_tmpEndTime == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpEndTime)
        }
        statement.bindText(4, entity.stopType)
        statement.bindText(5, entity.activities)
        statement.bindText(6, entity.machineId)
        statement.bindText(7, entity.lineId)
        val _tmp: Int = if (entity.isFinished) 1 else 0
        statement.bindLong(8, _tmp.toLong())
        val _tmpOperatorId: String? = entity.operatorId
        if (_tmpOperatorId == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpOperatorId)
        }
        val _tmpProductId: String? = entity.productId
        if (_tmpProductId == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpProductId)
        }
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpSigla)
        }
        val _tmpShiftName: String? = entity.shiftName
        if (_tmpShiftName == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpShiftName)
        }
        statement.bindLong(13, entity.lastUpdated)
        statement.bindText(14, entity.syncStatus)
      }
    }
    this.__insertAdapterOfStopSuggestion = object : EntityInsertAdapter<StopSuggestion>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `stop_suggestions` (`text`,`category`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StopSuggestion) {
        statement.bindText(1, entity.text)
        statement.bindText(2, entity.category)
        statement.bindLong(3, entity.lastUpdated)
        statement.bindText(4, entity.syncStatus)
      }
    }
    this.__deleteAdapterOfStopEvent = object : EntityDeleteOrUpdateAdapter<StopEvent>() {
      protected override fun createQuery(): String = "DELETE FROM `stop_events` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: StopEvent) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfStopEvent = object : EntityDeleteOrUpdateAdapter<StopEvent>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `stop_events` SET `id` = ?,`startTime` = ?,`endTime` = ?,`stopType` = ?,`activities` = ?,`machineId` = ?,`lineId` = ?,`isFinished` = ?,`operatorId` = ?,`productId` = ?,`sigla` = ?,`shiftName` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: StopEvent) {
        statement.bindText(1, entity.id)
        statement.bindLong(2, entity.startTime)
        val _tmpEndTime: Long? = entity.endTime
        if (_tmpEndTime == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpEndTime)
        }
        statement.bindText(4, entity.stopType)
        statement.bindText(5, entity.activities)
        statement.bindText(6, entity.machineId)
        statement.bindText(7, entity.lineId)
        val _tmp: Int = if (entity.isFinished) 1 else 0
        statement.bindLong(8, _tmp.toLong())
        val _tmpOperatorId: String? = entity.operatorId
        if (_tmpOperatorId == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpOperatorId)
        }
        val _tmpProductId: String? = entity.productId
        if (_tmpProductId == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpProductId)
        }
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpSigla)
        }
        val _tmpShiftName: String? = entity.shiftName
        if (_tmpShiftName == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpShiftName)
        }
        statement.bindLong(13, entity.lastUpdated)
        statement.bindText(14, entity.syncStatus)
        statement.bindText(15, entity.id)
      }
    }
  }

  public override suspend fun insertStop(stop: StopEvent): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfStopEvent.insertAndReturnId(_connection, stop)
    _result
  }

  public override suspend fun insertSuggestion(suggestion: StopSuggestion): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfStopSuggestion.insert(_connection, suggestion)
  }

  public override suspend fun deleteStop(stop: StopEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfStopEvent.handle(_connection, stop)
  }

  public override suspend fun updateStop(stop: StopEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfStopEvent.handle(_connection, stop)
  }

  public override fun getActiveStop(): Flow<StopEvent?> {
    val _sql: String = "SELECT * FROM stop_events WHERE isFinished = 0 LIMIT 1"
    return createFlow(__db, false, arrayOf("stop_events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfStopType: Int = getColumnIndexOrThrow(_stmt, "stopType")
        val _columnIndexOfActivities: Int = getColumnIndexOrThrow(_stmt, "activities")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfIsFinished: Int = getColumnIndexOrThrow(_stmt, "isFinished")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfShiftName: Int = getColumnIndexOrThrow(_stmt, "shiftName")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: StopEvent?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpStopType: String
          _tmpStopType = _stmt.getText(_columnIndexOfStopType)
          val _tmpActivities: String
          _tmpActivities = _stmt.getText(_columnIndexOfActivities)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpIsFinished: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFinished).toInt()
          _tmpIsFinished = _tmp != 0
          val _tmpOperatorId: String?
          if (_stmt.isNull(_columnIndexOfOperatorId)) {
            _tmpOperatorId = null
          } else {
            _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          }
          val _tmpProductId: String?
          if (_stmt.isNull(_columnIndexOfProductId)) {
            _tmpProductId = null
          } else {
            _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          }
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpShiftName: String?
          if (_stmt.isNull(_columnIndexOfShiftName)) {
            _tmpShiftName = null
          } else {
            _tmpShiftName = _stmt.getText(_columnIndexOfShiftName)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _result = StopEvent(_tmpId,_tmpStartTime,_tmpEndTime,_tmpStopType,_tmpActivities,_tmpMachineId,_tmpLineId,_tmpIsFinished,_tmpOperatorId,_tmpProductId,_tmpSigla,_tmpShiftName,_tmpLastUpdated,_tmpSyncStatus)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllStops(): Flow<List<StopEvent>> {
    val _sql: String = "SELECT * FROM stop_events ORDER BY startTime DESC"
    return createFlow(__db, false, arrayOf("stop_events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfStopType: Int = getColumnIndexOrThrow(_stmt, "stopType")
        val _columnIndexOfActivities: Int = getColumnIndexOrThrow(_stmt, "activities")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfIsFinished: Int = getColumnIndexOrThrow(_stmt, "isFinished")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfShiftName: Int = getColumnIndexOrThrow(_stmt, "shiftName")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<StopEvent> = mutableListOf()
        while (_stmt.step()) {
          val _item: StopEvent
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpStopType: String
          _tmpStopType = _stmt.getText(_columnIndexOfStopType)
          val _tmpActivities: String
          _tmpActivities = _stmt.getText(_columnIndexOfActivities)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpIsFinished: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFinished).toInt()
          _tmpIsFinished = _tmp != 0
          val _tmpOperatorId: String?
          if (_stmt.isNull(_columnIndexOfOperatorId)) {
            _tmpOperatorId = null
          } else {
            _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          }
          val _tmpProductId: String?
          if (_stmt.isNull(_columnIndexOfProductId)) {
            _tmpProductId = null
          } else {
            _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          }
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpShiftName: String?
          if (_stmt.isNull(_columnIndexOfShiftName)) {
            _tmpShiftName = null
          } else {
            _tmpShiftName = _stmt.getText(_columnIndexOfShiftName)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = StopEvent(_tmpId,_tmpStartTime,_tmpEndTime,_tmpStopType,_tmpActivities,_tmpMachineId,_tmpLineId,_tmpIsFinished,_tmpOperatorId,_tmpProductId,_tmpSigla,_tmpShiftName,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSuggestions(category: String): Flow<List<StopSuggestion>> {
    val _sql: String = "SELECT * FROM stop_suggestions WHERE category = ?"
    return createFlow(__db, false, arrayOf("stop_suggestions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, category)
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<StopSuggestion> = mutableListOf()
        while (_stmt.step()) {
          val _item: StopSuggestion
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = StopSuggestion(_tmpText,_tmpCategory,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedStops(): Flow<List<StopEvent>> {
    val _sql: String = "SELECT * FROM stop_events WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("stop_events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfStopType: Int = getColumnIndexOrThrow(_stmt, "stopType")
        val _columnIndexOfActivities: Int = getColumnIndexOrThrow(_stmt, "activities")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfIsFinished: Int = getColumnIndexOrThrow(_stmt, "isFinished")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfShiftName: Int = getColumnIndexOrThrow(_stmt, "shiftName")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<StopEvent> = mutableListOf()
        while (_stmt.step()) {
          val _item: StopEvent
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpStopType: String
          _tmpStopType = _stmt.getText(_columnIndexOfStopType)
          val _tmpActivities: String
          _tmpActivities = _stmt.getText(_columnIndexOfActivities)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpIsFinished: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsFinished).toInt()
          _tmpIsFinished = _tmp != 0
          val _tmpOperatorId: String?
          if (_stmt.isNull(_columnIndexOfOperatorId)) {
            _tmpOperatorId = null
          } else {
            _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          }
          val _tmpProductId: String?
          if (_stmt.isNull(_columnIndexOfProductId)) {
            _tmpProductId = null
          } else {
            _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          }
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpShiftName: String?
          if (_stmt.isNull(_columnIndexOfShiftName)) {
            _tmpShiftName = null
          } else {
            _tmpShiftName = _stmt.getText(_columnIndexOfShiftName)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = StopEvent(_tmpId,_tmpStartTime,_tmpEndTime,_tmpStopType,_tmpActivities,_tmpMachineId,_tmpLineId,_tmpIsFinished,_tmpOperatorId,_tmpProductId,_tmpSigla,_tmpShiftName,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredColumnConverters(): List<KClass<*>> = emptyList()

    public fun getRequiredDaoReturnTypeConverters(): List<KClass<*>> = emptyList()
  }
}
