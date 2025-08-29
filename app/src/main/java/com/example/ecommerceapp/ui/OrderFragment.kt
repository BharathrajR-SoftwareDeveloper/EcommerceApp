package com.example.ecommerceapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryOrderItems
import com.example.ecommerceapp.data.repo.GroceryDatabase
import com.example.ecommerceapp.data.repo.GroceryRepository
import com.example.ecommerceapp.ui.adapter.OrderGroceryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class OrderFragment : Fragment() {
    private lateinit var viewModel: GroceryViewModel
    private lateinit var rvList: RecyclerView
    private lateinit var groceryAdapter: OrderGroceryAdapter
    private lateinit var actionBar: View
    private lateinit var btnDelete: Button
    private lateinit var btnFav: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groceryRepository = GroceryRepository(GroceryDatabase(requireContext()))
        val factory = GroceryViewModelFactory(groceryRepository)
        viewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]


        rvList = view.findViewById(R.id.rvOrders)
        actionBar = view.findViewById(R.id.actionBar)
        btnDelete = view.findViewById(R.id.btnDelete)
        btnFav = view.findViewById(R.id.btnFavorite)

        groceryAdapter = OrderGroceryAdapter(emptyList())
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = groceryAdapter


        groceryAdapter.onItemLongClicked = { orderItem ->
            showBottomSheet(orderItem)
        }


        viewModel.allOrderItems().observe(viewLifecycleOwner) { orderItems ->
            groceryAdapter.updateList(orderItems)
        }
    }
    private fun showBottomSheet(items: GroceryOrderItems){
        val dialog= BottomSheetDialog(requireContext())
        val sheetView= LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_dialogue,null)
        dialog.setContentView(sheetView)
        sheetView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            viewModel.removeFromOrder(items)
            Snackbar.make(requireView(),"Item Removed",Snackbar.LENGTH_SHORT).apply {
                setAction("Undo") { viewModel.addToOrder(items) }
                show()
            }
            dialog.dismiss()
        }
        sheetView.findViewById<Button>(R.id.btnFavorite).setOnClickListener {
            Snackbar.make(requireView(),"Added to Favorites",Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.setOnDismissListener {
            groceryAdapter.clearSelection()
        }
        dialog.show()
    }
}


