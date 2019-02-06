package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

public class VerPublicacion extends AppCompatActivity {
    private TextView tV_titulo;
    private TextView tV_subtitulo;
    private TextView tV_cuerpo;
    private Button btn_comentar;
    private DatabaseReference firebaseDatabase;
    private Button btn_volver;
    private Publicacion p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Declaraciones
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_ver_publicacion);
        tV_titulo=(TextView) findViewById(R.id.ver_publicacion_tV_titulo);
        tV_subtitulo=(TextView) findViewById(R.id.ver_publicacion_tV_subtitulo);
        tV_cuerpo=(TextView) findViewById(R.id.ver_publicacion_tV_cuerpo);
        btn_comentar=(Button) findViewById(R.id.ver_publicacion_btn_comentar);
        btn_volver=(Button) findViewById(R.id.ver_publicacion_btn_volver);
        final String idPublicacion = extras.getString("Publicacion");

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = firebaseDatabase.child("Publicaciones").child(idPublicacion);
        //se busca la publicacion para el id pasado como extra en el intent
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 p = dataSnapshot.getValue(Publicacion.class);
                tV_titulo.setText(p.getTituloPublicacion());
                tV_subtitulo.setText(p.getSubtituloPublicacion());
                tV_cuerpo.setText(p.getCuerpoPublicacion());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerPublicacion.this, TablaComentarios.class);

                i.putExtra("Publicacion",idPublicacion);
                i.putExtra("idEditor",p.getEditor().getIdUsuario());
                startActivity(i);
            }
        });
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerPublicacion.this, ConfigurarPerfil.class);
                startActivity(i);

            }
        });
    }
}
