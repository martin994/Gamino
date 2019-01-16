package ar.edu.utn.frsf.isi.dam.gamino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.gamino.Modelo.Interes;

public class ListaDeIntereses extends AppCompatActivity {

    private RecyclerView recyclerViewIntereses;
    private AdaptadorIntereses adaptadorIntereses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_intereses );

        recyclerViewIntereses=(RecyclerView) findViewById( R.id.InteresesRV);
        recyclerViewIntereses.setLayoutManager( new LinearLayoutManager( this ) );

        adaptadorIntereses= new AdaptadorIntereses( obtenerInteres() );
        recyclerViewIntereses.setAdapter( adaptadorIntereses );

    }


    public List<Interes> obtenerInteres(){

        List<Interes> intereses= new ArrayList<>();
        //Aca vamos a cargar los intereses desde la base de firebase

        return intereses;
    }
}
