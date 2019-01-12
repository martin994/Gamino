package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import android.media.Image;

public class Interes {

    public Interes() {
    }

    private int idInteres;
    private String nombrePublicacion;
    private Image IconoPublicacion;

    public int getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(int idInteres) {
        this.idInteres = idInteres;
    }

    public String getNombrePublicacion() {
        return nombrePublicacion;
    }

    public void setNombrePublicacion(String nombrePublicacion) {
        this.nombrePublicacion = nombrePublicacion;
    }

    public Image getIconoPublicacion() {
        return IconoPublicacion;
    }

    public void setIconoPublicacion(Image iconoPublicacion) {
        IconoPublicacion = iconoPublicacion;
    }
}
