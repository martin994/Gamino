package ar.edu.utn.frsf.isi.dam.gamino.Modelo;

import java.util.Date;

public class Comentario {
    private String idComentario;
    private String cuerpoComentario;
    private String usuario;
    private String idEditor;
    private Double fechaComentario;
    private String tokenComentario;


    public String getTokenComentario() {
        return tokenComentario;
    }

    public void setTokenComentario(String tokenComentario) {
        this.tokenComentario = tokenComentario;
    }

    public String getIdEditor() {
        return idEditor;
    }

    public void setIdEditor(String idEditor) {
        this.idEditor = idEditor;
    }

    public Comentario() {
    }

    public String getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(String idComentario) {
        this.idComentario = idComentario;
    }

    public String getCuerpoComentario() {
        return cuerpoComentario;
    }

    public void setCuerpoComentario(String cuerpoComentario) {
        this.cuerpoComentario = cuerpoComentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Double getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Double fechaComentario) {
        this.fechaComentario = fechaComentario;
    }
}
