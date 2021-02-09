package net.mssc.clientecontentproviderusuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import net.mssc.clientecontentproviderusuarios.Provider.MiProviderContrato;

public class MainActivity extends AppCompatActivity {

    ContentResolver cr;
    Uri CONTENT_URI = MiProviderContrato.Usuarios.CONTENT_URI;
    Spinner spinner;
    EditText txtNombre;
    EditText txtPass;
    EditText txtEmail;
    EditText txtTelefono;
    ContentValues cv;
    Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //------INICIALIZAR ELEMENTOS--------//
        spinner = findViewById(R.id.spinnerP);
        txtNombre = findViewById(R.id.txtNombreUser);
        txtPass = findViewById(R.id.txtPassUser);
        txtEmail = findViewById(R.id.txtEmailUser);
        txtTelefono = findViewById(R.id.txtTelefonoUser);
        btnInsert = findViewById(R.id.btnInsert);

        cr = getContentResolver();
        cv = new ContentValues();

        //------------------CONSUMER CONTENT PROVIDER, METODO QUERY CASO 1--------------------//
        cr = getContentResolver();
        Cursor c = cr.query(
                CONTENT_URI,
                null,
                null,
                null,
                null);


        //MOSTRAR USUARIOS SPINNER
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this, //contexto
                android.R.layout.simple_list_item_2, //layout con una lista de 2 item
                c, //cursor que tiene los datos
                new String[]{"_id", "nombre"}, //columnas que se quieren recuperar del cursor
                new int[]{android.R.id.text1, android.R.id.text2}, //ids de controles a donde se van a poner los elementos
                SimpleCursorAdapter.NO_SELECTION //comportamiento para los controles
        );

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  String id = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();

                  //-------------------------------CONSUMIR CONTENT PROVIDER METODO QUERY CASO 2---------------------//
                  Cursor getUno = cr.query(
                          Uri.parse(CONTENT_URI+ id),
                          null,
                          null,
                          null,
                          null
                  );

                  String datos = "";
                  if(getUno.moveToNext()) {
                      datos = getUno.getInt(0) + " - " + getUno.getString(1) + " - " + getUno.getString(2);
                  }

                  if(!datos.equals(""))
                      Snackbar.make(view, datos, BaseTransientBottomBar.LENGTH_INDEFINITE).show();
              }

              @Override
              public void onNothingSelected(AdapterView<?> adapterView) {

              }
          }
        );

        spinner.setAdapter(simpleCursorAdapter);

        btnInsert.setOnClickListener(v->{
            //------------------CONSUMER CONTENT PROVIDER, METODO INSERT--------------------//
            cv.put(MiProviderContrato.Usuarios.NOMBRE, txtNombre.getText().toString());
            cv.put(MiProviderContrato.Usuarios.CONTRASENA, txtPass.getText().toString());
            cv.put(MiProviderContrato.Usuarios.EMAIL, txtEmail.getText().toString());
            cv.put(MiProviderContrato.Usuarios.TELEFONO, txtTelefono.getText().toString());

            Uri uri = cr.insert(CONTENT_URI, cv);
            Toast.makeText(getApplicationContext(), "Usuario agregado con exito", Toast.LENGTH_LONG).show();
        });

    }
}