package com.appops.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.appops.kotlincountries.model.Country

@Dao
interface CountryDao {

    // Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO
    // suspend -> coroutine çalıştırmak için yazdığımız keyword, Pause & Resume işlemleri yaptırabiliyoruz.
    // vararg -> Multiple Country Objects
    // List<Long> -> primary key döndürüyor.

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}