package com.example.empotradosstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingPersonas extends AppCompatActivity {

    ListView listView_ranking;
    EditText editText_nombre;
    TextView textView_nombre;
    Button button_anterior, button_siguiente, button_buscar;
    DatabaseHelper myDb;
    int offset = 0, limit = 6, offset_buscar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_personas2);

        listView_ranking = (ListView) findViewById(R.id.listView_ranking);
        editText_nombre = (EditText) findViewById(R.id.editText_nombre_ranking);
        textView_nombre = (TextView) findViewById(R.id.textView_nombre_ranking);
        button_anterior = (Button) findViewById(R.id.button_anterior_ranking);
        button_siguiente = (Button) findViewById(R.id.button_siguiente_ranking);
        button_buscar = (Button) findViewById(R.id.button_buscar_ranking);

        textView_nombre.setText(R.string.textView_nombre_string);
        button_siguiente.setText(R.string.boton_siguiente);
        button_anterior.setText(R.string.boton_anterior);
        button_buscar.setText(R.string.boton_buscar);

        myDb = new DatabaseHelper(this);

        Adaptar();
        Anterior();
        Siguiente();
        Buscar();
    }

    public void Adaptar(){
        Cursor res = myDb.getAllData(offset);

        res.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!res.isAfterLast()) {
            names.add(res.getString(3) + " " + res.getString(4) + " " + res.getInt(5));
            res.moveToNext();
        }
        res.close();


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        listView_ranking.setAdapter(adapter);
    }

    public void Adaptar(String username){
        Cursor res = myDb.getAllData(offset);
        boolean encontrado = false;
        res.moveToFirst();
        while(!res.isAfterLast() && !encontrado) {
            if (username.equalsIgnoreCase(res.getString(3))) {
                encontrado = true;
            } else {
                offset_buscar++;
            }
            res.moveToNext();
        }
        res.close();

        Cursor resAux = myDb.getAllData(offset_buscar);

        resAux.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!resAux.isAfterLast()) {
            names.add(resAux.getString(3) + " " + resAux.getString(4) + " " + resAux.getInt(5));
            resAux.moveToNext();
        }
        resAux.close();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        listView_ranking.setAdapter(adapter);

        offset_buscar = 0;
    }

    public void Anterior(){
        button_anterior.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (offset > 0) {
                            offset-= limit;
                            Adaptar();
                        }
                    }
                }
        );
    }

    public void Siguiente(){
        button_siguiente.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData(offset+limit);
                        if(res.getCount()!= 0){
                            offset+=limit;
                            Adaptar();
                        }
                    }
                }
        );
    }

    public void Buscar(){
        button_buscar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(editText_nombre.getText().toString())){
                            Cursor res = myDb.getUsername(editText_nombre.getText().toString());
                            if (res.moveToNext()){
                                Adaptar(res.getString(3));
                            }
                        }
                    }
                }
        );
    }



}