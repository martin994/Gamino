package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

public class AltaPublicacion extends AppCompatActivity {

    private EditText edt_titulo;
    private EditText edt_subtitulo;
    private EditText edt_cuerpo;
    private Spinner sp_intereses;
    private Button btn_alta;
    private Button btn_volver;
    private DatabaseReference firebaseDatabase;
    private DatabaseReference firebaseDatabaseChild;
    private StorageReference mStorageReference;
    private ArrayAdapter<String> adaptadorIntereses;
    private List<Interes> intereses;
    private Publicacion p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_publicacion);
        edt_titulo = (EditText) findViewById(R.id.alta_publicacion_edt_titulo);
        edt_subtitulo = (EditText) findViewById(R.id.alta_publicacion_edt_subtitulo);
        edt_cuerpo = (EditText) findViewById(R.id.alta_publicacion_edt_cuerpo);
        sp_intereses = (Spinner) findViewById(R.id.alta_publicacion_sp_intereses);
        btn_alta = (Button) findViewById(R.id.alta_publicacion_btn_alta);
        btn_volver = (Button) findViewById(R.id.alta_publicacion_btn_volver);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabaseChild = firebaseDatabase.child("Intereses");
        mStorageReference= FirebaseStorage.getInstance().getReference();
        cargarAdaptador();


        btn_alta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_titulo.getText().length() == 0) {
                    Toast.makeText(AltaPublicacion.this, "La publicación debe tener un título", Toast.LENGTH_SHORT).show();
                } else if (edt_subtitulo.getText().length() == 0) {
                    Toast.makeText(AltaPublicacion.this, "La publicación debe tener un asunto", Toast.LENGTH_SHORT).show();
                } else if (edt_cuerpo.getText().length() == 0) {
                    Toast.makeText(AltaPublicacion.this, "La publicación debe tener un cuerpo", Toast.LENGTH_SHORT).show();
                } else if (sp_intereses.getSelectedItem() == null) {
                    Toast.makeText(AltaPublicacion.this, "La publicación debe tener una categoría", Toast.LENGTH_SHORT).show();
                } else {
                    p = new Publicacion();
                    p.setIdPublicacion(UUID.randomUUID().toString());
                    p.setTituloPublicacion(edt_titulo.getText().toString());
                    p.setSubtituloPublicacion(edt_subtitulo.getText().toString());
                    p.setCuerpoPublicacion(edt_cuerpo.getText().toString());



                    for (Interes i : intereses) {

                            p.setidInteres(i.getIdInteres());

                    }
                    cargarPublicacion(p);
                    Intent i = new Intent(AltaPublicacion.this, VerPublicacion.class);
                    i.putExtra("Publicacion", p.getIdPublicacion());
                    startActivity(i);
                }

            }

        });
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AltaPublicacion.this, VerPublicacion.class);
                i.putExtra("Publicacion", p.getIdPublicacion());
                startActivity(i);
            }
        });


    }

    public void cargarAdaptador() {

        firebaseDatabaseChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                intereses = new ArrayList<>();
                List<String> nombres = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Interes i = snapshot.getValue(Interes.class);

                    String nombre, descripcion, path;

                    nombre = i.getNombreInteres();
                    nombres.add(nombre);
                    descripcion = i.getDescripcionInteres();
                    path = i.getIconoInteres();

                    i.setNombreInteres(nombre);
                    i.setDescripcionInteres(descripcion);
                    i.setIconoInteres(path);
                    intereses.add(i);


                }
                adaptadorIntereses = new ArrayAdapter<String>(AltaPublicacion.this, android.R.layout.simple_list_item_single_choice, nombres);


                sp_intereses.setAdapter(adaptadorIntereses);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cargarPublicacion(final Publicacion nuevaPublicacion){
        // queda ver como le inserto el id del interes
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        Usuario u = new Usuario();
        u.setIdUsuario(mUser.getUid());
        nuevaPublicacion.setEditor(u);
        firebaseDatabase.child("Publicaciones").child(nuevaPublicacion.getIdPublicacion()).setValue(nuevaPublicacion);



    }
}