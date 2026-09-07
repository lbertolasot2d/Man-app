package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.FaultReport
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
internal class FaultDao_Impl(
  __db: RoomDatabase,
) : FaultDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfFaultReport: EntityInsertAdapter<FaultReport>

  private val __updateAdapterOfFaultReport: EntityDeleteOrUpdateAdapter<FaultReport>
  init {
    this.__db = __db
    this.__insertAdapterOfFaultReport = object : EntityInsertAdapter<FaultReport>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `fault_reports` (`id`,`timestamp`,`machineId`,`description`,`reporterId`,`severity`,`isResolved`,`resolutionNotes`,`lastUpdated`,`syncStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: FaultReport) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.timestamp)
        statement.bindText(3, entity.machineId)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.reporterId)
        statement.bindText(6, entity.severity)
        val _tmp: Int = if (entity.isResolved) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmpResolutionNotes: String? = entity.resolutionNotes
        if (_tmpResolutionNotes == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpResolutionNotes)
        }
        statement.bindLong(9, entity.lastUpdated)
        statement.bindText(10, entity.syncStatus)
      }
    }
    this.__updateAdapterOfFaultReport = object : EntityDeleteOrUpdateAdapter<FaultReport>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `fault_reports` SET `id` = ?,`timestamp` = ?,`machineId` = ?,`description` = ?,`reporterId` = ?,`severity` = ?,`isResolved` = ?,`resolutionNotes` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: FaultReport) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.timestamp)
        statement.bindText(3, entity.machineId)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.reporterId)
        statement.bindText(6, entity.severity)
        val _tmp: Int = if (entity.isResolved) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmpResolutionNotes: String? = entity.resolutionNotes
        if (_tmpResolutionNotes == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpResolutionNotes)
        }
        statement.bindLong(9, entity.lastUpdated)
        statement.bindText(10, entity.syncStatus)
        statement.bindLong(11, entity.id)
      }
    }
  }

  public override suspend fun insert(report: FaultReport): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfFaultReport.insert(_connection, report)
  }

  public override suspend fun update(report: FaultReport): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfFaultReport.handle(_connection, report)
  }

  public override fun getUnsynced(): Flow<List<FaultReport>> {
    val _sql: String = "SELECT * FROM fault_reports WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("fault_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfReporterId: Int = getColumnIndexOrThrow(_stmt, "reporterId")
        val _columnIndexOfSeverity: Int = getColumnIndexOrThrow(_stmt, "severity")
        val _columnIndexOfIsResolved: Int = getColumnIndexOrThrow(_stmt, "isResolved")
        val _columnIndexOfResolutionNotes: Int = getColumnIndexOrThrow(_stmt, "resolutionNotes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<FaultReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: FaultReport
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpReporterId: String
          _tmpReporterId = _stmt.getText(_columnIndexOfReporterId)
          val _tmpSeverity: String
          _tmpSeverity = _stmt.getText(_columnIndexOfSeverity)
          val _tmpIsResolved: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsResolved).toInt()
          _tmpIsResolved = _tmp != 0
          val _tmpResolutionNotes: String?
          if (_stmt.isNull(_columnIndexOfResolutionNotes)) {
            _tmpResolutionNotes = null
          } else {
            _tmpResolutionNotes = _stmt.getText(_columnIndexOfResolutionNotes)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = FaultReport(_tmpId,_tmpTimestamp,_tmpMachineId,_tmpDescription,_tmpReporterId,_tmpSeverity,_tmpIsResolved,_tmpResolutionNotes,_tmpLastUpdated,_tmpSyncStatus)
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
