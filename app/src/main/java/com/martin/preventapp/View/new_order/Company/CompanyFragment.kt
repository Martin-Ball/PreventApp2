package com.martin.preventapp.View.new_order.Company

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.*
import com.martin.preventapp.firebase.Company
import com.martin.preventapp.R
import com.martin.preventapp.View.new_order.NewOrderFragment
import androidx.fragment.app.Fragment
import com.martin.preventapp.databinding.FragmentCompanyBinding

class CompanyFragment : Fragment() {
    private lateinit var binding: FragmentCompanyBinding
    private var CompanySelected: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyBinding.inflate(layoutInflater)
        val root: View = binding.root

        //Spinner company
        val CompanyList = Company()

        //Spinner Company
        val adapter = ArrayAdapter(
            root.context,
            android.R.layout.simple_spinner_item,
            CompanyList.companyList(root)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCompany.adapter = adapter
        binding.spinnerCompany.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                position: Int,
                l: Long
            ) {
                CompanySelected = adapterView.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        //go to NewOrderFragment
        val bundle = Bundle()
        val fragmentManager = parentFragmentManager // getSupportFragmentManager();
        val newOrderFragment = NewOrderFragment()
        binding.goOrder.setOnClickListener {
            if (CompanySelected === "Seleccione un proveedor") {
                Toast.makeText(root.context, "Seleccione un proveedor", Toast.LENGTH_SHORT).show()
            } else {
                bundle.putString("CompanySelected", CompanySelected) // Put anything what you want
                newOrderFragment.arguments = bundle
                fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, newOrderFragment)
                    .addToBackStack(null) // name can be null
                    .commit()
            }
        }
        return root
    }
}