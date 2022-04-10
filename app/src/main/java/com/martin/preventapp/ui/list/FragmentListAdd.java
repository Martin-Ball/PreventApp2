package com.martin.preventapp.ui.list;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentListAddBinding;
import com.martin.preventapp.firebase.Company;
import com.martin.preventapp.firebase.Products;
import com.martin.preventapp.ui.list.dialogFragmentClient.CardViewDetailExcel;
import com.martin.preventapp.ui.list.dialogFragmentClient.DialogFragmentExcel;
import com.martin.preventapp.ui.list.dialogFragmentList.CardViewDetailProduct;
import com.martin.preventapp.ui.list.dialogFragmentList.DialogFragmentList;

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

public class FragmentListAdd extends Fragment {

    private FragmentListAddBinding binding;
    private ArrayList<String> productList;
    private HashMap<String, Object> nameClientList;
    private HashMap<String, Object> clientList;
    private String CompanySelected = null;
    private ArrayList<CardViewDetailExcel> arrayCardViewExcelClient;
    private ArrayList<CardViewDetailProduct> cardViewExcelProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Buttons add
        Button listFile = root.findViewById(R.id.list_file);
        Button clientsFile = root.findViewById(R.id.client_file);

        //TV test
        TextView tvTest = root.findViewById(R.id.textView6);

        Company company = new Company();

        ArrayList<String> arrayList = new ArrayList<>(company.companyList(root));
        tvTest.setText(arrayList.toString());
        Log.i("Current User: ", arrayList.toString());

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

                    AlertDialog.Builder alert = new AlertDialog.Builder(root.getContext());
                    alert.setTitle("Añadir lista de productos");
                    alert.setMessage("La primer columna del archivo excel sera la lista de productos.");

                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            openFileDialogList(sActivityResultLauncherList);
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
                    alert.setMessage("El formato del archivo excel debe ser: \n\n- Nombre\n- CUIT\n- Nombre de Fantasia \n- Dirección");

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
                cardViewExcelProduct = new ArrayList<>();
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
                                cardViewExcelProduct.add(new CardViewDetailProduct(product));
                            }
                            colno++;
                        }
                    }
                    rowno++;
                }
                if(!CompanySelected.equals("Seleccione un proveedor")) {
                    DialogFragmentList dialogFragmentList = new DialogFragmentList();

                    Bundle bundle = new Bundle();
                    bundle.putString("CompanySelected", CompanySelected);
                    bundle.putSerializable("CardViewProductList", cardViewExcelProduct);
                    bundle.putSerializable("ProductList", productList);
                    dialogFragmentList.setArguments(bundle);
                    dialogFragmentList.show(getActivity().getSupportFragmentManager(),"Detail Fragment");
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
                arrayCardViewExcelClient = new ArrayList<>();
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
                                CUIT = Long.toString((long) myCell.getNumericCellValue());
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
                        arrayCardViewExcelClient.add(new CardViewDetailExcel(Client, CUIT, FantasyName, StreetAddress));
                        nameClientList.put(Client, clientList);
                    }
                    rowno++;
                }
                if(!CompanySelected.equals("Seleccione un proveedor")) {
                    DialogFragmentExcel dialogFragmentExcel = new DialogFragmentExcel();

                    Bundle bundle = new Bundle();
                    bundle.putString("CompanySelected", CompanySelected);
                    bundle.putSerializable("ClientList", arrayCardViewExcelClient);
                    bundle.putSerializable("nameClientList", nameClientList);
                    dialogFragmentExcel.setArguments(bundle);
                    dialogFragmentExcel.show(getActivity().getSupportFragmentManager(),"Detail Fragment");
                }
            } catch (IOException e) {
                Toast.makeText(root.getContext(), "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (ExcelFilePath.endsWith(".xlsx")) {
            Toast.makeText(root.getContext(), "Elija un archivo excel .xls", Toast.LENGTH_SHORT).show();
        }
    }
}