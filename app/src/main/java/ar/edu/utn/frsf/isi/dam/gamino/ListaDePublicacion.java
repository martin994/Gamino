package ar.edu.utn.frsf.isi.dam.gamino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Publicacion;

public class ListaDePublicacion extends AppCompatActivity {

    private RecyclerView recyclerViewPublicacion;
    private AdaptadorPublicacion adaptadorPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_de_publicacion );
        recyclerViewPublicacion=findViewById( R.id.listadePublicacionRV );
        recyclerViewPublicacion.setLayoutManager( new LinearLayoutManager( this ) );
        adaptadorPublicacion=new AdaptadorPublicacion( obtenerPublicaiones() );
        recyclerViewPublicacion.setAdapter( adaptadorPublicacion );


    }

    public List<Publicacion> obtenerPublicaiones (){

        List<Publicacion> publicaciones= new ArrayList<>(  );

        return publicaciones;

    }
}
