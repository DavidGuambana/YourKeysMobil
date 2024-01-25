package com.franklin.interfaces.activity.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilViewModel extends ViewModel {

    private static MutableLiveData<Integer> orientationLiveData = new MutableLiveData<>();

    public LiveData<Integer> getOrientationLiveData() {
        return orientationLiveData;
    }

    public static void setOrientation(int orientation) {
        orientationLiveData.setValue(orientation);
    }
}