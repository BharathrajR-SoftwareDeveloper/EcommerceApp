package com.example.ecommerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryItem
import com.example.ecommerceapp.ui.GroceryViewModel

class GroceryAdapter(internal var list:List<GroceryItem>, private val viewModel: GroceryViewModel):
    RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroceryViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_grocery,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val currentPosition=list[position]
        holder.bind(currentPosition)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class GroceryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val txtItemPrice: TextView = itemView.findViewById(R.id.txtItemPrice)
        val txtItemQuantity: TextView = itemView.findViewById(R.id.txtItemQuantity)
        val txtItemTotalCost: TextView = itemView.findViewById(R.id.txtItemTotalCost)
        val txtTotalCostTitle: TextView = itemView.findViewById(R.id.txtTotalCostTitle)
        fun bind(item: GroceryItem){
            txtItemName.text=item.itemName.toString().trim()
            txtItemQuantity.text=item.itemQuantity.toString()+" kg"
            txtItemPrice.text="₹"+item.itemPrice.toString()
            val itemTotal:Int=(item.itemPrice.toInt())* (item.itemQuantity.toInt())
            txtItemTotalCost.text="₹"+itemTotal.toString()
            txtTotalCostTitle.visibility= View.VISIBLE
            txtTotalCostTitle.text="₹"+itemTotal.toString()

        }
    }
    fun onItemMove(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }
    fun updateList(newList: List<GroceryItem>) {
        list = newList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        val updatedList = list.toMutableList()
        updatedList.removeAt(position)
        list = updatedList
        notifyItemRemoved(position)
    }

    fun restoreItem(item: GroceryItem, position: Int) {
        val updatedList = list.toMutableList()
        updatedList.add(position, item)
        list = updatedList
        notifyItemInserted(position)
    }
}