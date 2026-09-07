package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.ScaricatriceKilnCar
import com.example.man_app.`data`.model.ScaricatriceReport
import com.example.man_app.`data`.model.ScaricatriceScrapRecord
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
internal class ScaricatriceDao_Impl(
  __db: RoomDatabase,
) : ScaricatriceDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfScaricatriceReport: EntityInsertAdapter<ScaricatriceReport>

  private val __insertAdapterOfScaricatriceScrapRecord: EntityInsertAdapter<ScaricatriceScrapRecord>

  private val __insertAdapterOfScaricatriceKilnCar: EntityInsertAdapter<ScaricatriceKilnCar>

  private val __deleteAdapterOfScaricatriceKilnCar: EntityDeleteOrUpdateAdapter<ScaricatriceKilnCar>

  private val __updateAdapterOfScaricatriceReport: EntityDeleteOrUpdateAdapter<ScaricatriceReport>

  private val __updateAdapterOfScaricatriceKilnCar: EntityDeleteOrUpdateAdapter<ScaricatriceKilnCar>
  init {
    this.__db = __db
    this.__insertAdapterOfScaricatriceReport = object : EntityInsertAdapter<ScaricatriceReport>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `scaricatrice_reports` (`id`,`date`,`startTime`,`endTime`,`operatorId`,`productId`,`shift`,`sigla`,`lineId`,`machineId`,`isClosed`,`pacchi1aScelta`,`pacchi2aScelta`,`bobineNylon`,`bobineReggia`,`pacchiSquadratura`,`pacchiImballo`,`pacchiCentraturaPallet`,`pacchiEtichettaOk`,`scrapMinutes`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ScaricatriceReport) {
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
        statement.bindLong(12, entity.pacchi1aScelta.toLong())
        statement.bindLong(13, entity.pacchi2aScelta.toLong())
        statement.bindLong(14, entity.bobineNylon.toLong())
        statement.bindLong(15, entity.bobineReggia.toLong())
        statement.bindText(16, entity.pacchiSquadratura)
        statement.bindText(17, entity.pacchiImballo)
        val _tmp_1: Int = if (entity.pacchiCentraturaPallet) 1 else 0
        statement.bindLong(18, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.pacchiEtichettaOk) 1 else 0
        statement.bindLong(19, _tmp_2.toLong())
        statement.bindLong(20, entity.scrapMinutes.toLong())
        statement.bindLong(21, entity.lastUpdated)
        statement.bindText(22, entity.syncStatus)
      }
    }
    this.__insertAdapterOfScaricatriceScrapRecord = object : EntityInsertAdapter<ScaricatriceScrapRecord>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `scaricatrice_scrap_records` (`id`,`reportId`,`timestamp`,`plcValue`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ScaricatriceScrapRecord) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.reportId)
        statement.bindLong(3, entity.timestamp)
        statement.bindDouble(4, entity.plcValue)
        statement.bindLong(5, entity.lastUpdated)
        statement.bindText(6, entity.syncStatus)
      }
    }
    this.__insertAdapterOfScaricatriceKilnCar = object : EntityInsertAdapter<ScaricatriceKilnCar>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `scaricatrice_kiln_cars` (`id`,`reportId`,`startTime`,`endTime`,`sigla`,`isCleaned`,`refractoriesOk`,`scelta`,`soundAltoOk`,`soundCentroOk`,`soundBassoOk`,`height`,`width`,`thickness`,`weight`,`firingRating`,`chipsOk`,`chipsNotes`,`efflorescenceRating`,`efflorescenceNotes`,`stainsRating`,`stainsColor`,`stainsNotes`,`cracksRating`,`cracksNotes`,`hairlinesRating`,`hairlinesNotes`,`breaksRating`,`breaksNotes`,`qualityNotes`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ScaricatriceKilnCar) {
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
        val _tmp_1: Int = if (entity.refractoriesOk) 1 else 0
        statement.bindLong(7, _tmp_1.toLong())
        val _tmpScelta: String? = entity.scelta
        if (_tmpScelta == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpScelta)
        }
        val _tmp_2: Int = if (entity.soundAltoOk) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.soundCentroOk) 1 else 0
        statement.bindLong(10, _tmp_3.toLong())
        val _tmp_4: Int = if (entity.soundBassoOk) 1 else 0
        statement.bindLong(11, _tmp_4.toLong())
        statement.bindDouble(12, entity.height)
        statement.bindDouble(13, entity.width)
        statement.bindDouble(14, entity.thickness)
        statement.bindDouble(15, entity.weight)
        statement.bindText(16, entity.firingRating)
        val _tmp_5: Int = if (entity.chipsOk) 1 else 0
        statement.bindLong(17, _tmp_5.toLong())
        statement.bindText(18, entity.chipsNotes)
        statement.bindText(19, entity.efflorescenceRating)
        statement.bindText(20, entity.efflorescenceNotes)
        statement.bindText(21, entity.stainsRating)
        statement.bindText(22, entity.stainsColor)
        statement.bindText(23, entity.stainsNotes)
        statement.bindText(24, entity.cracksRating)
        statement.bindText(25, entity.cracksNotes)
        statement.bindText(26, entity.hairlinesRating)
        statement.bindText(27, entity.hairlinesNotes)
        statement.bindText(28, entity.breaksRating)
        statement.bindText(29, entity.breaksNotes)
        statement.bindText(30, entity.qualityNotes)
        statement.bindLong(31, entity.lastUpdated)
        statement.bindText(32, entity.syncStatus)
      }
    }
    this.__deleteAdapterOfScaricatriceKilnCar = object : EntityDeleteOrUpdateAdapter<ScaricatriceKilnCar>() {
      protected override fun createQuery(): String = "DELETE FROM `scaricatrice_kiln_cars` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ScaricatriceKilnCar) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfScaricatriceReport = object : EntityDeleteOrUpdateAdapter<ScaricatriceReport>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `scaricatrice_reports` SET `id` = ?,`date` = ?,`startTime` = ?,`endTime` = ?,`operatorId` = ?,`productId` = ?,`shift` = ?,`sigla` = ?,`lineId` = ?,`machineId` = ?,`isClosed` = ?,`pacchi1aScelta` = ?,`pacchi2aScelta` = ?,`bobineNylon` = ?,`bobineReggia` = ?,`pacchiSquadratura` = ?,`pacchiImballo` = ?,`pacchiCentraturaPallet` = ?,`pacchiEtichettaOk` = ?,`scrapMinutes` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ScaricatriceReport) {
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
        statement.bindLong(12, entity.pacchi1aScelta.toLong())
        statement.bindLong(13, entity.pacchi2aScelta.toLong())
        statement.bindLong(14, entity.bobineNylon.toLong())
        statement.bindLong(15, entity.bobineReggia.toLong())
        statement.bindText(16, entity.pacchiSquadratura)
        statement.bindText(17, entity.pacchiImballo)
        val _tmp_1: Int = if (entity.pacchiCentraturaPallet) 1 else 0
        statement.bindLong(18, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.pacchiEtichettaOk) 1 else 0
        statement.bindLong(19, _tmp_2.toLong())
        statement.bindLong(20, entity.scrapMinutes.toLong())
        statement.bindLong(21, entity.lastUpdated)
        statement.bindText(22, entity.syncStatus)
        statement.bindText(23, entity.id)
      }
    }
    this.__updateAdapterOfScaricatriceKilnCar = object : EntityDeleteOrUpdateAdapter<ScaricatriceKilnCar>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `scaricatrice_kiln_cars` SET `id` = ?,`reportId` = ?,`startTime` = ?,`endTime` = ?,`sigla` = ?,`isCleaned` = ?,`refractoriesOk` = ?,`scelta` = ?,`soundAltoOk` = ?,`soundCentroOk` = ?,`soundBassoOk` = ?,`height` = ?,`width` = ?,`thickness` = ?,`weight` = ?,`firingRating` = ?,`chipsOk` = ?,`chipsNotes` = ?,`efflorescenceRating` = ?,`efflorescenceNotes` = ?,`stainsRating` = ?,`stainsColor` = ?,`stainsNotes` = ?,`cracksRating` = ?,`cracksNotes` = ?,`hairlinesRating` = ?,`hairlinesNotes` = ?,`breaksRating` = ?,`breaksNotes` = ?,`qualityNotes` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ScaricatriceKilnCar) {
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
        val _tmp_1: Int = if (entity.refractoriesOk) 1 else 0
        statement.bindLong(7, _tmp_1.toLong())
        val _tmpScelta: String? = entity.scelta
        if (_tmpScelta == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpScelta)
        }
        val _tmp_2: Int = if (entity.soundAltoOk) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.soundCentroOk) 1 else 0
        statement.bindLong(10, _tmp_3.toLong())
        val _tmp_4: Int = if (entity.soundBassoOk) 1 else 0
        statement.bindLong(11, _tmp_4.toLong())
        statement.bindDouble(12, entity.height)
        statement.bindDouble(13, entity.width)
        statement.bindDouble(14, entity.thickness)
        statement.bindDouble(15, entity.weight)
        statement.bindText(16, entity.firingRating)
        val _tmp_5: Int = if (entity.chipsOk) 1 else 0
        statement.bindLong(17, _tmp_5.toLong())
        statement.bindText(18, entity.chipsNotes)
        statement.bindText(19, entity.efflorescenceRating)
        statement.bindText(20, entity.efflorescenceNotes)
        statement.bindText(21, entity.stainsRating)
        statement.bindText(22, entity.stainsColor)
        statement.bindText(23, entity.stainsNotes)
        statement.bindText(24, entity.cracksRating)
        statement.bindText(25, entity.cracksNotes)
        statement.bindText(26, entity.hairlinesRating)
        statement.bindText(27, entity.hairlinesNotes)
        statement.bindText(28, entity.breaksRating)
        statement.bindText(29, entity.breaksNotes)
        statement.bindText(30, entity.qualityNotes)
        statement.bindLong(31, entity.lastUpdated)
        statement.bindText(32, entity.syncStatus)
        statement.bindText(33, entity.id)
      }
    }
  }

  public override suspend fun insertReport(report: ScaricatriceReport): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfScaricatriceReport.insert(_connection, report)
  }

  public override suspend fun insertScrapRecord(record: ScaricatriceScrapRecord): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfScaricatriceScrapRecord.insert(_connection, record)
  }

  public override suspend fun insertKilnCar(car: ScaricatriceKilnCar): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfScaricatriceKilnCar.insert(_connection, car)
  }

  public override suspend fun deleteKilnCar(car: ScaricatriceKilnCar): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfScaricatriceKilnCar.handle(_connection, car)
  }

  public override suspend fun updateReport(report: ScaricatriceReport): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfScaricatriceReport.handle(_connection, report)
  }

  public override suspend fun updateKilnCar(car: ScaricatriceKilnCar): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfScaricatriceKilnCar.handle(_connection, car)
  }

  public override fun getReportsByContext(lineId: String, machineId: String): Flow<List<ScaricatriceReport>> {
    val _sql: String = "SELECT * FROM scaricatrice_reports WHERE lineId = ? AND machineId = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("scaricatrice_reports")) { _connection ->
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
        val _columnIndexOfPacchi1aScelta: Int = getColumnIndexOrThrow(_stmt, "pacchi1aScelta")
        val _columnIndexOfPacchi2aScelta: Int = getColumnIndexOrThrow(_stmt, "pacchi2aScelta")
        val _columnIndexOfBobineNylon: Int = getColumnIndexOrThrow(_stmt, "bobineNylon")
        val _columnIndexOfBobineReggia: Int = getColumnIndexOrThrow(_stmt, "bobineReggia")
        val _columnIndexOfPacchiSquadratura: Int = getColumnIndexOrThrow(_stmt, "pacchiSquadratura")
        val _columnIndexOfPacchiImballo: Int = getColumnIndexOrThrow(_stmt, "pacchiImballo")
        val _columnIndexOfPacchiCentraturaPallet: Int = getColumnIndexOrThrow(_stmt, "pacchiCentraturaPallet")
        val _columnIndexOfPacchiEtichettaOk: Int = getColumnIndexOrThrow(_stmt, "pacchiEtichettaOk")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ScaricatriceReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScaricatriceReport
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
          val _tmpPacchi1aScelta: Int
          _tmpPacchi1aScelta = _stmt.getLong(_columnIndexOfPacchi1aScelta).toInt()
          val _tmpPacchi2aScelta: Int
          _tmpPacchi2aScelta = _stmt.getLong(_columnIndexOfPacchi2aScelta).toInt()
          val _tmpBobineNylon: Int
          _tmpBobineNylon = _stmt.getLong(_columnIndexOfBobineNylon).toInt()
          val _tmpBobineReggia: Int
          _tmpBobineReggia = _stmt.getLong(_columnIndexOfBobineReggia).toInt()
          val _tmpPacchiSquadratura: String
          _tmpPacchiSquadratura = _stmt.getText(_columnIndexOfPacchiSquadratura)
          val _tmpPacchiImballo: String
          _tmpPacchiImballo = _stmt.getText(_columnIndexOfPacchiImballo)
          val _tmpPacchiCentraturaPallet: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfPacchiCentraturaPallet).toInt()
          _tmpPacchiCentraturaPallet = _tmp_1 != 0
          val _tmpPacchiEtichettaOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfPacchiEtichettaOk).toInt()
          _tmpPacchiEtichettaOk = _tmp_2 != 0
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ScaricatriceReport(_tmpId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpOperatorId,_tmpProductId,_tmpShift,_tmpSigla,_tmpLineId,_tmpMachineId,_tmpIsClosed,_tmpPacchi1aScelta,_tmpPacchi2aScelta,_tmpBobineNylon,_tmpBobineReggia,_tmpPacchiSquadratura,_tmpPacchiImballo,_tmpPacchiCentraturaPallet,_tmpPacchiEtichettaOk,_tmpScrapMinutes,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getReportById(reportId: String): Flow<ScaricatriceReport?> {
    val _sql: String = "SELECT * FROM scaricatrice_reports WHERE id = ?"
    return createFlow(__db, false, arrayOf("scaricatrice_reports")) { _connection ->
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
        val _columnIndexOfPacchi1aScelta: Int = getColumnIndexOrThrow(_stmt, "pacchi1aScelta")
        val _columnIndexOfPacchi2aScelta: Int = getColumnIndexOrThrow(_stmt, "pacchi2aScelta")
        val _columnIndexOfBobineNylon: Int = getColumnIndexOrThrow(_stmt, "bobineNylon")
        val _columnIndexOfBobineReggia: Int = getColumnIndexOrThrow(_stmt, "bobineReggia")
        val _columnIndexOfPacchiSquadratura: Int = getColumnIndexOrThrow(_stmt, "pacchiSquadratura")
        val _columnIndexOfPacchiImballo: Int = getColumnIndexOrThrow(_stmt, "pacchiImballo")
        val _columnIndexOfPacchiCentraturaPallet: Int = getColumnIndexOrThrow(_stmt, "pacchiCentraturaPallet")
        val _columnIndexOfPacchiEtichettaOk: Int = getColumnIndexOrThrow(_stmt, "pacchiEtichettaOk")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: ScaricatriceReport?
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
          val _tmpPacchi1aScelta: Int
          _tmpPacchi1aScelta = _stmt.getLong(_columnIndexOfPacchi1aScelta).toInt()
          val _tmpPacchi2aScelta: Int
          _tmpPacchi2aScelta = _stmt.getLong(_columnIndexOfPacchi2aScelta).toInt()
          val _tmpBobineNylon: Int
          _tmpBobineNylon = _stmt.getLong(_columnIndexOfBobineNylon).toInt()
          val _tmpBobineReggia: Int
          _tmpBobineReggia = _stmt.getLong(_columnIndexOfBobineReggia).toInt()
          val _tmpPacchiSquadratura: String
          _tmpPacchiSquadratura = _stmt.getText(_columnIndexOfPacchiSquadratura)
          val _tmpPacchiImballo: String
          _tmpPacchiImballo = _stmt.getText(_columnIndexOfPacchiImballo)
          val _tmpPacchiCentraturaPallet: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfPacchiCentraturaPallet).toInt()
          _tmpPacchiCentraturaPallet = _tmp_1 != 0
          val _tmpPacchiEtichettaOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfPacchiEtichettaOk).toInt()
          _tmpPacchiEtichettaOk = _tmp_2 != 0
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _result = ScaricatriceReport(_tmpId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpOperatorId,_tmpProductId,_tmpShift,_tmpSigla,_tmpLineId,_tmpMachineId,_tmpIsClosed,_tmpPacchi1aScelta,_tmpPacchi2aScelta,_tmpBobineNylon,_tmpBobineReggia,_tmpPacchiSquadratura,_tmpPacchiImballo,_tmpPacchiCentraturaPallet,_tmpPacchiEtichettaOk,_tmpScrapMinutes,_tmpLastUpdated,_tmpSyncStatus)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getScrapRecords(reportId: String): Flow<List<ScaricatriceScrapRecord>> {
    val _sql: String = "SELECT * FROM scaricatrice_scrap_records WHERE reportId = ? ORDER BY timestamp ASC"
    return createFlow(__db, false, arrayOf("scaricatrice_scrap_records")) { _connection ->
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
        val _result: MutableList<ScaricatriceScrapRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScaricatriceScrapRecord
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
          _item = ScaricatriceScrapRecord(_tmpId,_tmpReportId,_tmpTimestamp,_tmpPlcValue,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getKilnCars(reportId: String): Flow<List<ScaricatriceKilnCar>> {
    val _sql: String = "SELECT * FROM scaricatrice_kiln_cars WHERE reportId = ? ORDER BY startTime ASC"
    return createFlow(__db, false, arrayOf("scaricatrice_kiln_cars")) { _connection ->
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
        val _columnIndexOfRefractoriesOk: Int = getColumnIndexOrThrow(_stmt, "refractoriesOk")
        val _columnIndexOfScelta: Int = getColumnIndexOrThrow(_stmt, "scelta")
        val _columnIndexOfSoundAltoOk: Int = getColumnIndexOrThrow(_stmt, "soundAltoOk")
        val _columnIndexOfSoundCentroOk: Int = getColumnIndexOrThrow(_stmt, "soundCentroOk")
        val _columnIndexOfSoundBassoOk: Int = getColumnIndexOrThrow(_stmt, "soundBassoOk")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWidth: Int = getColumnIndexOrThrow(_stmt, "width")
        val _columnIndexOfThickness: Int = getColumnIndexOrThrow(_stmt, "thickness")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfFiringRating: Int = getColumnIndexOrThrow(_stmt, "firingRating")
        val _columnIndexOfChipsOk: Int = getColumnIndexOrThrow(_stmt, "chipsOk")
        val _columnIndexOfChipsNotes: Int = getColumnIndexOrThrow(_stmt, "chipsNotes")
        val _columnIndexOfEfflorescenceRating: Int = getColumnIndexOrThrow(_stmt, "efflorescenceRating")
        val _columnIndexOfEfflorescenceNotes: Int = getColumnIndexOrThrow(_stmt, "efflorescenceNotes")
        val _columnIndexOfStainsRating: Int = getColumnIndexOrThrow(_stmt, "stainsRating")
        val _columnIndexOfStainsColor: Int = getColumnIndexOrThrow(_stmt, "stainsColor")
        val _columnIndexOfStainsNotes: Int = getColumnIndexOrThrow(_stmt, "stainsNotes")
        val _columnIndexOfCracksRating: Int = getColumnIndexOrThrow(_stmt, "cracksRating")
        val _columnIndexOfCracksNotes: Int = getColumnIndexOrThrow(_stmt, "cracksNotes")
        val _columnIndexOfHairlinesRating: Int = getColumnIndexOrThrow(_stmt, "hairlinesRating")
        val _columnIndexOfHairlinesNotes: Int = getColumnIndexOrThrow(_stmt, "hairlinesNotes")
        val _columnIndexOfBreaksRating: Int = getColumnIndexOrThrow(_stmt, "breaksRating")
        val _columnIndexOfBreaksNotes: Int = getColumnIndexOrThrow(_stmt, "breaksNotes")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ScaricatriceKilnCar> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScaricatriceKilnCar
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
          val _tmpRefractoriesOk: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfRefractoriesOk).toInt()
          _tmpRefractoriesOk = _tmp_1 != 0
          val _tmpScelta: String?
          if (_stmt.isNull(_columnIndexOfScelta)) {
            _tmpScelta = null
          } else {
            _tmpScelta = _stmt.getText(_columnIndexOfScelta)
          }
          val _tmpSoundAltoOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfSoundAltoOk).toInt()
          _tmpSoundAltoOk = _tmp_2 != 0
          val _tmpSoundCentroOk: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSoundCentroOk).toInt()
          _tmpSoundCentroOk = _tmp_3 != 0
          val _tmpSoundBassoOk: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfSoundBassoOk).toInt()
          _tmpSoundBassoOk = _tmp_4 != 0
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWidth: Double
          _tmpWidth = _stmt.getDouble(_columnIndexOfWidth)
          val _tmpThickness: Double
          _tmpThickness = _stmt.getDouble(_columnIndexOfThickness)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpFiringRating: String
          _tmpFiringRating = _stmt.getText(_columnIndexOfFiringRating)
          val _tmpChipsOk: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfChipsOk).toInt()
          _tmpChipsOk = _tmp_5 != 0
          val _tmpChipsNotes: String
          _tmpChipsNotes = _stmt.getText(_columnIndexOfChipsNotes)
          val _tmpEfflorescenceRating: String
          _tmpEfflorescenceRating = _stmt.getText(_columnIndexOfEfflorescenceRating)
          val _tmpEfflorescenceNotes: String
          _tmpEfflorescenceNotes = _stmt.getText(_columnIndexOfEfflorescenceNotes)
          val _tmpStainsRating: String
          _tmpStainsRating = _stmt.getText(_columnIndexOfStainsRating)
          val _tmpStainsColor: String
          _tmpStainsColor = _stmt.getText(_columnIndexOfStainsColor)
          val _tmpStainsNotes: String
          _tmpStainsNotes = _stmt.getText(_columnIndexOfStainsNotes)
          val _tmpCracksRating: String
          _tmpCracksRating = _stmt.getText(_columnIndexOfCracksRating)
          val _tmpCracksNotes: String
          _tmpCracksNotes = _stmt.getText(_columnIndexOfCracksNotes)
          val _tmpHairlinesRating: String
          _tmpHairlinesRating = _stmt.getText(_columnIndexOfHairlinesRating)
          val _tmpHairlinesNotes: String
          _tmpHairlinesNotes = _stmt.getText(_columnIndexOfHairlinesNotes)
          val _tmpBreaksRating: String
          _tmpBreaksRating = _stmt.getText(_columnIndexOfBreaksRating)
          val _tmpBreaksNotes: String
          _tmpBreaksNotes = _stmt.getText(_columnIndexOfBreaksNotes)
          val _tmpQualityNotes: String
          _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ScaricatriceKilnCar(_tmpId,_tmpReportId,_tmpStartTime,_tmpEndTime,_tmpSigla,_tmpIsCleaned,_tmpRefractoriesOk,_tmpScelta,_tmpSoundAltoOk,_tmpSoundCentroOk,_tmpSoundBassoOk,_tmpHeight,_tmpWidth,_tmpThickness,_tmpWeight,_tmpFiringRating,_tmpChipsOk,_tmpChipsNotes,_tmpEfflorescenceRating,_tmpEfflorescenceNotes,_tmpStainsRating,_tmpStainsColor,_tmpStainsNotes,_tmpCracksRating,_tmpCracksNotes,_tmpHairlinesRating,_tmpHairlinesNotes,_tmpBreaksRating,_tmpBreaksNotes,_tmpQualityNotes,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedReports(): Flow<List<ScaricatriceReport>> {
    val _sql: String = "SELECT * FROM scaricatrice_reports WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("scaricatrice_reports")) { _connection ->
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
        val _columnIndexOfPacchi1aScelta: Int = getColumnIndexOrThrow(_stmt, "pacchi1aScelta")
        val _columnIndexOfPacchi2aScelta: Int = getColumnIndexOrThrow(_stmt, "pacchi2aScelta")
        val _columnIndexOfBobineNylon: Int = getColumnIndexOrThrow(_stmt, "bobineNylon")
        val _columnIndexOfBobineReggia: Int = getColumnIndexOrThrow(_stmt, "bobineReggia")
        val _columnIndexOfPacchiSquadratura: Int = getColumnIndexOrThrow(_stmt, "pacchiSquadratura")
        val _columnIndexOfPacchiImballo: Int = getColumnIndexOrThrow(_stmt, "pacchiImballo")
        val _columnIndexOfPacchiCentraturaPallet: Int = getColumnIndexOrThrow(_stmt, "pacchiCentraturaPallet")
        val _columnIndexOfPacchiEtichettaOk: Int = getColumnIndexOrThrow(_stmt, "pacchiEtichettaOk")
        val _columnIndexOfScrapMinutes: Int = getColumnIndexOrThrow(_stmt, "scrapMinutes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ScaricatriceReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScaricatriceReport
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
          val _tmpPacchi1aScelta: Int
          _tmpPacchi1aScelta = _stmt.getLong(_columnIndexOfPacchi1aScelta).toInt()
          val _tmpPacchi2aScelta: Int
          _tmpPacchi2aScelta = _stmt.getLong(_columnIndexOfPacchi2aScelta).toInt()
          val _tmpBobineNylon: Int
          _tmpBobineNylon = _stmt.getLong(_columnIndexOfBobineNylon).toInt()
          val _tmpBobineReggia: Int
          _tmpBobineReggia = _stmt.getLong(_columnIndexOfBobineReggia).toInt()
          val _tmpPacchiSquadratura: String
          _tmpPacchiSquadratura = _stmt.getText(_columnIndexOfPacchiSquadratura)
          val _tmpPacchiImballo: String
          _tmpPacchiImballo = _stmt.getText(_columnIndexOfPacchiImballo)
          val _tmpPacchiCentraturaPallet: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfPacchiCentraturaPallet).toInt()
          _tmpPacchiCentraturaPallet = _tmp_1 != 0
          val _tmpPacchiEtichettaOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfPacchiEtichettaOk).toInt()
          _tmpPacchiEtichettaOk = _tmp_2 != 0
          val _tmpScrapMinutes: Int
          _tmpScrapMinutes = _stmt.getLong(_columnIndexOfScrapMinutes).toInt()
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ScaricatriceReport(_tmpId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpOperatorId,_tmpProductId,_tmpShift,_tmpSigla,_tmpLineId,_tmpMachineId,_tmpIsClosed,_tmpPacchi1aScelta,_tmpPacchi2aScelta,_tmpBobineNylon,_tmpBobineReggia,_tmpPacchiSquadratura,_tmpPacchiImballo,_tmpPacchiCentraturaPallet,_tmpPacchiEtichettaOk,_tmpScrapMinutes,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedScrapRecords(): Flow<List<ScaricatriceScrapRecord>> {
    val _sql: String = "SELECT * FROM scaricatrice_scrap_records WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("scaricatrice_scrap_records")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfPlcValue: Int = getColumnIndexOrThrow(_stmt, "plcValue")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ScaricatriceScrapRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScaricatriceScrapRecord
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
          _item = ScaricatriceScrapRecord(_tmpId,_tmpReportId,_tmpTimestamp,_tmpPlcValue,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsyncedKilnCars(): Flow<List<ScaricatriceKilnCar>> {
    val _sql: String = "SELECT * FROM scaricatrice_kiln_cars WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("scaricatrice_kiln_cars")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfReportId: Int = getColumnIndexOrThrow(_stmt, "reportId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfSigla: Int = getColumnIndexOrThrow(_stmt, "sigla")
        val _columnIndexOfIsCleaned: Int = getColumnIndexOrThrow(_stmt, "isCleaned")
        val _columnIndexOfRefractoriesOk: Int = getColumnIndexOrThrow(_stmt, "refractoriesOk")
        val _columnIndexOfScelta: Int = getColumnIndexOrThrow(_stmt, "scelta")
        val _columnIndexOfSoundAltoOk: Int = getColumnIndexOrThrow(_stmt, "soundAltoOk")
        val _columnIndexOfSoundCentroOk: Int = getColumnIndexOrThrow(_stmt, "soundCentroOk")
        val _columnIndexOfSoundBassoOk: Int = getColumnIndexOrThrow(_stmt, "soundBassoOk")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWidth: Int = getColumnIndexOrThrow(_stmt, "width")
        val _columnIndexOfThickness: Int = getColumnIndexOrThrow(_stmt, "thickness")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfFiringRating: Int = getColumnIndexOrThrow(_stmt, "firingRating")
        val _columnIndexOfChipsOk: Int = getColumnIndexOrThrow(_stmt, "chipsOk")
        val _columnIndexOfChipsNotes: Int = getColumnIndexOrThrow(_stmt, "chipsNotes")
        val _columnIndexOfEfflorescenceRating: Int = getColumnIndexOrThrow(_stmt, "efflorescenceRating")
        val _columnIndexOfEfflorescenceNotes: Int = getColumnIndexOrThrow(_stmt, "efflorescenceNotes")
        val _columnIndexOfStainsRating: Int = getColumnIndexOrThrow(_stmt, "stainsRating")
        val _columnIndexOfStainsColor: Int = getColumnIndexOrThrow(_stmt, "stainsColor")
        val _columnIndexOfStainsNotes: Int = getColumnIndexOrThrow(_stmt, "stainsNotes")
        val _columnIndexOfCracksRating: Int = getColumnIndexOrThrow(_stmt, "cracksRating")
        val _columnIndexOfCracksNotes: Int = getColumnIndexOrThrow(_stmt, "cracksNotes")
        val _columnIndexOfHairlinesRating: Int = getColumnIndexOrThrow(_stmt, "hairlinesRating")
        val _columnIndexOfHairlinesNotes: Int = getColumnIndexOrThrow(_stmt, "hairlinesNotes")
        val _columnIndexOfBreaksRating: Int = getColumnIndexOrThrow(_stmt, "breaksRating")
        val _columnIndexOfBreaksNotes: Int = getColumnIndexOrThrow(_stmt, "breaksNotes")
        val _columnIndexOfQualityNotes: Int = getColumnIndexOrThrow(_stmt, "qualityNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ScaricatriceKilnCar> = mutableListOf()
        while (_stmt.step()) {
          val _item: ScaricatriceKilnCar
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
          val _tmpRefractoriesOk: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfRefractoriesOk).toInt()
          _tmpRefractoriesOk = _tmp_1 != 0
          val _tmpScelta: String?
          if (_stmt.isNull(_columnIndexOfScelta)) {
            _tmpScelta = null
          } else {
            _tmpScelta = _stmt.getText(_columnIndexOfScelta)
          }
          val _tmpSoundAltoOk: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfSoundAltoOk).toInt()
          _tmpSoundAltoOk = _tmp_2 != 0
          val _tmpSoundCentroOk: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSoundCentroOk).toInt()
          _tmpSoundCentroOk = _tmp_3 != 0
          val _tmpSoundBassoOk: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfSoundBassoOk).toInt()
          _tmpSoundBassoOk = _tmp_4 != 0
          val _tmpHeight: Double
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight)
          val _tmpWidth: Double
          _tmpWidth = _stmt.getDouble(_columnIndexOfWidth)
          val _tmpThickness: Double
          _tmpThickness = _stmt.getDouble(_columnIndexOfThickness)
          val _tmpWeight: Double
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight)
          val _tmpFiringRating: String
          _tmpFiringRating = _stmt.getText(_columnIndexOfFiringRating)
          val _tmpChipsOk: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfChipsOk).toInt()
          _tmpChipsOk = _tmp_5 != 0
          val _tmpChipsNotes: String
          _tmpChipsNotes = _stmt.getText(_columnIndexOfChipsNotes)
          val _tmpEfflorescenceRating: String
          _tmpEfflorescenceRating = _stmt.getText(_columnIndexOfEfflorescenceRating)
          val _tmpEfflorescenceNotes: String
          _tmpEfflorescenceNotes = _stmt.getText(_columnIndexOfEfflorescenceNotes)
          val _tmpStainsRating: String
          _tmpStainsRating = _stmt.getText(_columnIndexOfStainsRating)
          val _tmpStainsColor: String
          _tmpStainsColor = _stmt.getText(_columnIndexOfStainsColor)
          val _tmpStainsNotes: String
          _tmpStainsNotes = _stmt.getText(_columnIndexOfStainsNotes)
          val _tmpCracksRating: String
          _tmpCracksRating = _stmt.getText(_columnIndexOfCracksRating)
          val _tmpCracksNotes: String
          _tmpCracksNotes = _stmt.getText(_columnIndexOfCracksNotes)
          val _tmpHairlinesRating: String
          _tmpHairlinesRating = _stmt.getText(_columnIndexOfHairlinesRating)
          val _tmpHairlinesNotes: String
          _tmpHairlinesNotes = _stmt.getText(_columnIndexOfHairlinesNotes)
          val _tmpBreaksRating: String
          _tmpBreaksRating = _stmt.getText(_columnIndexOfBreaksRating)
          val _tmpBreaksNotes: String
          _tmpBreaksNotes = _stmt.getText(_columnIndexOfBreaksNotes)
          val _tmpQualityNotes: String
          _tmpQualityNotes = _stmt.getText(_columnIndexOfQualityNotes)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ScaricatriceKilnCar(_tmpId,_tmpReportId,_tmpStartTime,_tmpEndTime,_tmpSigla,_tmpIsCleaned,_tmpRefractoriesOk,_tmpScelta,_tmpSoundAltoOk,_tmpSoundCentroOk,_tmpSoundBassoOk,_tmpHeight,_tmpWidth,_tmpThickness,_tmpWeight,_tmpFiringRating,_tmpChipsOk,_tmpChipsNotes,_tmpEfflorescenceRating,_tmpEfflorescenceNotes,_tmpStainsRating,_tmpStainsColor,_tmpStainsNotes,_tmpCracksRating,_tmpCracksNotes,_tmpHairlinesRating,_tmpHairlinesNotes,_tmpBreaksRating,_tmpBreaksNotes,_tmpQualityNotes,_tmpLastUpdated,_tmpSyncStatus)
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
