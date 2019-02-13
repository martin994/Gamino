package ar.edu.utn.frsf.isi.dam.gamino.Servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import ar.edu.utn.frsf.isi.dam.gamino.R;

import static android.graphics.Color.rgb;

public class MensajesReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {

       String idPublicacion=intent.getStringExtra( "Publicacion" );
       String titulo=intent.getStringExtra( "Titulo" );
       String cuerpo=intent.getStringExtra( "Cuerpo" );

        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notification=null;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder notificationBuilder =
                new Notification.Builder(context);
        notificationBuilder
                .setSmallIcon(R.drawable.logoaplicacion)
                .setColor(rgb(255,160,0))
                .setContentTitle(titulo)
                .setContentText(cuerpo)
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
            notificationManager.notify( "id", 0, notificationBuilder.build() );


        }
    }
}
