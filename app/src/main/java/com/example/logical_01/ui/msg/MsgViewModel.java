package com.example.logical_01.ui.msg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MsgViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MsgViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is message fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
