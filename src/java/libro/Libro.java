package libro;

import databasehelper.*;
import java.util.ArrayList;
import java.util.List;

public class Libro {
    private String isbn;
    private String titulo;
    private String categoria;

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Libro(String isbn, String titulo, String categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.categoria = categoria;
    }
    
    public void insert(){
        String consultation = "insert into Libros(isbn, titulo, categoria) "
                + "values(\'"+isbn+"\', \'"+titulo+"', '"+categoria+"\');";
        DataBaseHelper helper = new DataBaseHelper();
        helper.changeRegister(consultation);
    }
    
    public static List<Libro> SearchAll(){
        String consutation = "select isbn, titulo, categoria from Libros;";
        DataBaseHelper<Libro> helper = new DataBaseHelper<Libro>(); 
        List<Libro> listLibros = helper.selectRecords(consutation, Libro.class);
        return listLibros;
    }
    
    public static void main(String[] args){
//        Libro libro = new Libro("209", "Linux", "Computacion");
//        libro.insert();
        
        List<Libro> books = Libro.SearchAll();
        for(Libro libro:books){
            System.out.println("Titulo: "+libro.getTitulo());
            System.out.println("Categoria: "+libro.getCategoria());
            System.out.println("Isbn: "+libro.getIsbn());
        }
        
    }
}
