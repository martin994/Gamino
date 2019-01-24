package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;

public class CargarIntereses extends Activity {

    private EditText edtNombreInteres;
    private EditText edtDescripcionInteres;
    private ImageView imvInteres;
    private Button btnCargarFoto;
    private Button btnCargarInteres;

    private Uri filePath;
    private DatabaseReference firebaseDatabase;
    private StorageReference mStorageReference;

    private Interes nuevoInteres= new Interes();

    private final int PICK_PHOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cargar_intereses );

        firebaseDatabase=FirebaseDatabase.getInstance().getReference();
        mStorageReference=FirebaseStorage.getInstance().getReference();
        edtNombreInteres=findViewById( R.id.cargarInteresEDTNombre );
        edtDescripcionInteres= findViewById( R.id.cargarInteresEDTDescripcion );
        imvInteres=findViewById( R.id.cargarInteresImV );
        btnCargarFoto=findViewById( R.id.cargarInteresBTNImagen );
        btnCargarInteres= findViewById( R.id.cargarInteresBTNCargar );


        btnCargarFoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagenGaleria();
            }
        } );
        btnCargarInteres.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoInteres.setIdInteres( UUID.randomUUID().toString()  );
                nuevoInteres.setNombreInteres( edtNombreInteres.getText().toString() );
                nuevoInteres.setDescripcionInteres( edtDescripcionInteres.getText().toString() );

                cargarInteres( nuevoInteres, filePath );

                Intent intent1 = new Intent(CargarIntereses.this,ListaDeIntereses.class);
                startActivity(intent1);

            }
        } );

    }


    private void cargarImagenGaleria(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"),PICK_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() !=null){

            filePath = data.getData();


            try {
                Bitmap bitmapImagen = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imvInteres.setImageBitmap(bitmapImagen);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void cargarInteres(final Interes nuevoInteres, final Uri filePath){

        if(filePath!=null){
            final StorageReference fotoInteres= mStorageReference.child( "Foto Intereses" ).child( filePath.getLastPathSegment() );
            fotoInteres.putFile( filePath ).continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if(!task.isSuccessful()){
                        throw new Exception(  );
                    }
                    return fotoInteres.getDownloadUrl();
                }
            } ).addOnCompleteListener( new OnCompleteListener<Uri>() {// este método me dice si se pudo completar la operacion
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downLoadLink= task.getResult();
                    nuevoInteres.setIconoInteres(  downLoadLink.toString());
                    firebaseDatabase.child( "Intereses" ).child( nuevoInteres.getIdInteres() ).setValue( nuevoInteres );
                }
            } );

        }


    }


}
