package ar.edu.utn.frsf.isi.dam.gamino.Servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ar.edu.utn.frsf.isi.dam.gamino.ConfigurarPerfil;
import ar.edu.utn.frsf.isi.dam.gamino.MainActivity;
import ar.edu.utn.frsf.isi.dam.gamino.R;
import ar.edu.utn.frsf.isi.dam.gamino.VerPublicacion;

import static android.graphics.Color.rgb;

public class FCMNotificacion extends FirebaseMessagingService {


    private String titulo;
    private String cuerpo;
    BroadcastReceiver receptor= new MensajesReceiver();
    public FCMNotificacion() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken( s );
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );

        if (remoteMessage.getData()!=null){
            String idDePulicacion= remoteMessage.getData().get( "idP" );
            titulo=remoteMessage.getNotification().getTitle();
            cuerpo=remoteMessage.getNotification().getBody();

            enviarNotificacionComentario( idDePulicacion,titulo,cuerpo );

        }

    }

    private void enviarNotificacionComentario(String idPublicacion, String titulo, String cuerpo){

        Intent intent = new Intent(this, VerPublicacion.class);

        intent.putExtra( "Publicacion",idPublicacion );
        intent.putExtra( "Titulo",titulo );
        intent.putExtra( "Cuerpo",cuerpo );
        receptor.onReceive( this,intent );



    }
}
