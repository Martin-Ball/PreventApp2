package com.martin.preventapp.ui.orders_sent;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentOrdersSentBinding;
import com.martin.preventapp.ui.orders_sent.dialogCalendar.DatePicker;
import com.martin.preventapp.ui.orders_sent.fragment_orders.OrdersFragment;

import java.util.Calendar;

public class OrdersSentFragment extends Fragment {

    private OrdersSentViewModel ordersSentViewModel;
    private FragmentOrdersSentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSentViewModel =
                new ViewModelProvider(this).get(OrdersSentViewModel.class);

        binding = FragmentOrdersSentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button bt = root.findViewById(R.id.button);
        OrdersFragment of = new OrdersFragment();

        EditText etPlannedDate = (EditText) root.findViewById(R.id.etPlannedDate);
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
                         etPlannedDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                     }
                 }, year, month, day);
                 picker.show();
             }
         });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!of.isAdded()) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.fragment_container, of);
                    fragmentTransaction.commit();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showDatePickerDialog(EditText etPlannedDate) {
        DatePicker newFragment = DatePicker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etPlannedDate.setText(selectedDate);
            }
        });


        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}

