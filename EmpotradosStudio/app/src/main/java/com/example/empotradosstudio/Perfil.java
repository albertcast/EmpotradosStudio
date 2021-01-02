package com.example.empotradosstudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Perfil extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editText_nombre, editText_apellidos;
    TextView textView_nombre, textView_apellidos, textView_nivel, textView_experiencia;
    Button boton_volver, boton_guardar;
    String username;
    ProgressBar progressBar_experiencia;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        if (getIntent().hasExtra("usuario")){
            username = getIntent().getStringExtra("usuario");
            editText_nombre = (EditText) findViewById(R.id.editText_nombre_perfil);
            editText_apellidos = (EditText) findViewById(R.id.editText_apellidos_perfil);
            textView_nombre = (TextView) findViewById(R.id.textView_nombre_perfil);
            textView_apellidos = (TextView) findViewById(R.id.textView_apellido_perfil);
            textView_experiencia = (TextView) findViewById(R.id.textView_experiencia_perfil);
            textView_nivel = (TextView) findViewById(R.id.textView_nivel_perfil);
            boton_volver = (Button) findViewById(R.id.button_volver_perfil);
            boton_guardar = (Button) findViewById(R.id.button_guardar_perfil);
            progressBar_experiencia = (ProgressBar) findViewById(R.id.progressBar_experiencia_perfil);
            progressBar_experiencia.setIndeterminate(false);

            bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavigation);
            bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

            bottomNav.getMenu().getItem(0).setChecked(false);
            bottomNav.getMenu().getItem(1).setChecked(true);

            textView_nombre.setText(R.string.textView_nombre_string);
            textView_apellidos.setText(R.string.textView_apellido_string);
            boton_volver.setText(R.string.boton_volver);
            boton_guardar.setText(R.string.boton_guardar);


            progressBar_experiencia.setMax(100);


            myDb = new DatabaseHelper(this);

            Cursor res = myDb.getUsername(username);
            if (res.moveToNext()){
                editText_nombre.setText(res.getString(3));
                editText_apellidos.setText(res.getString(4));
                textView_nivel.setText(Integer.toString(res.getInt(5)));
                textView_experiencia.setText(Integer.toString(res.getInt(6))+" / 100");
                progressBar_experiencia.setProgress(res.getInt(6));
            }

        } else {
            Intent intento = new Intent(Perfil.this, Login.class);
            startActivity(intento);
        }

        Guardar();
        Volver();


    }


    public void Volver(){
        boton_volver.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(Perfil.this, InicioAplicacion.class);
                        intento.putExtra("usuario", username);
                        startActivity(intento);
                    }
                }
        );
    }

    public void Guardar(){
        boton_guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getUsername(username);
                        if (res.moveToNext()){
                            myDb.updateData(res.getString(0), editText_nombre.getText().toString(), editText_apellidos.getText().toString());
                            Intent intento = new Intent(Perfil.this, InicioAplicacion.class);
                            intento.putExtra("usuario", username);
                            intento.putExtra("perfil_guardar", "Datos guardados");
                            startActivity(intento);
                        } else {
                            Toast.makeText(Perfil.this, "No se han podido guardar los cambios", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void Perfil(){
        Intent intento = new Intent(Perfil.this, Perfil.class);
        intento.putExtra("usuario", username);
        startActivity(intento);
    }

    public void Ubicacion() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void Ranking(){
        Intent intento = new Intent(Perfil.this, RankingPersonas.class);
        intento.putExtra("usuario",username);
        startActivity(intento);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNav.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.map) {
                Ubicacion();
            } else if (itemId == R.id.perfil) {
                Perfil();
            } else if (itemId == R.id.rank) {
                Ranking();
            }
            finish();
        }, 300);
        return true;
    }








}