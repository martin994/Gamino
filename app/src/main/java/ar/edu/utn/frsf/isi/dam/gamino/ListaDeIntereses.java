package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;

public class ListaDeIntereses extends AppCompatActivity {

    private RecyclerView recyclerViewIntereses;
    private AdaptadorIntereses adaptadorIntereses;
    private DatabaseReference firebaseDatabase;
    private DatabaseReference firebaseDatabaseChild;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_intereses );

        firebaseDatabase=FirebaseDatabase.getInstance().getReference();
        firebaseDatabaseChild=firebaseDatabase.child( "Intereses" );
        recyclerViewIntereses=(RecyclerView) findViewById( R.id.InteresesRV);
        recyclerViewIntereses.setLayoutManager( new LinearLayoutManager( this ) );

        cargarAdaptador();


    }



    public void cargarAdaptador(){


        firebaseDatabaseChild.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Interes> intereses= new ArrayList<>();
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


                adaptadorIntereses= new AdaptadorIntereses( intereses, getApplicationContext());

                adaptadorIntereses.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                } );

                recyclerViewIntereses.setAdapter( adaptadorIntereses );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }


}
