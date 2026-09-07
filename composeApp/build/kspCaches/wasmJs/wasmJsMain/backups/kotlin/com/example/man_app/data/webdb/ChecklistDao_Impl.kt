package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.ChecklistResponse
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
internal class ChecklistDao_Impl(
  __db: RoomDatabase,
) : ChecklistDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfChecklistResponse: EntityInsertAdapter<ChecklistResponse>

  private val __updateAdapterOfChecklistResponse: EntityDeleteOrUpdateAdapter<ChecklistResponse>
  init {
    this.__db = __db
    this.__insertAdapterOfChecklistResponse = object : EntityInsertAdapter<ChecklistResponse>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `checklist_responses` (`id`,`checklistId`,`itemId`,`operatorId`,`timestamp`,`isChecked`,`note`,`lastUpdated`,`syncStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ChecklistResponse) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.checklistId)
        statement.bindText(3, entity.itemId)
        statement.bindText(4, entity.operatorId)
        statement.bindLong(5, entity.timestamp)
        val _tmp: Int = if (entity.isChecked) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpNote)
        }
        statement.bindLong(8, entity.lastUpdated)
        statement.bindText(9, entity.syncStatus)
      }
    }
    this.__updateAdapterOfChecklistResponse = object : EntityDeleteOrUpdateAdapter<ChecklistResponse>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `checklist_responses` SET `id` = ?,`checklistId` = ?,`itemId` = ?,`operatorId` = ?,`timestamp` = ?,`isChecked` = ?,`note` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ChecklistResponse) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.checklistId)
        statement.bindText(3, entity.itemId)
        statement.bindText(4, entity.operatorId)
        statement.bindLong(5, entity.timestamp)
        val _tmp: Int = if (entity.isChecked) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        val _tmpNote: String? = entity.note
        if (_tmpNote == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpNote)
        }
        statement.bindLong(8, entity.lastUpdated)
        statement.bindText(9, entity.syncStatus)
        statement.bindLong(10, entity.id)
      }
    }
  }

  public override suspend fun insertResponse(response: ChecklistResponse): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfChecklistResponse.insert(_connection, response)
  }

  public override suspend fun updateResponse(response: ChecklistResponse): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfChecklistResponse.handle(_connection, response)
  }

  public override fun getUnsyncedResponses(): Flow<List<ChecklistResponse>> {
    val _sql: String = "SELECT * FROM checklist_responses WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("checklist_responses")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfChecklistId: Int = getColumnIndexOrThrow(_stmt, "checklistId")
        val _columnIndexOfItemId: Int = getColumnIndexOrThrow(_stmt, "itemId")
        val _columnIndexOfOperatorId: Int = getColumnIndexOrThrow(_stmt, "operatorId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfIsChecked: Int = getColumnIndexOrThrow(_stmt, "isChecked")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ChecklistResponse> = mutableListOf()
        while (_stmt.step()) {
          val _item: ChecklistResponse
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpChecklistId: String
          _tmpChecklistId = _stmt.getText(_columnIndexOfChecklistId)
          val _tmpItemId: String
          _tmpItemId = _stmt.getText(_columnIndexOfItemId)
          val _tmpOperatorId: String
          _tmpOperatorId = _stmt.getText(_columnIndexOfOperatorId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpIsChecked: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsChecked).toInt()
          _tmpIsChecked = _tmp != 0
          val _tmpNote: String?
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote)
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ChecklistResponse(_tmpId,_tmpChecklistId,_tmpItemId,_tmpOperatorId,_tmpTimestamp,_tmpIsChecked,_tmpNote,_tmpLastUpdated,_tmpSyncStatus)
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
