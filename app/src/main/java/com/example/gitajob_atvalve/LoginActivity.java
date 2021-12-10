package com.example.gitajob_atvalve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gitajob_atvalve.bd.DB_Master;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText user;
    EditText password;
    DB_Master bd;
    Button bLogin;
    Button bSignUp;
    int bdV;


    @Override
    public void onClick(View v) {
        // do something when the button is clicked
        // Yes we will handle click here but which button clicked??? We don't know
        // So we will make
        switch (v.getId()/*to get clicked view id**/) {
            case R.id.bLogin:


                break;
            case R.id.bRegistrar:
                    String texto1 = user.getText().toString(); String texto2 = user.getText().toString();
                if ((texto1 == "") || (texto2 =="")) {
                    Toast.makeText(this, "Has de rellenar todos los campos1", Toast.LENGTH_LONG + 2).show();
                    Toast.makeText(this, "Has de rellenar todos los campos2", Toast.LENGTH_LONG + 2).show();
                    Toast.makeText(this, "Has de rellenar todos los campos3", Toast.LENGTH_LONG + 2).show();
                    Toast.makeText(this, "Has de rellenar todos los campos4", Toast.LENGTH_LONG + 2).show();

                    Toast.makeText(this, "Has de rellenar todos los campos5", Toast.LENGTH_LONG + 2).show();
                    user.setText("");
                    password.setText("");
                } else {
                    bd.insertUsuarios(user.getText().toString(), password.getText().toString());
                    Toast.makeText(this, "bd", Toast.LENGTH_LONG + 2).show();

                }


                break;

            default:
                break;
        }
    }

    public void cargarImagenes() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(LoginActivity.this)
                //esto es una biblioteca donde subir icones svg
                .load("https://cdn4.iconfinder.com/data/icons/bubble-gradient-social-media-1/200/steam-512.png").centerCrop()
                .placeholder(android.R.drawable.ic_menu_edit)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);

        ImageView stack = (ImageView) findViewById(R.id.stack);
        Glide.with(LoginActivity.this)
                .load("https://cdn1.iconfinder.com/data/icons/apps-8/64/stack-overflow-stackoverflow-apps-platform-512.png")
                .placeholder(android.R.drawable.ic_delete)
                .into(stack);
        // https://cdn0.iconfinder.com/data/icons/social-media-flags/398/Flag_Social_Media_Icons-29-256.png


    }

    public void initComponents(EditText user, EditText password, DB_Master bd, Button bLogin, Button bSignUp) {
        this.user = (EditText) findViewById(R.id.txtUsuario);
        this.password = (EditText) findViewById(R.id.txtContrasenia);
        this.bLogin = (Button)findViewById(R.id.bLogin);
        this.bSignUp = (Button) findViewById(R.id.bRegistrar);


    }


    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(null);
        setContentView(R.layout.activity_login);
        initComponents(user, password, bd, bLogin, bSignUp);
        cargarImagenes();
        bd = new DB_Master(this);



        //creo mi base de datos


        bdV = bd.getVersionDB(); //paso mi numero de version;


        //le doy funcionalidad a los botones


        bSignUp.setOnClickListener(this::onClick);
        //un array inicializador de botones, que guay
        // for (Button button : Arrays.asList(bLogin, bSignUp)) {
        //            button.setOnClickListener(this);
        //        }
    }
}







