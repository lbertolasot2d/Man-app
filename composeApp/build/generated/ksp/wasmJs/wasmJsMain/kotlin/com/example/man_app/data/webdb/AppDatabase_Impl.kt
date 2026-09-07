package com.example.man_app.`data`.webdb

import androidx.room3.InvalidationTracker
import androidx.room3.RoomOpenDelegate
import androidx.room3.migration.AutoMigrationSpec
import androidx.room3.migration.Migration
import androidx.room3.util.TableInfo
import androidx.room3.util.TableInfo.Companion.read
import androidx.room3.util.dropFtsSyncTriggers
import androidx.room3.util.performClear
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL", "MemberExtensionConflict"])
internal class AppDatabase_Impl : AppDatabase() {
  private val _personnelDao: Lazy<PersonnelDao> = lazy {
    PersonnelDao_Impl(this)
  }

  private val _productDao: Lazy<ProductDao> = lazy {
    ProductDao_Impl(this)
  }

  private val _mattonieraDao: Lazy<MattonieraDao> = lazy {
    MattonieraDao_Impl(this)
  }

  private val _impilatriceDao: Lazy<ImpilatriceDao> = lazy {
    ImpilatriceDao_Impl(this)
  }

  private val _stopDao: Lazy<StopDao> = lazy {
    StopDao_Impl(this)
  }

  private val _shiftDao: Lazy<ShiftDao> = lazy {
    ShiftDao_Impl(this)
  }

  private val _scaricatriceDao: Lazy<ScaricatriceDao> = lazy {
    ScaricatriceDao_Impl(this)
  }

  private val _faultDao: Lazy<FaultDao> = lazy {
    FaultDao_Impl(this)
  }

  private val _checklistDao: Lazy<ChecklistDao> = lazy {
    ChecklistDao_Impl(this)
  }

  private val _productionDao: Lazy<ProductionDao> = lazy {
    ProductionDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(23, "133e5c2ff876d811a094de768871c8c4", "b9518b7039113ff0f79e14317f8cac85") {
      public override suspend fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `personnel` (`id` TEXT NOT NULL, `firstName` TEXT NOT NULL, `lastName` TEXT NOT NULL, `role` TEXT NOT NULL, `line` TEXT NOT NULL, `department` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `targetWeight` REAL NOT NULL, `targetLength` REAL NOT NULL, `targetWidth` REAL NOT NULL, `targetHeight` REAL NOT NULL, `targetDiagonal` REAL NOT NULL, `theoreticalCagesPerHour` REAL NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `mattoniera_reports` (`id` TEXT NOT NULL, `date` INTEGER NOT NULL, `shift` TEXT NOT NULL, `productId` TEXT NOT NULL, `operatorId` TEXT NOT NULL, `dieId` TEXT NOT NULL, `sigla` TEXT, `clayRecipeCode` TEXT, `lineId` TEXT NOT NULL, `machineId` TEXT NOT NULL, `dimaCheckStart` INTEGER NOT NULL, `dimaCheckMid` INTEGER NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `scrapMinutes` INTEGER NOT NULL, `cagesProduced` INTEGER NOT NULL, `doserSpeedHz` REAL NOT NULL, `absorptionAmpere` REAL NOT NULL, `productionNotes` TEXT, `qualityNotes` TEXT, `isClosed` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `exit_quality_checks` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isOk` INTEGER NOT NULL, `temperature` INTEGER NOT NULL, `pressure` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `measurement_checks` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `height` REAL NOT NULL, `width` REAL NOT NULL, `thickness` REAL NOT NULL, `diagonal1` REAL NOT NULL, `diagonal2` REAL NOT NULL, `weight` REAL NOT NULL, `isDiagonalOk` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `roller_cleanings` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `clogging_removals` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `reason` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `clogging_reasons` (`reasonText` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`reasonText`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `mattoniera_production_data` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `doserGiri` INTEGER NOT NULL, `isDoserOk` INTEGER NOT NULL, `absorptionAmpere` INTEGER NOT NULL, `isAbsorptionOk` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `scrap_records` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `plcValue` REAL NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `cage_events` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `change` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `production_stops` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `workstation` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `reason` TEXT NOT NULL, `operators` TEXT NOT NULL, `activities` TEXT, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `stop_events` (`id` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `stopType` TEXT NOT NULL, `activities` TEXT NOT NULL, `machineId` TEXT NOT NULL, `lineId` TEXT NOT NULL, `isFinished` INTEGER NOT NULL, `operatorId` TEXT, `productId` TEXT, `sigla` TEXT, `shiftName` TEXT, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `stop_suggestions` (`text` TEXT NOT NULL, `category` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`text`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `fault_reports` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `machineId` TEXT NOT NULL, `description` TEXT NOT NULL, `reporterId` TEXT NOT NULL, `severity` TEXT NOT NULL, `isResolved` INTEGER NOT NULL, `resolutionNotes` TEXT, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `checklist_templates` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `checklist_items` (`id` TEXT NOT NULL, `templateId` TEXT NOT NULL, `text` TEXT NOT NULL, `order` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `checklist_responses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `checklistId` TEXT NOT NULL, `itemId` TEXT NOT NULL, `operatorId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isChecked` INTEGER NOT NULL, `note` TEXT, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `impilatrice_reports` (`id` TEXT NOT NULL, `date` INTEGER NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `operatorId` TEXT NOT NULL, `productId` TEXT NOT NULL, `shift` TEXT NOT NULL, `sigla` TEXT, `lineId` TEXT NOT NULL, `machineId` TEXT NOT NULL, `isClosed` INTEGER NOT NULL, `cagesProduced` INTEGER NOT NULL, `scrapMinutes` INTEGER NOT NULL, `cageMaterialDistribution` TEXT NOT NULL, `cageNotes` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `impilatrice_cage_events` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `change` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `impilatrice_scrap_records` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `plcValue` REAL NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `impilatrice_kiln_cars` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `sigla` TEXT, `isCleaned` INTEGER NOT NULL, `loadPercentage` INTEGER NOT NULL, `refractoriesOk` INTEGER NOT NULL, `height` REAL NOT NULL, `width` REAL NOT NULL, `thickness` REAL NOT NULL, `weight` REAL NOT NULL, `isDiagonalOk` INTEGER NOT NULL, `dryingRating` TEXT NOT NULL, `chipsRating` TEXT NOT NULL, `cracksRating` TEXT NOT NULL, `hairlinesRating` TEXT NOT NULL, `breaksRating` TEXT NOT NULL, `qualityNotes` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `shift_configurations` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `lineId` TEXT NOT NULL, `mondayStart` INTEGER, `mondayEnd` INTEGER, `tuesdayStart` INTEGER, `tuesdayEnd` INTEGER, `wednesdayStart` INTEGER, `wednesdayEnd` INTEGER, `thursdayStart` INTEGER, `thursdayEnd` INTEGER, `fridayStart` INTEGER, `fridayEnd` INTEGER, `saturdayStart` INTEGER, `saturdayEnd` INTEGER, `sundayStart` INTEGER, `sundayEnd` INTEGER, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `scaricatrice_reports` (`id` TEXT NOT NULL, `date` INTEGER NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `operatorId` TEXT NOT NULL, `productId` TEXT NOT NULL, `shift` TEXT NOT NULL, `sigla` TEXT, `lineId` TEXT NOT NULL, `machineId` TEXT NOT NULL, `isClosed` INTEGER NOT NULL, `pacchi1aScelta` INTEGER NOT NULL, `pacchi2aScelta` INTEGER NOT NULL, `bobineNylon` INTEGER NOT NULL, `bobineReggia` INTEGER NOT NULL, `pacchiSquadratura` TEXT NOT NULL, `pacchiImballo` TEXT NOT NULL, `pacchiCentraturaPallet` INTEGER NOT NULL, `pacchiEtichettaOk` INTEGER NOT NULL, `scrapMinutes` INTEGER NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `scaricatrice_scrap_records` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `plcValue` REAL NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `scaricatrice_kiln_cars` (`id` TEXT NOT NULL, `reportId` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `sigla` TEXT, `isCleaned` INTEGER NOT NULL, `refractoriesOk` INTEGER NOT NULL, `scelta` TEXT, `soundAltoOk` INTEGER NOT NULL, `soundCentroOk` INTEGER NOT NULL, `soundBassoOk` INTEGER NOT NULL, `height` REAL NOT NULL, `width` REAL NOT NULL, `thickness` REAL NOT NULL, `weight` REAL NOT NULL, `firingRating` TEXT NOT NULL, `chipsOk` INTEGER NOT NULL, `chipsNotes` TEXT NOT NULL, `efflorescenceRating` TEXT NOT NULL, `efflorescenceNotes` TEXT NOT NULL, `stainsRating` TEXT NOT NULL, `stainsColor` TEXT NOT NULL, `stainsNotes` TEXT NOT NULL, `cracksRating` TEXT NOT NULL, `cracksNotes` TEXT NOT NULL, `hairlinesRating` TEXT NOT NULL, `hairlinesNotes` TEXT NOT NULL, `breaksRating` TEXT NOT NULL, `breaksNotes` TEXT NOT NULL, `qualityNotes` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `production_reports` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `shift` TEXT NOT NULL, `operatorId` TEXT NOT NULL, `machineId` TEXT NOT NULL, `unitsProduced` INTEGER NOT NULL, `unitsDefective` INTEGER NOT NULL, `notes` TEXT, `lastUpdated` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '133e5c2ff876d811a094de768871c8c4')")
      }

      public override suspend fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `personnel`")
        connection.execSQL("DROP TABLE IF EXISTS `products`")
        connection.execSQL("DROP TABLE IF EXISTS `mattoniera_reports`")
        connection.execSQL("DROP TABLE IF EXISTS `exit_quality_checks`")
        connection.execSQL("DROP TABLE IF EXISTS `measurement_checks`")
        connection.execSQL("DROP TABLE IF EXISTS `roller_cleanings`")
        connection.execSQL("DROP TABLE IF EXISTS `clogging_removals`")
        connection.execSQL("DROP TABLE IF EXISTS `clogging_reasons`")
        connection.execSQL("DROP TABLE IF EXISTS `mattoniera_production_data`")
        connection.execSQL("DROP TABLE IF EXISTS `scrap_records`")
        connection.execSQL("DROP TABLE IF EXISTS `cage_events`")
        connection.execSQL("DROP TABLE IF EXISTS `production_stops`")
        connection.execSQL("DROP TABLE IF EXISTS `stop_events`")
        connection.execSQL("DROP TABLE IF EXISTS `stop_suggestions`")
        connection.execSQL("DROP TABLE IF EXISTS `fault_reports`")
        connection.execSQL("DROP TABLE IF EXISTS `checklist_templates`")
        connection.execSQL("DROP TABLE IF EXISTS `checklist_items`")
        connection.execSQL("DROP TABLE IF EXISTS `checklist_responses`")
        connection.execSQL("DROP TABLE IF EXISTS `impilatrice_reports`")
        connection.execSQL("DROP TABLE IF EXISTS `impilatrice_cage_events`")
        connection.execSQL("DROP TABLE IF EXISTS `impilatrice_scrap_records`")
        connection.execSQL("DROP TABLE IF EXISTS `impilatrice_kiln_cars`")
        connection.execSQL("DROP TABLE IF EXISTS `shift_configurations`")
        connection.execSQL("DROP TABLE IF EXISTS `scaricatrice_reports`")
        connection.execSQL("DROP TABLE IF EXISTS `scaricatrice_scrap_records`")
        connection.execSQL("DROP TABLE IF EXISTS `scaricatrice_kiln_cars`")
        connection.execSQL("DROP TABLE IF EXISTS `production_reports`")
      }

      public override suspend fun onCreate(connection: SQLiteConnection) {
      }

      public override suspend fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override suspend fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override suspend fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override suspend fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsPersonnel: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPersonnel.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("firstName", TableInfo.Column("firstName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("lastName", TableInfo.Column("lastName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("role", TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("line", TableInfo.Column("line", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("department", TableInfo.Column("department", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPersonnel.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPersonnel: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPersonnel: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPersonnel: TableInfo = TableInfo("personnel", _columnsPersonnel, _foreignKeysPersonnel, _indicesPersonnel)
        val _existingPersonnel: TableInfo = read(connection, "personnel")
        if (!_infoPersonnel.equals(_existingPersonnel)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |personnel(com.example.man_app.data.model.Personnel).
              | Expected:
              |""".trimMargin() + _infoPersonnel + """
              |
              | Found:
              |""".trimMargin() + _existingPersonnel)
        }
        val _columnsProducts: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsProducts.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("targetWeight", TableInfo.Column("targetWeight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("targetLength", TableInfo.Column("targetLength", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("targetWidth", TableInfo.Column("targetWidth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("targetHeight", TableInfo.Column("targetHeight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("targetDiagonal", TableInfo.Column("targetDiagonal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("theoreticalCagesPerHour", TableInfo.Column("theoreticalCagesPerHour", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProducts.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysProducts: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesProducts: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoProducts: TableInfo = TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts)
        val _existingProducts: TableInfo = read(connection, "products")
        if (!_infoProducts.equals(_existingProducts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |products(com.example.man_app.data.model.Product).
              | Expected:
              |""".trimMargin() + _infoProducts + """
              |
              | Found:
              |""".trimMargin() + _existingProducts)
        }
        val _columnsMattonieraReports: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMattonieraReports.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("shift", TableInfo.Column("shift", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("productId", TableInfo.Column("productId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("operatorId", TableInfo.Column("operatorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("dieId", TableInfo.Column("dieId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("sigla", TableInfo.Column("sigla", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("clayRecipeCode", TableInfo.Column("clayRecipeCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("lineId", TableInfo.Column("lineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("machineId", TableInfo.Column("machineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("dimaCheckStart", TableInfo.Column("dimaCheckStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("dimaCheckMid", TableInfo.Column("dimaCheckMid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("endTime", TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("scrapMinutes", TableInfo.Column("scrapMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("cagesProduced", TableInfo.Column("cagesProduced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("doserSpeedHz", TableInfo.Column("doserSpeedHz", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("absorptionAmpere", TableInfo.Column("absorptionAmpere", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("productionNotes", TableInfo.Column("productionNotes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("qualityNotes", TableInfo.Column("qualityNotes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("isClosed", TableInfo.Column("isClosed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraReports.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMattonieraReports: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMattonieraReports: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMattonieraReports: TableInfo = TableInfo("mattoniera_reports", _columnsMattonieraReports, _foreignKeysMattonieraReports, _indicesMattonieraReports)
        val _existingMattonieraReports: TableInfo = read(connection, "mattoniera_reports")
        if (!_infoMattonieraReports.equals(_existingMattonieraReports)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |mattoniera_reports(com.example.man_app.data.model.MattonieraReport).
              | Expected:
              |""".trimMargin() + _infoMattonieraReports + """
              |
              | Found:
              |""".trimMargin() + _existingMattonieraReports)
        }
        val _columnsExitQualityChecks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExitQualityChecks.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("isOk", TableInfo.Column("isOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("temperature", TableInfo.Column("temperature", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("pressure", TableInfo.Column("pressure", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExitQualityChecks.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExitQualityChecks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesExitQualityChecks: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoExitQualityChecks: TableInfo = TableInfo("exit_quality_checks", _columnsExitQualityChecks, _foreignKeysExitQualityChecks, _indicesExitQualityChecks)
        val _existingExitQualityChecks: TableInfo = read(connection, "exit_quality_checks")
        if (!_infoExitQualityChecks.equals(_existingExitQualityChecks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |exit_quality_checks(com.example.man_app.data.model.ExitQualityCheck).
              | Expected:
              |""".trimMargin() + _infoExitQualityChecks + """
              |
              | Found:
              |""".trimMargin() + _existingExitQualityChecks)
        }
        val _columnsMeasurementChecks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMeasurementChecks.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("height", TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("width", TableInfo.Column("width", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("thickness", TableInfo.Column("thickness", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("diagonal1", TableInfo.Column("diagonal1", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("diagonal2", TableInfo.Column("diagonal2", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("weight", TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("isDiagonalOk", TableInfo.Column("isDiagonalOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMeasurementChecks.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMeasurementChecks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMeasurementChecks: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMeasurementChecks: TableInfo = TableInfo("measurement_checks", _columnsMeasurementChecks, _foreignKeysMeasurementChecks, _indicesMeasurementChecks)
        val _existingMeasurementChecks: TableInfo = read(connection, "measurement_checks")
        if (!_infoMeasurementChecks.equals(_existingMeasurementChecks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |measurement_checks(com.example.man_app.data.model.MeasurementCheck).
              | Expected:
              |""".trimMargin() + _infoMeasurementChecks + """
              |
              | Found:
              |""".trimMargin() + _existingMeasurementChecks)
        }
        val _columnsRollerCleanings: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRollerCleanings.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRollerCleanings.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRollerCleanings.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRollerCleanings.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRollerCleanings.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRollerCleanings: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesRollerCleanings: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoRollerCleanings: TableInfo = TableInfo("roller_cleanings", _columnsRollerCleanings, _foreignKeysRollerCleanings, _indicesRollerCleanings)
        val _existingRollerCleanings: TableInfo = read(connection, "roller_cleanings")
        if (!_infoRollerCleanings.equals(_existingRollerCleanings)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |roller_cleanings(com.example.man_app.data.model.RollerCleaning).
              | Expected:
              |""".trimMargin() + _infoRollerCleanings + """
              |
              | Found:
              |""".trimMargin() + _existingRollerCleanings)
        }
        val _columnsCloggingRemovals: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCloggingRemovals.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingRemovals.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingRemovals.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingRemovals.put("reason", TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingRemovals.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingRemovals.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCloggingRemovals: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCloggingRemovals: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCloggingRemovals: TableInfo = TableInfo("clogging_removals", _columnsCloggingRemovals, _foreignKeysCloggingRemovals, _indicesCloggingRemovals)
        val _existingCloggingRemovals: TableInfo = read(connection, "clogging_removals")
        if (!_infoCloggingRemovals.equals(_existingCloggingRemovals)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |clogging_removals(com.example.man_app.data.model.CloggingRemoval).
              | Expected:
              |""".trimMargin() + _infoCloggingRemovals + """
              |
              | Found:
              |""".trimMargin() + _existingCloggingRemovals)
        }
        val _columnsCloggingReasons: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCloggingReasons.put("reasonText", TableInfo.Column("reasonText", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingReasons.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCloggingReasons.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCloggingReasons: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCloggingReasons: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCloggingReasons: TableInfo = TableInfo("clogging_reasons", _columnsCloggingReasons, _foreignKeysCloggingReasons, _indicesCloggingReasons)
        val _existingCloggingReasons: TableInfo = read(connection, "clogging_reasons")
        if (!_infoCloggingReasons.equals(_existingCloggingReasons)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |clogging_reasons(com.example.man_app.data.model.CloggingReason).
              | Expected:
              |""".trimMargin() + _infoCloggingReasons + """
              |
              | Found:
              |""".trimMargin() + _existingCloggingReasons)
        }
        val _columnsMattonieraProductionData: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMattonieraProductionData.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("doserGiri", TableInfo.Column("doserGiri", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("isDoserOk", TableInfo.Column("isDoserOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("absorptionAmpere", TableInfo.Column("absorptionAmpere", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("isAbsorptionOk", TableInfo.Column("isAbsorptionOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMattonieraProductionData.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMattonieraProductionData: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMattonieraProductionData: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMattonieraProductionData: TableInfo = TableInfo("mattoniera_production_data", _columnsMattonieraProductionData, _foreignKeysMattonieraProductionData, _indicesMattonieraProductionData)
        val _existingMattonieraProductionData: TableInfo = read(connection, "mattoniera_production_data")
        if (!_infoMattonieraProductionData.equals(_existingMattonieraProductionData)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |mattoniera_production_data(com.example.man_app.data.model.MattonieraProductionData).
              | Expected:
              |""".trimMargin() + _infoMattonieraProductionData + """
              |
              | Found:
              |""".trimMargin() + _existingMattonieraProductionData)
        }
        val _columnsScrapRecords: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsScrapRecords.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScrapRecords.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScrapRecords.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScrapRecords.put("plcValue", TableInfo.Column("plcValue", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScrapRecords.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScrapRecords.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysScrapRecords: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesScrapRecords: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoScrapRecords: TableInfo = TableInfo("scrap_records", _columnsScrapRecords, _foreignKeysScrapRecords, _indicesScrapRecords)
        val _existingScrapRecords: TableInfo = read(connection, "scrap_records")
        if (!_infoScrapRecords.equals(_existingScrapRecords)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |scrap_records(com.example.man_app.data.model.ScrapRecord).
              | Expected:
              |""".trimMargin() + _infoScrapRecords + """
              |
              | Found:
              |""".trimMargin() + _existingScrapRecords)
        }
        val _columnsCageEvents: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCageEvents.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCageEvents.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCageEvents.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCageEvents.put("change", TableInfo.Column("change", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCageEvents.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCageEvents.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCageEvents: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCageEvents: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCageEvents: TableInfo = TableInfo("cage_events", _columnsCageEvents, _foreignKeysCageEvents, _indicesCageEvents)
        val _existingCageEvents: TableInfo = read(connection, "cage_events")
        if (!_infoCageEvents.equals(_existingCageEvents)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |cage_events(com.example.man_app.data.model.CageEvent).
              | Expected:
              |""".trimMargin() + _infoCageEvents + """
              |
              | Found:
              |""".trimMargin() + _existingCageEvents)
        }
        val _columnsProductionStops: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsProductionStops.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("workstation", TableInfo.Column("workstation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("endTime", TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("reason", TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("operators", TableInfo.Column("operators", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("activities", TableInfo.Column("activities", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionStops.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysProductionStops: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesProductionStops: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoProductionStops: TableInfo = TableInfo("production_stops", _columnsProductionStops, _foreignKeysProductionStops, _indicesProductionStops)
        val _existingProductionStops: TableInfo = read(connection, "production_stops")
        if (!_infoProductionStops.equals(_existingProductionStops)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |production_stops(com.example.man_app.data.model.ProductionStop).
              | Expected:
              |""".trimMargin() + _infoProductionStops + """
              |
              | Found:
              |""".trimMargin() + _existingProductionStops)
        }
        val _columnsStopEvents: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStopEvents.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("endTime", TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("stopType", TableInfo.Column("stopType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("activities", TableInfo.Column("activities", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("machineId", TableInfo.Column("machineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("lineId", TableInfo.Column("lineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("isFinished", TableInfo.Column("isFinished", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("operatorId", TableInfo.Column("operatorId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("productId", TableInfo.Column("productId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("sigla", TableInfo.Column("sigla", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("shiftName", TableInfo.Column("shiftName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopEvents.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStopEvents: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesStopEvents: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoStopEvents: TableInfo = TableInfo("stop_events", _columnsStopEvents, _foreignKeysStopEvents, _indicesStopEvents)
        val _existingStopEvents: TableInfo = read(connection, "stop_events")
        if (!_infoStopEvents.equals(_existingStopEvents)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |stop_events(com.example.man_app.data.model.StopEvent).
              | Expected:
              |""".trimMargin() + _infoStopEvents + """
              |
              | Found:
              |""".trimMargin() + _existingStopEvents)
        }
        val _columnsStopSuggestions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStopSuggestions.put("text", TableInfo.Column("text", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopSuggestions.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopSuggestions.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStopSuggestions.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStopSuggestions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesStopSuggestions: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoStopSuggestions: TableInfo = TableInfo("stop_suggestions", _columnsStopSuggestions, _foreignKeysStopSuggestions, _indicesStopSuggestions)
        val _existingStopSuggestions: TableInfo = read(connection, "stop_suggestions")
        if (!_infoStopSuggestions.equals(_existingStopSuggestions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |stop_suggestions(com.example.man_app.data.model.StopSuggestion).
              | Expected:
              |""".trimMargin() + _infoStopSuggestions + """
              |
              | Found:
              |""".trimMargin() + _existingStopSuggestions)
        }
        val _columnsFaultReports: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsFaultReports.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("machineId", TableInfo.Column("machineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("reporterId", TableInfo.Column("reporterId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("severity", TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("isResolved", TableInfo.Column("isResolved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("resolutionNotes", TableInfo.Column("resolutionNotes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsFaultReports.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysFaultReports: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesFaultReports: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoFaultReports: TableInfo = TableInfo("fault_reports", _columnsFaultReports, _foreignKeysFaultReports, _indicesFaultReports)
        val _existingFaultReports: TableInfo = read(connection, "fault_reports")
        if (!_infoFaultReports.equals(_existingFaultReports)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |fault_reports(com.example.man_app.data.model.FaultReport).
              | Expected:
              |""".trimMargin() + _infoFaultReports + """
              |
              | Found:
              |""".trimMargin() + _existingFaultReports)
        }
        val _columnsChecklistTemplates: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsChecklistTemplates.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistTemplates.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistTemplates.put("description", TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysChecklistTemplates: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesChecklistTemplates: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoChecklistTemplates: TableInfo = TableInfo("checklist_templates", _columnsChecklistTemplates, _foreignKeysChecklistTemplates, _indicesChecklistTemplates)
        val _existingChecklistTemplates: TableInfo = read(connection, "checklist_templates")
        if (!_infoChecklistTemplates.equals(_existingChecklistTemplates)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |checklist_templates(com.example.man_app.data.model.ChecklistTemplate).
              | Expected:
              |""".trimMargin() + _infoChecklistTemplates + """
              |
              | Found:
              |""".trimMargin() + _existingChecklistTemplates)
        }
        val _columnsChecklistItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsChecklistItems.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistItems.put("templateId", TableInfo.Column("templateId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistItems.put("text", TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistItems.put("order", TableInfo.Column("order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysChecklistItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesChecklistItems: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoChecklistItems: TableInfo = TableInfo("checklist_items", _columnsChecklistItems, _foreignKeysChecklistItems, _indicesChecklistItems)
        val _existingChecklistItems: TableInfo = read(connection, "checklist_items")
        if (!_infoChecklistItems.equals(_existingChecklistItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |checklist_items(com.example.man_app.data.model.ChecklistItem).
              | Expected:
              |""".trimMargin() + _infoChecklistItems + """
              |
              | Found:
              |""".trimMargin() + _existingChecklistItems)
        }
        val _columnsChecklistResponses: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsChecklistResponses.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("checklistId", TableInfo.Column("checklistId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("itemId", TableInfo.Column("itemId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("operatorId", TableInfo.Column("operatorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("isChecked", TableInfo.Column("isChecked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("note", TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChecklistResponses.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysChecklistResponses: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesChecklistResponses: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoChecklistResponses: TableInfo = TableInfo("checklist_responses", _columnsChecklistResponses, _foreignKeysChecklistResponses, _indicesChecklistResponses)
        val _existingChecklistResponses: TableInfo = read(connection, "checklist_responses")
        if (!_infoChecklistResponses.equals(_existingChecklistResponses)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |checklist_responses(com.example.man_app.data.model.ChecklistResponse).
              | Expected:
              |""".trimMargin() + _infoChecklistResponses + """
              |
              | Found:
              |""".trimMargin() + _existingChecklistResponses)
        }
        val _columnsImpilatriceReports: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsImpilatriceReports.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("endTime", TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("operatorId", TableInfo.Column("operatorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("productId", TableInfo.Column("productId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("shift", TableInfo.Column("shift", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("sigla", TableInfo.Column("sigla", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("lineId", TableInfo.Column("lineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("machineId", TableInfo.Column("machineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("isClosed", TableInfo.Column("isClosed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("cagesProduced", TableInfo.Column("cagesProduced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("scrapMinutes", TableInfo.Column("scrapMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("cageMaterialDistribution", TableInfo.Column("cageMaterialDistribution", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("cageNotes", TableInfo.Column("cageNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceReports.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysImpilatriceReports: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesImpilatriceReports: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoImpilatriceReports: TableInfo = TableInfo("impilatrice_reports", _columnsImpilatriceReports, _foreignKeysImpilatriceReports, _indicesImpilatriceReports)
        val _existingImpilatriceReports: TableInfo = read(connection, "impilatrice_reports")
        if (!_infoImpilatriceReports.equals(_existingImpilatriceReports)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |impilatrice_reports(com.example.man_app.data.model.ImpilatriceReport).
              | Expected:
              |""".trimMargin() + _infoImpilatriceReports + """
              |
              | Found:
              |""".trimMargin() + _existingImpilatriceReports)
        }
        val _columnsImpilatriceCageEvents: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsImpilatriceCageEvents.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceCageEvents.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceCageEvents.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceCageEvents.put("change", TableInfo.Column("change", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceCageEvents.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceCageEvents.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysImpilatriceCageEvents: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesImpilatriceCageEvents: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoImpilatriceCageEvents: TableInfo = TableInfo("impilatrice_cage_events", _columnsImpilatriceCageEvents, _foreignKeysImpilatriceCageEvents, _indicesImpilatriceCageEvents)
        val _existingImpilatriceCageEvents: TableInfo = read(connection, "impilatrice_cage_events")
        if (!_infoImpilatriceCageEvents.equals(_existingImpilatriceCageEvents)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |impilatrice_cage_events(com.example.man_app.data.model.ImpilatriceCageEvent).
              | Expected:
              |""".trimMargin() + _infoImpilatriceCageEvents + """
              |
              | Found:
              |""".trimMargin() + _existingImpilatriceCageEvents)
        }
        val _columnsImpilatriceScrapRecords: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsImpilatriceScrapRecords.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceScrapRecords.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceScrapRecords.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceScrapRecords.put("plcValue", TableInfo.Column("plcValue", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceScrapRecords.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceScrapRecords.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysImpilatriceScrapRecords: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesImpilatriceScrapRecords: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoImpilatriceScrapRecords: TableInfo = TableInfo("impilatrice_scrap_records", _columnsImpilatriceScrapRecords, _foreignKeysImpilatriceScrapRecords, _indicesImpilatriceScrapRecords)
        val _existingImpilatriceScrapRecords: TableInfo = read(connection, "impilatrice_scrap_records")
        if (!_infoImpilatriceScrapRecords.equals(_existingImpilatriceScrapRecords)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |impilatrice_scrap_records(com.example.man_app.data.model.ImpilatriceScrapRecord).
              | Expected:
              |""".trimMargin() + _infoImpilatriceScrapRecords + """
              |
              | Found:
              |""".trimMargin() + _existingImpilatriceScrapRecords)
        }
        val _columnsImpilatriceKilnCars: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsImpilatriceKilnCars.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("endTime", TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("sigla", TableInfo.Column("sigla", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("isCleaned", TableInfo.Column("isCleaned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("loadPercentage", TableInfo.Column("loadPercentage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("refractoriesOk", TableInfo.Column("refractoriesOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("height", TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("width", TableInfo.Column("width", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("thickness", TableInfo.Column("thickness", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("weight", TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("isDiagonalOk", TableInfo.Column("isDiagonalOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("dryingRating", TableInfo.Column("dryingRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("chipsRating", TableInfo.Column("chipsRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("cracksRating", TableInfo.Column("cracksRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("hairlinesRating", TableInfo.Column("hairlinesRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("breaksRating", TableInfo.Column("breaksRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("qualityNotes", TableInfo.Column("qualityNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsImpilatriceKilnCars.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysImpilatriceKilnCars: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesImpilatriceKilnCars: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoImpilatriceKilnCars: TableInfo = TableInfo("impilatrice_kiln_cars", _columnsImpilatriceKilnCars, _foreignKeysImpilatriceKilnCars, _indicesImpilatriceKilnCars)
        val _existingImpilatriceKilnCars: TableInfo = read(connection, "impilatrice_kiln_cars")
        if (!_infoImpilatriceKilnCars.equals(_existingImpilatriceKilnCars)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |impilatrice_kiln_cars(com.example.man_app.data.model.ImpilatriceKilnCar).
              | Expected:
              |""".trimMargin() + _infoImpilatriceKilnCars + """
              |
              | Found:
              |""".trimMargin() + _existingImpilatriceKilnCars)
        }
        val _columnsShiftConfigurations: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsShiftConfigurations.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("lineId", TableInfo.Column("lineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("mondayStart", TableInfo.Column("mondayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("mondayEnd", TableInfo.Column("mondayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("tuesdayStart", TableInfo.Column("tuesdayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("tuesdayEnd", TableInfo.Column("tuesdayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("wednesdayStart", TableInfo.Column("wednesdayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("wednesdayEnd", TableInfo.Column("wednesdayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("thursdayStart", TableInfo.Column("thursdayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("thursdayEnd", TableInfo.Column("thursdayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("fridayStart", TableInfo.Column("fridayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("fridayEnd", TableInfo.Column("fridayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("saturdayStart", TableInfo.Column("saturdayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("saturdayEnd", TableInfo.Column("saturdayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("sundayStart", TableInfo.Column("sundayStart", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("sundayEnd", TableInfo.Column("sundayEnd", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShiftConfigurations.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysShiftConfigurations: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesShiftConfigurations: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoShiftConfigurations: TableInfo = TableInfo("shift_configurations", _columnsShiftConfigurations, _foreignKeysShiftConfigurations, _indicesShiftConfigurations)
        val _existingShiftConfigurations: TableInfo = read(connection, "shift_configurations")
        if (!_infoShiftConfigurations.equals(_existingShiftConfigurations)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |shift_configurations(com.example.man_app.data.model.ShiftConfiguration).
              | Expected:
              |""".trimMargin() + _infoShiftConfigurations + """
              |
              | Found:
              |""".trimMargin() + _existingShiftConfigurations)
        }
        val _columnsScaricatriceReports: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsScaricatriceReports.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("endTime", TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("operatorId", TableInfo.Column("operatorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("productId", TableInfo.Column("productId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("shift", TableInfo.Column("shift", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("sigla", TableInfo.Column("sigla", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("lineId", TableInfo.Column("lineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("machineId", TableInfo.Column("machineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("isClosed", TableInfo.Column("isClosed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("pacchi1aScelta", TableInfo.Column("pacchi1aScelta", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("pacchi2aScelta", TableInfo.Column("pacchi2aScelta", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("bobineNylon", TableInfo.Column("bobineNylon", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("bobineReggia", TableInfo.Column("bobineReggia", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("pacchiSquadratura", TableInfo.Column("pacchiSquadratura", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("pacchiImballo", TableInfo.Column("pacchiImballo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("pacchiCentraturaPallet", TableInfo.Column("pacchiCentraturaPallet", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("pacchiEtichettaOk", TableInfo.Column("pacchiEtichettaOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("scrapMinutes", TableInfo.Column("scrapMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceReports.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysScaricatriceReports: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesScaricatriceReports: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoScaricatriceReports: TableInfo = TableInfo("scaricatrice_reports", _columnsScaricatriceReports, _foreignKeysScaricatriceReports, _indicesScaricatriceReports)
        val _existingScaricatriceReports: TableInfo = read(connection, "scaricatrice_reports")
        if (!_infoScaricatriceReports.equals(_existingScaricatriceReports)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |scaricatrice_reports(com.example.man_app.data.model.ScaricatriceReport).
              | Expected:
              |""".trimMargin() + _infoScaricatriceReports + """
              |
              | Found:
              |""".trimMargin() + _existingScaricatriceReports)
        }
        val _columnsScaricatriceScrapRecords: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsScaricatriceScrapRecords.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceScrapRecords.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceScrapRecords.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceScrapRecords.put("plcValue", TableInfo.Column("plcValue", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceScrapRecords.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceScrapRecords.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysScaricatriceScrapRecords: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesScaricatriceScrapRecords: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoScaricatriceScrapRecords: TableInfo = TableInfo("scaricatrice_scrap_records", _columnsScaricatriceScrapRecords, _foreignKeysScaricatriceScrapRecords, _indicesScaricatriceScrapRecords)
        val _existingScaricatriceScrapRecords: TableInfo = read(connection, "scaricatrice_scrap_records")
        if (!_infoScaricatriceScrapRecords.equals(_existingScaricatriceScrapRecords)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |scaricatrice_scrap_records(com.example.man_app.data.model.ScaricatriceScrapRecord).
              | Expected:
              |""".trimMargin() + _infoScaricatriceScrapRecords + """
              |
              | Found:
              |""".trimMargin() + _existingScaricatriceScrapRecords)
        }
        val _columnsScaricatriceKilnCars: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsScaricatriceKilnCars.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("reportId", TableInfo.Column("reportId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("endTime", TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("sigla", TableInfo.Column("sigla", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("isCleaned", TableInfo.Column("isCleaned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("refractoriesOk", TableInfo.Column("refractoriesOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("scelta", TableInfo.Column("scelta", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("soundAltoOk", TableInfo.Column("soundAltoOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("soundCentroOk", TableInfo.Column("soundCentroOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("soundBassoOk", TableInfo.Column("soundBassoOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("height", TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("width", TableInfo.Column("width", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("thickness", TableInfo.Column("thickness", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("weight", TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("firingRating", TableInfo.Column("firingRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("chipsOk", TableInfo.Column("chipsOk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("chipsNotes", TableInfo.Column("chipsNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("efflorescenceRating", TableInfo.Column("efflorescenceRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("efflorescenceNotes", TableInfo.Column("efflorescenceNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("stainsRating", TableInfo.Column("stainsRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("stainsColor", TableInfo.Column("stainsColor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("stainsNotes", TableInfo.Column("stainsNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("cracksRating", TableInfo.Column("cracksRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("cracksNotes", TableInfo.Column("cracksNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("hairlinesRating", TableInfo.Column("hairlinesRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("hairlinesNotes", TableInfo.Column("hairlinesNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("breaksRating", TableInfo.Column("breaksRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("breaksNotes", TableInfo.Column("breaksNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("qualityNotes", TableInfo.Column("qualityNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsScaricatriceKilnCars.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysScaricatriceKilnCars: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesScaricatriceKilnCars: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoScaricatriceKilnCars: TableInfo = TableInfo("scaricatrice_kiln_cars", _columnsScaricatriceKilnCars, _foreignKeysScaricatriceKilnCars, _indicesScaricatriceKilnCars)
        val _existingScaricatriceKilnCars: TableInfo = read(connection, "scaricatrice_kiln_cars")
        if (!_infoScaricatriceKilnCars.equals(_existingScaricatriceKilnCars)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |scaricatrice_kiln_cars(com.example.man_app.data.model.ScaricatriceKilnCar).
              | Expected:
              |""".trimMargin() + _infoScaricatriceKilnCars + """
              |
              | Found:
              |""".trimMargin() + _existingScaricatriceKilnCars)
        }
        var _result: RoomOpenDelegate.ValidationResult
        _result = onValidateSchema2(connection)
        if (!_result.isValid) {
          return _result
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }

      private suspend fun onValidateSchema2(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsProductionReports: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsProductionReports.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("shift", TableInfo.Column("shift", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("operatorId", TableInfo.Column("operatorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("machineId", TableInfo.Column("machineId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("unitsProduced", TableInfo.Column("unitsProduced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("unitsDefective", TableInfo.Column("unitsDefective", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("notes", TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("lastUpdated", TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsProductionReports.put("syncStatus", TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysProductionReports: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesProductionReports: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoProductionReports: TableInfo = TableInfo("production_reports", _columnsProductionReports, _foreignKeysProductionReports, _indicesProductionReports)
        val _existingProductionReports: TableInfo = read(connection, "production_reports")
        if (!_infoProductionReports.equals(_existingProductionReports)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |production_reports(com.example.man_app.data.model.ProductionReport).
              | Expected:
              |""".trimMargin() + _infoProductionReports + """
              |
              | Found:
              |""".trimMargin() + _existingProductionReports)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "personnel", "products", "mattoniera_reports", "exit_quality_checks", "measurement_checks", "roller_cleanings", "clogging_removals", "clogging_reasons", "mattoniera_production_data", "scrap_records", "cage_events", "production_stops", "stop_events", "stop_suggestions", "fault_reports", "checklist_templates", "checklist_items", "checklist_responses", "impilatrice_reports", "impilatrice_cage_events", "impilatrice_scrap_records", "impilatrice_kiln_cars", "shift_configurations", "scaricatrice_reports", "scaricatrice_scrap_records", "scaricatrice_kiln_cars", "production_reports")
  }

  public override suspend fun clearAllTables() {
    performClear(this, false, "personnel", "products", "mattoniera_reports", "exit_quality_checks", "measurement_checks", "roller_cleanings", "clogging_removals", "clogging_reasons", "mattoniera_production_data", "scrap_records", "cage_events", "production_stops", "stop_events", "stop_suggestions", "fault_reports", "checklist_templates", "checklist_items", "checklist_responses", "impilatrice_reports", "impilatrice_cage_events", "impilatrice_scrap_records", "impilatrice_kiln_cars", "shift_configurations", "scaricatrice_reports", "scaricatrice_scrap_records", "scaricatrice_kiln_cars", "production_reports")
  }

  protected override fun getRequiredColumnTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _columnTypeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _columnTypeConvertersMap.put(PersonnelDao::class, PersonnelDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(ProductDao::class, ProductDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(MattonieraDao::class, MattonieraDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(ImpilatriceDao::class, ImpilatriceDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(StopDao::class, StopDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(ShiftDao::class, ShiftDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(ScaricatriceDao::class, ScaricatriceDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(FaultDao::class, FaultDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(ChecklistDao::class, ChecklistDao_Impl.getRequiredColumnConverters())
    _columnTypeConvertersMap.put(ProductionDao::class, ProductionDao_Impl.getRequiredColumnConverters())
    return _columnTypeConvertersMap
  }

  protected override fun getRequiredDaoReturnTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _daoReturnTypeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _daoReturnTypeConvertersMap.put(PersonnelDao::class, PersonnelDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(ProductDao::class, ProductDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(MattonieraDao::class, MattonieraDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(ImpilatriceDao::class, ImpilatriceDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(StopDao::class, StopDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(ShiftDao::class, ShiftDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(ScaricatriceDao::class, ScaricatriceDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(FaultDao::class, FaultDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(ChecklistDao::class, ChecklistDao_Impl.getRequiredDaoReturnTypeConverters())
    _daoReturnTypeConvertersMap.put(ProductionDao::class, ProductionDao_Impl.getRequiredDaoReturnTypeConverters())
    return _daoReturnTypeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun personnelDao(): PersonnelDao = _personnelDao.value

  public override fun productDao(): ProductDao = _productDao.value

  public override fun mattonieraDao(): MattonieraDao = _mattonieraDao.value

  public override fun impilatriceDao(): ImpilatriceDao = _impilatriceDao.value

  public override fun stopDao(): StopDao = _stopDao.value

  public override fun shiftDao(): ShiftDao = _shiftDao.value

  public override fun scaricatriceDao(): ScaricatriceDao = _scaricatriceDao.value

  public override fun faultDao(): FaultDao = _faultDao.value

  public override fun checklistDao(): ChecklistDao = _checklistDao.value

  public override fun productionDao(): ProductionDao = _productionDao.value
}
