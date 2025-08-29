package com.example.ecommerceapp.ui.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryOrderItems
import com.example.ecommerceapp.databinding.OrderItemsBinding

class OrderGroceryAdapter(
    private var list: List<GroceryOrderItems>
) : RecyclerView.Adapter<OrderGroceryAdapter.OrderGroceryViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    var onItemLongClicked: ((GroceryOrderItems) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderGroceryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_items, parent, false)
        return OrderGroceryViewHolder(view)
    }

    inner class OrderGroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ViewGroup = itemView.findViewById(R.id.orderView)
        val txtItemName: TextView = itemView.findViewById(R.id.tvOrderItem)
        val txtItemPrice: TextView = itemView.findViewById(R.id.tvOrderPrice)
        val txtTotalPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)
        val txtItemQuantity: TextView = itemView.findViewById(R.id.tvOrderQuantity)
        val txtOrderName: TextView = itemView.findViewById(R.id.tvOrderName)
        val txtOrderPhone: TextView = itemView.findViewById(R.id.tvOrderPhone)
        val txtOrderAddress: TextView = itemView.findViewById(R.id.tvOrderAddress)

        fun bind(item: GroceryOrderItems) {
            txtItemName.text = item.orderItemName.trim()
            txtItemQuantity.text = "${item.orderItemQuantity} kg"
            txtItemPrice.text = "₹${item.orderItemPrice}"
            val itemTotal = item.orderItemPrice.toInt() * item.orderItemQuantity.toInt()
            txtTotalPrice.text = "₹$itemTotal"
            txtOrderName.text = item.name
            txtOrderPhone.text = item.phone
            txtOrderAddress.text = item.address
        }
    }

    override fun onBindViewHolder(holder: OrderGroceryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)

        holder.cardView.scaleX = 1f
        holder.cardView.scaleY = 1f
        holder.cardView.alpha = 1f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            holder.cardView.setRenderEffect(null)
        } else {
            holder.cardView.background?.clearColorFilter()
        }

        if (selectedPosition == position) {
            holder.cardView.animate()
                .scaleX(1.1f).scaleY(1.1f).alpha(1f)
                .setDuration(200).start()
        } else if (selectedPosition != RecyclerView.NO_POSITION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blurEffect = RenderEffect.createBlurEffect(5f,5f, Shader.TileMode.REPEAT)
                holder.cardView.setRenderEffect(blurEffect)
            } else {
                holder.cardView.alpha = 0.1f
                val matrix = ColorMatrix().apply { setSaturation(0f) }
                holder.cardView.background?.colorFilter = ColorMatrixColorFilter(matrix)
            }
        }


        holder.itemView.setOnLongClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onItemLongClicked?.invoke(item)
            true
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<GroceryOrderItems>) {
        list = newList
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}
