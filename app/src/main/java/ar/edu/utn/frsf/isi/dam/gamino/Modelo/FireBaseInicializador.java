package ar.edu.utn.frsf.isi.dam.gamino.Modelo;
import com.google.firebase.database.FirebaseDatabase;
public class FireBaseInicializador extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled( true );


    }


}
