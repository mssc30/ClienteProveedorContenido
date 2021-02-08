package net.mssc.clientecontentproviderusuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import net.mssc.clientecontentproviderusuarios.Provider.MiProviderContrato;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();

        ContentValues cv = new ContentValues();

        cv.put(MiProviderContrato.Usuarios.NOMBRE, "Jhony");
        cv.put(MiProviderContrato.Usuarios.CONTRASENA, "123");
        cv.put(MiProviderContrato.Usuarios.EMAIL, "jhony@google.com");
        cv.put(MiProviderContrato.Usuarios.TELEFONO, "445");

        Uri uri = cr.insert(MiProviderContrato.Usuarios.CONTENT_URI, cv);

        Cursor cursor = getContentResolver().query(
                MiProviderContrato.Usuarios.CONTENT_URI,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
           Log.d("MCP", cursor.getInt(0) + " - " + cursor.getString(2));
        }
    }
}