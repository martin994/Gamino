package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;
import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Usuario;

public class ListaDePublicacion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerViewPublicacion;
    private AdaptadorPublicacion adaptadorPublicacion;
    private FloatingActionButton fAB_publicar;
    private TextView tV_usuario;
    private ImageView img_avatar;
    private DatabaseReference firebaseDatabase;
    private DatabaseReference firebaseDatabaseDrawer;
    private ArrayList<Publicacion> publicaciones;
    private ArrayList<Publicacion> publicacionesListadas;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MenuItem settings;
    private Usuario user;
    private Interes interes=null;
    private FirebaseUser mUser;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_de_publicacion );
        fAB_publicar=findViewById(R.id.lista_de_publicacion_fAB_publicar);
        //tV_usuario = (TextView) findViewById(R.id.header_tV_usuario);
        //img_avatar= (ImageView) findViewById(R.id.header_img_avatar) ;
        recyclerViewPublicacion=findViewById( R.id.listadePublicacionRV );
        recyclerViewPublicacion.setLayoutManager( new LinearLayoutManager( this ) );
        navigationView = (NavigationView) findViewById(R.id.lista_pub_nav_view);

        publicaciones=new ArrayList<Publicacion>();
        publicacionesListadas=new ArrayList<Publicacion>();
        drawerLayout= (DrawerLayout)findViewById(R.id.lista_de_publicacion_drawerLayOut);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.ListaPubAbrir,R.string.ListaPubCerrar);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabaseDrawer = FirebaseDatabase.getInstance().getReference();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_settings:
                        Intent i = new Intent(ListaDePublicacion.this, ConfigurarPerfil.class);
                        startActivity(i);
                        break;
                    case R.id.menu_logout:
                        FirebaseAuth.getInstance().signOut();
                        finishAffinity();
                        break;
                    case R.id.menu_exit:

                        finishAffinity();
                        break;
                        default:break;
                }

                return false;
            }
        });
        cargarNavigatorDrawer();
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
    private void cargarNavigatorDrawer(){
        View hView= navigationView.getHeaderView(0);
        tV_usuario=(TextView) hView.findViewById(R.id.header_tV_usuario);
        img_avatar=(ImageView) hView.findViewById(R.id.header_img_avatar);
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabaseDrawer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child("Usuarios").child(mUser.getUid()).getValue(Usuario.class);

                String ruta = dataSnapshot.child("Usuarios").child(mUser.getUid()).child("FotoPerfilUsuario").getValue(String.class);
                user.setFotoPerfilUsuario(Uri.parse(ruta));
                tV_usuario.setText("Usuario: "+ user.getNombreusuario());
                if(user.getFotoPerfilUsuario()!=null)
                    Glide.with(getApplicationContext()).load(user.getFotoPerfilUsuario()).addListener(new RequestListener<Drawable>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                            return false;
                        }
                    }).into(img_avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id==R.id.menu_settings) {
            Intent i = new Intent(ListaDePublicacion.this, ConfigurarPerfil.class);
            startActivity(i);
        }


        return false;
    }
}
