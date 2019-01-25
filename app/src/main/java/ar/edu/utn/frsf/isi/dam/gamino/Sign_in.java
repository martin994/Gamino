package ar.edu.utn.frsf.isi.dam.gamino;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

import static com.google.firebase.auth.FirebaseAuth.*;

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
    private Usuario nuevoUsuario;

    private DatabaseReference myRefDatabase;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth ;

    private AuthStateListener firebaseAuthListener;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

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
        inicializarFirebase();



        btnCrearUsuario.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correoElectonico=edtCorreoElectonico.getText().toString();
                contrasenia=edtContrasenia.getText().toString();
                nombreUsuario=edtNombreUsuario.getText().toString();
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
                if(TextUtils.isEmpty( nombreUsuario)){
                    Toast.makeText( getApplicationContext(),"Campo nombre usuario vacio",Toast.LENGTH_LONG ).show();
                }
                if (contrasenia.equals( confirmacionContrasenia )==false){
                    Toast.makeText( getApplicationContext(),"Las Contraseñas no coinciden",Toast.LENGTH_LONG ).show();

                }


                if (contrasenia.equals( confirmacionContrasenia )) {


                    registrarUsuario( correoElectonico, contrasenia, nombreUsuario );


                }




            }
        } );

        firebaseAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(Sign_in.this, ConfigurarPerfil.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

    }

    private void registrarUsuario(final String email, final String contrasenia, final String nombreUsuario){

        final ProgressDialog dialog= new ProgressDialog( this );

        dialog.setMessage( "Registrando Usuario......" );
        dialog.setCancelable( false );
        dialog.show();
        firebaseAuth.createUserWithEmailAndPassword( email,contrasenia ).addOnCompleteListener( Sign_in.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    nuevoUsuario= new Usuario();
                    nuevoUsuario.setIdUsuario( task.getResult().getUser().getUid());
                    nuevoUsuario.setMailUsuario( email );
                    nuevoUsuario.setContrsenia( contrasenia );
                    nuevoUsuario.setNombreusuario( nombreUsuario );
                    nuevoUsuario.setListaInteres( new ArrayList<Interes>(  ) );
                    nuevoUsuario.setListaDePublicaciones( new ArrayList<Publicacion>(  ) );
                    nuevoUsuario.setListaDePublicacionesPuntuadas( new ArrayList<Publicacion>(  ) );//sacar las listas
                    myRefDatabase.child( "Usuarios" ).child( nuevoUsuario.getIdUsuario() ).setValue( nuevoUsuario );
                    firebaseAuth.signInWithEmailAndPassword(email, email);
                    dialog.dismiss();



                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        dialog.dismiss();
                        limpiarCajasDetexto( edtCorreoElectonico,edtNombreUsuario ,edtContrasenia,edtConfirmacionContrasenia);
                        Toast.makeText( Sign_in.this, "El mail:" + email + "ya está en uso", Toast.LENGTH_LONG ).show();
                    }

                    if(task.getException() instanceof FirebaseAuthWeakPasswordException){
                        dialog.dismiss();
                        edtContrasenia.setText( "" );
                        edtConfirmacionContrasenia.setText( "" );
                        Toast.makeText( Sign_in.this, "La contraseña no es valida", Toast.LENGTH_LONG ).show();
                    }

                    else{
                        dialog.dismiss();
                        limpiarCajasDetexto( edtCorreoElectonico,edtNombreUsuario ,edtContrasenia,edtConfirmacionContrasenia);
                        Toast.makeText( Sign_in.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG ).show();
                    }

                }



            }
        }

        );

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRefDatabase= firebaseDatabase.getReference();
        firebaseAuth=getInstance();
    }

    private void limpiarCajasDetexto( EditText edtCorreoElectonico, EditText edtNombreUsuario, EditText edtContrasenia,EditText edtConfirmacionContrasenia){
         edtCorreoElectonico.setText( "" );
         edtNombreUsuario.setText( "" );
         edtContrasenia.setText( "" );
         edtConfirmacionContrasenia.setText( "" );

    }



}
