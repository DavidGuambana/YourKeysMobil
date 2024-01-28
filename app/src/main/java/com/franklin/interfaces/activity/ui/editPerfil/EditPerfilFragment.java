package com.franklin.interfaces.activity.ui.editPerfil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.franklin.interfaces.databinding.FragmentEditPerfilBinding;

public class EditPerfilFragment extends Fragment {

    private FragmentEditPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditPerfilViewModel editPerfilViewModel =
                new ViewModelProvider(this).get(EditPerfilViewModel.class);

        binding = FragmentEditPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView imagePerfil = binding.imagePerfil;
        Button btnGuardar = binding.btnGuardar;
        androidx.constraintlayout.widget.Guideline guideline = binding.guideline;

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}