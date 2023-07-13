package com.martin.preventapp.View.ui.orders_sent.fragment_orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.martin.preventapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapterOrders extends RecyclerView.Adapter<RecyclerViewAdapterOrders.ViewHolder> {

    //private List<String> mData;
    private ArrayList<InfoOrders> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapterOrders(ArrayList<InfoOrders> courseModalArrayList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = courseModalArrayList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<InfoOrders> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        mData = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_view_order_sent, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        InfoOrders infoOrders = mData.get(position);

        holder.company.setText("Empresa: " + infoOrders.getCompany());
        holder.client.setText("Cliente: " + infoOrders.getClient());
        holder.Hour.setText("Hora: " + infoOrders.getHour());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView company;
        TextView client;
        TextView Hour;

        ViewHolder(View itemView) {
            super(itemView);
            company = itemView.findViewById(R.id.company);
            client = itemView.findViewById(R.id.client);
            Hour = itemView.findViewById(R.id.Hour);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    /*String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
