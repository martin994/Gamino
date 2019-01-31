package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;

public class Publicacion {

    public Publicacion() {
    }

    private String idPublicacion;
    private Usuario editor;
    private String tituloPublicacion;
    private String subtituloPublicacion;
    private String cuerpoPublicacion;
    private String idInteres;
    private Double puntuacion;
    private ArrayList<Comentario> listaDeComentarios;
    private Uri imagenPublicacion;//Verificar bien como traer FireBase las imagenes para saber como se setea



    public String getSubtituloPublicacion() {
        return subtituloPublicacion;
    }

    public void setSubtituloPublicacion(String subtituloPublicacion) {
        this.subtituloPublicacion = subtituloPublicacion;
    }

    public Uri getImagenPublicacion() {
        return imagenPublicacion;
    }

    public void setImagenPublicacion(Uri imagenPublicacion) {
        this.imagenPublicacion = imagenPublicacion;
    }

    public String getIdPublicacion() {

        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public Usuario getEditor() {
        return editor;
    }

    public void setEditor(Usuario editor) {
        this.editor = editor;
    }

    public String getTituloPublicacion() {
        return tituloPublicacion;
    }

    public void setTituloPublicacion(String tituloPublicacion) {
        this.tituloPublicacion = tituloPublicacion;
    }

    public String getCuerpoPublicacion() {
        return cuerpoPublicacion;
    }

    public void setCuerpoPublicacion(String cuerpoPublicacion) {
        this.cuerpoPublicacion = cuerpoPublicacion;
    }

    public String getidInteres() {
        return idInteres;
    }

    public void setidInteres(String idInteres) {
        this.idInteres = idInteres;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ArrayList<Comentario> getListaDeComentarios() {
        return listaDeComentarios;
    }

    public void setListaDeComentarios(ArrayList<Comentario> listaDeComentarios) {
        this.listaDeComentarios = listaDeComentarios;
    }
}
