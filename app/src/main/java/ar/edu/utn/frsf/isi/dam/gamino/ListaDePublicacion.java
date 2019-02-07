package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

public class ListaDePublicacion extends AppCompatActivity {

    private RecyclerView recyclerViewPublicacion;
    private AdaptadorPublicacion adaptadorPublicacion;
    private FloatingActionButton fAB_publicar;
    private DatabaseReference firebaseDatabase;
    private ArrayList<Publicacion> publicaciones;
    private ArrayList<Publicacion> publicacionesListadas;
    private Interes interes=null;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_de_publicacion );
        fAB_publicar=findViewById(R.id.lista_de_publicacion_fAB_publicar);
        recyclerViewPublicacion=findViewById( R.id.listadePublicacionRV );
        recyclerViewPublicacion.setLayoutManager( new LinearLayoutManager( this ) );


        publicaciones=new ArrayList<Publicacion>();
        publicacionesListadas=new ArrayList<Publicacion>();

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        cargarLVPublicaciones();
        fAB_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaDePublicacion.this, AltaPublicacion.class);
                startActivity(i);
            }
        });
    }

    public void cargarLVPublicaciones(){

        final List<Publicacion> publicaciones= new ArrayList<Publicacion>(  );

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                DataSnapshot usuarioInteres=dataSnapshot.child("Usuarios").child(mUser.getUid()).child("Intereses Usuario");

                interes=usuarioInteres.getValue(Interes.class);




                for(DataSnapshot d: dataSnapshot.child("Publicaciones").getChildren()){
                    Publicacion p =d.getValue(Publicacion.class);
                    Usuario u = dataSnapshot.child("Usuarios").child(p.getEditor().getIdUsuario()).getValue(Usuario.class);

                    p.setEditor(u);
                    publicaciones.add(p);

                }

                if(interes==null){
                    Collections.sort(publicaciones, new Comparator<Publicacion>() {
                        @Override
                        public int compare(Publicacion o1, Publicacion o2) {
                            return Double.compare(o1.getTimeInMillis(),o2.getTimeInMillis());
                        }
                    });
                    publicacionesListadas.addAll(publicaciones);
                    adaptadorPublicacion=new AdaptadorPublicacion( publicacionesListadas, interes, getApplicationContext());
                }else{

                        for(Publicacion p : publicaciones){
                            if(p.getidInteres().equals(interes.getIdInteres())){

                                publicacionesListadas.add(p);
                            }

                    }
                    Collections.sort(publicacionesListadas, new Comparator<Publicacion>() {
                        @Override
                        public int compare(Publicacion o1, Publicacion o2) {
                            return Double.compare(o1.getTimeInMillis(),o2.getTimeInMillis());
                        }
                    });
                    adaptadorPublicacion=new AdaptadorPublicacion(publicacionesListadas,interes, getApplicationContext() );
                    adaptadorPublicacion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int posicion = recyclerViewPublicacion.getChildAdapterPosition( v );


                            Intent i = new Intent(ListaDePublicacion.this, VerPublicacion.class);
                            i.putExtra("Publicacion",publicacionesListadas.get( posicion ).getIdPublicacion());
                            startActivity(i);
                        }
                    });
                    recyclerViewPublicacion.setAdapter( adaptadorPublicacion );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}
