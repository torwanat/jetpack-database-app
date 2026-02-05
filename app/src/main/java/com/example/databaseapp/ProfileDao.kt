package com.example.databaseapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Update
    fun update(profile: Profile)

    @Delete
    fun delete(profile: Profile)

    @Upsert
    fun upsert(profile: Profile)

    @Query("SELECT * FROM profiles")
    fun getProfilesSync(): List<Profile>
}