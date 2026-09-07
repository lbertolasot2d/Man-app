package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.Personnel
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
internal class PersonnelDao_Impl(
  __db: RoomDatabase,
) : PersonnelDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPersonnel: EntityInsertAdapter<Personnel>

  private val __deleteAdapterOfPersonnel: EntityDeleteOrUpdateAdapter<Personnel>

  private val __updateAdapterOfPersonnel: EntityDeleteOrUpdateAdapter<Personnel>
  init {
    this.__db = __db
    this.__insertAdapterOfPersonnel = object : EntityInsertAdapter<Personnel>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `personnel` (`id`,`firstName`,`lastName`,`role`,`line`,`department`,`isActive`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Personnel) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.firstName)
        statement.bindText(3, entity.lastName)
        statement.bindText(4, entity.role)
        statement.bindText(5, entity.line)
        statement.bindText(6, entity.department)
        val _tmp: Int = if (entity.isActive) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        statement.bindLong(8, entity.lastUpdated)
        statement.bindText(9, entity.syncStatus)
      }
    }
    this.__deleteAdapterOfPersonnel = object : EntityDeleteOrUpdateAdapter<Personnel>() {
      protected override fun createQuery(): String = "DELETE FROM `personnel` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Personnel) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfPersonnel = object : EntityDeleteOrUpdateAdapter<Personnel>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `personnel` SET `id` = ?,`firstName` = ?,`lastName` = ?,`role` = ?,`line` = ?,`department` = ?,`isActive` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Personnel) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.firstName)
        statement.bindText(3, entity.lastName)
        statement.bindText(4, entity.role)
        statement.bindText(5, entity.line)
        statement.bindText(6, entity.department)
        val _tmp: Int = if (entity.isActive) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        statement.bindLong(8, entity.lastUpdated)
        statement.bindText(9, entity.syncStatus)
        statement.bindText(10, entity.id)
      }
    }
  }

  public override suspend fun insert(personnel: Personnel): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPersonnel.insert(_connection, personnel)
  }

  public override suspend fun delete(personnel: Personnel): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfPersonnel.handle(_connection, personnel)
  }

  public override suspend fun update(personnel: Personnel): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfPersonnel.handle(_connection, personnel)
  }

  public override fun getAll(): Flow<List<Personnel>> {
    val _sql: String = "SELECT * FROM personnel"
    return createFlow(__db, false, arrayOf("personnel")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfFirstName: Int = getColumnIndexOrThrow(_stmt, "firstName")
        val _columnIndexOfLastName: Int = getColumnIndexOrThrow(_stmt, "lastName")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfLine: Int = getColumnIndexOrThrow(_stmt, "line")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<Personnel> = mutableListOf()
        while (_stmt.step()) {
          val _item: Personnel
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpFirstName: String
          _tmpFirstName = _stmt.getText(_columnIndexOfFirstName)
          val _tmpLastName: String
          _tmpLastName = _stmt.getText(_columnIndexOfLastName)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpLine: String
          _tmpLine = _stmt.getText(_columnIndexOfLine)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpIsActive: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = Personnel(_tmpId,_tmpFirstName,_tmpLastName,_tmpRole,_tmpLine,_tmpDepartment,_tmpIsActive,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM personnel"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsynced(): Flow<List<Personnel>> {
    val _sql: String = "SELECT * FROM personnel WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("personnel")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfFirstName: Int = getColumnIndexOrThrow(_stmt, "firstName")
        val _columnIndexOfLastName: Int = getColumnIndexOrThrow(_stmt, "lastName")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfLine: Int = getColumnIndexOrThrow(_stmt, "line")
        val _columnIndexOfDepartment: Int = getColumnIndexOrThrow(_stmt, "department")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<Personnel> = mutableListOf()
        while (_stmt.step()) {
          val _item: Personnel
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpFirstName: String
          _tmpFirstName = _stmt.getText(_columnIndexOfFirstName)
          val _tmpLastName: String
          _tmpLastName = _stmt.getText(_columnIndexOfLastName)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpLine: String
          _tmpLine = _stmt.getText(_columnIndexOfLine)
          val _tmpDepartment: String
          _tmpDepartment = _stmt.getText(_columnIndexOfDepartment)
          val _tmpIsActive: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp != 0
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = Personnel(_tmpId,_tmpFirstName,_tmpLastName,_tmpRole,_tmpLine,_tmpDepartment,_tmpIsActive,_tmpLastUpdated,_tmpSyncStatus)
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
