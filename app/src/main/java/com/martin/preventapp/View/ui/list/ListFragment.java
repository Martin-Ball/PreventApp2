package com.martin.preventapp.View.ui.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.martin.preventapp.R;
import com.martin.preventapp.Model.Company;
import com.martin.preventapp.databinding.FragmentListBinding;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        new Task().execute();

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

    private class Task extends AsyncTask<String, String, ArrayList<String>> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected ArrayList<String> doInBackground(String... params) {

            Company company = new Company();

                // Call this to update your progress
                //publishProgress(companyList);

            return company.companyList(getView());
        }

        // This is called from background thread but runs in UI
        protected void onProgressUpdate(ArrayList<String> arr1) {

        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            ArrayList<String> companyList = new ArrayList<String>(result);
            TextView tvt = getView().findViewById(R.id.textView7);

            if(companyList.size() > 1){
                addFragmentListAdd();
                tvt.setText(companyList.toString());
            }
            Log.i("Current User TASK: ", companyList.toString());
        }
    }
}
