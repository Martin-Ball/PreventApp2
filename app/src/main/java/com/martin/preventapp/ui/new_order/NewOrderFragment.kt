package com.martin.preventapp.ui.new_order

import android.app.AlertDialog
import com.martin.preventapp.ui.new_order.recyclerView.CardViewOrder
import androidx.recyclerview.widget.RecyclerView
import com.martin.preventapp.ui.new_order.recyclerView.CardViewOrderAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.martin.preventapp.firebase.Products
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import com.martin.preventapp.R
import com.martin.preventapp.firebase.Clients
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.martin.preventapp.databinding.FragmentNewOrderBinding
import com.martin.preventapp.firebase.Order
import java.util.ArrayList
import java.util.HashMap

class NewOrderFragment : Fragment() {
    private lateinit var newOrderViewModel: NewOrderViewModel
    private var binding : FragmentNewOrderBinding? = null
    private lateinit var selectedClient: String
    private lateinit var arrayCardViewProducts: ArrayList<CardViewOrder>
    private lateinit var arrayProducts: ArrayList<ArrayList<String>>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var cardViewProductsAdapter: CardViewOrderAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var comment: String

    //Orders
    private val ProductsOrders = HashMap<String, HashMap<String, Any>>()

    //Clients
    private var ClientList = ArrayList<String>()

    //Products
    private var ProductList = ArrayList<String>()
    var Units = ArrayList<String>()

    //
    private var CompanySelected = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        newOrderViewModel = ViewModelProvider(this).get(NewOrderViewModel::class.java)
        binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val bundle = this.arguments
        if (bundle != null) {
            CompanySelected = bundle["CompanySelected"].toString()
        }

        val units = Products()
        Units = units.getUnits(root, CompanySelected)

        //spinner searchable
        val spinnerClient = root.findViewById<SearchableSpinner>(R.id.spinner_searchable_new_client)
        val clientNewOrder = root.findViewById<TextView>(R.id.client_new_order)

        //new client
        val clients = Clients()
        ClientList = clients.clientlist(root, CompanySelected)
        val adapterNewClientSelector = ArrayAdapter(
            root.context,
            android.R.layout.simple_list_item_1,
            ClientList
        )
        spinnerClient.adapter = adapterNewClientSelector
        spinnerClient.setTitle("Seleccione un cliente")
        spinnerClient.setPositiveButton("CANCELAR")
        spinnerClient.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> {
                    }
                    1 -> {
                        val newClient = AddInfo()
                        newClient.newClient(root, CompanySelected, ClientList, spinnerClient)
                    }
                    else -> {
                        val sNumber = adapterView.getItemAtPosition(i).toString()
                        clientNewOrder.text = "Nuevo pedido para el cliente: $sNumber"
                        selectedClient = sNumber
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }


        //spinner searchable
        val spinnerNewProduct =
            root.findViewById<SearchableSpinner>(R.id.spinner_new_product_searchable)

        //products
        arrayCardViewProducts = ArrayList()
        arrayProducts = ArrayList()
        val products = Products()
        ProductList = products.getProductlist(root, CompanySelected)
        val adapterNewProductSelector = ArrayAdapter(
            root.context,
            android.R.layout.simple_list_item_1,
            ProductList
        )
        spinnerNewProduct.adapter = adapterNewProductSelector
        spinnerNewProduct.setTitle("Seleccione un producto")
        spinnerNewProduct.setPositiveButton("CANCELAR")
        spinnerNewProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> {
                    }
                    1 -> {
                        val addNewProduct = AddInfo()
                        addNewProduct.newProduct(root, CompanySelected, ProductList, spinnerNewProduct)
                    }
                    else -> {
                        val productSelected = adapterView.getItemAtPosition(i).toString()
                        arrayCardViewProducts.add(CardViewOrder(productSelected, "0", "UNIDAD", "0"))
                        val productAndAmount = ArrayList<String>()

                        //add product and amount into ArrayList
                        productAndAmount.add(0, productSelected)
                        productAndAmount.add(1, "0")

                        //add product and amount into array
                        arrayProducts.add(productAndAmount)

                        //build Recycler View with CardViews
                        buildRecyclerView(root)
                    }
                }
                spinnerNewProduct.setSelection(0)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        //button finish
        val finishOrder = root.findViewById<Button>(R.id.client_file)
        finishOrder.setOnClickListener {
            if (selectedClient.isNullOrEmpty()) {
                Toast.makeText(root.context, "Seleccione un cliente", Toast.LENGTH_LONG).show()
            } else if (arrayProducts!!.isEmpty()) {
                Toast.makeText(root.context, "Seleccione productos", Toast.LENGTH_LONG).show()
            } else {
                alertDialogComments(root)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun removeItem(position: Int) {
        arrayCardViewProducts.removeAt(position)
        cardViewProductsAdapter.notifyItemRemoved(position)
    }

    fun changeItem(position: Int, text: String?) {
        arrayCardViewProducts[position].setTextProduct(text)
        cardViewProductsAdapter.notifyItemChanged(position)
    }

    fun addAmountItem(position: Int) {
        val amountAdd = arrayCardViewProducts[position].amount.toInt()
        arrayCardViewProducts[position].setTextAmount(Integer.toString(amountAdd + 1))
        cardViewProductsAdapter.notifyItemChanged(position)
        val productAndAmount = ArrayList<String>()
        productAndAmount.add(0, arrayCardViewProducts[position].product)
        productAndAmount.add(1, Integer.toString(amountAdd + 1))
        productAndAmount.add(2, arrayCardViewProducts[position].unit)
        productAndAmount.add(3, arrayCardViewProducts[position].positionItem)
        arrayProducts[position] = productAndAmount
    }

    fun editTextAmountItem(position: Int, amount: String) {
        arrayCardViewProducts[position].setTextAmount(amount)
        val productAndAmount = ArrayList<String>()
        productAndAmount.add(0, arrayCardViewProducts[position].product)
        productAndAmount.add(1, amount)
        productAndAmount.add(2, arrayCardViewProducts[position].unit)
        productAndAmount.add(3, arrayCardViewProducts[position].positionItem)
        arrayProducts[position] = productAndAmount
    }

    fun selectUnitToOrder(
        position: Int,
        unit: String,
        positionItem: Int,
        sizeSpinner: Int,
        spinnerUnit: Spinner?
    ) {
        if (positionItem == sizeSpinner - 1) {
            val newUnit = AddInfo()
            newUnit.newUnit(view, CompanySelected, spinnerUnit, Units)
        } else {
            arrayCardViewProducts[position].unit = unit
            arrayCardViewProducts[position].positionItem = Integer.toString(positionItem)
            val productAndAmount = ArrayList<String>()
            productAndAmount.add(0, arrayCardViewProducts[position].product)
            productAndAmount.add(1, arrayCardViewProducts[position].amount)
            productAndAmount.add(2, unit)
            productAndAmount.add(3, Integer.toString(positionItem))
            arrayProducts!![position] = productAndAmount
        }
    }

    fun removeAmountItem(position: Int) {
        val amountRemove = arrayCardViewProducts[position].amount.toInt()
        if (amountRemove > 0) {
            arrayCardViewProducts[position].setTextAmount(Integer.toString(amountRemove - 1))
            cardViewProductsAdapter.notifyItemChanged(position)
            val productAndAmount = ArrayList<String>()
            productAndAmount.add(0, arrayCardViewProducts[position].product)
            productAndAmount.add(1, Integer.toString(amountRemove - 1))
            productAndAmount.add(2, arrayCardViewProducts[position].unit)
            productAndAmount.add(3, arrayCardViewProducts[position].positionItem)
            arrayProducts[position] = productAndAmount
        }
    }

    fun buildRecyclerView(root: View) {
        mRecyclerView = root.findViewById(R.id.ordersRecycler)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(root.context)
        cardViewProductsAdapter =
            CardViewOrderAdapter(arrayCardViewProducts, CompanySelected, Units)
        mRecyclerView.setLayoutManager(mLayoutManager)
        mRecyclerView.setAdapter(cardViewProductsAdapter)
        cardViewProductsAdapter.setOnItemClickListener(object :
            CardViewOrderAdapter.OnItemClickListener {
            override fun addButtonClick(position: Int) {
                addAmountItem(position)
            }

            override fun editTextAmountChange(position: Int, amount: String) {
                editTextAmountItem(position, amount)
            }

            override fun removeButtonClick(position: Int) {
                removeAmountItem(position)
            }

            override fun selectUnit(
                position: Int,
                unit: String,
                positionItem: Int,
                sizeSpinner: Int,
                spinnerUnit: Spinner
            ) {
                selectUnitToOrder(position, unit, positionItem, sizeSpinner, spinnerUnit)
            }

            override fun onDeleteClick(position: Int) {
                removeItem(position)
            }
        })
    }

    private fun alertDialogComments(root: View): String? {
        val li = LayoutInflater.from(root.context)
        val promptsView = li.inflate(R.layout.dialog_comment_finish_button, null)
        val alertDialogBuilder = AlertDialog.Builder(root.context)
        alertDialogBuilder.setView(promptsView)
        val userInput = promptsView.findViewById<View>(R.id.etUserInput) as EditText


        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("Enviar") { dialog, id ->
            comment = userInput.text.toString()
            Toast.makeText(promptsView.context, "Comentario agregado al pedido", Toast.LENGTH_SHORT)
                .show()
            val order = Order()
            order.orderDone(
                arrayCardViewProducts,
                arrayProducts,
                CompanySelected,
                ProductsOrders,
                selectedClient,
                binding!!.ordersRecycler,
                comment,
                root
            )
        }
            .setNegativeButton(
                "NO"
            ) { dialog, id ->
                comment = " "
                Toast.makeText(promptsView.context, "Pedido sin comentario", Toast.LENGTH_SHORT)
                    .show()
                val order = Order()
                order.orderDone(
                    arrayCardViewProducts,
                    arrayProducts,
                    CompanySelected,
                    ProductsOrders,
                    selectedClient,
                    binding!!.ordersRecycler,
                    comment,
                    root
                )
            }
            .setNeutralButton("CANCELAR") { dialog, i -> dialog.cancel() }

        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialog.show()
        return comment
    }
}