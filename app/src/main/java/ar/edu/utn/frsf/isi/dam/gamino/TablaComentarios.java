package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Comentario;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

public class TablaComentarios extends AppCompatActivity {
    private DatabaseReference firebaseDatabase;
    private DatabaseReference firebaseDatabasePublicar;
    private DatabaseReference firebaseDatabaseUsuario;
    private ListView lV_comentarios;
    private EditText edt_comentar;
    private Button btn_comentar;
    private Publicacion publicacion;
    private Button btn_volver;
    private String tokenMensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializacion de variables
        setContentView(R.layout.activity_tabla_comentarios);
        final Bundle extras = getIntent().getExtras();
        lV_comentarios= (ListView) findViewById(R.id.tabla_comentarios_lV_comentarios);
        edt_comentar = (EditText) findViewById(R.id.tabla_comentarios_edt_comentar);
        btn_comentar = (Button) findViewById(R.id.tabla_comentarios_btn_comentar);
        btn_volver=(Button) findViewById(R.id.tabla_comentarios_btn_volver);

        final String idPublicacion = extras.getString("Publicacion");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabasePublicar = FirebaseDatabase.getInstance().getReference();
        firebaseDatabaseUsuario = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = firebaseDatabase;
        firebaseDatabaseUsuario = firebaseDatabaseUsuario.child("Usuarios");
        publicacion = new Publicacion();
        publicacion.setIdPublicacion(idPublicacion);

        //cargado de tabla de comentarios
        cargarListView(publicacion.getIdPublicacion(),extras.getString( "idEditor" ));

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se genera el comentario con el usuario loggueado
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                Comentario nuevo = new Comentario();
                nuevo.setIdEditor(extras.getString("idEditor"));
                nuevo.setCuerpoComentario(edt_comentar.getText().toString());
                nuevo.setIdComentario(UUID.randomUUID().toString());
                nuevo.setFechaComentario((double) System.currentTimeMillis());
                nuevo.setUsuario(mUser.getUid());
                nuevo.setTokenComentario( tokenMensaje );
                firebaseDatabase.child( "Comentarios" ).child( nuevo.getIdComentario() ).setValue( nuevo );
                firebaseDatabasePublicar.child("Publicaciones").child(publicacion.getIdPublicacion()).child("Comentarios").child(nuevo.getIdComentario()).setValue(nuevo);

                cargarListView(publicacion.getIdPublicacion(),extras.getString( "idEditor" ));
                edt_comentar.setText("");

            }
        });
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TablaComentarios.this, VerPublicacion.class);
                i.putExtra("Publicacion", publicacion.getIdPublicacion());
                startActivity(i);

            }
        });

    }

    private void cargarListView(final String idPublicacion, final String idEditor){
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            //se verifica cada comentario y se busca el nombre de su respectivo usuario
            //NOTA actualmente no ordena por fecha,falta agregar
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                final ArrayList<Comentario> listaDeComentarios = new ArrayList<Comentario>();
                TreeMap<Double,String> comentario=new TreeMap<Double,String>();
                tokenMensaje =  dataSnapshot.child( "Usuarios" ).child( idEditor ).child( "idMensajeUsuario").getValue(String.class);
                for (DataSnapshot snapshot : dataSnapshot.child("Publicaciones").child(publicacion.getIdPublicacion()).child("Comentarios").getChildren()) {



                        Comentario c = snapshot.getValue(Comentario.class);
                        Usuario u = dataSnapshot.child("Usuarios").child(c.getUsuario()).getValue(Usuario.class);
                        comentario.put(c.getFechaComentario(),u.getNombreusuario() + ": " + c.getCuerpoComentario());
                        listaDeComentarios.add(c);




                }



                //se setea el adaptador, el metodo del medio es para settear color negro, ya que se veía blanco

                for(Comentario c:listaDeComentarios){
                    publicacion.addComentario(c);
                }
                ArrayAdapter adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<String>(comentario.values())){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        // Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        // Initialize a TextView for ListView each Item
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text color of TextView (ListView Item)
                        tv.setTextColor(Color.BLACK);

                        // Generate ListView Item using TextView
                        return view;
                    }
                };

                lV_comentarios.setAdapter(adaptador);
                lV_comentarios.setSelection(adaptador.getCount() - 1);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
