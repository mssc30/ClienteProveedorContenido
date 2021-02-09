package net.mssc.clientecontentproviderusuarios.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class MiProviderContrato {
    //public static final String AUTHORITY = "net.socorrosamano.actividadesenandroid.provider"; //NO SE QUE VA AQUI
    //public static final Uri CONTENT_URI = null;

    public MiProviderContrato() {
        throw new RuntimeException("Stub!");
    }

    //ES UNA CLASE ESTATICA POR CADA ENTIDAD QUE MANEJE EL CONTENT PROVIDER
    public static class Usuarios implements BaseColumns {
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.net.socorrosamano.actividadesenandroid.provider.usuarios";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.net.socorrosamano.actividadesenandroid.provider.usuarios";
        public static final Uri CONTENT_URI = Uri.parse("content://net.socorrosamano.actividadesenandroid.provider/usuarios/");

        //DEFINIR LAS COLUMNAS
        public static final String _ID = "_id";
        public static final String NOMBRE = "nombre";
        public static final String EMAIL = "email";
        public static final String CONTRASENA = "password";
        public static final String TELEFONO = "telefono";
    }
}
