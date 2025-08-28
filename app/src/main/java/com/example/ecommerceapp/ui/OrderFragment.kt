package com.example.ecommerceapp.ui

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryItem
import com.example.ecommerceapp.data.local.GroceryOrderItems
import com.example.ecommerceapp.data.repo.GroceryDatabase
import com.example.ecommerceapp.data.repo.GroceryDatabase.Companion.invoke
import com.example.ecommerceapp.data.repo.GroceryRepository
import com.example.ecommerceapp.ui.adapter.GroceryAdapter
import com.example.ecommerceapp.ui.adapter.OrderGroceryAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.Callback.DISMISS_EVENT_ACTION
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class OrderFragment : Fragment() {
    private lateinit var viewModel: GroceryViewModel
    private lateinit var rvList: RecyclerView
    private lateinit var groceryAdapter: OrderGroceryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_order, container, false)
        rvList = view.findViewById(R.id.rvOrders)

        return view
    }
    override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        val groceryRepository = GroceryRepository(GroceryDatabase(requireContext()))
        val factory = GroceryViewModelFactory(groceryRepository)

        viewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryAdapter = OrderGroceryAdapter(listOf())
        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = groceryAdapter

        viewModel.allOrderItems().observe(viewLifecycleOwner) {
            groceryAdapter.list = it
            groceryAdapter.notifyItemInserted(groceryAdapter.list.size)
            groceryAdapter.notifyDataSetChanged()
        }
        setupViewModel()
        setupRecylerView(view)
        setupObservers()
    }
    private fun setupViewModel() {
        val groceryRepository = GroceryRepository(GroceryDatabase(requireContext()))
        val factory = GroceryViewModelFactory(groceryRepository)
        viewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
    }
    private fun setupRecylerView(view: View) {
        rvList = view.findViewById(R.id.rvOrders)
        groceryAdapter = OrderGroceryAdapter(emptyList())

        // Set the long-click listener lambda
        groceryAdapter.onItemLongClicked = { orderItem ->
            showDeleteConfirmationDialog(orderItem, view)
        }

        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = groceryAdapter
    }
    private fun setupObservers() {
        viewModel.allOrderItems().observe(viewLifecycleOwner) { orderItems ->
            groceryAdapter.updateList(orderItems)
        }
    }
    private fun showDeleteConfirmationDialog(items: GroceryOrderItems,view: View){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Order")
            setMessage("Are you sure you want to delete this order?")
            setPositiveButton ("Delete"){dialog, which ->
                deleteOrderItem(items,view)
            }
            setNegativeButton("Cancel"){dialog, which ->
                dialog.dismiss()
            }
            setCancelable(true)
        }.create().show()
    }
    private fun deleteOrderItem(items: GroceryOrderItems,view: View){
        viewModel.removeFromOrder(items)
        Snackbar.make(view,"Order Deleted",Snackbar.LENGTH_LONG).apply {
            setAction("Undo"){
                viewModel.addToOrder(items)
            }
            show()
        }
    }
}