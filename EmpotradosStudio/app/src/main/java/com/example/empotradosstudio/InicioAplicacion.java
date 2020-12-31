package com.example.empotradosstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioAplicacion extends AppCompatActivity {

    Button boton_ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_aplicacion);
        boton_ubicacion = (Button) findViewById(R.id.button_maps);

        Ubicacion();

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

}