package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import android.media.Image;
import android.net.Uri;

import java.util.ArrayList;

public class Usuario {
   private String idUsuario;
   private String nombreusuario;
   private String contrsenia;
   private Boolean recordarSecion;
   private String mailUsuario;
   private Uri fotoPerfilUsuario;
   private ArrayList<Interes> listaInteres;
   private ArrayList<Publicacion> listaDePublicaciones;
   private ArrayList<Publicacion> listaDePublicacionesPuntuadas;

    public String getIdMensajeUsuario() {
        return idMensajeUsuario;
    }

    public void setIdMensajeUsuario(String idMensajeUsuario) {
        this.idMensajeUsuario = idMensajeUsuario;
    }

    private String idMensajeUsuario;





    public ArrayList<Publicacion> getListaDePublicaciones() {
        return listaDePublicaciones;
    }

    public void setListaDePublicaciones(ArrayList<Publicacion> listaDePublicaciones) {
        this.listaDePublicaciones = listaDePublicaciones;
    }

    public ArrayList<Publicacion> getListaDePublicacionesPuntuadas() {
        return listaDePublicacionesPuntuadas;
    }

    public void setListaDePublicacionesPuntuadas(ArrayList<Publicacion> listaDePublicacionesPuntuadas) {
        this.listaDePublicacionesPuntuadas = listaDePublicacionesPuntuadas;
    }


    public ArrayList<Interes> getListaInteres() {
        return listaInteres;
    }

    public void setListaInteres(ArrayList<Interes> listaInteres) {
        this.listaInteres = listaInteres;
    }




    public Usuario() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContrsenia() {
        return contrsenia;
    }

    public void setContrsenia(String contrsenia) {
        this.contrsenia = contrsenia;
    }

    public Boolean getRecordarSecion() {
        return recordarSecion;
    }

    public void setRecordarSecion(Boolean recordarSecion) {
        this.recordarSecion = recordarSecion;
    }

    public String getMailUsuario() {
        return mailUsuario;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public Uri getFotoPerfilUsuario() {
        return fotoPerfilUsuario;
    }

    public void setFotoPerfilUsuario(Uri fotoPerfilUsuario) {
        this.fotoPerfilUsuario = fotoPerfilUsuario;
    }
}
