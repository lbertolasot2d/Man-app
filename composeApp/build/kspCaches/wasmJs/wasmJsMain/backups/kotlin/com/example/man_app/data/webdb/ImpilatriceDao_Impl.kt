package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.ImpilatriceCageEvent
import com.example.man_app.`data`.model.ImpilatriceKilnCar
import com.example.man_app.`data`.model.ImpilatriceReport
import com.example.man_app.`data`.model.ImpilatriceScrapRecord
import kotlin.Boolean
import kotlin.Double
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
internal class ImpilatriceDao_Impl(
  __db: RoomDatabase,
) : ImpilatriceDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfImpilatriceReport: EntityInsertAdapter<ImpilatriceReport>

  private val __insertAdapterOfImpilatriceCageEvent: EntityInsertAdapter<ImpilatriceCageEvent>

  private val __insertAdapterOfImpilatriceScrapRecord: EntityInsertAdapter<ImpilatriceScrapRecord>

  private val __insertAdapterOfImpilatriceKilnCar: EntityInsertAdapter<ImpilatriceKilnCar>

  private val __deleteAdapterOfImpilatriceCageEvent:
      EntityDeleteOrUpdateAdapter<ImpilatriceCageEvent>

  private val __deleteAdapterOfImpilatriceKilnCar: EntityDeleteOrUpdateAdapter<ImpilatriceKilnCar>

  private val __updateAdapterOfImpilatriceReport: EntityDeleteOrUpdateAdapter<ImpilatriceReport>

  private val __updateAdapterOfImpilatriceCageEvent:
      EntityDeleteOrUpdateAdapter<ImpilatriceCageEvent>

  private val __updateAdapterOfImpilatriceKilnCar: EntityDeleteOrUpdateAdapter<ImpilatriceKilnCar>
  init {
    this.__db = __db
    this.__insertAdapterOfImpilatriceReport = object : EntityInsertAdapter<ImpilatriceReport>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `impilatrice_reports` (`id`,`date`,`startTime`,`endTime`,`operatorId`,`productId`,`shift`,`sigla`,`lineId`,`machineId`,`isClosed`,`cagesProduced`,`scrapMinutes`,`cageMaterialDistribution`,`cageNotes`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceReport) {
        statement.bindText(1, entity.id)
        statement.bindLong(2, entity.date)
        statement.bindLong(3, entity.startTime)
        statement.bindLong(4, entity.endTime)
        statement.bindText(5, entity.operatorId)
        statement.bindText(6, entity.productId)
        statement.bindText(7, entity.shift)
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpSigla)
        }
        statement.bindText(9, entity.lineId)
        statement.bindText(10, entity.machineId)
        val _tmp: Int = if (entity.isClosed) 1 else 0
        statement.bindLong(11, _tmp.toLong())
        statement.bindLong(12, entity.cagesProduced.toLong())
        statement.bindLong(13, entity.scrapMinutes.toLong())
        statement.bindText(14, entity.cageMaterialDistribution)
        statement.bindText(15, entity.cageNotes)
        statement.bindLong(16, entity.lastUpdated)
        statement.bindText(17, entity.syncStatus)
      }
    }
    this.__insertAdapterOfImpilatriceCageEvent = object : EntityInsertAdapter<ImpilatriceCageEvent>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `impilatrice_cage_events` (`id`,`reportId`,`timestamp`,`change`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceCageEvent) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.change.toLong())
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
      }
    }
    this.__insertAdapterOfImpilatriceScrapRecord = object : EntityInsertAdapter<ImpilatriceScrapRecord>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `impilatrice_scrap_records` (`id`,`reportId`,`timestamp`,`plcValue`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceScrapRecord) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindDouble(4, entity.plcValue)
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
      }
    }
    this.__insertAdapterOfImpilatriceKilnCar = object : EntityInsertAdapter<ImpilatriceKilnCar>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `impilatrice_kiln_cars` (`id`,`reportId`,`startTime`,`endTime`,`sigla`,`isCleaned`,`loadPercentage`,`refractoriesOk`,`height`,`width`,`thickness`,`weight`,`isDiagonalOk`,`dryingRating`,`chipsRating`,`cracksRating`,`hairlinesRating`,`breaksRating`,`qualityNotes`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceKilnCar) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.startTime)
        val _tmpEndTime: Long? = entity.endTime
        if (_tmpEndTime == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpEndTime)
        }
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpSigla)
        }
        val _tmp: Int = if (entity.isCleaned) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.loadPercentage.toLong())
        val _tmp_1: Int = if (entity.refractoriesOk) 1 else 0
        statement.bindLong(8, _tmp_1.toLong())
        statement.bindDouble(9, entity.height)
        statement.bindDouble(10, entity.width)
        statement.bindDouble(11, entity.thickness)
        statement.bindDouble(12, entity.weight)
        val _tmp_2: Int = if (entity.isDiagonalOk) 1 else 0
        statement.bindLong(13, _tmp_2.toLong())
        statement.bindText(14, entity.dryingRating)
        statement.bindText(15, entity.chipsRating)
        statement.bindText(16, entity.cracksRating)
        statement.bindText(17, entity.hairlinesRating)
        statement.bindText(18, entity.breaksRating)
        statement.bindText(19, entity.qualityNotes)
        statement.bindLong(20, entity.lastUpdated)
        statement.bindText(21, entity.syncStatus)
      }
    }
    this.__deleteAdapterOfImpilatriceCageEvent = object : EntityDeleteOrUpdateAdapter<ImpilatriceCageEvent>() {
      protected override fun createQuery(): String = "DELETE FROM `impilatrice_cage_events` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceCageEvent) {
        statement.bindText(1, entity.id)
      }
    }
    this.__deleteAdapterOfImpilatriceKilnCar = object : EntityDeleteOrUpdateAdapter<ImpilatriceKilnCar>() {
      protected override fun createQuery(): String = "DELETE FROM `impilatrice_kiln_cars` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceKilnCar) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfImpilatriceReport = object : EntityDeleteOrUpdateAdapter<ImpilatriceReport>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `impilatrice_reports` SET `id` = ?,`date` = ?,`startTime` = ?,`endTime` = ?,`operatorId` = ?,`productId` = ?,`shift` = ?,`sigla` = ?,`lineId` = ?,`machineId` = ?,`isClosed` = ?,`cagesProduced` = ?,`scrapMinutes` = ?,`cageMaterialDistribution` = ?,`cageNotes` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceReport) {
        statement.bindText(1, entity.id)
        statement.bindLong(2, entity.date)
        statement.bindLong(3, entity.startTime)
        statement.bindLong(4, entity.endTime)
        statement.bindText(5, entity.operatorId)
        statement.bindText(6, entity.productId)
        statement.bindText(7, entity.shift)
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpSigla)
        }
        statement.bindText(9, entity.lineId)
        statement.bindText(10, entity.machineId)
        val _tmp: Int = if (entity.isClosed) 1 else 0
        statement.bindLong(11, _tmp.toLong())
        statement.bindLong(12, entity.cagesProduced.toLong())
        statement.bindLong(13, entity.scrapMinutes.toLong())
        statement.bindText(14, entity.cageMaterialDistribution)
        statement.bindText(15, entity.cageNotes)
        statement.bindLong(16, entity.lastUpdated)
        statement.bindText(17, entity.syncStatus)
        statement.bindText(18, entity.id)
      }
    }
    this.__updateAdapterOfImpilatriceCageEvent = object : EntityDeleteOrUpdateAdapter<ImpilatriceCageEvent>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `impilatrice_cage_events` SET `id` = ?,`reportId` = ?,`timestamp` = ?,`change` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceCageEvent) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.change.toLong())
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
        statement.bindText(7, entity.id)
      }
    }
    this.__updateAdapterOfImpilatriceKilnCar = object : EntityDeleteOrUpdateAdapter<ImpilatriceKilnCar>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `impilatrice_kiln_cars` SET `id` = ?,`reportId` = ?,`startTime` = ?,`endTime` = ?,`sigla` = ?,`isCleaned` = ?,`loadPercentage` = ?,`refractoriesOk` = ?,`height` = ?,`width` = ?,`thickness` = ?,`weight` = ?,`isDiagonalOk` = ?,`dryingRating` = ?,`chipsRating` = ?,`cracksRating` = ?,`hairlinesRating` = ?,`breaksRating` = ?,`qualityNotes` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ImpilatriceKilnCar) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.startTime)
        val _tmpEndTime: Long? = entity.endTime
        if (_tmpEndTime == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpEndTime)
        }
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpSigla)
        }
        val _tmp: Int = if (entity.isCleaned) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.loadPercentage.toLong())
        val _tmp_1: Int = if (entity.refractoriesOk) 1 else 0
        statement.bindLong(8, _tmp_1.toLong())
        statement.bindDouble(9, entity.height)
        statement.bindDouble(10, entity.width)
        statement.bindDouble(11, entity.thickness)
        statement.bindDouble(12, entity.weight)
        val _tmp_2: Int = if (entity.isDiagonalOk) 1 else 0
        statement.bindLong(13, _tmp_2.toLong())
        statement.bindText(14, entity.dryingRating)
        statement.bindText(15, entity.chipsRating)
        statement.bindText(16, entity.cracksRating)
        statement.bindText(17, entity.hairlinesRating)
        statement.bindText(18, entity.breaksRating)
        statement.bindText(19, entity.qualityNotes)
        statement.bindLong(20, entity.lastUpdated)
        statement.bindText(21, entity.syncStatus)
        statement.bindText(22, entity.id)
      }
    }
  }

  public override suspend fun insertReport(report: ImpilatriceReport): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfImpilatriceReport.insert(_connection, report)
  }

  public override suspend fun insertCageEvent(event: ImpilatriceCageEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfImpilatriceCageEvent.insert(_connection, event)
  }

  public override suspend fun insertScrapRecord(record: ImpilatriceScrapRecord): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfImpilatriceScrapRecord.insert(_connection, record)
  }

  public override suspend fun insertKilnCar(car: ImpilatriceKilnCar): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfImpilatriceKilnCar.insert(_connection, car)
  }

  public override suspend fun deleteCageEvent(event: ImpilatriceCageEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfImpilatriceCageEvent.handle(_connection, event)
  }

  public override suspend fun deleteKilnCar(car: ImpilatriceKilnCar): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfImpilatriceKilnCar.handle(_connection, car)
  }

  public override suspend fun updateReport(report: ImpilatriceReport): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfImpilatriceReport.handle(_connection, report)
  }

  public override suspend fun updateCageEvent(event: ImpilatriceCageEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfImpilatriceCageEvent.handle(_connection, event)
  }

  public override suspend fun updateKilnCar(car: ImpilatriceKilnCar): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfImpilatriceKilnCar.handle(_connection, car)
  }

  public override fun getReportsByContext(lineId: String, machineId: String): Flow<List<ImpilatriceReport>> {
    val _sql: String = "SELECT * FROM impilatrice_reports WHERE lineId = ? AND machineId = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("impilatrice_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, lineId)
        _argIndex = 2
        _stmt.bindText(_argIndex, machineId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCageMaterialDistribution: Int = getColumnIndexOrThrow(_stmt, "cageMaterialDistribution")
        val _columnIndexOfCageNotes: Int = getColumnIndexOrThrow(_stmt, "cageNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceReport
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpIsClosed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp != 0
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCageMaterialDistribution: String
          _tmpCageMaterialDistribution = _stmt.getText(_columnIndexOfCageMaterialDistribution)
          val _tmpCageNotes: String
          _tmpCageNotes = _stmt.getText(_columnIndexOfCageNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceReport(_tmpId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpOperatorId,_tmpProductId,_tmpShift,_tmpSigla,_tmpLineId,_tmpMachineId,_tmpIsClosed,_tmpCagesProduced,_tmpScrapMinutes,_tmpCageMaterialDistribution,_tmpCageNotes,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getReportById(reportId: String): Flow<ImpilatriceReport?> {
    val _sql: String = "SELECT * FROM impilatrice_reports WHERE id = ?"
    return createFlow(__db, false, arrayOf("impilatrice_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCageMaterialDistribution: Int = getColumnIndexOrThrow(_stmt, "cageMaterialDistribution")
        val _columnIndexOfCageNotes: Int = getColumnIndexOrThrow(_stmt, "cageNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: ImpilatriceReport?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpIsClosed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp != 0
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCageMaterialDistribution: String
          _tmpCageMaterialDistribution = _stmt.getText(_columnIndexOfCageMaterialDistribution)
          val _tmpCageNotes: String
          _tmpCageNotes = _stmt.getText(_columnIndexOfCageNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _result = ImpilatriceReport(_tmpId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpOperatorId,_tmpProductId,_tmpShift,_tmpSigla,_tmpLineId,_tmpMachineId,_tmpIsClosed,_tmpCagesProduced,_tmpScrapMinutes,_tmpCageMaterialDistribution,_tmpCageNotes,_tmpLastUpdated,_tmpSyncStatus)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCageEvents(reportId: String): Flow<List<ImpilatriceCageEvent>> {
    val _sql: String = "SELECT * FROM impilatrice_cage_events WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("impilatrice_cage_events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfChange: Int = getColumnIndexOrThrow(_stmt, "change")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceCageEvent> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceCageEvent
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpChange: Int
          _tmpChange = _stmt.getLong(_columnIndexOfChange).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceCageEvent(_tmpId,_tmpReportId,_tmpTimestamp,_tmpChange,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getScrapRecords(reportId: String): Flow<List<ImpilatriceScrapRecord>> {
    val _sql: String = "SELECT * FROM impilatrice_scrap_records WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("impilatrice_scrap_records")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfPlcValue: Int = getColumnIndexOrThrow(_stmt, "plcValue")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceScrapRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceScrapRecord
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpPlcValue: Double
          _tmpPlcValue = _stmt.getDouble(_columnIndexOfPlcValue)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceScrapRecord(_tmpId,_tmpReportId,_tmpTimestamp,_tmpPlcValue,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getKilnCars(reportId: String): Flow<List<ImpilatriceKilnCar>> {
    val _sql: String = "SELECT * FROM impilatrice_kiln_cars WHERE reportId = ? ORDER BY startTime ASC"
    return createFlow(__db, false, arrayOf("impilatrice_kiln_cars")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfIsCleaned: Int = getColumnIndexOrThrow(_stmt, "isCleaned")
        val _columnIndexOfLoadPercentage: Int = getColumnIndexOrThrow(_stmt, "loadPercentage")
        val _columnIndexOfRefractoriesOk: Int = getColumnIndexOrThrow(_stmt, "refractoriesOk")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWidth: Int = getColumnIndexOrThrow(_stmt, "width")
        val _columnIndexOfThickness: Int = getColumnIndexOrThrow(_stmt, "thickness")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfIsDiagonalOk: Int = getColumnIndexOrThrow(_stmt, "isDiagonalOk")
        val _columnIndexOfDryingRating: Int = getColumnIndexOrThrow(_stmt, "dryingRating")
        val _columnIndexOfChipsRating: Int = getColumnIndexOrThrow(_stmt, "chipsRating")
        val _columnIndexOfCracksRating: Int = getColumnIndexOrThrow(_stmt, "cracksRating")
        val _columnIndexOfHairlinesRating: Int = getColumnIndexOrThrow(_stmt, "hairlinesRating")
        val _columnIndexOfBreaksRating: Int = getColumnIndexOrThrow(_stmt, "breaksRating")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceKilnCar> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceKilnCar
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpIsCleaned: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCleaned).toInt()
          _tmpIsCleaned = _tmp != 0
          val _tmpLoadPercentage: Int
          _tmpLoadPercentage = _stmt.getLong(_columnIndexOfLoadPercentage).toInt()
          val _tmpRefractoriesOk: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfRefractoriesOk).toInt()
          _tmpRefractoriesOk = _tmp_1 != 0
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWidth: Double
          _tmpWidth = _stmt.getDouble(_columnIndexOfWidth)
          val _tmpThickness: Double
          _tmpThickness = _stmt.getDouble(_columnIndexOfThickness)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpIsDiagonalOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsDiagonalOk).toInt()
          _tmpIsDiagonalOk = _tmp_2 != 0
          val _tmpDryingRating: String
          _tmpDryingRating = _stmt.getText(_columnIndexOfDryingRating)
          val _tmpChipsRating: String
          _tmpChipsRating = _stmt.getText(_columnIndexOfChipsRating)
          val _tmpCracksRating: String
          _tmpCracksRating = _stmt.getText(_columnIndexOfCracksRating)
          val _tmpHairlinesRating: String
          _tmpHairlinesRating = _stmt.getText(_columnIndexOfHairlinesRating)
          val _tmpBreaksRating: String
          _tmpBreaksRating = _stmt.getText(_columnIndexOfBreaksRating)
          val _tmpQualityNotes: String
          _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceKilnCar(_tmpId,_tmpReportId,_tmpStartTime,_tmpEndTime,_tmpSigla,_tmpIsCleaned,_tmpLoadPercentage,_tmpRefractoriesOk,_tmpHeight,_tmpWidth,_tmpThickness,_tmpWeight,_tmpIsDiagonalOk,_tmpDryingRating,_tmpChipsRating,_tmpCracksRating,_tmpHairlinesRating,_tmpBreaksRating,_tmpQualityNotes,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedReports(): Flow<List<ImpilatriceReport>> {
    val _sql: String = "SELECT * FROM impilatrice_reports WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("impilatrice_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCageMaterialDistribution: Int = getColumnIndexOrThrow(_stmt, "cageMaterialDistribution")
        val _columnIndexOfCageNotes: Int = getColumnIndexOrThrow(_stmt, "cageNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceReport
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpIsClosed: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp != 0
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCageMaterialDistribution: String
          _tmpCageMaterialDistribution = _stmt.getText(_columnIndexOfCageMaterialDistribution)
          val _tmpCageNotes: String
          _tmpCageNotes = _stmt.getText(_columnIndexOfCageNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceReport(_tmpId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpOperatorId,_tmpProductId,_tmpShift,_tmpSigla,_tmpLineId,_tmpMachineId,_tmpIsClosed,_tmpCagesProduced,_tmpScrapMinutes,_tmpCageMaterialDistribution,_tmpCageNotes,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedCageEvents(): Flow<List<ImpilatriceCageEvent>> {
    val _sql: String = "SELECT * FROM impilatrice_cage_events WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("impilatrice_cage_events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfChange: Int = getColumnIndexOrThrow(_stmt, "change")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceCageEvent> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceCageEvent
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpChange: Int
          _tmpChange = _stmt.getLong(_columnIndexOfChange).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceCageEvent(_tmpId,_tmpReportId,_tmpTimestamp,_tmpChange,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedScrapRecords(): Flow<List<ImpilatriceScrapRecord>> {
    val _sql: String = "SELECT * FROM impilatrice_scrap_records WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("impilatrice_scrap_records")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfPlcValue: Int = getColumnIndexOrThrow(_stmt, "plcValue")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceScrapRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceScrapRecord
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpPlcValue: Double
          _tmpPlcValue = _stmt.getDouble(_columnIndexOfPlcValue)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceScrapRecord(_tmpId,_tmpReportId,_tmpTimestamp,_tmpPlcValue,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedKilnCars(): Flow<List<ImpilatriceKilnCar>> {
    val _sql: String = "SELECT * FROM impilatrice_kiln_cars WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("impilatrice_kiln_cars")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfIsCleaned: Int = getColumnIndexOrThrow(_stmt, "isCleaned")
        val _columnIndexOfLoadPercentage: Int = getColumnIndexOrThrow(_stmt, "loadPercentage")
        val _columnIndexOfRefractoriesOk: Int = getColumnIndexOrThrow(_stmt, "refractoriesOk")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWidth: Int = getColumnIndexOrThrow(_stmt, "width")
        val _columnIndexOfThickness: Int = getColumnIndexOrThrow(_stmt, "thickness")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfIsDiagonalOk: Int = getColumnIndexOrThrow(_stmt, "isDiagonalOk")
        val _columnIndexOfDryingRating: Int = getColumnIndexOrThrow(_stmt, "dryingRating")
        val _columnIndexOfChipsRating: Int = getColumnIndexOrThrow(_stmt, "chipsRating")
        val _columnIndexOfCracksRating: Int = getColumnIndexOrThrow(_stmt, "cracksRating")
        val _columnIndexOfHairlinesRating: Int = getColumnIndexOrThrow(_stmt, "hairlinesRating")
        val _columnIndexOfBreaksRating: Int = getColumnIndexOrThrow(_stmt, "breaksRating")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ImpilatriceKilnCar> = mutableListOf()
        while (_stmt.step()) {
          val _item: ImpilatriceKilnCar
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpIsCleaned: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCleaned).toInt()
          _tmpIsCleaned = _tmp != 0
          val _tmpLoadPercentage: Int
          _tmpLoadPercentage = _stmt.getLong(_columnIndexOfLoadPercentage).toInt()
          val _tmpRefractoriesOk: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfRefractoriesOk).toInt()
          _tmpRefractoriesOk = _tmp_1 != 0
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWidth: Double
          _tmpWidth = _stmt.getDouble(_columnIndexOfWidth)
          val _tmpThickness: Double
          _tmpThickness = _stmt.getDouble(_columnIndexOfThickness)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpIsDiagonalOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsDiagonalOk).toInt()
          _tmpIsDiagonalOk = _tmp_2 != 0
          val _tmpDryingRating: String
          _tmpDryingRating = _stmt.getText(_columnIndexOfDryingRating)
          val _tmpChipsRating: String
          _tmpChipsRating = _stmt.getText(_columnIndexOfChipsRating)
          val _tmpCracksRating: String
          _tmpCracksRating = _stmt.getText(_columnIndexOfCracksRating)
          val _tmpHairlinesRating: String
          _tmpHairlinesRating = _stmt.getText(_columnIndexOfHairlinesRating)
          val _tmpBreaksRating: String
          _tmpBreaksRating = _stmt.getText(_columnIndexOfBreaksRating)
          val _tmpQualityNotes: String
          _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ImpilatriceKilnCar(_tmpId,_tmpReportId,_tmpStartTime,_tmpEndTime,_tmpSigla,_tmpIsCleaned,_tmpLoadPercentage,_tmpRefractoriesOk,_tmpHeight,_tmpWidth,_tmpThickness,_tmpWeight,_tmpIsDiagonalOk,_tmpDryingRating,_tmpChipsRating,_tmpCracksRating,_tmpHairlinesRating,_tmpBreaksRating,_tmpQualityNotes,_tmpLastUpdated,_tmpSyncStatus)
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
