package com.example.ecommerceapp.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryItem

class GroceryItemDialog(context: Context, private var dialogListener: DialogListener) : AppCompatDialog(context) {
    private lateinit var etItemName: EditText
    private lateinit var etItemQuantity: EditText
    private lateinit var etItemPrice: EditText
    private lateinit var tvSave: TextView
    private lateinit var tvCancel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.grocerydialog)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        etItemName=findViewById(R.id.etItemName)!!
        etItemQuantity=findViewById(R.id.etItemQuantity)!!
        etItemPrice=findViewById(R.id.etItemPrice)!!
        tvSave=findViewById(R.id.tvSave)!!
        tvCancel=findViewById(R.id.tvCancel)!!

        tvSave.setOnClickListener{
            val name=etItemName.text.toString().trim()
            val quantity=etItemQuantity.text.toString()
            val price=etItemPrice.text.toString()
            try{if(name.isEmpty() || quantity.isEmpty() || price.isEmpty()){
                Toast.makeText(context,"Please enter all the details",Toast.LENGTH_SHORT).show()
            }else{
                val item= GroceryItem(name,quantity,price)
                dialogListener.onAddButtononClicked(item)
                dismiss()
            }}catch (e:Exception){
                Toast.makeText(context,"Please enter all the details",Toast.LENGTH_SHORT).show()
                dismiss()
            }

        }
        tvCancel.setOnClickListener {
            cancel()

        }
    }
}
