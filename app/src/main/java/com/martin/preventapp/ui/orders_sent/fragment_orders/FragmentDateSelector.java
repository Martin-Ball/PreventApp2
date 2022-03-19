package com.martin.preventapp.ui.orders_sent.fragment_orders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentDateSelectorBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class FragmentDateSelector extends Fragment {

    private FragmentDateSelectorBinding binding;
    private String CompanySelected = "";
    private String SelectedClient;
    public String DateSelected = "";



    private HashMap<String, Object> User = new HashMap<>();
    private HashMap<String, Object> Orders = new HashMap<>();
    private HashMap<String, Object> Company = new HashMap<>();
    private HashMap<String, Object> Date = new HashMap<>();
    private  HashMap<String, Object> Client = new HashMap<>();
    private HashMap<String, Object> Hour = new HashMap<>();
    private ArrayList<String> ProductAndAmount = new ArrayList<>();

    String Comment = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentDateSelectorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bundle from Orders Sent Fragment

        Bundle bundle = this.getArguments();

        if(bundle != null){
            CompanySelected = bundle.getString("CompanySelected");
        }


        // Orders --> Company --> Date --> Client --> Hour --> Product
        //                                                 |--> Amount

        //Orders:  [HASHMAP]
        //{"Orders"={"Ideas Gastronomicas"={"19-02-2022"={"12"={"20:26"={{"Product6", "4"}, comment=""}}}}}

        //Company:  [HASHMAP]
        //{"Ideas Gastronomicas"={"19-02-2022"={"12"={"20:26"={{"Product6", "4"}, comment=""}}}}

        //19-02-2022:  [HASHMAP]
        //{"19-02-2022"={"12"={"20:26"={{"Product6", "4"}, comment=""}}}

        //12  [HASHMAP]
        //{"20:26"={{"Product6", "4"}, comment=""}}

        //20:26:  [ArrayList]
        //{{"Product6", "4"}, comment=""}

        //When i use get(0).get(0) the result is Product6

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("users").document(currentFirebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User = (HashMap<String, Object>) documentSnapshot.getData();
                Orders = (HashMap<String, Object>) User.get("Orders");
                Company = (HashMap<String, Object>) Orders.get(CompanySelected);
            }
        });

        //Fragment layout Orders
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        OrdersFragment ordersFragment = new OrdersFragment();

        //EditText for DatePicker

        EditText etPlannedDate = root.findViewById(R.id.etPlannedDate);

        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(root.getContext(), R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        //add zero for month
                        if(monthOfYear<10) {
                            DateSelected = dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year;
                        }else{
                            DateSelected = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        }

                        Date = (HashMap<String, Object>) Company.get(DateSelected);
                        if(Date == null){
                            Toast.makeText(root.getContext(), "No hay pedidos enviados en la fecha " + DateSelected, Toast.LENGTH_SHORT).show();
                            if(ordersFragment.isAdded()) {
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(ordersFragment).commit();
                            }
                        }else {
                            Client = (HashMap<String, Object>) Date.get("CLIENTE NUTRIFRESCA");
                            //Date selected on calendar fragment
                            Hour = (HashMap<String, Object>) Client.get("01:47");
                            ProductAndAmount = (ArrayList<String>) Hour.get("2");
                            Comment = Hour.get("comment").toString();

                            if(!ordersFragment.isAdded()) {
                                addFragmentOrders(fragmentManager, ordersFragment, etPlannedDate.getText().toString(), SelectedClient);
                            }else {
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(ordersFragment).commit();
                                OrdersFragment ordersFragment1 = new OrdersFragment();
                                addFragmentOrders(fragmentManager, ordersFragment1, etPlannedDate.getText().toString(), SelectedClient);
                            }
                        }
                        etPlannedDate.setText(DateSelected);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        return root;
    }

    public void onDestroyView(){
        super.onDestroyView();
    }

    private void addFragmentOrders(FragmentManager fragmentManager, Fragment ordersFragment, String DateSelected, String SelectedClient){
        Bundle bundle = new Bundle();
        bundle.putString("DateSelected", DateSelected);
        bundle.putString("CompanySelected", CompanySelected);
        bundle.putSerializable("DateHashMap", Date);
        ordersFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_orders_container, ordersFragment);
        fragmentTransaction.commit();
    }

}