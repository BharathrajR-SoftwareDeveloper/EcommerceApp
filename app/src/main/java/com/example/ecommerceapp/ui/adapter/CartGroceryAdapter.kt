package com.example.ecommerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryCartItems
import com.example.ecommerceapp.ui.GroceryViewModel

class CartGroceryAdapter(internal var list:List<GroceryCartItems>,
                         private val viewModel: GroceryViewModel,
                         private val addToOrder: (GroceryCartItems) -> Unit
    ): RecyclerView.Adapter<CartGroceryAdapter.CartGroceryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartGroceryViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.cart_item_grocery,parent,false)
        return CartGroceryViewHolder(view)
    }
    inner class CartGroceryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val txtItemPrice: TextView = itemView.findViewById(R.id.txtItemPrice)
        val txtItemQuantity: TextView = itemView.findViewById(R.id.txtItemQuantity)
        val txtItemTotalCost: TextView = itemView.findViewById(R.id.txtItemTotalCost)
        val txtTotalCostTitle: TextView = itemView.findViewById(R.id.txtTotalCostTitle)
        val ibAdd: ImageButton = itemView.findViewById(R.id.ibAdd)
        fun bind(item: GroceryCartItems){
            txtItemName.text=item.cardItemName.toString().trim()
            txtItemQuantity.text=item.cardItemQuantity.toString()+" kg"
            txtItemPrice.text="₹"+item.cardItemPrice.toString()
            val itemTotal:Int=(item.cardItemPrice.toInt())* (item.cardItemQuantity.toInt())
            txtItemTotalCost.text="₹"+itemTotal.toString()
            txtTotalCostTitle.visibility= View.VISIBLE
            txtTotalCostTitle.text="₹"+itemTotal.toString()
            ibAdd.setOnClickListener {
                addToOrder(item)

            }
        }
    }

    override fun onBindViewHolder(
        holder: CartGroceryViewHolder,
        position: Int
    ) {
        val currentPosition=list[position]
        holder.bind(currentPosition)

    }

    override fun getItemCount(): Int {
       return list.size
    }

}