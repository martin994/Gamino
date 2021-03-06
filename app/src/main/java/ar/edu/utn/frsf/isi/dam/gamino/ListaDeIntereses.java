package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;

public class ListaDeIntereses extends AppCompatActivity {

    private RecyclerView recyclerViewIntereses;
    private AdaptadorIntereses adaptadorIntereses;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseUsuario;
    private DatabaseReference firebaseDatabaseChild;

    private Button btnConfirmarIntereses;
    private Button btn_Omitir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_intereses );

        firebaseDatabase=FirebaseDatabase.getInstance().getReference();

        firebaseDatabaseUsuario=firebaseDatabase.child( "Usuarios" );
        firebaseDatabaseChild=firebaseDatabase.child( "Intereses" );
        recyclerViewIntereses=(RecyclerView) findViewById( R.id.InteresesRV);
        btnConfirmarIntereses=(Button)findViewById( R.id.InteresesBtnConfirmar );
        btn_Omitir=(Button) findViewById(R.id.InteresesBtnOmitir);
        recyclerViewIntereses.setLayoutManager( new LinearLayoutManager( this ) );
        firebaseAuth=FirebaseAuth.getInstance();
        cargarAdaptador();



        btnConfirmarIntereses.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaDeIntereses.this, ListaDePublicacion.class);
                startActivity(i);


            }
        } );
        btn_Omitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaDeIntereses.this ,ConfigurarPerfil.class);
                startActivity(i);
            }
        });



    }



    public void cargarAdaptador(){


        firebaseDatabaseChild.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Interes> intereses= new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Interes i= snapshot.getValue(Interes.class);

                        String nombre, descripcion, path;

                        nombre=i.getNombreInteres();
                        descripcion=i.getDescripcionInteres();
                        path=i.getIconoInteres();

                        i.setNombreInteres( nombre );
                        i.setDescripcionInteres( descripcion );
                        i.setIconoInteres( path );
                        intereses.add( i );



                }

                actualizarRecycler( intereses );


                adaptadorIntereses.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int i = recyclerViewIntereses.getChildAdapterPosition( v );
                        intereses.get( i ).getNombreInteres();







                        firebaseDatabaseUsuario.child( firebaseAuth.getCurrentUser().getUid() ).child( "Intereses Usuario" ).setValue(intereses.get( i )  );

                        Toast.makeText( ListaDeIntereses.this, "Se añadió "+intereses.get( i ).getNombreInteres()+"a sus intereses", Toast.LENGTH_LONG ).show();
                        intereses.remove( i );
                        adaptadorIntereses.notifyItemRemoved(i);

                    }
                } );




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }

    public void actualizarRecycler(List<Interes> intereses){
        adaptadorIntereses= new AdaptadorIntereses( intereses, getApplicationContext());
        recyclerViewIntereses.setAdapter( adaptadorIntereses );

    }





}
