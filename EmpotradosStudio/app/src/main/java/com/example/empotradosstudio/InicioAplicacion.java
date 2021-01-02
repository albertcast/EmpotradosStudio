package com.example.empotradosstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class InicioAplicacion extends AppCompatActivity {

    Button boton_ubicacion, boton_perfil, boton_logout, boton_ranking;
    String username;
    private boolean locationPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_aplicacion);

        if(getIntent().hasExtra("perfil_guardar")){
            Toast.makeText(this, getIntent().getStringExtra("perfil_guardar"),Toast.LENGTH_LONG).show();
        }

        boton_ubicacion = (Button) findViewById(R.id.button_maps);
        boton_perfil = (Button) findViewById(R.id.button_perfil_inicio);
        boton_logout = (Button) findViewById(R.id.button_logout);
        boton_ranking = (Button) findViewById(R.id.button_ranking_inicio);
        username = getIntent().getStringExtra("usuario");


        boton_ubicacion.setText(R.string.boton_location);
        boton_perfil.setText(R.string.boton_perfil);
        Ubicacion();
        Perfil();
        Logout();
        Ranking();
    }

    public void Perfil(){
        boton_perfil.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(InicioAplicacion.this, Perfil.class);
                        intento.putExtra("usuario", username);
                        startActivity(intento);
                    }
                }
        );
    }

    public void sendMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void Ubicacion(){
        boton_ubicacion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMaps(v);
                    }
                }
        );
    }

    public void Logout(){
        boton_logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(InicioAplicacion.this, Login.class);
                        startActivity(intento);
                    }
                }
        );
    }

    public void Ranking(){
        boton_ranking.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(InicioAplicacion.this, RankingPersonas.class);
                        intento.putExtra("usuario",username);
                        startActivity(intento);
                    }
                }
        );
    }

}