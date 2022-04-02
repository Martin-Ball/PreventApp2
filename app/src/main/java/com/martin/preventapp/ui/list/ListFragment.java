package com.martin.preventapp.ui.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentListBinding;
import com.martin.preventapp.firebase.Company;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView tvt = root.findViewById(R.id.textView7);

        Company company = new Company();

        addFragmentListAdd();

        //dialog new company

        Button newCompany = root.findViewById(R.id.new_company);
        newCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext());
                final EditText edittext = new EditText(root.getContext());
                alert.setMessage("Crear nuevo proveedor");
                alert.setTitle("Ingrese el nombre del proveedor");

                alert.setView(edittext);

                alert.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newCompany = edittext.getText().toString();
                        company.addCompany(newCompany, root);
                        addFragmentListAdd();
                    }
                });

                alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });

        //if(companyList.size() > 1){
        //addFragmentListAdd();
        //}

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addFragmentListAdd(){
        FragmentListAdd fragmentListAdd = new FragmentListAdd();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerAddFiles, fragmentListAdd);
        fragmentTransaction.commit();
    }
}
