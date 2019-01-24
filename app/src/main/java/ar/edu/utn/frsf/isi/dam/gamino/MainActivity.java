package ar.edu.utn.frsf.isi.dam.gamino;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnSigIn;
    private Button btnLogIn;



    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogIn=findViewById( R.id.mainActivityLogInbtn );
        btnSigIn=findViewById( R.id.mainActivitySigInbtn );



        btnSigIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent( MainActivity.this,Sign_in.class );
                startActivity( i1 );
            }
        } );

        btnLogIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(),"Todavia no esta hecha",Toast.LENGTH_LONG ).show();
            }
        } );



    }


}
