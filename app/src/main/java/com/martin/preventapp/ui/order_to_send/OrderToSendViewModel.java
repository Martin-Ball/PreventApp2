package com.martin.preventapp.ui.order_to_send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderToSendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderToSendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}