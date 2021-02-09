package net.mssc.clientecontentproviderusuarios;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.mssc.clientecontentproviderusuarios.Provider.MiProviderContrato;

public class AgregarActivity extends AppCompatActivity {

    Uri CONTENT_URI = MiProviderContrato.Usuarios.CONTENT_URI;
    EditText txtNombre;
    EditText txtPass;
    EditText txtEmail;
    EditText txtTelefono;
    Button btnRegistrar;
    Button btnCancelar;
    ContentResolver cr;
    ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_usuario);

        //elementos gui
        txtNombre = findViewById(R.id.txtNombreUser);
        txtPass = findViewById(R.id.txtPassUser);
        txtEmail = findViewById(R.id.txtEmailUser);
        txtTelefono = findViewById(R.id.txtTelefonoUser);
        btnRegistrar = findViewById(R.id.btnGuardarUser);
        btnCancelar = findViewById(R.id.btnCancelUser);

        cr = getContentResolver();
        cv = new ContentValues();

        btnRegistrar.setOnClickListener(v->{
            cv.put(MiProviderContrato.Usuarios.NOMBRE, txtNombre.getText().toString());
            cv.put(MiProviderContrato.Usuarios.CONTRASENA, txtPass.getText().toString());
            cv.put(MiProviderContrato.Usuarios.EMAIL, txtEmail.getText().toString());
            cv.put(MiProviderContrato.Usuarios.TELEFONO, txtTelefono.getText().toString());

            Uri uri = cr.insert(CONTENT_URI, cv);
            Toast.makeText(getApplicationContext(), "Usuario agregado con exito", Toast.LENGTH_LONG).show();
            finish();
        });

        btnCancelar.setOnClickListener(v->{
            finish();
        });

    }
}
