package com.franklin.interfaces.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.franklin.interfaces.MainActivity;
import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Persona;
import com.franklin.interfaces.activity.services.RUTA;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.franklin.interfaces.databinding.ActivityInicioBinding;
import com.squareup.picasso.Picasso;

public class InicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarInicio.toolbar);
        binding.appBarInicio.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_perfil, R.id.nav_autos, R.id.nav_historial)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Encuentra el botón dentro del fragmento
        View headerView = navigationView.getHeaderView(0);
        Button salir = headerView.findViewById(R.id.btnCerrarSesion);
        salir.setOnClickListener(l -> salir());

        Intent intent = getIntent();

        // Verifica si hay datos extras en el Intent y obtén el objeto Usuario

        if (intent != null && intent.hasExtra("objeto_persona")) {
            Persona persona = (Persona) intent.getSerializableExtra("objeto_persona");
            TextView name = headerView.findViewById(R.id.Name);
            TextView username = headerView.findViewById(R.id.Username);
            TextView email = headerView.findViewById(R.id.Email);
            ImageView imagen = headerView.findViewById(R.id.Imagen);

            name.setText(persona.getNombre1()+" "+persona.getApellido1()+"");
            username.setText("("+persona.getUsuario().getUsername()+")");
            email.setText(persona.getCorreo()+"");
            Picasso.get()
                    .load(RUTA.getUrlFoto(persona.getUrl_imagen()))
                    .error(R.drawable.perfil)
                    .into(imagen);
        }
    }

    public void salir(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}