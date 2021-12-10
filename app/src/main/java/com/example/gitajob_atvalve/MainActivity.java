package com.example.gitajob_atvalve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gitajob_atvalve.bd.DB_Master;

public class MainActivity extends AppCompatActivity {


            DB_Master bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bd = new DB_Master(this);
        bd.getVersionDB();


        long result =  bd.insertUsuarios("aeee","aaaa");
        if(result == -1){Toast.makeText(this,"no se ha hecho el insert",Toast.LENGTH_SHORT).show();}
        bd.getDatabaseName();
        //metodo para insertar la imagenes de la app
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(MainActivity.this)
                //esto es una biblioteca donde subir icones svg
                .load("https://cdn4.iconfinder.com/data/icons/bubble-gradient-social-media-1/200/steam-512.png").centerCrop()
                .placeholder(android.R.drawable.ic_menu_edit)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);

        ImageView stack = (ImageView)findViewById(R.id.stack);
            Glide.with(MainActivity.this)
                    .load("https://cdn1.iconfinder.com/data/icons/apps-8/64/stack-overflow-stackoverflow-apps-platform-512.png")
                    .placeholder(android.R.drawable.ic_delete)
                    .into(stack);
       // https://cdn0.iconfinder.com/data/icons/social-media-flags/398/Flag_Social_Media_Icons-29-256.png

                //mirar como encagar imagenes vectoriales https://svgshare.com/i/cgd.svg

        String ciudad = bd.getFirstCity();
        Toast.makeText(this, "la ciudad es+"+ciudad, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, bd.getDatabaseName(), Toast.LENGTH_SHORT).show();


    }



}