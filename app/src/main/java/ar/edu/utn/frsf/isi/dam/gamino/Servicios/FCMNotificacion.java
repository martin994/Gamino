package ar.edu.utn.frsf.isi.dam.gamino.Servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
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


    public FCMNotificacion() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken( s );
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );

        String idDePulicacion= remoteMessage.getData().get( "idP" );

        Intent intent = new Intent(this, VerPublicacion.class).putExtra( "Publicacion",idDePulicacion );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder =
                new Notification.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.logoaplicacion)
                .setColor(rgb(255,160,0))
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentInfo("info");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("id",
                    "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(notificationChannel );
            }

            notificationBuilder.setChannelId( "id" );
        }
        if(notificationManager!=null) {
            notificationManager.notify( "id",0, notificationBuilder.build());

        }



    }
}
