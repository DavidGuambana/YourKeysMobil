package com.franklin.interfaces.activity.ui.vista_auto;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franklin.interfaces.R;

public class VistaAutoFragment extends Fragment {

    private VistaAutoViewModel mViewModel;

    public static VistaAutoFragment newInstance() {
        return new VistaAutoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vista_auto, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VistaAutoViewModel.class);
        // TODO: Use the ViewModel
    }

}