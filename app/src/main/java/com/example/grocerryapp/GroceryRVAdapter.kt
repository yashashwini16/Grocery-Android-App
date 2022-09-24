package com.example.grocerryapp

import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryRVAdapter(var list:List<GroceryItems>, val groceryItemClickInterface: MainActivity)
    : RecyclerView.Adapter<GroceryRVAdapter.GroceryViewHolder>() {

        inner class GroceryViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            val nameTV = itemView.findViewById<TextView>(R.id.idTVItemName)
            val QuantityTV=itemView.findViewById<TextView>(R.id.TVQuantity)
            val RateTV=itemView.findViewById<TextView>(R.id.idTVRate)
            val AmountTV=itemView.findViewById<TextView>(R.id.idTVTotalAmt)
            val deleteTV=itemView.findViewById<ImageView>(R.id.idIVDelete)
        }


    interface GroceryItemClickInterface{
       fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.grovery_rv_item,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTV.text=list.get(position).itemName
        holder.QuantityTV.text=list.get(position).itemQuantity.toString()
        holder.RateTV.text="Rs. "+list.get(position).itemPrice.toString()
        val itemTotal:Int=list.get(position).itemPrice*list.get(position).itemQuantity
        holder.AmountTV.text="Rs. "+itemTotal.toString()
        holder.deleteTV.setOnClickListener {
            groceryItemClickInterface.onItemClick(list.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}