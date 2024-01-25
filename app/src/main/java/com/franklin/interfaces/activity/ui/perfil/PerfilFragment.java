package com.franklin.interfaces.activity.ui.perfil;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.franklin.interfaces.R;
import com.franklin.interfaces.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView imagePerfil = binding.imagePerfil;
        Button btnSeleccionarFoto = binding.btnSeleccionarFoto;
        Button btnEditarPerfil = binding.btnEditarPerfil;
        androidx.constraintlayout.widget.Guideline guideline = binding.guideline;

        // Configura el clic del botón para navegar a la pantalla de editar perfil
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega a la acción correspondiente
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_inicio);
                navController.navigate(R.id.action_perfilFragment_to_editPerfilFragment);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}