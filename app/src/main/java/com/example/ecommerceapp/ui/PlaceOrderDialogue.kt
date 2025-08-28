package com.example.ecommerceapp.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.ecommerceapp.data.local.GroceryCartItems
import com.example.ecommerceapp.databinding.PlaceOrderDialogueBinding

class PlaceOrderDialogue(  private val item: GroceryCartItems,
                           private val dialogListener: PlaceOrderDialogueListener,
                           context: Context): AppCompatDialog(context) {
    private lateinit var binding: PlaceOrderDialogueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding= PlaceOrderDialogueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancel.setOnClickListener {
            cancel()
        }

        binding.btnPlaceOrder.setOnClickListener {
            val name=binding.etName.text.toString().trim()
            val phone=binding.etPhone.text.toString()
            val address=binding.etAddress.text.toString()
           try {
               if(phone.length!=10){
                   Toast.makeText(context,"Please enter a valid phone number",Toast.LENGTH_SHORT).show()
               }else if (name.isEmpty() || phone.isEmpty() || address.isEmpty()){
                   Toast.makeText(context,"Please enter all the details",Toast.LENGTH_SHORT).show()
               }else{
                   dialogListener.onAddButtononClicked(item ,name,phone,address)
                   Toast.makeText(context,"Order Placed",Toast.LENGTH_SHORT).show()
                   dismiss()
               }
           }catch (e:Exception){
               Toast.makeText(context,"Please enter all the details",Toast.LENGTH_SHORT).show()
               dismiss()
           }
        }



    }
}