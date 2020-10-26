package com.appops.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appops.kotlincountries.model.Country


@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao

    //Singleton

    companion object{
        @Volatile private var instance : CountryDatabase? = null  //Değişkeni Volatile tanımlamak o değişkeni farklı threadlerde görünür hale getirir

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            //Synchronized demek asenksron çalıştırmanın tersidir. Yani senkron çalışır.
            //O da aslında şu demek; Aynı anda tek bir thread'den işlem yapılacak
            // O işlem bittikten sonra diğer thread'de çalıştırılabilecek

            instance ?: makeDatabase(context).also {
                // .also {} Şu anlama geliyor: Bunu yap, bunu yaptıktan sonra üstüne bir de bunu yap(parantez bloğu içinde yazılanları)
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, //Activity'nin context'ini almak yerine application'un contextini alıyoruz. Çünkü activity destroy vs olursa sorun çıkmasın
            CountryDatabase::class.java, //Bu sınıftan oluşturmasını söyledik
            "countrydatabase"
        ).build()
    }
}