package ar.edu.utn.frsf.isi.dam.gamino;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

public class ConfigurarPerfil extends AppCompatActivity {

    private static final int PICK_PHOTO = 1;

    //componentes de interfaz

    private Button btn_Intereses;
    private Button btn_Capturar_Foto;
    private Button btn_Cargar_Foto;
    private Button btn_Omitir;
    private ImageView img_Avatar;
    private TextView tV_Usuario;
    private Bitmap imageBitmap = null;
    private ProgressBar progressBar;
    private StorageReference mStorageReference;
    private FirebaseUser mUser;
    private Usuario actual;



    private final int REQUEST_IMAGE_SAVE=2;
    private String pathFoto;

    private Boolean tieneFoto=false;
    private DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_perfil);
        firebaseDatabase= FirebaseDatabase.getInstance().getReference();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        btn_Intereses= (Button) findViewById(R.id.Configurar_Perfil_btn_Intereses);
        btn_Capturar_Foto= (Button) findViewById(R.id.Configurar_Perfil_btn_Capturar_Foto);
        btn_Cargar_Foto= (Button) findViewById(R.id.Configurar_Perfil_btn_Cargar_Foto);
        btn_Omitir= (Button) findViewById(R.id.Configurar_Perfil_btn_Omitir);
        img_Avatar= (ImageView) findViewById(R.id.Configurar_Perfil_img_Avatar);
        tV_Usuario= (TextView) findViewById(R.id.Configurar_Perfil_tV_Usuario);
        img_Avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        cargarUsuarioActual();

//        BitmapDrawable drawable = (BitmapDrawable) img_Avatar.getDrawable();
//        Bitmap bitmap = Bitmap.createScaledBitmap(drawable.getBitmap() , img_Avatar.getWidth(), img_Avatar.getHeight(), true);
//        img_Avatar.setImageBitmap(bitmap);




        /*btn_Capturar_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ConfigurarPerfil.this,
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( ConfigurarPerfil.this, new String[]{Manifest.permission.CAMERA}, 1);

                    } else {
                        sacarGuardarFoto();

                    }
                }
            }

        });*/
        btn_Cargar_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagenGaleria();
            }
        });

        btn_Omitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ConfigurarPerfil.this, ListaDePublicacion.class);
                startActivity(i);
            }
        });
        btn_Intereses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfigurarPerfil.this, ListaDeIntereses.class);
                startActivity(i);
            }
        });


    }

    private File createImageFile()throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir( Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        pathFoto = image.getAbsolutePath();


        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){



        if (requestCode == REQUEST_IMAGE_SAVE && resultCode == RESULT_OK) {
            File file = new File(pathFoto);
          /*  Bundle extras= data.getExtras();
            Bitmap imageBitmap= (Bitmap) extras.get("data");
            img_Avatar.setImageBitmap(imageBitmap);*/
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                if (imageBitmap != null) {
                    Bitmap b = Bitmap.createScaledBitmap(imageBitmap , img_Avatar.getWidth(), img_Avatar.getHeight(), true);
                    //imageBitmap.setHeight(img_Avatar.getHeight());
//                    imageBitmap.setWidth(img_Avatar.getWidth());
                    //img_Avatar.setScaleType(ImageView.ScaleType.FIT_XY);
                    cargarFotoUsuario(Uri.fromFile(file));
                    img_Avatar.setImageBitmap(b);
                    tieneFoto=true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(requestCode == PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() !=null){

            Uri filePath = data.getData();



            try {
                Bitmap bitmapImagen = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                Bitmap b = Bitmap.createScaledBitmap(bitmapImagen , img_Avatar.getWidth(), img_Avatar.getHeight(), true);
                cargarFotoUsuario(filePath);
                img_Avatar.setImageBitmap(b);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private void cargarImagenGaleria(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"),PICK_PHOTO);

    }



    private void sacarGuardarFoto(){
        Intent sacarFoto= new Intent( MediaStore.ACTION_IMAGE_CAPTURE);

        if(sacarFoto.resolveActivity( getPackageManager() )!=null){
            File archivoFoto=null;
            try {
                archivoFoto=createImageFile();

            }catch(IOException e){
                e.printStackTrace();
            }

            if ( archivoFoto!= null) {
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "ar.edu.utn.frsf.isi.dam.gamino.fileprovider",
                        archivoFoto);


                sacarFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(sacarFoto, REQUEST_IMAGE_SAVE);

            }


        }

    }
    private void cargarFotoUsuario( final Uri filePath){
        final StorageReference fotoUsuario= mStorageReference.child( "Foto Usuarios" ).child( filePath.getLastPathSegment() );
        fotoUsuario.putFile(filePath).continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if(!task.isSuccessful()){
                    throw new Exception(  );
                }
                return fotoUsuario.getDownloadUrl();
            }
        } ).addOnCompleteListener( new OnCompleteListener<Uri>() {// este método me dice si se pudo completar la operacion
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri downLoadLink= task.getResult();
                //Map<String, Uri> direccionFoto = new HashMap<>();
                //direccionFoto.put("FotoPerfilUsuario", downLoadLink);
                firebaseDatabase.child( "Usuarios" ).child(mUser.getUid()).child("FotoPerfilUsuario").setValue(downLoadLink.toString());
            }
        } );

    }
    private void cargarUsuarioActual(){
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference firebaseDatabaseChild= firebaseDatabase.child("Usuarios").child(mUser.getUid());
        firebaseDatabaseChild.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario u = dataSnapshot.getValue(Usuario.class);
                actual= u;
                tV_Usuario.setText("Usuario: "+actual.getNombreusuario());



           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        DatabaseReference firebaseDatabaseChildRuta=firebaseDatabaseChild.child("FotoPerfilUsuario");
        firebaseDatabaseChildRuta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ruta= dataSnapshot.getValue(String.class);

                if(ruta!=null){
                    Glide.with(getApplicationContext()).load(Uri.parse(ruta)).addListener( new RequestListener<Drawable>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                            return false;
                        }
                    }).into(img_Avatar);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
