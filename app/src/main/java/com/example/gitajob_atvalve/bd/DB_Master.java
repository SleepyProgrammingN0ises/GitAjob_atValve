package com.example.gitajob_atvalve.bd;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DB_Master extends SQLiteOpenHelper {

    //Todo 1. Extendemos la clase con SQLiteOpenHelper para tener acceso a los métodos que gestiona
    //todo-> la base de datos.

    //Database name
    private static final String DB_NAME = "GitAJob";

    //Table name
    private static final String DB_TABLE_NAME = "USERS";

    //Database version must be >= 1
    private static final int DB_VERSION = 1;

    //Columns
    private static final String USER_NAME_COLUMN = "CUSER";

    private static final String USER_PASSWORD_COLUMN = "CPASS";


    //Application Context
    private Context mContext;


    /**
     * Constructor de la base de datos, si no existe la base de datos la crea, sino se conecta.
     * En el caso de que se hiciese una actualización y se cambiase la versión,
     * el constructor llamaría al método onUpgrade para actualizar los cambios de la base de datos.
     *
     * @param context Contexto de la aplicación
     */
    public DB_Master(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;

    }

    //Todo 2. Sobrecargamos onCreate, encargado de crear las tablas asociadas a la base de datos.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " + DB_TABLE_NAME + "("
                + USER_NAME_COLUMN + " TEXT ," + USER_PASSWORD_COLUMN + " TEXT " + ")";

        //Todo 2.1. Lanzamos la consulta con execSQL
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);


        // Los mensajes LOG sirven para que el programación, durante el desarrollo pueda recibir mensajes
        // tecnicos sobre el funcionamiento del programa sin que el usuario las pueda ver.
        // Estos mensajes aparecen en la pestaña Logcat de Android studio.
        Log("Tablas creadas");

    }

    // Todo 3. Sobrecargamos onUpgrade, encargado de actualizar la base de datos y las tablas asociadas.

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        Log("onUpadre");
        Log("oldversion -> " + oldVersion);

        //Todo 3.1. En el caso de que hagamos algun cambio en la base de datos es necesario controlar
        //todo -> la versión donde se encuentra e ir modificando la base de datos desde la última
        //todo -> que tenga de la BD en adelante.
        switch (oldVersion) {
            case 1:
                sqLiteDatabase.execSQL("ALTER TABLE " + DB_TABLE_NAME + " ADD COLUMN " + USER_PASSWORD_COLUMN + " TEXT");
                Log.i("DB", "BBDD Actualizada a la versión 1");


        }


    }


    public int getVersionDB() {
        int version = this.getReadableDatabase().getVersion();
        return version;

    }

    //Todo 4. Creamos un método para insertar un dato en la BD.
    public long insertUsuarios(String name, String pass) {

        //Todo 4.1. Pedimos acceso de escritura en la base de datos.
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        // Contenedor clave,valor -> columna, valor de entrada registro
        ContentValues values = new ContentValues();

        values.put(USER_NAME_COLUMN, name);
        values.put(USER_PASSWORD_COLUMN, pass);
        Log("USUARIO CREADO");
        Toast.makeText(mContext, "USUARIO CREADO", Toast.LENGTH_LONG + 2).show();

        //Todo 4.2. Insertamos a través del método insert, cuyos parametro son:
        //todo -> nombre de la tabla
        //todo -> nullColumnHack permite indicar si hay una columna cuyo valor pueda ser nulo.
        //todo -> valores asociados a la inserción.

        result = db.insert(DB_TABLE_NAME, null, values);

        //Se cierra la conexión de la base de datos
        db.close();

        return result;

    }

    //Todo 5. Creamos un método para recuperar datos en la BD.
    public String getFirstCity() {

        String result = null;

        //Todo 5.1. Pedimos acceso de lectura de la BD.
        SQLiteDatabase db = this.getReadableDatabase();

        //Todo 5.2. Realizamos la consulta a través del método 'query', cuyo significado de los
        // todo-> parámetros tenemos en los apuntes. Este método devuelve un cursor que nos
        // todo-> permite recorrer las tuplas del resultado.
        String[] cols = new String[]{USER_NAME_COLUMN};

        //String selection = "city=?"; // -> el caracter interrogación será sustituido por los valores del array 'args' en orden de aparición
        //String[] args = new String[]{"jerez"};

        //Un cursor es un tipo de dato que se mueve entre los registros devueltos por una consulta de una base de datos.
        Cursor c = db.query(DB_TABLE_NAME, cols, null, null, null, null, null);

        if (c.moveToFirst()) {
            //Todo 5.4. Cogemos el valor referente a la posicion de la columna
            String city = c.getString(0);
            result = city;
        }

        //Todo 5.5. Cerramos el cursor
        if (c != null) {
            c.close();
        }

        //Todo 5.6. Cerramos la base de datos.
        db.close();

        return result;

    }


    //Todo 6. Ejemplo de acceso a base de datos con argumentos
    public ArrayList<String> getAllCities() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> resultCities = new ArrayList<>();

        String[] cols = new String[]{USER_NAME_COLUMN};

        //Un cursor es un tipo de dato que se mueve entre los registros devueltos por una consulta de una base de datos.
        Cursor c = db.query(DB_TABLE_NAME, cols, null, null, null, null, null);

        //Todo 6.1. Movemos el iterador al primer elemento (si existe devuelve true sino false)
        if (c.moveToFirst()) {

            do {
                //Todo 6.2. Cogemos el valor referente a la posicion de la columna
                String city = c.getString(0);

                resultCities.add(city);
            } while (c.moveToNext()); //Todo 6.3. Mientras exista siguientes registros el cursor va iterando sobre ellos
        }

        return resultCities;
    }


    public void Log(String msg) {
        Log.d("DB", msg);
    }


    public Boolean verifyUserData(String user, String password) {
        boolean estado;
        //comprueba primero si existe un registro con usuario.
        SQLiteDatabase db = this.getWritableDatabase();            //importante dejar espacio entre las comillas que si no da error
        Cursor cursorpass;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_NAME + " WHERE " + USER_NAME_COLUMN + "= ?", new String[]{user});
        if (cursor.getCount() > 0) {
            cursorpass = db.rawQuery("SELECT '" + user + "' FROM " + DB_TABLE_NAME + " WHERE " + USER_PASSWORD_COLUMN + " = ? ", new String[]{password});
            if (cursorpass.getCount() > 0) {
                System.out.println("el usuario  existe entra");
                estado = true;
                return estado;
            } else {
                Log.d("base", "en mi cabeza esto tiene sentido");
                estado = false;
                System.out.println("LA CONTRASEÑA ES INCORRECTA");
            }
        } else {
            System.out.println("no existe el usuario ");

            estado = false;
            return estado;
        }
        return null;
    }
}
