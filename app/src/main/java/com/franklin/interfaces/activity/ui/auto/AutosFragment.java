package com.franklin.interfaces.activity.ui.auto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.franklin.interfaces.databinding.FragmentAutosBinding;

public class AutosFragment extends Fragment {

    private FragmentAutosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AutosViewModel autosViewModel =
                new ViewModelProvider(this).get(AutosViewModel.class);

        binding = FragmentAutosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAutos;
        autosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}