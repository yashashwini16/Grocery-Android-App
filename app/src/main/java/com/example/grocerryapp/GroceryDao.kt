package com.example.grocerryapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryDao {

     @Insert(onConflict= OnConflictStrategy.REPLACE)
      fun insert(item: GroceryItems)

     @Delete
     fun delete(item: GroceryItems)

     @Query("SELECT * FROM grocerry_items")
     fun getAllGroceryItems(): LiveData<List<GroceryItems>>

}