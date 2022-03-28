package com.martin.preventapp.ui.list;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.martin.preventapp.R;
import com.martin.preventapp.databinding.FragmentListBinding;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ListFragment extends Fragment {

    private ListViewModel ordersSentViewModel;
    private FragmentListBinding binding;

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

        //file chooser

        ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            Toast.makeText(getContext(), uri.getPath().toString(), Toast.LENGTH_SHORT).show();
                            readExcelData(uri.getPath(), root, tvTest);
                        }
                    }
                });

        listFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileDialog(root, sActivityResultLauncher);
            }
        });


        int EXTERNAL_STORAGE_PERMISSION_CODE = 23;

        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);

        //"sdcard/Download/contacts.xlsx"

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openFileDialog(View view, ActivityResultLauncher<Intent> activityResultLauncher){
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("application/vnd.ms-excel");
        data = Intent.createChooser(data, "Choose a file");
        activityResultLauncher.launch(data);
    }

    private void readExcelData(String ExcelFilePath, View root, TextView tvTest) {

        // HSSFWorkbook is for .xls
        // XSSFWorkbook is for .xlsx
        // Check the extension of the Excel file

        String[] path = ExcelFilePath.split(":");

        HSSFWorkbook workbook = null;

        if (ExcelFilePath.endsWith(".xls")) {

            try {
                InputStream inputStream = new FileInputStream(new File(path[1]));
                workbook = new HSSFWorkbook(inputStream);

                // Get the first sheet from workbook
                HSSFSheet mySheet = workbook.getSheetAt(0);
                // We now need something to iterate through the cells.
                Iterator<Row> rowIter = mySheet.rowIterator();
                int rowno = 0;
                tvTest.setText("\n");
                while (rowIter.hasNext()) {
                    HSSFRow myRow = (HSSFRow) rowIter.next();
                    if (rowno != 0) {
                        Iterator<Cell> cellIter = myRow.cellIterator();
                        int colno = 0;
                        String sno = "", date = "", det = "";
                        while (cellIter.hasNext()) {
                            HSSFCell myCell = (HSSFCell) cellIter.next();
                            if (colno == 0) {
                                sno = myCell.toString();
                            } else if (colno == 1) {
                                date = myCell.toString();
                            } else if (colno == 2) {
                                det = myCell.toString();
                            }
                            colno++;
                        }
                        tvTest.append(sno + " -- " + date + "  -- " + det + "\n\n");
                    }
                    rowno++;
                }
            } catch (FileNotFoundException e) {
                Log.i("debinf cliinf", "readExcelData: FileNotFoundException " + e.getMessage());
            } catch (IOException e) {
                Log.i("debinf cliinf", "readExcelData: Error reading InputStream " + e.getMessage());
            }
        } else if (ExcelFilePath.endsWith(".xlsx")) {

           /* try {
                InputStream inputStream = new FileInputStream(new File(path[1]));
                workbook = new XSSFWorkbook(inputStream);
            } catch (FileNotFoundException e) {
                Log.i("debinf cliinf", "readExcelData: FileNotFoundException " + e.getMessage());
            } catch (IOException e) {
                Log.i("debinf cliinf", "readExcelData: Error reading InputStream " + e.getMessage());
            }*/
        }


    }


}
