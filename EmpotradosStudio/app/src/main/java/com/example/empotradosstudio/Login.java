package com.example.empotradosstudio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editText_usuario, editText_password;
    Button boton_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editText_usuario = (EditText) findViewById(R.id.editText_usuario);
        editText_password = (EditText) findViewById(R.id.editText_password);
        boton_login = (Button) findViewById(R.id.boton_login);

        Login();
    }

    public void Login(){
        boton_login.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Cursor res = myDb.getUsername(editText_usuario.getText().toString());
                        String password;
                        if(res.moveToNext()) {
                            password = res.getString(2);
                            if(editText_password.getText().toString().equalsIgnoreCase(password)){
                                Intent intento = new Intent(Login.this, MapsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("usuario", editText_usuario.getText().toString());
                                intento.putExtras(bundle);
                                startActivity(intento);
                            } else {
                                Intent intento = new Intent(Login.this, MapsActivity.class);
                                startActivity(intento);
                                Toast.makeText(Login.this,"Usuario o contraseña inválidos", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Login.this,"Usuario o contraseña inválidos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}