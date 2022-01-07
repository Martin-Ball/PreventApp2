package com.martin.preventapp.ui.new_order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new order fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}