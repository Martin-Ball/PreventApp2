package com.martin.preventapp.ui.new_order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.martin.preventapp.R;
import com.martin.preventapp.firebase.Clients;

import java.util.Locale;

public class AddNewClient {

    public void newClient (View view)
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

                newclient.addNewClient(EditTextName, EditTextCUIT, EditTextStreetAddress, EditTextFantasyName, view);
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
}
