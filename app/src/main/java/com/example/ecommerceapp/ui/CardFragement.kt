package com.example.ecommerceapp.ui

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.ui.adapter.CartGroceryAdapter
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.local.GroceryCartItems
import com.example.ecommerceapp.data.local.GroceryOrderItems
import com.example.ecommerceapp.data.repo.GroceryDatabase
import com.example.ecommerceapp.data.repo.GroceryRepository
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class CardFragement : Fragment() {
    private lateinit var viewModel: GroceryViewModel
    private lateinit var rvList: RecyclerView
    private lateinit var groceryAdapter: CartGroceryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_card_fragement,container,false)
        rvList=view.findViewById<RecyclerView>(R.id.rvList)

        return view
    }

    override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        val groceryRepository = GroceryRepository(GroceryDatabase(requireContext()))
        val factory = GroceryViewModelFactory(groceryRepository)

        viewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryAdapter = CartGroceryAdapter(listOf(), viewModel, { item ->
            PlaceOrderDialogue(item, object : PlaceOrderDialogueListener {
                override fun onAddButtononClicked(
                    item: GroceryCartItems,
                    name: String,
                    phone: String,
                    address: String
                ) {
                    val orderItems = GroceryOrderItems(
                        orderItemName = item.cardItemName,
                        orderItemQuantity = item.cardItemQuantity,
                        orderItemPrice = item.cardItemPrice,
                        name = name,
                        phone = phone,
                        address = address
                    )
                    viewModel.addToOrder(orderItems)
                    viewModel.removeFromCart(item)
                }
            }, requireContext()).show()

        })

        rvList.layoutManager = LinearLayoutManager(requireContext())
        rvList.adapter = groceryAdapter

        viewModel.allGroceryCartItems().observe(viewLifecycleOwner) {
            groceryAdapter.list = it
            groceryAdapter.notifyItemInserted(groceryAdapter.list.size)
            groceryAdapter.notifyDataSetChanged()
        }
        swipeToDelete()

    }
    private fun swipeToDelete() {
        val itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN , ItemTouchHelper.LEFT  or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter=recyclerView.adapter as CartGroceryAdapter
                val from=viewHolder.absoluteAdapterPosition
                val to=target.absoluteAdapterPosition
                adapter.notifyItemMoved(from,to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val item = groceryAdapter.list[position]
                when(direction){
                    ItemTouchHelper.RIGHT->{
                        groceryAdapter.list=groceryAdapter.list.toMutableList().apply { removeAt(position) }
                        groceryAdapter.notifyItemRemoved(position)
                        Snackbar.make(rvList,"Item Achived",Snackbar.LENGTH_LONG).apply{
                            setAction("Undo"){
                                groceryAdapter.list=groceryAdapter.list.toMutableList().apply { add(position,item) }
                                groceryAdapter.notifyItemInserted(position)
                            }
                            setBackgroundTint(resources.getColor(R.color.green))
                            addCallback(object : Snackbar.Callback(){
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    super.onDismissed(transientBottomBar, event)
                                    if (event != DISMISS_EVENT_ACTION) {
                                        viewModel.insert(item)
                                    }
                                }
                            })
                                .setDuration(500)
                                .show()
                        }
                    }
                    ItemTouchHelper.LEFT->{
                        groceryAdapter.list=groceryAdapter.list.toMutableList().apply { removeAt(position) }
                        groceryAdapter.notifyItemRemoved(position)
                        Snackbar.make(rvList,"Item Deleted",Snackbar.LENGTH_LONG).apply{
                            setAction("Undo"){
                                groceryAdapter.list=groceryAdapter.list.toMutableList().apply { add(position,item) }
                                groceryAdapter.notifyItemInserted(position)
                            }
                            setBackgroundTint(resources.getColor(R.color.red))
                            addCallback(object : Snackbar.Callback(){
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    super.onDismissed(transientBottomBar, event)
                                    if (event != DISMISS_EVENT_ACTION) {
                                        viewModel.removeFromCart(item)
                                    }
                                }
                            })
                            show()
                        }
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            )

            {
                RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(resources.getColor(R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate()
                RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addSwipeRightBackgroundColor(resources.getColor(R.color.green))
                    .addSwipeRightActionIcon(R.drawable.order)
                    .create()
                    .decorate()


                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvList)
    }
}