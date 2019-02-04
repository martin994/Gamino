package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import android.graphics.Bitmap;
import android.net.Uri;

public class Interes {

    private String idInteres;
    private String nombreInteres;
    private String IconoInteres;//Verificar bien como traer FireBase las imagenes para saber como se setea
    private String descripcionInteres;

    public Interes() {
    }





    public String getDescripcionInteres() {
        return descripcionInteres;
    }

    public void setDescripcionInteres(String descripcionInteres) {
        this.descripcionInteres = descripcionInteres;
    }



    public String getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(String idInteres) {
        this.idInteres = idInteres;
    }

    public String getNombreInteres() {
        return nombreInteres;
    }

    public void setNombreInteres(String nombreInteres) {
        this.nombreInteres = nombreInteres;
    }

    public String getIconoInteres() {
        return IconoInteres;
    }

    public void setIconoInteres(String iconoInteres) {
        IconoInteres = iconoInteres;
    }
}
