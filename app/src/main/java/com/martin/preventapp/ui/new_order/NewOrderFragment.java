package com.martin.preventapp.ui.new_order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentNewOrderBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.ui.AddNewClient;
import com.martin.preventapp.ui.recyclerView.Anime;
import com.martin.preventapp.ui.recyclerView.AnimeAdapter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewOrderFragment extends Fragment {

    private NewOrderViewModel newOrderViewModel;
    private FragmentNewOrderBinding binding;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter2;
    private RecyclerView.LayoutManager lManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newOrderViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //spinner searchable
        SearchableSpinner spinner = root.findViewById(R.id.spinner_searchable_new_order);
        TextView text = root.findViewById(R.id.textView3);

        //clients
        Clients clients = new Clients();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1,clients.clientlist());
        spinner.setAdapter(adapter);
        spinner.setTitle("Seleccione un cliente");
        spinner.setPositiveButton("CANCELAR");

        //firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Clients");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                {
                    AddNewClient newClient = new AddNewClient();
                    newClient.newClient(root);
                }
                else
                {
                    String sNumber = adapterView.getItemAtPosition(i).toString();
                    text.setText(sNumber);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //table layout

        // Inicializar Animes
        List items = new ArrayList();

        items.add(new Anime(R.drawable.ic_add, "Angel Beats", 230));
        items.add(new Anime(R.drawable.ic_check, "Death Note", 456));
        items.add(new Anime(R.drawable.ic_menu_camera, "Fate Stay Night", 342));
        items.add(new Anime(R.drawable.ic_cart, "Welcome to the NHK", 645));
        items.add(new Anime(R.drawable.ic_list, "Suzumiya Haruhi", 459));
        items.add(new Anime(R.drawable.ic_list, "Pruebita pruebini", 459));

// Obtener el Recycler
        recycler = (RecyclerView) root.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(root.getContext());
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter2 = new AnimeAdapter(items);
        recycler.setAdapter(adapter2);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}