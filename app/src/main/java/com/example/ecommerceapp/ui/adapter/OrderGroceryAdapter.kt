package com.example.ecommerceapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryOrderItems
import com.example.ecommerceapp.databinding.OrderItemsBinding

class OrderGroceryAdapter(internal var list:List<GroceryOrderItems>): RecyclerView.Adapter<OrderGroceryAdapter.OrderGroceryViewHolder>() {
    private lateinit var bind: OrderItemsBinding

    var onItemLongClicked:((GroceryOrderItems)->Unit)?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderGroceryAdapter.OrderGroceryViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.order_items,parent,false)
        return OrderGroceryViewHolder(view)
    }

    inner class OrderGroceryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtItemName: TextView = itemView.findViewById(R.id.tvOrderItem)
        val txtItemPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
        val txtItemQuantity: TextView = itemView.findViewById(R.id.tvOrderQuantity)
        val txtOrderName: TextView = itemView.findViewById(R.id.tvOrderName)
        val txtOrderPhone: TextView = itemView.findViewById(R.id.tvOrderPhone)
        val txtOrderAddress: TextView = itemView.findViewById(R.id.tvOrderAddress)

        init {
            itemView.setOnLongClickListener {
                val position=adapterPosition
                if (position!= RecyclerView.NO_POSITION){
                    onItemLongClicked?.invoke(list[position])
                }
                true
            }
        }

        fun bind(item: GroceryOrderItems){
            txtItemName.text=item.orderItemName.toString().trim()
            txtItemQuantity.text=item.orderItemQuantity.toString()+" kg"
            txtItemPrice.text="₹"+item.orderItemPrice.toString()
            val itemTotal:Int=(item.orderItemPrice.toInt())* (item.orderItemQuantity.toInt())
            txtOrderName.text=item.name.toString()
            txtOrderPhone.text=item.phone.toString()
            txtOrderAddress.text=item.address.toString()
        }
    }
    override fun onBindViewHolder(
        holder: OrderGroceryAdapter.OrderGroceryViewHolder,
        position: Int
    ) {
        val currentPosition=list[position]
        holder.bind(currentPosition)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(newList: List<GroceryOrderItems>) {
        list = newList
        notifyDataSetChanged()
    }
}