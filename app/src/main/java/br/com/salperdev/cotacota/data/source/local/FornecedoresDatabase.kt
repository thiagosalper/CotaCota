package br.com.salperdev.cotacota.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Database(entities = [FornecedorEntity::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class FornecedoresDatabase : RoomDatabase() {
    abstract fun fornecedoresDao(): FornecedoresDao
}

//object Converters {
//    @TypeConverter
//    fun timestampToDate(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimeStamp(date: Date?) = date?.time
//}