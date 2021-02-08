package net.mssc.clientecontentproviderusuarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
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

        Uri CONTENT_URI = MiProviderContrato.Usuarios.CONTENT_URI;

        cv.put(MiProviderContrato.Usuarios.NOMBRE, "Koko");
        cv.put(MiProviderContrato.Usuarios.CONTRASENA, "123");
        cv.put(MiProviderContrato.Usuarios.EMAIL, "kokoy@google.com");
        cv.put(MiProviderContrato.Usuarios.TELEFONO, "445");

        Uri uri = cr.insert(CONTENT_URI, cv);

        Cursor c = cr.query(
                CONTENT_URI,
                null,
                null,
                null,
                null);

        while (c.moveToNext()) {
            Log.d("MCP", c.getInt(1) + " - " + c.getString(2));
        }

    }
}