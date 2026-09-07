package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.ProductionReport
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
internal class ProductionDao_Impl(
  __db: RoomDatabase,
) : ProductionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfProductionReport: EntityInsertAdapter<ProductionReport>

  private val __updateAdapterOfProductionReport: EntityDeleteOrUpdateAdapter<ProductionReport>
  init {
    this.__db = __db
    this.__insertAdapterOfProductionReport = object : EntityInsertAdapter<ProductionReport>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `production_reports` (`id`,`date`,`shift`,`operatorId`,`machineId`,`unitsProduced`,`unitsDefective`,`notes`,`lastUpdated`,`syncStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ProductionReport) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.date)
        statement.bindText(3, entity.shift)
        statement.bindText(4, entity.operatorId)
        statement.bindText(5, entity.machineId)
        statement.bindLong(6, entity.unitsProduced.toLong())
        statement.bindLong(7, entity.unitsDefective.toLong())
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpNotes)
        }
        statement.bindLong(9, entity.lastUpdated)
        statement.bindText(10, entity.syncStatus)
      }
    }
    this.__updateAdapterOfProductionReport = object : EntityDeleteOrUpdateAdapter<ProductionReport>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `production_reports` SET `id` = ?,`date` = ?,`shift` = ?,`operatorId` = ?,`machineId` = ?,`unitsProduced` = ?,`unitsDefective` = ?,`notes` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ProductionReport) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.date)
        statement.bindText(3, entity.shift)
        statement.bindText(4, entity.operatorId)
        statement.bindText(5, entity.machineId)
        statement.bindLong(6, entity.unitsProduced.toLong())
        statement.bindLong(7, entity.unitsDefective.toLong())
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpNotes)
        }
        statement.bindLong(9, entity.lastUpdated)
        statement.bindText(10, entity.syncStatus)
        statement.bindLong(11, entity.id)
      }
    }
  }

  public override suspend fun insert(report: ProductionReport): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfProductionReport.insert(_connection, report)
  }

  public override suspend fun update(report: ProductionReport): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfProductionReport.handle(_connection, report)
  }

  public override fun getUnsynced(): Flow<List<ProductionReport>> {
    val _sql: String = "SELECT * FROM production_reports WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("production_reports")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfShift: Int = getColumnIndexOrThrow(_stmt, "shift")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfMachineId: Int = getColumnIndexOrThrow(_stmt, "machineId")
        val _columnIndexOfUnitsProduced: Int = getColumnIndexOrThrow(_stmt, "unitsProduced")
        val _columnIndexOfUnitsDefective: Int = getColumnIndexOrThrow(_stmt, "unitsDefective")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ProductionReport> = mutableListOf()
        while (_stmt.step()) {
          val _item: ProductionReport
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpShift: String
          _tmpShift = _stmt.getText(_columnIndexOfShift)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpMachineId: String
          _tmpMachineId = _stmt.getText(_columnIndexOfMachineId)
          val _tmpUnitsProduced: Int
          _tmpUnitsProduced = _stmt.getLong(_columnIndexOfUnitsProduced).toInt()
          val _tmpUnitsDefective: Int
          _tmpUnitsDefective = _stmt.getLong(_columnIndexOfUnitsDefective).toInt()
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ProductionReport(_tmpId,_tmpDate,_tmpShift,_tmpOperatorId,_tmpMachineId,_tmpUnitsProduced,_tmpUnitsDefective,_tmpNotes,_tmpLastUpdated,_tmpSyncStatus)
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
