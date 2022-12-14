package base;


import org.bson.types.ObjectId;
import util.Util;

import java.util.Date;

public class Pelicula {

    public static String COLECCION = "peliculas";

    private ObjectId id;
    private String titulo;
    private String descripcion;
    private String autor;
    private Date fecha;

    public Pelicula() {

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return titulo;
    }


    public String toJSON() {
        return "{'titulo':'" + titulo + "', 'descripcion':'" + descripcion +
                "', 'autor':'" + autor + "', 'fecha':'" + Util.formatFecha(fecha)+ "'}";
    }
}
