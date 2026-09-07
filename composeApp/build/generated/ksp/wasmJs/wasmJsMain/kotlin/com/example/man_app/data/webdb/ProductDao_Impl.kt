package com.example.man_app.`data`.webdb

import androidx.room3.EntityInsertAdapter
import androidx.room3.RoomDatabase
import androidx.room3.coroutines.createFlow
import androidx.room3.util.getColumnIndexOrThrow
import androidx.room3.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.man_app.`data`.model.Product
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
internal class ProductDao_Impl(
  __db: RoomDatabase,
) : ProductDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfProduct: EntityInsertAdapter<Product>
  init {
    this.__db = __db
    this.__insertAdapterOfProduct = object : EntityInsertAdapter<Product>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `products` (`id`,`name`,`targetWeight`,`targetLength`,`targetWidth`,`targetHeight`,`targetDiagonal`,`theoreticalCagesPerHour`,`lastUpdated`,`syncStatus`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Product) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindDouble(3, entity.targetWeight)
        statement.bindDouble(4, entity.targetLength)
        statement.bindDouble(5, entity.targetWidth)
        statement.bindDouble(6, entity.targetHeight)
        statement.bindDouble(7, entity.targetDiagonal)
        statement.bindDouble(8, entity.theoreticalCagesPerHour)
        statement.bindLong(9, entity.lastUpdated)
        statement.bindText(10, entity.syncStatus)
      }
    }
  }

  public override suspend fun insert(product: Product): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfProduct.insert(_connection, product)
  }

  public override fun getAll(): Flow<List<Product>> {
    val _sql: String = "SELECT * FROM products"
    return createFlow(__db, false, arrayOf("products")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfTargetLength: Int = getColumnIndexOrThrow(_stmt, "targetLength")
        val _columnIndexOfTargetWidth: Int = getColumnIndexOrThrow(_stmt, "targetWidth")
        val _columnIndexOfTargetHeight: Int = getColumnIndexOrThrow(_stmt, "targetHeight")
        val _columnIndexOfTargetDiagonal: Int = getColumnIndexOrThrow(_stmt, "targetDiagonal")
        val _columnIndexOfTheoreticalCagesPerHour: Int = getColumnIndexOrThrow(_stmt, "theoreticalCagesPerHour")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<Product> = mutableListOf()
        while (_stmt.step()) {
          val _item: Product
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetWeight: Double
          _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight)
          val _tmpTargetLength: Double
          _tmpTargetLength = _stmt.getDouble(_columnIndexOfTargetLength)
          val _tmpTargetWidth: Double
          _tmpTargetWidth = _stmt.getDouble(_columnIndexOfTargetWidth)
          val _tmpTargetHeight: Double
          _tmpTargetHeight = _stmt.getDouble(_columnIndexOfTargetHeight)
          val _tmpTargetDiagonal: Double
          _tmpTargetDiagonal = _stmt.getDouble(_columnIndexOfTargetDiagonal)
          val _tmpTheoreticalCagesPerHour: Double
          _tmpTheoreticalCagesPerHour = _stmt.getDouble(_columnIndexOfTheoreticalCagesPerHour)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = Product(_tmpId,_tmpName,_tmpTargetWeight,_tmpTargetLength,_tmpTargetWidth,_tmpTargetHeight,_tmpTargetDiagonal,_tmpTheoreticalCagesPerHour,_tmpLastUpdated,_tmpSyncStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: String): Product? {
    val _sql: String = "SELECT * FROM products WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfTargetLength: Int = getColumnIndexOrThrow(_stmt, "targetLength")
        val _columnIndexOfTargetWidth: Int = getColumnIndexOrThrow(_stmt, "targetWidth")
        val _columnIndexOfTargetHeight: Int = getColumnIndexOrThrow(_stmt, "targetHeight")
        val _columnIndexOfTargetDiagonal: Int = getColumnIndexOrThrow(_stmt, "targetDiagonal")
        val _columnIndexOfTheoreticalCagesPerHour: Int = getColumnIndexOrThrow(_stmt, "theoreticalCagesPerHour")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: Product?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetWeight: Double
          _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight)
          val _tmpTargetLength: Double
          _tmpTargetLength = _stmt.getDouble(_columnIndexOfTargetLength)
          val _tmpTargetWidth: Double
          _tmpTargetWidth = _stmt.getDouble(_columnIndexOfTargetWidth)
          val _tmpTargetHeight: Double
          _tmpTargetHeight = _stmt.getDouble(_columnIndexOfTargetHeight)
          val _tmpTargetDiagonal: Double
          _tmpTargetDiagonal = _stmt.getDouble(_columnIndexOfTargetDiagonal)
          val _tmpTheoreticalCagesPerHour: Double
          _tmpTheoreticalCagesPerHour = _stmt.getDouble(_columnIndexOfTheoreticalCagesPerHour)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _result = Product(_tmpId,_tmpName,_tmpTargetWeight,_tmpTargetLength,_tmpTargetWidth,_tmpTargetHeight,_tmpTargetDiagonal,_tmpTheoreticalCagesPerHour,_tmpLastUpdated,_tmpSyncStatus)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnsynced(): Flow<List<Product>> {
    val _sql: String = "SELECT * FROM products WHERE syncStatus != 'SYNCED'"
    return createFlow(__db, false, arrayOf("products")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfTargetLength: Int = getColumnIndexOrThrow(_stmt, "targetLength")
        val _columnIndexOfTargetWidth: Int = getColumnIndexOrThrow(_stmt, "targetWidth")
        val _columnIndexOfTargetHeight: Int = getColumnIndexOrThrow(_stmt, "targetHeight")
        val _columnIndexOfTargetDiagonal: Int = getColumnIndexOrThrow(_stmt, "targetDiagonal")
        val _columnIndexOfTheoreticalCagesPerHour: Int = getColumnIndexOrThrow(_stmt, "theoreticalCagesPerHour")
        val _columnIndexOfLastUpdated: Int = getColumnIndexOrThrow(_stmt, "lastUpdated")
        val _columnIndexOfSyncStatus: Int = getColumnIndexOrThrow(_stmt, "syncStatus")
        val _result: MutableList<Product> = mutableListOf()
        while (_stmt.step()) {
          val _item: Product
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpTargetWeight: Double
          _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight)
          val _tmpTargetLength: Double
          _tmpTargetLength = _stmt.getDouble(_columnIndexOfTargetLength)
          val _tmpTargetWidth: Double
          _tmpTargetWidth = _stmt.getDouble(_columnIndexOfTargetWidth)
          val _tmpTargetHeight: Double
          _tmpTargetHeight = _stmt.getDouble(_columnIndexOfTargetHeight)
          val _tmpTargetDiagonal: Double
          _tmpTargetDiagonal = _stmt.getDouble(_columnIndexOfTargetDiagonal)
          val _tmpTheoreticalCagesPerHour: Double
          _tmpTheoreticalCagesPerHour = _stmt.getDouble(_columnIndexOfTheoreticalCagesPerHour)
          val _tmpLastUpdated: Long
          _tmpLastUpdated = _stmt.getLong(_columnIndexOfLastUpdated)
          val _tmpSyncStatus: String
          _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus)
          _item = Product(_tmpId,_tmpName,_tmpTargetWeight,_tmpTargetLength,_tmpTargetWidth,_tmpTargetHeight,_tmpTargetDiagonal,_tmpTheoreticalCagesPerHour,_tmpLastUpdated,_tmpSyncStatus)
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
