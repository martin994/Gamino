package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import java.util.ArrayList;

public class Publicacion {

    public Publicacion() {
    }

    private int idPublicacion;
    private Usuario editor;
    private String tituloPublicacion;
    private String cuerpoPublicacion;
    private ArrayList<Interes> listaInteresesPublicacion;
    private Double puntuacion;
    private ArrayList<Comentario> listaDeComentarios;

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
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

    public ArrayList<Interes> getListaInteresesPublicacion() {
        return listaInteresesPublicacion;
    }

    public void setListaInteresesPublicacion(ArrayList<Interes> listaInteresesPublicacion) {
        this.listaInteresesPublicacion = listaInteresesPublicacion;
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
