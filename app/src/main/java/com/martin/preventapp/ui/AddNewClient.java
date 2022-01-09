package com.martin.preventapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import com.martin.preventapp.firebase.Clients;

import java.util.Locale;

public class AddNewClient {

    public void newClient (View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

        final EditText edittext = new EditText(view.getContext());
        alert.setTitle("Agregar un nuevo cliente");
        alert.setMessage("Escriba el nombre del cliente");

        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Clients newclient = new Clients();
                String EditText = edittext.getText().toString().toUpperCase(Locale.ROOT); //UpperCase to convert in "Mayusculas"
                newclient.addNewClient(EditText);
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
}
