package Ventana;


import base.Pelicula;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import util.Constantes;
import util.Util;

import java.text.ParseException;
import java.util.*;


public class VentanaModel {

    private MongoClient mongoClient;
    private MongoDatabase db;

    /**
     * Conecta con la Base de Datos
     */
    public void conectar() {
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase(Constantes.NOMBRE_BASEDEDATOS);
    }


    public void desconectar() {
        mongoClient.close();
    }


    public void anadirPelicula(Pelicula pelicula) {

        Document documento = new Document()
                .append("titulo", pelicula.getTitulo())
                .append("descripcion", pelicula.getDescripcion())
                .append("autor", pelicula.getAutor())
                .append("fecha", Util.formatFecha(pelicula.getFecha()));
        db.getCollection(Pelicula.COLECCION).insertOne(documento);

        // También es posible pasar directamente la información en formato JSON
        // db.getCollection(Libro.COLECCION).insertOne(Document.parse(pelicula.toJSON()));
    }

    public void modificarPelicula(Pelicula pelicula) {

        db.getCollection(Pelicula.COLECCION).replaceOne(new Document("_id", pelicula.getId()),
                new Document()
                        .append("titulo", pelicula.getTitulo())
                        .append("descripcion", pelicula.getDescripcion())
                        .append("autor", pelicula.getAutor())
                        .append("fecha", Util.formatFecha(pelicula.getFecha())));
    }


    public void eliminarPelicula(String titulo) {
        db.getCollection(Pelicula.COLECCION).deleteOne(new Document("titulo", titulo));
    }


    public List<Pelicula> buscarPelicula(String busqueda) throws ParseException {

        // Búsqueda utilizando el operador OR
        /*Document documento = new Document("$or", Arrays.asList(
                new Document("titulo", busqueda),
                new Document("descripcion", busqueda),
                new Document("autor", busqueda)));*/

        // Búsqueda utilizando expresiones regulares
        BasicDBObject documento = new BasicDBObject();
        documento.put("titulo", new BasicDBObject("$regex", "/*" + busqueda + "/*"));

        FindIterable findIterable = db.getCollection(Pelicula.COLECCION)
                .find(documento)
                .sort(new Document("titulo", 1));
        return getListaPelicula(findIterable);

        // También es posible realizar la búsqueda de esta manera
        /*FindIterable findIterable = db.getCollection(Libro.COLECCION).find(Filters.or(
                Filters.eq("titulo", busqueda),
                Filters.eq("descripcion", busqueda),
                Filters.eq("autor", busqueda)))
                .sort(new Document("titulo", 1));
        return getListaLibros(findIterable);*/
    }


    private List<Pelicula> getListaPelicula(FindIterable<Document> findIterable) throws ParseException {

        List<Pelicula> peliculas = new ArrayList<>();
        Pelicula pelicula = null;
        Iterator<Document> iter = findIterable.iterator();

        while (iter.hasNext()) {
            Document documento = iter.next();
            pelicula = new Pelicula();
            pelicula.setId(documento.getObjectId("_id"));
            pelicula.setTitulo(documento.getString("titulo"));
            pelicula.setDescripcion(documento.getString("descripcion"));
            pelicula.setAutor(documento.getString("autor"));
            pelicula.setFecha(Util.parseFecha(documento.getString("fecha")));
            peliculas.add(pelicula);
        }

        return peliculas;
    }


    private Pelicula getPelicula(Document documento) throws ParseException {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(documento.getObjectId("_id"));
        pelicula.setTitulo(documento.getString("titulo"));
        pelicula.setDescripcion(documento.getString("descripcion"));
        pelicula.setAutor(documento.getString("autor"));
        pelicula.setFecha(Util.parseFecha(documento.getString("fecha")));

        return pelicula;
    }


    public List<Pelicula> getPeliculas() throws ParseException {

        FindIterable<Document> findIterable = db.getCollection(Pelicula.COLECCION).find();
        return getListaPelicula(findIterable);
    }


    public Pelicula getPelicula(String titulo) throws ParseException {

        FindIterable<Document> findIterable = db.getCollection(Pelicula.COLECCION).find(new Document("titulo", titulo));
        Document documento = findIterable.first();
        return getPelicula(documento);
    }


    public List<Pelicula> getPeliculas(String autor) throws ParseException {

        FindIterable<Document> findIterable = db.getCollection(Pelicula.COLECCION).find(new Document("autor", autor));
        return getListaPelicula(findIterable);
    }
}
