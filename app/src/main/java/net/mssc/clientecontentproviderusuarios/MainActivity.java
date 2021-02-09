package net.mssc.clientecontentproviderusuarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CONSUMER CONTENT PROVIDER, METODO QUERY CASO 1
        cr = getContentResolver();
        Cursor c = cr.query(
                CONTENT_URI,
                null,
                null,
                null,
                null);

        //mostrar los usuarios en el CP
        //adaptador
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this, //contexto
                android.R.layout.simple_list_item_2, //layout con una lista de 2 item
                c, //cursor que tiene los datos
                new String[]{"_id", "nombre"}, //columnas que se quieren recuperar del cursor
                new int[]{android.R.id.text1, android.R.id.text2}, //ids de controles a donde se van a poner los elementos
                SimpleCursorAdapter.NO_SELECTION //comportamiento para los controles
        );
        //settear adaptador a spinner
        spinner = findViewById(R.id.spinnerP);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                  String id = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();

                  //CONSUMIR CONTENT PROVIDER METODO QUERY CASO 2
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

        btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"entro", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), AgregarActivity.class);
            startActivity(intent);
        });
    }
}