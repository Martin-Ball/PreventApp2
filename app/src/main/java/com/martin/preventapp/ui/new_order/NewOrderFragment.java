package com.martin.preventapp.ui.new_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private FragmentNewOrderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newOrderViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SearchableSpinner spinner = root.findViewById(R.id.spinner_searchable_new_order);
        TextView text = root.findViewById(R.id.textView3);

        Clients clients = new Clients();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1,clients.clientlist());
        spinner.setAdapter(adapter);
        spinner.setTitle("Seleccione un cliente");
        spinner.setPositiveButton("CANCELAR");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                text.append(value);
                text.append("\n \n el mensaje cambio");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                text.append("Error al escribir");
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                {
                    myRef.setValue("Agregar cliente");
                    text.setText(myRef.toString());
                }
                else
                {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    text.setText(sNumber);
                    myRef.setValue("Entre a la app preventapp");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}