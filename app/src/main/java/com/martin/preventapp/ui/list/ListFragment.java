package com.martin.preventapp.ui.list;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentListBinding;
import com.martin.preventapp.firebase.Clients;
import com.martin.preventapp.firebase.Company;
import com.martin.preventapp.firebase.Products;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ListFragment extends Fragment {

    private ListViewModel ordersSentViewModel;
    private FragmentListBinding binding;
    private ArrayList<String> productList;
    private HashMap<String, Object> nameClientList;
    private HashMap<String, Object> clientList;
    private String CompanySelected = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSentViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Buttons add
        Button listFile = root.findViewById(R.id.list_file);
        Button clientsFile = root.findViewById(R.id.client_file);

        //TV test
        TextView tvTest = root.findViewById(R.id.textView6);

        //dialog new company

        Button newCompany = root.findViewById(R.id.new_company);
        newCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext());
                final EditText edittext = new EditText(root.getContext());
                alert.setMessage("Crear nuevo proveedor");
                alert.setTitle("Ingrese el nombre del proveedor");
                Company company = new Company();

                alert.setView(edittext);

                alert.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newCompany = edittext.getText().toString();
                        company.addCompany(newCompany, root);
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

        //Spinner Company

        Company CompanyList = new Company();
        Spinner spinnerCompany = root.findViewById(R.id.spinnerCompany);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CompanyList.companyList(root));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);

        spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CompanySelected = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //file chooser list

        ActivityResultLauncher<Intent> sActivityResultLauncherList = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            readExcelDataList(uri.getPath(), root, CompanySelected, tvTest);
                        }
                    }
        });

        listFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED && CompanySelected != null) {
                    openFileDialogList(sActivityResultLauncherList);
                }else{
                    Toast.makeText(root.getContext(), "permission denied", Toast.LENGTH_SHORT).show();
                }

                }
        });

        //file chooser clients

        ActivityResultLauncher<Intent> sActivityResultLauncherClient = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            readExcelDataClient(uri.getPath(), root, CompanySelected, tvTest);
                        }
                    }
                });

        clientsFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED && CompanySelected != null) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext());
                    alert.setTitle("Añadir clientes");
                    alert.setMessage("El formato del archivo excel debe ser: \n\n- CUIT\n- Nombre de Fantasia \n- Dirección");

                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            openFileDialogClient(sActivityResultLauncherClient);
                        }
                    });

                    alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();

                }else{
                    Toast.makeText(root.getContext(), "permission denied", Toast.LENGTH_SHORT).show();
                }

            }
        });


        int PERMISSION_REQUEST_CODE = 1;

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, PERMISSION_REQUEST_CODE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openFileDialogList(ActivityResultLauncher<Intent> activityResultLauncher) {
        String[] mimeTypes = {"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
        Intent searchExcel = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        searchExcel.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
        if (mimeTypes.length > 0) {
            searchExcel.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }

        searchExcel = Intent.createChooser(searchExcel, "Choose a file");
        activityResultLauncher.launch(searchExcel);
    }

    private void readExcelDataList(String ExcelFilePath, View root, String CompanySelected, TextView tvTest) {

        // HSSFWorkbook is for .xls
        // XSSFWorkbook is for .xlsx
        // Check the extension of the Excel file

        String[] path;
        path = ExcelFilePath.split(":");

        Workbook workbook = null;

        if (ExcelFilePath.endsWith(".xls")) {

            try {
                InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory(), path[1]));
                workbook = new HSSFWorkbook(inputStream);
                productList = new ArrayList<>();
               // Get the first sheet from workbook
                Sheet mySheet = workbook.getSheetAt(0);
                // We now need something to iterate through the cells.
                Iterator<Row> rowIter = mySheet.rowIterator();
                int rowno = 0;
                tvTest.setText("\n");
                while (rowIter.hasNext()) {
                    HSSFRow myRow = (HSSFRow) rowIter.next();
                    if (rowno != 0) {
                        Iterator<Cell> cellIter = myRow.cellIterator();
                        int colno = 0;
                        String product = "";
                        while (cellIter.hasNext()) {
                            HSSFCell myCell = (HSSFCell) cellIter.next();
                            if (colno == 0) {
                                product = myCell.toString();
                                productList.add(product);
                            }
                            colno++;
                        }
                    }
                    rowno++;
                }
                if(CompanySelected != "Seleccione un proveedor") {
                    Products products = new Products();
                    products.setProductList(CompanySelected, productList, root);
                    tvTest.append(productList.toString() + "\n\n");
                }
            } catch (IOException e) {
                Toast.makeText(root.getContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (ExcelFilePath.endsWith(".xlsx")) {
            Toast.makeText(root.getContext(), "Elija un archivo excel .xls", Toast.LENGTH_SHORT).show();
        }
    }

    public void openFileDialogClient(ActivityResultLauncher<Intent> activityResultLauncher) {
        String[] mimeTypes = {"application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
        Intent searchExcel = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        searchExcel.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
        if (mimeTypes.length > 0) {
            searchExcel.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }

        searchExcel = Intent.createChooser(searchExcel, "Choose a file");
        activityResultLauncher.launch(searchExcel);
    }

    private void readExcelDataClient(String ExcelFilePath, View root, String CompanySelected, TextView tvTest) {

        // HSSFWorkbook is for .xls
        // XSSFWorkbook is for .xlsx
        // Check the extension of the Excel file

        String[] path;
        path = ExcelFilePath.split(":");

        Workbook workbook = null;

        if (ExcelFilePath.endsWith(".xls")) {

            try {
                InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory(), path[1]));
                workbook = new HSSFWorkbook(inputStream);
                nameClientList = new HashMap<>();
                // Get the first sheet from workbook
                Sheet mySheet = workbook.getSheetAt(0);
                // We now need something to iterate through the cells.
                Iterator<Row> rowIter = mySheet.rowIterator();
                int rowno = 0;
                tvTest.setText("\n");
                while (rowIter.hasNext()) {
                    HSSFRow myRow = (HSSFRow) rowIter.next();
                    if (rowno != 0) {
                        Iterator<Cell> cellIter = myRow.cellIterator();
                        int colno = 0;
                        clientList = new HashMap<>();
                        String Client = "", CUIT = "", FantasyName = "", StreetAddress = "";
                        while (cellIter.hasNext()) {
                            HSSFCell myCell = (HSSFCell) cellIter.next();
                            if (colno == 0) {
                                Client = myCell.toString();
                                nameClientList.put("Client", Client);
                            }else if(colno == 1){
                                CUIT = myCell.toString();
                                clientList.put("CUIT", CUIT);
                            }else if(colno == 2){
                                FantasyName = myCell.toString();
                                clientList.put("FantasyName", FantasyName);
                            }else if(colno == 3){
                                StreetAddress = myCell.toString();
                                clientList.put("StreetAddress", StreetAddress);
                            }
                            colno++;
                        }
                        nameClientList.put(Client, clientList);
                    }
                    rowno++;
                }
                if(CompanySelected != "Seleccione un proveedor") {
                    Clients clients = new Clients();
                    clients.setClientList(CompanySelected, nameClientList, root);
                    tvTest.append(nameClientList.toString() + "\n\n");
                }
            } catch (IOException e) {
                Toast.makeText(root.getContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (ExcelFilePath.endsWith(".xlsx")) {
            Toast.makeText(root.getContext(), "Elija un archivo excel .xls", Toast.LENGTH_SHORT).show();
        }
    }
}
