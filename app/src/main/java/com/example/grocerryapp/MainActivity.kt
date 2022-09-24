package com.example.grocerryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var itemRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)
        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list, this)
        itemRV.layoutManager = LinearLayoutManager(this)
        itemRV.adapter = groceryRVAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase.invoke(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this, factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this, {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener {
            openDialog()
        }
    }
    fun openDialog(){
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.grocerry_add_dialog)
        val cancelBtn=dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn=dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt=dialog.findViewById<EditText>(R.id.idEdtItemName)
        val itemPriceEdt=dialog.findViewById<EditText>(R.id.idEdtItemPrice)
        val itemQuantityEdt=dialog.findViewById<EditText>(R.id.idEdtItemQuantity)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName: String = itemEdt.text.toString()
            val itemPrice: String = itemPriceEdt.text.toString()
            val itemQuantity: String = itemQuantityEdt.text.toString()
            val qty: Int = itemQuantity.toInt()
            val pr: Int = itemPrice.toInt()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val items = GroceryItems(itemName, qty, pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, "Item Inserted..", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please Enter All the Data..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog.show()

    }

    fun onItemClick(groceryItems: GroceryItems){
        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "item deleted",Toast.LENGTH_SHORT).show()
    }



}
