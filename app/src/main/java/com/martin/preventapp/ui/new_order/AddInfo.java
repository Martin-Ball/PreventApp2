package com.martin.preventapp.ui.new_order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.martin.preventapp.R;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.Products;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddInfo {

    public void newClient (View view, String CompanySelected, ArrayList<String> ClientList, SearchableSpinner spinnerClient)
    {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dialoglayout = inflater.inflate(R.layout.dialog_new_client, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

        EditText Name = dialoglayout.findViewById(R.id.editTextNameClient);
        EditText CUIT = dialoglayout.findViewById(R.id.editTextCUITClient);
        EditText StreetAddress = dialoglayout.findViewById(R.id.editTextStreetAddressClient);
        EditText FantasyName = dialoglayout.findViewById(R.id.editTextFantasyNameClient);

        alert.setTitle("Agregar un nuevo cliente");
        alert.setMessage("Escriba los datos del cliente");

        alert.setView(dialoglayout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Clients newclient = new Clients();

                //Get values from EditTexts
                String EditTextName = Name.getText().toString().toUpperCase(Locale.ROOT); //UpperCase to convert in "Mayusculas"
                String EditTextCUIT = CUIT.getText().toString().toUpperCase(Locale.ROOT); //UpperCase to convert in "Mayusculas"
                String EditTextStreetAddress = StreetAddress.getText().toString().toUpperCase(Locale.ROOT); //UpperCase to convert in "Mayusculas"
                String EditTextFantasyName = FantasyName.getText().toString().toUpperCase(Locale.ROOT);

                newclient.addNewClient(CompanySelected, EditTextName, EditTextCUIT, EditTextStreetAddress, EditTextFantasyName, view);

                ArrayList<String> ClientList2 = newclient.clientlist(view.getRootView(), CompanySelected);

                ArrayAdapter<String> adapterNewClientSelector = new ArrayAdapter<>(view.getContext(),
                        android.R.layout.simple_list_item_1,
                        ClientList2);
                spinnerClient.setAdapter(adapterNewClientSelector);
                spinnerClient.setSelection(0);
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

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

    public void newUnit (View view, String CompanySelected, Spinner spinnerUnits) {
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

        final EditText edittext = new EditText(view.getContext());
        alert.setMessage("Agregar unidad");
        alert.setTitle("Denominación:");

        alert.setView(edittext);

        alert.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String UnitName = edittext.getText().toString().toUpperCase(Locale.ROOT);
                Products units = new Products();
                units.addNewUnit(CompanySelected, UnitName, view);

                //products
                ArrayList<String> UnitList2 = units.getUnits(view, CompanySelected);

                ArrayAdapter<String> adapterNewProductSelector = new ArrayAdapter<>(view.getContext(),
                        android.R.layout.simple_list_item_1,
                        UnitList2);
                spinnerUnits.setAdapter(adapterNewProductSelector);
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

}
