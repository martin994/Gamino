package ar.edu.utn.frsf.isi.dam.gamino;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_in extends AppCompatActivity {

    private String correoElectonico;
    private String nombreUsuario;//todavia no lo uso
    private String contrasenia;
    private String confirmacionContrasenia;

    private EditText edtCorreoElectonico;
    private EditText edtNombreUsuario;
    private EditText edtContrasenia;
    private EditText edtConfirmacionContrasenia;
    private Button  btnCrearUsuario;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Window w = getWindow();
        w.setTitle("Sign in");

        edtCorreoElectonico=findViewById(R.id.signETCorreo  );
        edtNombreUsuario=findViewById( R.id.signETUsuario );
        edtContrasenia=findViewById( R.id.signETContraseña );
        edtConfirmacionContrasenia=findViewById( R.id.signETConfirmarContraseña );
        btnCrearUsuario=findViewById( R.id.signBTNRegistrar);
        firebaseAuth=FirebaseAuth.getInstance();


        btnCrearUsuario.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correoElectonico=edtCorreoElectonico.getText().toString();
                contrasenia=edtContrasenia.getText().toString();
                confirmacionContrasenia=edtConfirmacionContrasenia.getText().toString();

                if(TextUtils.isEmpty( correoElectonico )){
                    Toast.makeText( getApplicationContext(),"Campo correo electronico vacio",Toast.LENGTH_LONG ).show();
                }

                if (TextUtils.isEmpty( contrasenia )){
                    Toast.makeText( getApplicationContext(),"Campo contraseña vacio",Toast.LENGTH_LONG ).show();

                }

                if(TextUtils.isEmpty( confirmacionContrasenia )){
                    Toast.makeText( getApplicationContext(),"Campo confirmar contraseña vacio",Toast.LENGTH_LONG ).show();

                }
                if (contrasenia.equals( confirmacionContrasenia )==false){
                    Toast.makeText( getApplicationContext(),"Las Contraseñas no coinciden",Toast.LENGTH_LONG ).show();

                }


                if (contrasenia.equals( confirmacionContrasenia )) {
                    registrarUsuario( correoElectonico, contrasenia );



                }




            }
        } );

    }

    private void registrarUsuario(final String email, String contrasenia){

        firebaseAuth.getInstance().createUserWithEmailAndPassword( email,contrasenia );

    }





}
