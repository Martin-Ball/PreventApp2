package com.martin.preventapp.ui.orders_sent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrdersSentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrdersSentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Orders Sent fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}