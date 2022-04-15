package com.martin.preventapp.ui.new_order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.martin.preventapp.firebase.Products;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Locale;

public class AddNewProduct {

    public void newProduct(View view, String CompanySelected, ArrayList<String> ProductList,
                           SearchableSpinner spinnerNewProduct) {

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

        final EditText edittext = new EditText(view.getContext());
        alert.setMessage("Nombre del producto");
        alert.setTitle("Agregar un producto");

        alert.setView(edittext);

        alert.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String ProductName = edittext.getText().toString().toUpperCase(Locale.ROOT);
                Products products = new Products();
                products.addNewProduct(CompanySelected, ProductName, view, ProductList);

                //products
                ArrayList<String> ProductList2 = products.getProductlist(view.getRootView(), CompanySelected);

                ArrayAdapter<String> adapterNewProductSelector = new ArrayAdapter<>(view.getContext(),
                        android.R.layout.simple_list_item_1,
                        ProductList2);
                spinnerNewProduct.setAdapter(adapterNewProductSelector);
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();

    }
}
