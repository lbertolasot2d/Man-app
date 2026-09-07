package com.example.man_app.`data`.webdb

import androidx.room3.EntityDeleteOrUpdateAdapter
import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.ShiftConfiguration
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
internal class ShiftDao_Impl(
  __db: RoomDatabase,
) : ShiftDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfShiftConfiguration: EntityInsertAdapter<ShiftConfiguration>

  private val __deleteAdapterOfShiftConfiguration: EntityDeleteOrUpdateAdapter<ShiftConfiguration>

  private val __updateAdapterOfShiftConfiguration: EntityDeleteOrUpdateAdapter<ShiftConfiguration>
  init {
    this.__db = __db
    this.__insertAdapterOfShiftConfiguration = object : EntityInsertAdapter<ShiftConfiguration>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `shift_configurations` (`id`,`name`,`lineId`,`mondayStart`,`mondayEnd`,`tuesdayStart`,`tuesdayEnd`,`wednesdayStart`,`wednesdayEnd`,`thursdayStart`,`thursdayEnd`,`fridayStart`,`fridayEnd`,`saturdayStart`,`saturdayEnd`,`sundayStart`,`sundayEnd`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ShiftConfiguration) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.lineId)
        val _tmpMondayStart: Int? = entity.mondayStart
        if (_tmpMondayStart == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpMondayStart.toLong())
        }
        val _tmpMondayEnd: Int? = entity.mondayEnd
        if (_tmpMondayEnd == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpMondayEnd.toLong())
        }
        val _tmpTuesdayStart: Int? = entity.tuesdayStart
        if (_tmpTuesdayStart == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpTuesdayStart.toLong())
        }
        val _tmpTuesdayEnd: Int? = entity.tuesdayEnd
        if (_tmpTuesdayEnd == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpTuesdayEnd.toLong())
        }
        val _tmpWednesdayStart: Int? = entity.wednesdayStart
        if (_tmpWednesdayStart == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpWednesdayStart.toLong())
        }
        val _tmpWednesdayEnd: Int? = entity.wednesdayEnd
        if (_tmpWednesdayEnd == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpWednesdayEnd.toLong())
        }
        val _tmpThursdayStart: Int? = entity.thursdayStart
        if (_tmpThursdayStart == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpThursdayStart.toLong())
        }
        val _tmpThursdayEnd: Int? = entity.thursdayEnd
        if (_tmpThursdayEnd == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpThursdayEnd.toLong())
        }
        val _tmpFridayStart: Int? = entity.fridayStart
        if (_tmpFridayStart == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpFridayStart.toLong())
        }
        val _tmpFridayEnd: Int? = entity.fridayEnd
        if (_tmpFridayEnd == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmpFridayEnd.toLong())
        }
        val _tmpSaturdayStart: Int? = entity.saturdayStart
        if (_tmpSaturdayStart == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmpSaturdayStart.toLong())
        }
        val _tmpSaturdayEnd: Int? = entity.saturdayEnd
        if (_tmpSaturdayEnd == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmpSaturdayEnd.toLong())
        }
        val _tmpSundayStart: Int? = entity.sundayStart
        if (_tmpSundayStart == null) {
          statement.bindNull(16)
        } else {
          statement.bindLong(16, _tmpSundayStart.toLong())
        }
        val _tmpSundayEnd: Int? = entity.sundayEnd
        if (_tmpSundayEnd == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmpSundayEnd.toLong())
        }
        statement.bindLong(18, entity.lastUpdated)
        statement.bindText(19, entity.syncStatus)
      }
    }
    this.__deleteAdapterOfShiftConfiguration = object : EntityDeleteOrUpdateAdapter<ShiftConfiguration>() {
      protected override fun createQuery(): String = "DELETE FROM `shift_configurations` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ShiftConfiguration) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfShiftConfiguration = object : EntityDeleteOrUpdateAdapter<ShiftConfiguration>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `shift_configurations` SET `id` = ?,`name` = ?,`lineId` = ?,`mondayStart` = ?,`mondayEnd` = ?,`tuesdayStart` = ?,`tuesdayEnd` = ?,`wednesdayStart` = ?,`wednesdayEnd` = ?,`thursdayStart` = ?,`thursdayEnd` = ?,`fridayStart` = ?,`fridayEnd` = ?,`saturdayStart` = ?,`saturdayEnd` = ?,`sundayStart` = ?,`sundayEnd` = ?,`lastUpdated` = ?,`syncStatus` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ShiftConfiguration) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.lineId)
        val _tmpMondayStart: Int? = entity.mondayStart
        if (_tmpMondayStart == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpMondayStart.toLong())
        }
        val _tmpMondayEnd: Int? = entity.mondayEnd
        if (_tmpMondayEnd == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpMondayEnd.toLong())
        }
        val _tmpTuesdayStart: Int? = entity.tuesdayStart
        if (_tmpTuesdayStart == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpTuesdayStart.toLong())
        }
        val _tmpTuesdayEnd: Int? = entity.tuesdayEnd
        if (_tmpTuesdayEnd == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpTuesdayEnd.toLong())
        }
        val _tmpWednesdayStart: Int? = entity.wednesdayStart
        if (_tmpWednesdayStart == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpWednesdayStart.toLong())
        }
        val _tmpWednesdayEnd: Int? = entity.wednesdayEnd
        if (_tmpWednesdayEnd == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpWednesdayEnd.toLong())
        }
        val _tmpThursdayStart: Int? = entity.thursdayStart
        if (_tmpThursdayStart == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpThursdayStart.toLong())
        }
        val _tmpThursdayEnd: Int? = entity.thursdayEnd
        if (_tmpThursdayEnd == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpThursdayEnd.toLong())
        }
        val _tmpFridayStart: Int? = entity.fridayStart
        if (_tmpFridayStart == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpFridayStart.toLong())
        }
        val _tmpFridayEnd: Int? = entity.fridayEnd
        if (_tmpFridayEnd == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmpFridayEnd.toLong())
        }
        val _tmpSaturdayStart: Int? = entity.saturdayStart
        if (_tmpSaturdayStart == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmpSaturdayStart.toLong())
        }
        val _tmpSaturdayEnd: Int? = entity.saturdayEnd
        if (_tmpSaturdayEnd == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmpSaturdayEnd.toLong())
        }
        val _tmpSundayStart: Int? = entity.sundayStart
        if (_tmpSundayStart == null) {
          statement.bindNull(16)
        } else {
          statement.bindLong(16, _tmpSundayStart.toLong())
        }
        val _tmpSundayEnd: Int? = entity.sundayEnd
        if (_tmpSundayEnd == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmpSundayEnd.toLong())
        }
        statement.bindLong(18, entity.lastUpdated)
        statement.bindText(19, entity.syncStatus)
        statement.bindText(20, entity.id)
      }
    }
  }

  public override suspend fun insert(shift: ShiftConfiguration): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfShiftConfiguration.insert(_connection, shift)
  }

  public override suspend fun delete(shift: ShiftConfiguration): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfShiftConfiguration.handle(_connection, shift)
  }

  public override suspend fun update(shift: ShiftConfiguration): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfShiftConfiguration.handle(_connection, shift)
  }

  public override fun getShiftsForLine(lineId: String): Flow<List<ShiftConfiguration>> {
    val _sql: String = "SELECT * FROM shift_configurations WHERE lineId = ?"
    return createFlow(__db, false, arrayOf("shift_configurations")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, lineId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMondayStart: Int = getColumnIndexOrThrow(_stmt, "mondayStart")
        val _columnIndexOfMondayEnd: Int = getColumnIndexOrThrow(_stmt, "mondayEnd")
        val _columnIndexOfTuesdayStart: Int = getColumnIndexOrThrow(_stmt, "tuesdayStart")
        val _columnIndexOfTuesdayEnd: Int = getColumnIndexOrThrow(_stmt, "tuesdayEnd")
        val _columnIndexOfWednesdayStart: Int = getColumnIndexOrThrow(_stmt, "wednesdayStart")
        val _columnIndexOfWednesdayEnd: Int = getColumnIndexOrThrow(_stmt, "wednesdayEnd")
        val _columnIndexOfThursdayStart: Int = getColumnIndexOrThrow(_stmt, "thursdayStart")
        val _columnIndexOfThursdayEnd: Int = getColumnIndexOrThrow(_stmt, "thursdayEnd")
        val _columnIndexOfFridayStart: Int = getColumnIndexOrThrow(_stmt, "fridayStart")
        val _columnIndexOfFridayEnd: Int = getColumnIndexOrThrow(_stmt, "fridayEnd")
        val _columnIndexOfSaturdayStart: Int = getColumnIndexOrThrow(_stmt, "saturdayStart")
        val _columnIndexOfSaturdayEnd: Int = getColumnIndexOrThrow(_stmt, "saturdayEnd")
        val _columnIndexOfSundayStart: Int = getColumnIndexOrThrow(_stmt, "sundayStart")
        val _columnIndexOfSundayEnd: Int = getColumnIndexOrThrow(_stmt, "sundayEnd")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ShiftConfiguration> = mutableListOf()
        while (_stmt.step()) {
          val _item: ShiftConfiguration
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMondayStart: Int?
          if (_stmt.isNull(_columnIndexOfMondayStart)) {
            _tmpMondayStart = null
          } else {
            _tmpMondayStart = _stmt.getLong(_columnIndexOfMondayStart).toInt()
          }
          val _tmpMondayEnd: Int?
          if (_stmt.isNull(_columnIndexOfMondayEnd)) {
            _tmpMondayEnd = null
          } else {
            _tmpMondayEnd = _stmt.getLong(_columnIndexOfMondayEnd).toInt()
          }
          val _tmpTuesdayStart: Int?
          if (_stmt.isNull(_columnIndexOfTuesdayStart)) {
            _tmpTuesdayStart = null
          } else {
            _tmpTuesdayStart = _stmt.getLong(_columnIndexOfTuesdayStart).toInt()
          }
          val _tmpTuesdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfTuesdayEnd)) {
            _tmpTuesdayEnd = null
          } else {
            _tmpTuesdayEnd = _stmt.getLong(_columnIndexOfTuesdayEnd).toInt()
          }
          val _tmpWednesdayStart: Int?
          if (_stmt.isNull(_columnIndexOfWednesdayStart)) {
            _tmpWednesdayStart = null
          } else {
            _tmpWednesdayStart = _stmt.getLong(_columnIndexOfWednesdayStart).toInt()
          }
          val _tmpWednesdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfWednesdayEnd)) {
            _tmpWednesdayEnd = null
          } else {
            _tmpWednesdayEnd = _stmt.getLong(_columnIndexOfWednesdayEnd).toInt()
          }
          val _tmpThursdayStart: Int?
          if (_stmt.isNull(_columnIndexOfThursdayStart)) {
            _tmpThursdayStart = null
          } else {
            _tmpThursdayStart = _stmt.getLong(_columnIndexOfThursdayStart).toInt()
          }
          val _tmpThursdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfThursdayEnd)) {
            _tmpThursdayEnd = null
          } else {
            _tmpThursdayEnd = _stmt.getLong(_columnIndexOfThursdayEnd).toInt()
          }
          val _tmpFridayStart: Int?
          if (_stmt.isNull(_columnIndexOfFridayStart)) {
            _tmpFridayStart = null
          } else {
            _tmpFridayStart = _stmt.getLong(_columnIndexOfFridayStart).toInt()
          }
          val _tmpFridayEnd: Int?
          if (_stmt.isNull(_columnIndexOfFridayEnd)) {
            _tmpFridayEnd = null
          } else {
            _tmpFridayEnd = _stmt.getLong(_columnIndexOfFridayEnd).toInt()
          }
          val _tmpSaturdayStart: Int?
          if (_stmt.isNull(_columnIndexOfSaturdayStart)) {
            _tmpSaturdayStart = null
          } else {
            _tmpSaturdayStart = _stmt.getLong(_columnIndexOfSaturdayStart).toInt()
          }
          val _tmpSaturdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfSaturdayEnd)) {
            _tmpSaturdayEnd = null
          } else {
            _tmpSaturdayEnd = _stmt.getLong(_columnIndexOfSaturdayEnd).toInt()
          }
          val _tmpSundayStart: Int?
          if (_stmt.isNull(_columnIndexOfSundayStart)) {
            _tmpSundayStart = null
          } else {
            _tmpSundayStart = _stmt.getLong(_columnIndexOfSundayStart).toInt()
          }
          val _tmpSundayEnd: Int?
          if (_stmt.isNull(_columnIndexOfSundayEnd)) {
            _tmpSundayEnd = null
          } else {
            _tmpSundayEnd = _stmt.getLong(_columnIndexOfSundayEnd).toInt()
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ShiftConfiguration(_tmpId,_tmpName,_tmpLineId,_tmpMondayStart,_tmpMondayEnd,_tmpTuesdayStart,_tmpTuesdayEnd,_tmpWednesdayStart,_tmpWednesdayEnd,_tmpThursdayStart,_tmpThursdayEnd,_tmpFridayStart,_tmpFridayEnd,_tmpSaturdayStart,_tmpSaturdayEnd,_tmpSundayStart,_tmpSundayEnd,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsynced(): Flow<List<ShiftConfiguration>> {
    val _sql: String = "SELECT * FROM shift_configurations WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("shift_configurations")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfLineId: Int = getColumnIndexOrThrow(_stmt, "lineId")
        val _columnIndexOfMondayStart: Int = getColumnIndexOrThrow(_stmt, "mondayStart")
        val _columnIndexOfMondayEnd: Int = getColumnIndexOrThrow(_stmt, "mondayEnd")
        val _columnIndexOfTuesdayStart: Int = getColumnIndexOrThrow(_stmt, "tuesdayStart")
        val _columnIndexOfTuesdayEnd: Int = getColumnIndexOrThrow(_stmt, "tuesdayEnd")
        val _columnIndexOfWednesdayStart: Int = getColumnIndexOrThrow(_stmt, "wednesdayStart")
        val _columnIndexOfWednesdayEnd: Int = getColumnIndexOrThrow(_stmt, "wednesdayEnd")
        val _columnIndexOfThursdayStart: Int = getColumnIndexOrThrow(_stmt, "thursdayStart")
        val _columnIndexOfThursdayEnd: Int = getColumnIndexOrThrow(_stmt, "thursdayEnd")
        val _columnIndexOfFridayStart: Int = getColumnIndexOrThrow(_stmt, "fridayStart")
        val _columnIndexOfFridayEnd: Int = getColumnIndexOrThrow(_stmt, "fridayEnd")
        val _columnIndexOfSaturdayStart: Int = getColumnIndexOrThrow(_stmt, "saturdayStart")
        val _columnIndexOfSaturdayEnd: Int = getColumnIndexOrThrow(_stmt, "saturdayEnd")
        val _columnIndexOfSundayStart: Int = getColumnIndexOrThrow(_stmt, "sundayStart")
        val _columnIndexOfSundayEnd: Int = getColumnIndexOrThrow(_stmt, "sundayEnd")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<ShiftConfiguration> = mutableListOf()
        while (_stmt.step()) {
          val _item: ShiftConfiguration
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpLineId: String
          _tmpLineId = _stmt.getText(_columnIndexOfLineId)
          val _tmpMondayStart: Int?
          if (_stmt.isNull(_columnIndexOfMondayStart)) {
            _tmpMondayStart = null
          } else {
            _tmpMondayStart = _stmt.getLong(_columnIndexOfMondayStart).toInt()
          }
          val _tmpMondayEnd: Int?
          if (_stmt.isNull(_columnIndexOfMondayEnd)) {
            _tmpMondayEnd = null
          } else {
            _tmpMondayEnd = _stmt.getLong(_columnIndexOfMondayEnd).toInt()
          }
          val _tmpTuesdayStart: Int?
          if (_stmt.isNull(_columnIndexOfTuesdayStart)) {
            _tmpTuesdayStart = null
          } else {
            _tmpTuesdayStart = _stmt.getLong(_columnIndexOfTuesdayStart).toInt()
          }
          val _tmpTuesdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfTuesdayEnd)) {
            _tmpTuesdayEnd = null
          } else {
            _tmpTuesdayEnd = _stmt.getLong(_columnIndexOfTuesdayEnd).toInt()
          }
          val _tmpWednesdayStart: Int?
          if (_stmt.isNull(_columnIndexOfWednesdayStart)) {
            _tmpWednesdayStart = null
          } else {
            _tmpWednesdayStart = _stmt.getLong(_columnIndexOfWednesdayStart).toInt()
          }
          val _tmpWednesdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfWednesdayEnd)) {
            _tmpWednesdayEnd = null
          } else {
            _tmpWednesdayEnd = _stmt.getLong(_columnIndexOfWednesdayEnd).toInt()
          }
          val _tmpThursdayStart: Int?
          if (_stmt.isNull(_columnIndexOfThursdayStart)) {
            _tmpThursdayStart = null
          } else {
            _tmpThursdayStart = _stmt.getLong(_columnIndexOfThursdayStart).toInt()
          }
          val _tmpThursdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfThursdayEnd)) {
            _tmpThursdayEnd = null
          } else {
            _tmpThursdayEnd = _stmt.getLong(_columnIndexOfThursdayEnd).toInt()
          }
          val _tmpFridayStart: Int?
          if (_stmt.isNull(_columnIndexOfFridayStart)) {
            _tmpFridayStart = null
          } else {
            _tmpFridayStart = _stmt.getLong(_columnIndexOfFridayStart).toInt()
          }
          val _tmpFridayEnd: Int?
          if (_stmt.isNull(_columnIndexOfFridayEnd)) {
            _tmpFridayEnd = null
          } else {
            _tmpFridayEnd = _stmt.getLong(_columnIndexOfFridayEnd).toInt()
          }
          val _tmpSaturdayStart: Int?
          if (_stmt.isNull(_columnIndexOfSaturdayStart)) {
            _tmpSaturdayStart = null
          } else {
            _tmpSaturdayStart = _stmt.getLong(_columnIndexOfSaturdayStart).toInt()
          }
          val _tmpSaturdayEnd: Int?
          if (_stmt.isNull(_columnIndexOfSaturdayEnd)) {
            _tmpSaturdayEnd = null
          } else {
            _tmpSaturdayEnd = _stmt.getLong(_columnIndexOfSaturdayEnd).toInt()
          }
          val _tmpSundayStart: Int?
          if (_stmt.isNull(_columnIndexOfSundayStart)) {
            _tmpSundayStart = null
          } else {
            _tmpSundayStart = _stmt.getLong(_columnIndexOfSundayStart).toInt()
          }
          val _tmpSundayEnd: Int?
          if (_stmt.isNull(_columnIndexOfSundayEnd)) {
            _tmpSundayEnd = null
          } else {
            _tmpSundayEnd = _stmt.getLong(_columnIndexOfSundayEnd).toInt()
          }
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = ShiftConfiguration(_tmpId,_tmpName,_tmpLineId,_tmpMondayStart,_tmpMondayEnd,_tmpTuesdayStart,_tmpTuesdayEnd,_tmpWednesdayStart,_tmpWednesdayEnd,_tmpThursdayStart,_tmpThursdayEnd,_tmpFridayStart,_tmpFridayEnd,_tmpSaturdayStart,_tmpSaturdayEnd,_tmpSundayStart,_tmpSundayEnd,_tmpLastUpdated,_tmpSyncStatus)
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
