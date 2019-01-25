package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Log_in extends AppCompatActivity {

    private String correUsuario;
    private String contraseñaUsuario;

    private EditText edtCorreoElectronico;
    private EditText edtContraseña;
    private Button btnLoig;
    private Button btnSign;
    private DatabaseReference myRefDatabase;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        inicializarFirebase();
        edtCorreoElectronico=findViewById( R.id.loginETUsuario );
        edtContraseña=findViewById( R.id.loginETContraseña );
        btnSign=findViewById( R.id.loginBTNRegistrarse );
        btnLoig=findViewById( R.id.loginBTNIngresar );

        btnSign.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent( Log_in.this,Sign_in.class );
                startActivity( i1 );
            }
        } );

        btnLoig.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correUsuario=edtCorreoElectronico.getText().toString();
                contraseñaUsuario=edtContraseña.getText().toString();
                ingresoUsuario( correUsuario,contraseñaUsuario);


            }
        } );

        firebaseAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(Log_in.this, ListaDeIntereses.class);
                    startActivity(intent);
                }

            }
        };
    }

    private void ingresoUsuario(String correoUsuario, String passUsuario){
        firebaseAuth.signInWithEmailAndPassword( correoUsuario, passUsuario ).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText( Log_in.this, "No se pudo acceder a la cuenta verifique correo y contraseña", Toast.LENGTH_LONG ).show();

                }
                else {
                    Intent intent1 = new Intent(Log_in.this, CargarIntereses.class);
                    startActivity(intent1);
                }


            }
        }

        );

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRefDatabase= firebaseDatabase.getReference();
        firebaseAuth=FirebaseAuth.getInstance();
    }


}
