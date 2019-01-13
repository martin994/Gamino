package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import android.graphics.Bitmap;
import android.media.Image;

public class Interes {

    private int idInteres;
    private String nombreInteres;
    private Bitmap IconoInteres;//Verificar bien como traer FireBase las imagenes para saber como se setea
    private String descripcionInteres;

    public Interes() {
    }





    public String getDescripcionInteres() {
        return descripcionInteres;
    }

    public void setDescripcionInteres(String descripcionInteres) {
        this.descripcionInteres = descripcionInteres;
    }



    public int getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(int idInteres) {
        this.idInteres = idInteres;
    }

    public String getNombreInteres() {
        return nombreInteres;
    }

    public void setNombreInteres(String nombreInteres) {
        this.nombreInteres = nombreInteres;
    }

    public Bitmap getIconoInteres() {
        return IconoInteres;
    }

    public void setIconoInteres(Bitmap iconoInteres) {
        IconoInteres = iconoInteres;
    }
}
