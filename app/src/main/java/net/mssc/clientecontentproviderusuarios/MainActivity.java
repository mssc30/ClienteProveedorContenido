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
    Button btnInsert, btnUpdate, btnDelete, btnMIME;
    String id;

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
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnMIME = findViewById(R.id.btnMIME);

        cr = getContentResolver();
        cv = new ContentValues();

        metodoQuery();

        btnInsert.setOnClickListener(v -> {
            metodoInsert();
        });

        btnUpdate.setOnClickListener(v -> {
            metodoUpdate();
        });

        btnDelete.setOnClickListener(v -> {
            metodoDelete();
        });

        btnMIME.setOnClickListener(v -> {
            metodoMIME(Uri.parse(CONTENT_URI + id));
        });

    }

    private void metodoMIME(Uri uri) {
        //------------------CONSUMER CONTENT PROVIDER, METODO GET TYPE--------------------//
        String mime = cr.getType(uri);

        Toast.makeText(getApplicationContext(), mime, Toast.LENGTH_LONG).show();
    }

    public void metodoQuery() {
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
              id = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();

              //------------------CONSUMER CONTENT PROVIDER, METODO QUERY CASO 2--------------------//
              Cursor getUno = cr.query(
                      Uri.parse(CONTENT_URI + id),
                      null,
                      null,
                      null,
                      null
              );

              if (getUno.moveToNext()) {
                  txtNombre.setText(getUno.getString(1));
                  txtEmail.setText(getUno.getString(2));
                  txtPass.setText(getUno.getString(3));
                  txtTelefono.setText(getUno.getString(4));
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      }
        );

        spinner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                metodoMIME(CONTENT_URI);
                return false;
            }
        });

        spinner.setAdapter(simpleCursorAdapter);
    }

    public void metodoInsert() {
        cv.clear();

        //------------------CONSUMER CONTENT PROVIDER, METODO INSERT--------------------//
        cv.put(MiProviderContrato.Usuarios.NOMBRE, txtNombre.getText().toString());
        cv.put(MiProviderContrato.Usuarios.CONTRASENA, txtPass.getText().toString());
        cv.put(MiProviderContrato.Usuarios.EMAIL, txtEmail.getText().toString());
        cv.put(MiProviderContrato.Usuarios.TELEFONO, txtTelefono.getText().toString());

        Uri uri = cr.insert(CONTENT_URI, cv);

        if (!uri.toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Usuario agregado con exito", Toast.LENGTH_LONG).show();
            metodoQuery(); //actualizar spinner
        } else {
            Toast.makeText(getApplicationContext(), "No se pudo agregar el usuario", Toast.LENGTH_LONG).show();
        }

    }

    public void metodoUpdate() {
        cv.clear();

        //------------------CONSUMER CONTENT PROVIDER, METODO INSERT--------------------//
        cv.put(MiProviderContrato.Usuarios.NOMBRE, txtNombre.getText().toString());
        cv.put(MiProviderContrato.Usuarios.CONTRASENA, txtPass.getText().toString());
        cv.put(MiProviderContrato.Usuarios.EMAIL, txtEmail.getText().toString());
        cv.put(MiProviderContrato.Usuarios.TELEFONO, txtTelefono.getText().toString());

        Uri update = Uri.parse(CONTENT_URI + id);

        int ok = cr.update(update, cv, null, null);

        if (ok > 0) {
            Toast.makeText(getApplicationContext(), "Usuario modificado con exito", Toast.LENGTH_LONG).show();
            metodoQuery(); //actualizar spinner
        } else {
            Toast.makeText(getApplicationContext(), "No se edito el usuario " + id, Toast.LENGTH_LONG).show();
        }
    }

    public void metodoDelete() {
        Uri delete = Uri.parse(CONTENT_URI + id);

        int ok = cr.delete(delete, null, null);

        if (ok > 0) {
            Toast.makeText(getApplicationContext(), "Usuario elimina con exito", Toast.LENGTH_LONG).show();
            limpiarTextBox();
            metodoQuery(); //actualizar spinner
        } else {
            Toast.makeText(getApplicationContext(), "No se elimino el usuario " + id, Toast.LENGTH_LONG).show();
        }
    }

    public void limpiarTextBox() {
        txtNombre.setText("", null);
        txtPass.setText("", null);
        txtEmail.setText("", null);
        txtTelefono.setText("", null);
    }

}