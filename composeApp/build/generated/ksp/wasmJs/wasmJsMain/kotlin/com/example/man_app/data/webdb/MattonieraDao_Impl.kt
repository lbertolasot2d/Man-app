package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.CageEvent
import com.example.man_app.`data`.model.CloggingReason
import com.example.man_app.`data`.model.CloggingRemoval
import com.example.man_app.`data`.model.ExitQualityCheck
import com.example.man_app.`data`.model.MattonieraProductionData
import com.example.man_app.`data`.model.MattonieraReport
import com.example.man_app.`data`.model.MeasurementCheck
import com.example.man_app.`data`.model.ProductionStop
import com.example.man_app.`data`.model.RollerCleaning
import com.example.man_app.`data`.model.ScrapRecord
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
internal class MattonieraDao_Impl(
  __db: RoomDatabase,
) : MattonieraDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMattonieraReport: EntityInsertAdapter<MattonieraReport>

  private val __insertAdapterOfExitQualityCheck: EntityInsertAdapter<ExitQualityCheck>

  private val __insertAdapterOfMeasurementCheck: EntityInsertAdapter<MeasurementCheck>

  private val __insertAdapterOfRollerCleaning: EntityInsertAdapter<RollerCleaning>

  private val __insertAdapterOfCloggingRemoval: EntityInsertAdapter<CloggingRemoval>

  private val __insertAdapterOfCloggingReason: EntityInsertAdapter<CloggingReason>

  private val __insertAdapterOfProductionStop: EntityInsertAdapter<ProductionStop>

  private val __insertAdapterOfMattonieraProductionData:
      EntityInsertAdapter<MattonieraProductionData>

  private val __insertAdapterOfScrapRecord: EntityInsertAdapter<ScrapRecord>

  private val __insertAdapterOfCageEvent: EntityInsertAdapter<CageEvent>

  private val __deleteAdapterOfCloggingRemoval: EntityDeleteOrUpdateAdapter<CloggingRemoval>

  private val __deleteAdapterOfCageEvent: EntityDeleteOrUpdateAdapter<CageEvent>

  private val __updateAdapterOfMattonieraReport: EntityDeleteOrUpdateAdapter<MattonieraReport>

  private val __updateAdapterOfCloggingRemoval: EntityDeleteOrUpdateAdapter<CloggingRemoval>

  private val __updateAdapterOfCageEvent: EntityDeleteOrUpdateAdapter<CageEvent>
  init {
    this.__db = __db
    this.__insertAdapterOfMattonieraReport = object : EntityInsertAdapter<MattonieraReport>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `mattoniera_reports` (`id`,`date`,`shift`,`productId`,`operatorId`,`dieId`,`sigla`,`clayRecipeCode`,`lineId`,`machineId`,`dimaCheckStart`,`dimaCheckMid`,`startTime`,`endTime`,`scrapMinutes`,`cagesProduced`,`doserSpeedHz`,`absorptionAmpere`,`productionNotes`,`qualityNotes`,`isClosed`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MattonieraReport) {
        statement.bindText(1, entity.id)
        statement.bindLong(2, entity.date)
        statement.bindText(3, entity.shift)
        statement.bindText(4, entity.productId)
        statement.bindText(5, entity.operatorId)
        statement.bindText(6, entity.dieId)
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpSigla)
        }
        val _tmpClayRecipeCode: String? = entity.clayRecipeCode
        if (_tmpClayRecipeCode == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpClayRecipeCode)
        }
        statement.bindText(9, entity.lineId)
        statement.bindText(10, entity.machineId)
        val _tmp: Int = if (entity.dimaCheckStart) 1 else 0
        statement.bindLong(11, _tmp.toLong())
        val _tmp_1: Int = if (entity.dimaCheckMid) 1 else 0
        statement.bindLong(12, _tmp_1.toLong())
        statement.bindLong(13, entity.startTime)
        statement.bindLong(14, entity.endTime)
        statement.bindLong(15, entity.scrapMinutes.toLong())
        statement.bindLong(16, entity.cagesProduced.toLong())
        statement.bindDouble(17, entity.doserSpeedHz)
        statement.bindDouble(18, entity.absorptionAmpere)
        val _tmpProductionNotes: String? = entity.productionNotes
        if (_tmpProductionNotes == null) {
          statement.bindNull(19)
        } else {
          statement.bindText(19, _tmpProductionNotes)
        }
        val _tmpQualityNotes: String? = entity.qualityNotes
        if (_tmpQualityNotes == null) {
          statement.bindNull(20)
        } else {
          statement.bindText(20, _tmpQualityNotes)
        }
        val _tmp_2: Int = if (entity.isClosed) 1 else 0
        statement.bindLong(21, _tmp_2.toLong())
        statement.bindLong(22, entity.lastUpdated)
        statement.bindText(23, entity.syncStatus)
      }
    }
    this.__insertAdapterOfExitQualityCheck = object : EntityInsertAdapter<ExitQualityCheck>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `exit_quality_checks` (`id`,`reportId`,`timestamp`,`isOk`,`temperature`,`pressure`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExitQualityCheck) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        val _tmp: Int = if (entity.isOk) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.temperature.toLong())
        statement.bindLong(6, entity.pressure.toLong())
        statement.bindLong(7, entity.lastUpdated)
        statement.bindText(8, entity.syncStatus)
      }
    }
    this.__insertAdapterOfMeasurementCheck = object : EntityInsertAdapter<MeasurementCheck>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `measurement_checks` (`id`,`reportId`,`timestamp`,`height`,`width`,`thickness`,`diagonal1`,`diagonal2`,`weight`,`isDiagonalOk`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MeasurementCheck) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindDouble(4, entity.height)
        statement.bindDouble(5, entity.width)
        statement.bindDouble(6, entity.thickness)
        statement.bindDouble(7, entity.diagonal1)
        statement.bindDouble(8, entity.diagonal2)
        statement.bindDouble(9, entity.weight)
        val _tmp: Int = if (entity.isDiagonalOk) 1 else 0
        statement.bindLong(10, _tmp.toLong())
        statement.bindLong(11, entity.lastUpdated)
        statement.bindText(12, entity.syncStatus)
      }
    }
    this.__insertAdapterOfRollerCleaning = object : EntityInsertAdapter<RollerCleaning>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `roller_cleanings` (`id`,`reportId`,`timestamp`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RollerCleaning) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.lastUpdated)
        statement.bindText(5, entity.syncStatus)
      }
    }
    this.__insertAdapterOfCloggingRemoval = object : EntityInsertAdapter<CloggingRemoval>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `clogging_removals` (`id`,`reportId`,`timestamp`,`reason`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CloggingRemoval) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindText(4, entity.reason)
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
      }
    }
    this.__insertAdapterOfCloggingReason = object : EntityInsertAdapter<CloggingReason>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `clogging_reasons` (`reasonText`,`lastUpdated`,`syncStatus`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CloggingReason) {
        statement.bindText(1, entity.reasonText)
        statement.bindLong(2, entity.lastUpdated)
        statement.bindText(3, entity.syncStatus)
      }
    }
    this.__insertAdapterOfProductionStop = object : EntityInsertAdapter<ProductionStop>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `production_stops` (`id`,`reportId`,`workstation`,`startTime`,`endTime`,`reason`,`operators`,`activities`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ProductionStop) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindText(3, entity.workstation)
        statement.bindLong(4, entity.startTime)
        val _tmpEndTime: Long? = entity.endTime
        if (_tmpEndTime == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpEndTime)
        }
        statement.bindText(6, entity.reason)
        statement.bindText(7, entity.operators)
        val _tmpActivities: String? = entity.activities
        if (_tmpActivities == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpActivities)
        }
        statement.bindLong(9, entity.lastUpdated)
        statement.bindText(10, entity.syncStatus)
      }
    }
    this.__insertAdapterOfMattonieraProductionData = object : EntityInsertAdapter<MattonieraProductionData>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `mattoniera_production_data` (`id`,`reportId`,`timestamp`,`doserGiri`,`isDoserOk`,`absorptionAmpere`,`isAbsorptionOk`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MattonieraProductionData) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.doserGiri.toLong())
        val _tmp: Int = if (entity.isDoserOk) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.absorptionAmpere.toLong())
        val _tmp_1: Int = if (entity.isAbsorptionOk) 1 else 0
        statement.bindLong(7, _tmp_1.toLong())
        statement.bindLong(8, entity.lastUpdated)
        statement.bindText(9, entity.syncStatus)
      }
    }
    this.__insertAdapterOfScrapRecord = object : EntityInsertAdapter<ScrapRecord>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `scrap_records` (`id`,`reportId`,`timestamp`,`plcValue`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ScrapRecord) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindDouble(4, entity.plcValue)
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
      }
    }
    this.__insertAdapterOfCageEvent = object : EntityInsertAdapter<CageEvent>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `cage_events` (`id`,`reportId`,`timestamp`,`change`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CageEvent) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.change.toLong())
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
      }
    }
    this.__deleteAdapterOfCloggingRemoval = object : EntityDeleteOrUpdateAdapter<CloggingRemoval>() {
      protected override fun createQuery(): String = "DELETE FROM `clogging_removals` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CloggingRemoval) {
        statement.bindText(1, entity.id)
      }
    }
    this.__deleteAdapterOfCageEvent = object : EntityDeleteOrUpdateAdapter<CageEvent>() {
      protected override fun createQuery(): String = "DELETE FROM `cage_events` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CageEvent) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfMattonieraReport = object : EntityDeleteOrUpdateAdapter<MattonieraReport>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `mattoniera_reports` SET `id` = ?,`date` = ?,`shift` = ?,`productId` = ?,`operatorId` = ?,`dieId` = ?,`sigla` = ?,`clayRecipeCode` = ?,`lineId` = ?,`machineId` = ?,`dimaCheckStart` = ?,`dimaCheckMid` = ?,`startTime` = ?,`endTime` = ?,`scrapMinutes` = ?,`cagesProduced` = ?,`doserSpeedHz` = ?,`absorptionAmpere` = ?,`productionNotes` = ?,`qualityNotes` = ?,`isClosed` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: MattonieraReport) {
        statement.bindText(1, entity.id)
        statement.bindLong(2, entity.date)
        statement.bindText(3, entity.shift)
        statement.bindText(4, entity.productId)
        statement.bindText(5, entity.operatorId)
        statement.bindText(6, entity.dieId)
        val _tmpSigla: String? = entity.sigla
        if (_tmpSigla == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpSigla)
        }
        val _tmpClayRecipeCode: String? = entity.clayRecipeCode
        if (_tmpClayRecipeCode == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpClayRecipeCode)
        }
        statement.bindText(9, entity.lineId)
        statement.bindText(10, entity.machineId)
        val _tmp: Int = if (entity.dimaCheckStart) 1 else 0
        statement.bindLong(11, _tmp.toLong())
        val _tmp_1: Int = if (entity.dimaCheckMid) 1 else 0
        statement.bindLong(12, _tmp_1.toLong())
        statement.bindLong(13, entity.startTime)
        statement.bindLong(14, entity.endTime)
        statement.bindLong(15, entity.scrapMinutes.toLong())
        statement.bindLong(16, entity.cagesProduced.toLong())
        statement.bindDouble(17, entity.doserSpeedHz)
        statement.bindDouble(18, entity.absorptionAmpere)
        val _tmpProductionNotes: String? = entity.productionNotes
        if (_tmpProductionNotes == null) {
          statement.bindNull(19)
        } else {
          statement.bindText(19, _tmpProductionNotes)
        }
        val _tmpQualityNotes: String? = entity.qualityNotes
        if (_tmpQualityNotes == null) {
          statement.bindNull(20)
        } else {
          statement.bindText(20, _tmpQualityNotes)
        }
        val _tmp_2: Int = if (entity.isClosed) 1 else 0
        statement.bindLong(21, _tmp_2.toLong())
        statement.bindLong(22, entity.lastUpdated)
        statement.bindText(23, entity.syncStatus)
        statement.bindText(24, entity.id)
      }
    }
    this.__updateAdapterOfCloggingRemoval = object : EntityDeleteOrUpdateAdapter<CloggingRemoval>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `clogging_removals` SET `id` = ?,`reportId` = ?,`timestamp` = ?,`reason` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CloggingRemoval) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindText(4, entity.reason)
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
        statement.bindText(7, entity.id)
      }
    }
    this.__updateAdapterOfCageEvent = object : EntityDeleteOrUpdateAdapter<CageEvent>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `cage_events` SET `id` = ?,`reportId` = ?,`timestamp` = ?,`change` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CageEvent) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindLong(4, entity.change.toLong())
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
        statement.bindText(7, entity.id)
      }
    }
  }

  public override suspend fun insertReport(report: MattonieraReport): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfMattonieraReport.insert(_connection, report)
  }

  public override suspend fun insertExitCheck(check: ExitQualityCheck): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfExitQualityCheck.insert(_connection, check)
  }

  public override suspend fun insertMeasurement(check: MeasurementCheck): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfMeasurementCheck.insert(_connection, check)
  }

  public override suspend fun insertRollerCleaning(cleaning: RollerCleaning): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfRollerCleaning.insert(_connection, cleaning)
  }

  public override suspend fun insertCloggingRemoval(removal: CloggingRemoval): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCloggingRemoval.insert(_connection, removal)
  }

  public override suspend fun insertCloggingReason(reason: CloggingReason): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCloggingReason.insert(_connection, reason)
  }

  public override suspend fun insertStop(stop: ProductionStop): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfProductionStop.insert(_connection, stop)
  }

  public override suspend fun insertProductionData(`data`: MattonieraProductionData): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfMattonieraProductionData.insert(_connection, data)
  }

  public override suspend fun insertScrapRecord(record: ScrapRecord): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfScrapRecord.insert(_connection, record)
  }

  public override suspend fun insertCageEvent(event: CageEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCageEvent.insert(_connection, event)
  }

  public override suspend fun deleteCloggingRemoval(removal: CloggingRemoval): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfCloggingRemoval.handle(_connection, removal)
  }

  public override suspend fun deleteCageEvent(event: CageEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfCageEvent.handle(_connection, event)
  }

  public override suspend fun updateReport(report: MattonieraReport): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfMattonieraReport.handle(_connection, report)
  }

  public override suspend fun updateCloggingRemoval(removal: CloggingRemoval): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfCloggingRemoval.handle(_connection, removal)
  }

  public override suspend fun updateCageEvent(event: CageEvent): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfCageEvent.handle(_connection, event)
  }

  public override fun getActiveReports(): Flow<List<MattonieraReport>> {
    val _sql: String = "SELECT * FROM mattoniera_reports WHERE isClosed = 0"
    return createFlow(__db, false, arrayOf("mattoniera_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfDieId: Int = getColumnIndexOrThrow(_stmt, "dieId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfClayRecipeCode: Int = getColumnIndexOrThrow(_stmt, "clayRecipeCode")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfDimaCheckStart: Int = getColumnIndexOrThrow(_stmt, "dimaCheckStart")
        val _columnIndexOfDimaCheckMid: Int = getColumnIndexOrThrow(_stmt, "dimaCheckMid")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfDoserSpeedHz: Int = getColumnIndexOrThrow(_stmt, "doserSpeedHz")
        val _columnIndexOfAbsorptionAmpere: Int = getColumnIndexOrThrow(_stmt, "absorptionAmpere")
        val _columnIndexOfProductionNotes: Int = getColumnIndexOrThrow(_stmt, "productionNotes")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MattonieraReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: MattonieraReport
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpDieId: String
          _tmpDieId = _stmt.getText(_columnIndexOfDieId)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpClayRecipeCode: String?
          if (_stmt.isNull(_columnIndexOfClayRecipeCode)) {
            _tmpClayRecipeCode = null
          } else {
            _tmpClayRecipeCode = _stmt.getText(_columnIndexOfClayRecipeCode)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpDimaCheckStart: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfDimaCheckStart).toInt()
          _tmpDimaCheckStart = _tmp != 0
          val _tmpDimaCheckMid: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfDimaCheckMid).toInt()
          _tmpDimaCheckMid = _tmp_1 != 0
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpDoserSpeedHz: Double
          _tmpDoserSpeedHz = _stmt.getDouble(_columnIndexOfDoserSpeedHz)
          val _tmpAbsorptionAmpere: Double
          _tmpAbsorptionAmpere = _stmt.getDouble(_columnIndexOfAbsorptionAmpere)
          val _tmpProductionNotes: String?
          if (_stmt.isNull(_columnIndexOfProductionNotes)) {
            _tmpProductionNotes = null
          } else {
            _tmpProductionNotes = _stmt.getText(_columnIndexOfProductionNotes)
          }
          val _tmpQualityNotes: String?
          if (_stmt.isNull(_columnIndexOfQualityNotes)) {
            _tmpQualityNotes = null
          } else {
            _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          }
          val _tmpIsClosed: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp_2 != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MattonieraReport(_tmpId,_tmpDate,_tmpShift,_tmpProductId,_tmpOperatorId,_tmpDieId,_tmpSigla,_tmpClayRecipeCode,_tmpLineId,_tmpMachineId,_tmpDimaCheckStart,_tmpDimaCheckMid,_tmpStartTime,_tmpEndTime,_tmpScrapMinutes,_tmpCagesProduced,_tmpDoserSpeedHz,_tmpAbsorptionAmpere,_tmpProductionNotes,_tmpQualityNotes,_tmpIsClosed,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getReportsByContext(lineId: String, machineId: String): Flow<List<MattonieraReport>> {
    val _sql: String = "SELECT * FROM mattoniera_reports WHERE lineId = ? AND machineId = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("mattoniera_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, lineId)
        _argIndex = 2
        _stmt.bindText(_argIndex, machineId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfDieId: Int = getColumnIndexOrThrow(_stmt, "dieId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfClayRecipeCode: Int = getColumnIndexOrThrow(_stmt, "clayRecipeCode")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfDimaCheckStart: Int = getColumnIndexOrThrow(_stmt, "dimaCheckStart")
        val _columnIndexOfDimaCheckMid: Int = getColumnIndexOrThrow(_stmt, "dimaCheckMid")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfDoserSpeedHz: Int = getColumnIndexOrThrow(_stmt, "doserSpeedHz")
        val _columnIndexOfAbsorptionAmpere: Int = getColumnIndexOrThrow(_stmt, "absorptionAmpere")
        val _columnIndexOfProductionNotes: Int = getColumnIndexOrThrow(_stmt, "productionNotes")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MattonieraReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: MattonieraReport
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpDieId: String
          _tmpDieId = _stmt.getText(_columnIndexOfDieId)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpClayRecipeCode: String?
          if (_stmt.isNull(_columnIndexOfClayRecipeCode)) {
            _tmpClayRecipeCode = null
          } else {
            _tmpClayRecipeCode = _stmt.getText(_columnIndexOfClayRecipeCode)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpDimaCheckStart: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfDimaCheckStart).toInt()
          _tmpDimaCheckStart = _tmp != 0
          val _tmpDimaCheckMid: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfDimaCheckMid).toInt()
          _tmpDimaCheckMid = _tmp_1 != 0
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpDoserSpeedHz: Double
          _tmpDoserSpeedHz = _stmt.getDouble(_columnIndexOfDoserSpeedHz)
          val _tmpAbsorptionAmpere: Double
          _tmpAbsorptionAmpere = _stmt.getDouble(_columnIndexOfAbsorptionAmpere)
          val _tmpProductionNotes: String?
          if (_stmt.isNull(_columnIndexOfProductionNotes)) {
            _tmpProductionNotes = null
          } else {
            _tmpProductionNotes = _stmt.getText(_columnIndexOfProductionNotes)
          }
          val _tmpQualityNotes: String?
          if (_stmt.isNull(_columnIndexOfQualityNotes)) {
            _tmpQualityNotes = null
          } else {
            _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          }
          val _tmpIsClosed: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp_2 != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MattonieraReport(_tmpId,_tmpDate,_tmpShift,_tmpProductId,_tmpOperatorId,_tmpDieId,_tmpSigla,_tmpClayRecipeCode,_tmpLineId,_tmpMachineId,_tmpDimaCheckStart,_tmpDimaCheckMid,_tmpStartTime,_tmpEndTime,_tmpScrapMinutes,_tmpCagesProduced,_tmpDoserSpeedHz,_tmpAbsorptionAmpere,_tmpProductionNotes,_tmpQualityNotes,_tmpIsClosed,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getReportById(reportId: String): Flow<MattonieraReport?> {
    val _sql: String = "SELECT * FROM mattoniera_reports WHERE id = ?"
    return createFlow(__db, false, arrayOf("mattoniera_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfDieId: Int = getColumnIndexOrThrow(_stmt, "dieId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfClayRecipeCode: Int = getColumnIndexOrThrow(_stmt, "clayRecipeCode")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfDimaCheckStart: Int = getColumnIndexOrThrow(_stmt, "dimaCheckStart")
        val _columnIndexOfDimaCheckMid: Int = getColumnIndexOrThrow(_stmt, "dimaCheckMid")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfDoserSpeedHz: Int = getColumnIndexOrThrow(_stmt, "doserSpeedHz")
        val _columnIndexOfAbsorptionAmpere: Int = getColumnIndexOrThrow(_stmt, "absorptionAmpere")
        val _columnIndexOfProductionNotes: Int = getColumnIndexOrThrow(_stmt, "productionNotes")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MattonieraReport?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpDieId: String
          _tmpDieId = _stmt.getText(_columnIndexOfDieId)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpClayRecipeCode: String?
          if (_stmt.isNull(_columnIndexOfClayRecipeCode)) {
            _tmpClayRecipeCode = null
          } else {
            _tmpClayRecipeCode = _stmt.getText(_columnIndexOfClayRecipeCode)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpDimaCheckStart: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfDimaCheckStart).toInt()
          _tmpDimaCheckStart = _tmp != 0
          val _tmpDimaCheckMid: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfDimaCheckMid).toInt()
          _tmpDimaCheckMid = _tmp_1 != 0
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpDoserSpeedHz: Double
          _tmpDoserSpeedHz = _stmt.getDouble(_columnIndexOfDoserSpeedHz)
          val _tmpAbsorptionAmpere: Double
          _tmpAbsorptionAmpere = _stmt.getDouble(_columnIndexOfAbsorptionAmpere)
          val _tmpProductionNotes: String?
          if (_stmt.isNull(_columnIndexOfProductionNotes)) {
            _tmpProductionNotes = null
          } else {
            _tmpProductionNotes = _stmt.getText(_columnIndexOfProductionNotes)
          }
          val _tmpQualityNotes: String?
          if (_stmt.isNull(_columnIndexOfQualityNotes)) {
            _tmpQualityNotes = null
          } else {
            _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          }
          val _tmpIsClosed: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp_2 != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _result = MattonieraReport(_tmpId,_tmpDate,_tmpShift,_tmpProductId,_tmpOperatorId,_tmpDieId,_tmpSigla,_tmpClayRecipeCode,_tmpLineId,_tmpMachineId,_tmpDimaCheckStart,_tmpDimaCheckMid,_tmpStartTime,_tmpEndTime,_tmpScrapMinutes,_tmpCagesProduced,_tmpDoserSpeedHz,_tmpAbsorptionAmpere,_tmpProductionNotes,_tmpQualityNotes,_tmpIsClosed,_tmpLastUpdated,_tmpSyncStatus)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getExitChecks(reportId: String): Flow<List<ExitQualityCheck>> {
    val _sql: String = "SELECT * FROM exit_quality_checks WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("exit_quality_checks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfIsOk: Int = getColumnIndexOrThrow(_stmt, "isOk")
        val _columnIndexOfTemperature: Int = getColumnIndexOrThrow(_stmt, "temperature")
        val _columnIndexOfPressure: Int = getColumnIndexOrThrow(_stmt, "pressure")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ExitQualityCheck> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExitQualityCheck
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpIsOk: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsOk).toInt()
          _tmpIsOk = _tmp != 0
          val _tmpTemperature: Int
          _tmpTemperature = _stmt.getLong(_columnIndexOfTemperature).toInt()
          val _tmpPressure: Int
          _tmpPressure = _stmt.getLong(_columnIndexOfPressure).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ExitQualityCheck(_tmpId,_tmpReportId,_tmpTimestamp,_tmpIsOk,_tmpTemperature,_tmpPressure,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getMeasurements(reportId: String): Flow<List<MeasurementCheck>> {
    val _sql: String = "SELECT * FROM measurement_checks WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("measurement_checks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWidth: Int = getColumnIndexOrThrow(_stmt, "width")
        val _columnIndexOfThickness: Int = getColumnIndexOrThrow(_stmt, "thickness")
        val _columnIndexOfDiagonal1: Int = getColumnIndexOrThrow(_stmt, "diagonal1")
        val _columnIndexOfDiagonal2: Int = getColumnIndexOrThrow(_stmt, "diagonal2")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfIsDiagonalOk: Int = getColumnIndexOrThrow(_stmt, "isDiagonalOk")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MeasurementCheck> = mutableListOf()
        while (_stmt.step()) {
          val _item: MeasurementCheck
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWidth: Double
          _tmpWidth = _stmt.getDouble(_columnIndexOfWidth)
          val _tmpThickness: Double
          _tmpThickness = _stmt.getDouble(_columnIndexOfThickness)
          val _tmpDiagonal1: Double
          _tmpDiagonal1 = _stmt.getDouble(_columnIndexOfDiagonal1)
          val _tmpDiagonal2: Double
          _tmpDiagonal2 = _stmt.getDouble(_columnIndexOfDiagonal2)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpIsDiagonalOk: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDiagonalOk).toInt()
          _tmpIsDiagonalOk = _tmp != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MeasurementCheck(_tmpId,_tmpReportId,_tmpTimestamp,_tmpHeight,_tmpWidth,_tmpThickness,_tmpDiagonal1,_tmpDiagonal2,_tmpWeight,_tmpIsDiagonalOk,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getRollerCleanings(reportId: String): Flow<List<RollerCleaning>> {
    val _sql: String = "SELECT * FROM roller_cleanings WHERE reportId = ?"
    return createFlow(__db, false, arrayOf("roller_cleanings")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<RollerCleaning> = mutableListOf()
        while (_stmt.step()) {
          val _item: RollerCleaning
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = RollerCleaning(_tmpId,_tmpReportId,_tmpTimestamp,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCloggingRemovals(reportId: String): Flow<List<CloggingRemoval>> {
    val _sql: String = "SELECT * FROM clogging_removals WHERE reportId = ?"
    return createFlow(__db, false, arrayOf("clogging_removals")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<CloggingRemoval> = mutableListOf()
        while (_stmt.step()) {
          val _item: CloggingRemoval
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = CloggingRemoval(_tmpId,_tmpReportId,_tmpTimestamp,_tmpReason,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllCloggingReasons(): Flow<List<CloggingReason>> {
    val _sql: String = "SELECT * FROM clogging_reasons"
    return createFlow(__db, false, arrayOf("clogging_reasons")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfReasonText: Int = getColumnIndexOrThrow(_stmt, "reasonText")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<CloggingReason> = mutableListOf()
        while (_stmt.step()) {
          val _item: CloggingReason
          val _tmpReasonText: String
          _tmpReasonText = _stmt.getText(_columnIndexOfReasonText)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = CloggingReason(_tmpReasonText,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getStopsForReport(reportId: String): Flow<List<ProductionStop>> {
    val _sql: String = "SELECT * FROM production_stops WHERE reportId = ?"
    return createFlow(__db, false, arrayOf("production_stops")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfWorkstation: Int = getColumnIndexOrThrow(_stmt, "workstation")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfOperators: Int = getColumnIndexOrThrow(_stmt, "operators")
        val _columnIndexOfActivities: Int = getColumnIndexOrThrow(_stmt, "activities")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ProductionStop> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductionStop
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpWorkstation: String
          _tmpWorkstation = _stmt.getText(_columnIndexOfWorkstation)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpOperators: String
          _tmpOperators = _stmt.getText(_columnIndexOfOperators)
          val _tmpActivities: String?
          if (_stmt.isNull(_columnIndexOfActivities)) {
            _tmpActivities = null
          } else {
            _tmpActivities = _stmt.getText(_columnIndexOfActivities)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ProductionStop(_tmpId,_tmpReportId,_tmpWorkstation,_tmpStartTime,_tmpEndTime,_tmpReason,_tmpOperators,_tmpActivities,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getProductionData(reportId: String): Flow<List<MattonieraProductionData>> {
    val _sql: String = "SELECT * FROM mattoniera_production_data WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("mattoniera_production_data")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, reportId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfDoserGiri: Int = getColumnIndexOrThrow(_stmt, "doserGiri")
        val _columnIndexOfIsDoserOk: Int = getColumnIndexOrThrow(_stmt, "isDoserOk")
        val _columnIndexOfAbsorptionAmpere: Int = getColumnIndexOrThrow(_stmt, "absorptionAmpere")
        val _columnIndexOfIsAbsorptionOk: Int = getColumnIndexOrThrow(_stmt, "isAbsorptionOk")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MattonieraProductionData> = mutableListOf()
        while (_stmt.step()) {
          val _item: MattonieraProductionData
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpDoserGiri: Int
          _tmpDoserGiri = _stmt.getLong(_columnIndexOfDoserGiri).toInt()
          val _tmpIsDoserOk: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDoserOk).toInt()
          _tmpIsDoserOk = _tmp != 0
          val _tmpAbsorptionAmpere: Int
          _tmpAbsorptionAmpere = _stmt.getLong(_columnIndexOfAbsorptionAmpere).toInt()
          val _tmpIsAbsorptionOk: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsAbsorptionOk).toInt()
          _tmpIsAbsorptionOk = _tmp_1 != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MattonieraProductionData(_tmpId,_tmpReportId,_tmpTimestamp,_tmpDoserGiri,_tmpIsDoserOk,_tmpAbsorptionAmpere,_tmpIsAbsorptionOk,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getScrapRecords(reportId: String): Flow<List<ScrapRecord>> {
    val _sql: String = "SELECT * FROM scrap_records WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("scrap_records")) { _connection ->
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
        val _result: MutableList<ScrapRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScrapRecord
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
          _item = ScrapRecord(_tmpId,_tmpReportId,_tmpTimestamp,_tmpPlcValue,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCageEvents(reportId: String): Flow<List<CageEvent>> {
    val _sql: String = "SELECT * FROM cage_events WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("cage_events")) { _connection ->
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
        val _result: MutableList<CageEvent> = mutableListOf()
        while (_stmt.step()) {
          val _item: CageEvent
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
          _item = CageEvent(_tmpId,_tmpReportId,_tmpTimestamp,_tmpChange,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUniqueProductsForLine(lineId: String): Flow<List<String>> {
    val _sql: String = "SELECT DISTINCT productId FROM mattoniera_reports WHERE lineId = ?"
    return createFlow(__db, false, arrayOf("mattoniera_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, lineId)
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUniqueSigleForLine(lineId: String): Flow<List<String?>> {
    val _sql: String = "SELECT DISTINCT sigla FROM mattoniera_reports WHERE lineId = ?"
    return createFlow(__db, false, arrayOf("mattoniera_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, lineId)
        val _result: MutableList<String?> = mutableListOf()
        while (_stmt.step()) {
          val _item: String?
          if (_stmt.isNull(0)) {
            _item = null
          } else {
            _item = _stmt.getText(0)
          }
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedReports(): Flow<List<MattonieraReport>> {
    val _sql: String = "SELECT * FROM mattoniera_reports WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("mattoniera_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfDieId: Int = getColumnIndexOrThrow(_stmt, "dieId")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfClayRecipeCode: Int = getColumnIndexOrThrow(_stmt, "clayRecipeCode")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfDimaCheckStart: Int = getColumnIndexOrThrow(_stmt, "dimaCheckStart")
        val _columnIndexOfDimaCheckMid: Int = getColumnIndexOrThrow(_stmt, "dimaCheckMid")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfCagesProduced: Int = getColumnIndexOrThrow(_stmt, "cagesProduced")
        val _columnIndexOfDoserSpeedHz: Int = getColumnIndexOrThrow(_stmt, "doserSpeedHz")
        val _columnIndexOfAbsorptionAmpere: Int = getColumnIndexOrThrow(_stmt, "absorptionAmpere")
        val _columnIndexOfProductionNotes: Int = getColumnIndexOrThrow(_stmt, "productionNotes")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfIsClosed: Int = getColumnIndexOrThrow(_stmt, "isClosed")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MattonieraReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: MattonieraReport
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpProductId: String
          _tmpProductId = _stmt.getText(_columnIndexOfProductId)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpDieId: String
          _tmpDieId = _stmt.getText(_columnIndexOfDieId)
          val _tmpSigla: String?
          if (_stmt.isNull(_columnIndexOfSigla)) {
            _tmpSigla = null
          } else {
            _tmpSigla = _stmt.getText(_columnIndexOfSigla)
          }
          val _tmpClayRecipeCode: String?
          if (_stmt.isNull(_columnIndexOfClayRecipeCode)) {
            _tmpClayRecipeCode = null
          } else {
            _tmpClayRecipeCode = _stmt.getText(_columnIndexOfClayRecipeCode)
          }
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpDimaCheckStart: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfDimaCheckStart).toInt()
          _tmpDimaCheckStart = _tmp != 0
          val _tmpDimaCheckMid: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfDimaCheckMid).toInt()
          _tmpDimaCheckMid = _tmp_1 != 0
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpCagesProduced: Int
          _tmpCagesProduced = _stmt.getLong(_columnIndexOfCagesProduced).toInt()
          val _tmpDoserSpeedHz: Double
          _tmpDoserSpeedHz = _stmt.getDouble(_columnIndexOfDoserSpeedHz)
          val _tmpAbsorptionAmpere: Double
          _tmpAbsorptionAmpere = _stmt.getDouble(_columnIndexOfAbsorptionAmpere)
          val _tmpProductionNotes: String?
          if (_stmt.isNull(_columnIndexOfProductionNotes)) {
            _tmpProductionNotes = null
          } else {
            _tmpProductionNotes = _stmt.getText(_columnIndexOfProductionNotes)
          }
          val _tmpQualityNotes: String?
          if (_stmt.isNull(_columnIndexOfQualityNotes)) {
            _tmpQualityNotes = null
          } else {
            _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          }
          val _tmpIsClosed: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsClosed).toInt()
          _tmpIsClosed = _tmp_2 != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MattonieraReport(_tmpId,_tmpDate,_tmpShift,_tmpProductId,_tmpOperatorId,_tmpDieId,_tmpSigla,_tmpClayRecipeCode,_tmpLineId,_tmpMachineId,_tmpDimaCheckStart,_tmpDimaCheckMid,_tmpStartTime,_tmpEndTime,_tmpScrapMinutes,_tmpCagesProduced,_tmpDoserSpeedHz,_tmpAbsorptionAmpere,_tmpProductionNotes,_tmpQualityNotes,_tmpIsClosed,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedExitChecks(): Flow<List<ExitQualityCheck>> {
    val _sql: String = "SELECT * FROM exit_quality_checks WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("exit_quality_checks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfIsOk: Int = getColumnIndexOrThrow(_stmt, "isOk")
        val _columnIndexOfTemperature: Int = getColumnIndexOrThrow(_stmt, "temperature")
        val _columnIndexOfPressure: Int = getColumnIndexOrThrow(_stmt, "pressure")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ExitQualityCheck> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExitQualityCheck
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpIsOk: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsOk).toInt()
          _tmpIsOk = _tmp != 0
          val _tmpTemperature: Int
          _tmpTemperature = _stmt.getLong(_columnIndexOfTemperature).toInt()
          val _tmpPressure: Int
          _tmpPressure = _stmt.getLong(_columnIndexOfPressure).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ExitQualityCheck(_tmpId,_tmpReportId,_tmpTimestamp,_tmpIsOk,_tmpTemperature,_tmpPressure,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedMeasurements(): Flow<List<MeasurementCheck>> {
    val _sql: String = "SELECT * FROM measurement_checks WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("measurement_checks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWidth: Int = getColumnIndexOrThrow(_stmt, "width")
        val _columnIndexOfThickness: Int = getColumnIndexOrThrow(_stmt, "thickness")
        val _columnIndexOfDiagonal1: Int = getColumnIndexOrThrow(_stmt, "diagonal1")
        val _columnIndexOfDiagonal2: Int = getColumnIndexOrThrow(_stmt, "diagonal2")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfIsDiagonalOk: Int = getColumnIndexOrThrow(_stmt, "isDiagonalOk")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MeasurementCheck> = mutableListOf()
        while (_stmt.step()) {
          val _item: MeasurementCheck
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWidth: Double
          _tmpWidth = _stmt.getDouble(_columnIndexOfWidth)
          val _tmpThickness: Double
          _tmpThickness = _stmt.getDouble(_columnIndexOfThickness)
          val _tmpDiagonal1: Double
          _tmpDiagonal1 = _stmt.getDouble(_columnIndexOfDiagonal1)
          val _tmpDiagonal2: Double
          _tmpDiagonal2 = _stmt.getDouble(_columnIndexOfDiagonal2)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpIsDiagonalOk: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDiagonalOk).toInt()
          _tmpIsDiagonalOk = _tmp != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MeasurementCheck(_tmpId,_tmpReportId,_tmpTimestamp,_tmpHeight,_tmpWidth,_tmpThickness,_tmpDiagonal1,_tmpDiagonal2,_tmpWeight,_tmpIsDiagonalOk,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedProductionData(): Flow<List<MattonieraProductionData>> {
    val _sql: String = "SELECT * FROM mattoniera_production_data WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("mattoniera_production_data")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfDoserGiri: Int = getColumnIndexOrThrow(_stmt, "doserGiri")
        val _columnIndexOfIsDoserOk: Int = getColumnIndexOrThrow(_stmt, "isDoserOk")
        val _columnIndexOfAbsorptionAmpere: Int = getColumnIndexOrThrow(_stmt, "absorptionAmpere")
        val _columnIndexOfIsAbsorptionOk: Int = getColumnIndexOrThrow(_stmt, "isAbsorptionOk")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<MattonieraProductionData> = mutableListOf()
        while (_stmt.step()) {
          val _item: MattonieraProductionData
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpDoserGiri: Int
          _tmpDoserGiri = _stmt.getLong(_columnIndexOfDoserGiri).toInt()
          val _tmpIsDoserOk: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDoserOk).toInt()
          _tmpIsDoserOk = _tmp != 0
          val _tmpAbsorptionAmpere: Int
          _tmpAbsorptionAmpere = _stmt.getLong(_columnIndexOfAbsorptionAmpere).toInt()
          val _tmpIsAbsorptionOk: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsAbsorptionOk).toInt()
          _tmpIsAbsorptionOk = _tmp_1 != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = MattonieraProductionData(_tmpId,_tmpReportId,_tmpTimestamp,_tmpDoserGiri,_tmpIsDoserOk,_tmpAbsorptionAmpere,_tmpIsAbsorptionOk,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedScrapRecords(): Flow<List<ScrapRecord>> {
    val _sql: String = "SELECT * FROM scrap_records WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("scrap_records")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfPlcValue: Int = getColumnIndexOrThrow(_stmt, "plcValue")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ScrapRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScrapRecord
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
          _item = ScrapRecord(_tmpId,_tmpReportId,_tmpTimestamp,_tmpPlcValue,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedCageEvents(): Flow<List<CageEvent>> {
    val _sql: String = "SELECT * FROM cage_events WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("cage_events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfChange: Int = getColumnIndexOrThrow(_stmt, "change")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<CageEvent> = mutableListOf()
        while (_stmt.step()) {
          val _item: CageEvent
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
          _item = CageEvent(_tmpId,_tmpReportId,_tmpTimestamp,_tmpChange,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedRollerCleanings(): Flow<List<RollerCleaning>> {
    val _sql: String = "SELECT * FROM roller_cleanings WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("roller_cleanings")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<RollerCleaning> = mutableListOf()
        while (_stmt.step()) {
          val _item: RollerCleaning
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = RollerCleaning(_tmpId,_tmpReportId,_tmpTimestamp,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedCloggingRemovals(): Flow<List<CloggingRemoval>> {
    val _sql: String = "SELECT * FROM clogging_removals WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("clogging_removals")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<CloggingRemoval> = mutableListOf()
        while (_stmt.step()) {
          val _item: CloggingRemoval
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = CloggingRemoval(_tmpId,_tmpReportId,_tmpTimestamp,_tmpReason,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedProductionStops(): Flow<List<ProductionStop>> {
    val _sql: String = "SELECT * FROM production_stops WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("production_stops")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfWorkstation: Int = getColumnIndexOrThrow(_stmt, "workstation")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfOperators: Int = getColumnIndexOrThrow(_stmt, "operators")
        val _columnIndexOfActivities: Int = getColumnIndexOrThrow(_stmt, "activities")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ProductionStop> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductionStop
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpReportId: String
          _tmpReportId = _stmt.getText(_columnIndexOfReportId)
          val _tmpWorkstation: String
          _tmpWorkstation = _stmt.getText(_columnIndexOfWorkstation)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmpEndTime = null
          } else {
            _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          }
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpOperators: String
          _tmpOperators = _stmt.getText(_columnIndexOfOperators)
          val _tmpActivities: String?
          if (_stmt.isNull(_columnIndexOfActivities)) {
            _tmpActivities = null
          } else {
            _tmpActivities = _stmt.getText(_columnIndexOfActivities)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ProductionStop(_tmpId,_tmpReportId,_tmpWorkstation,_tmpStartTime,_tmpEndTime,_tmpReason,_tmpOperators,_tmpActivities,_tmpLastUpdated,_tmpSyncStatus)
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
