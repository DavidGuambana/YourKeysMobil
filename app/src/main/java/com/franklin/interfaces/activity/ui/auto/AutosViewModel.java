package com.franklin.interfaces.activity.ui.auto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AutosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AutosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aveo Family V8");
    }

    public LiveData<String> getText() {
        return mText;
    }
}