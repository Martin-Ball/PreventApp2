package com.martin.preventapp.View.new_order

import android.app.AlertDialog
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import android.view.LayoutInflater
import com.martin.preventapp.R
import android.widget.EditText
import android.view.View
import com.martin.preventapp.Model.Clients
import android.widget.ArrayAdapter
import com.martin.preventapp.Model.Products
import android.widget.Spinner
import java.util.ArrayList

class AddInfo {

    fun newClient(
        view: View,
        CompanySelected: String,
        ClientList: ArrayList<String>,
        spinnerClient: SearchableSpinner
    ) {


        val inflater = LayoutInflater.from(view.context)
        val dialoglayout = inflater.inflate(R.layout.dialog_new_client, null)
        val alert = AlertDialog.Builder(view.context)
        val Name = dialoglayout.findViewById<EditText>(R.id.editTextNameClient)
        val CUIT = dialoglayout.findViewById<EditText>(R.id.editTextCUITClient)
        val StreetAddress = dialoglayout.findViewById<EditText>(R.id.editTextStreetAddressClient)
        val FantasyName = dialoglayout.findViewById<EditText>(R.id.editTextFantasyNameClient)

        alert.setTitle("Agregar un nuevo cliente")
        alert.setMessage("Escriba los datos del cliente")
        alert.setView(dialoglayout)
        alert.setPositiveButton("OK") { dialog, whichButton ->
            val newclient = Clients()

            //Get values from EditTexts
            val EditTextName =
                Name.text.toString().uppercase() //UpperCase to convert in "Mayusculas"
            val EditTextCUIT =
                CUIT.text.toString().uppercase() //UpperCase to convert in "Mayusculas"
            val EditTextStreetAddress =
                StreetAddress.text.toString().uppercase() //UpperCase to convert in "Mayusculas"
            val EditTextFantasyName = FantasyName.text.toString().uppercase()

            newclient.addNewClient(
                CompanySelected,
                EditTextName,
                EditTextCUIT,
                EditTextStreetAddress,
                EditTextFantasyName,
                view
            )

            val ClientList2 = newclient.clientlist(view.rootView, CompanySelected)
            val adapterNewClientSelector = ArrayAdapter(
                view.context,
                android.R.layout.simple_list_item_1,
                ClientList2
            )
            spinnerClient.adapter = adapterNewClientSelector
            spinnerClient.setSelection(0)
        }
        alert.setNegativeButton("CANCELAR") { dialog, whichButton -> }
        alert.show()
    }

    fun newProduct(
        view: View, CompanySelected: String, ProductList: ArrayList<String>,
        spinnerNewProduct: SearchableSpinner
    ) {
        val alert = AlertDialog.Builder(view.context)
        val edittext = EditText(view.context)
        alert.setMessage("Nombre del producto")
        alert.setTitle("Agregar un producto")
        alert.setView(edittext)
        alert.setPositiveButton("AGREGAR") { dialog, whichButton ->
            val ProductName = edittext.text.toString().uppercase()
            val products = Products()
            products.addNewProduct(CompanySelected, ProductName, view, ProductList)

            //products
            val ProductList2 = products.getProductlist(view.rootView, CompanySelected)
            val adapterNewProductSelector = ArrayAdapter(
                view.context,
                android.R.layout.simple_list_item_1,
                ProductList2
            )
            spinnerNewProduct.adapter = adapterNewProductSelector
        }
        alert.setNegativeButton("CANCELAR") { dialog, whichButton -> }
        alert.show()
    }

    fun newUnit(
        view: View,
        CompanySelected: String,
        spinnerUnits: Spinner,
        Units: ArrayList<String>
    ) {
        val alert = AlertDialog.Builder(view.context)
        val edittext = EditText(view.context)
        alert.setMessage("Denominación:")
        alert.setTitle("Agregar unidad")
        alert.setView(edittext)
        alert.setPositiveButton("AGREGAR") { dialog, whichButton ->
            val UnitName = edittext.text.toString().uppercase()
            val units = Products()

            //products
            units.addNewUnit(CompanySelected, UnitName, view, Units)
            val adapterNewProductSelector = ArrayAdapter(
                view.context,
                android.R.layout.simple_list_item_1, Units
            )
            spinnerUnits.adapter = adapterNewProductSelector
        }
        alert.setNegativeButton("CANCELAR") { dialog, whichButton -> }
        alert.show()
    }
}