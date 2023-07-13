package com.martin.preventapp.View.new_order.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.martin.preventapp.View.new_order.recyclerView.CardViewOrderAdapter.CardViewOrderViewHolder
import com.martin.preventapp.R
import android.text.TextWatcher
import android.text.Editable
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.martin.preventapp.databinding.CardViewOrderBinding
import java.util.ArrayList

class CardViewOrderAdapter(
    private val arrayProducts: ArrayList<CardViewOrder>,
    private val CompanySelected: String,
    private val Units: ArrayList<String>
    ) : RecyclerView.Adapter<CardViewOrderViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    private lateinit var binding:CardViewOrderBinding

    interface OnItemClickListener {
        fun addButtonClick(position: Int)
        fun editTextAmountChange(position: Int, amount: String?)
        fun removeButtonClick(position: Int)
        fun selectUnit(
            position: Int,
            unit: String?,
            positionItem: Int,
            sizeSpinner: Int,
            spinnerUnit: Spinner?
        )

        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class CardViewOrderViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var productText: TextView
        var unit: Spinner
        var amountTextSelected: EditText
        var deleteImage: ImageView
        var addImage: ImageView
        var removeImage: ImageView

        init {
            productText = itemView.findViewById(R.id.Client)
            unit = itemView.findViewById(R.id.Unit)
            amountTextSelected = itemView.findViewById(R.id.amountSelected)
            deleteImage = itemView.findViewById(R.id.image_delete_button)
            addImage = itemView.findViewById(R.id.image_add_button)
            removeImage = itemView.findViewById(R.id.image_remove_button)

            addImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.addButtonClick(position)
                }
            }
            amountTextSelected.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.editTextAmountChange(position, s.toString())
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
            removeImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.removeButtonClick(position)
                }
            }
            unit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val unitSelected = adapterView.getItemAtPosition(i).toString()
                        listener.selectUnit(
                            position,
                            unitSelected,
                            i,
                            adapterView.adapter.count,
                            unit
                        )
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
            deleteImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewOrderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_order, parent, false)
        val evh = CardViewOrderViewHolder(v, mListener)
        val UnitSelector = v.findViewById<Spinner>(R.id.Unit)
        val adapterUnitsSpinner = ArrayAdapter(
            v.rootView.context,
            android.R.layout.simple_list_item_1, Units
        )
        UnitSelector.adapter = adapterUnitsSpinner
        return evh
    }

    override fun onBindViewHolder(holder: CardViewOrderViewHolder, position: Int) {
        val currentItem = arrayProducts[position]
        holder.unit.setSelection(currentItem.positionItem.toInt())
        holder.productText.text = currentItem.product
        if (currentItem.amount.toInt() > 0) {
            holder.amountTextSelected.setText(currentItem.amount)
        }
    }

    override fun getItemCount(): Int {
        return arrayProducts.size
    }
}