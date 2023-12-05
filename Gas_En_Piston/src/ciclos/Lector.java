package ciclos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Clase que lee y almacena informacion desde archivos de texto para la creacion de estados
 * termodinamicos
 * @author Alan Torres
 * */
public class Lector {
    private FileReader fr;
    private List<Estado> ciclo;
    private List<String> datos;

    /**
     * Crea una instancia de Lector y lee los datos del archivo especificado.
     * 
     * @param archivo Ruta del archivo de texto que contiene los datos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Lector(String archivo) throws IOException {
        this.fr = new FileReader(archivo);
        this.ciclo = new ArrayList<>();
        this.datos = new ArrayList<>();
        leer();
        cerrar();
    }

    /**
     * Lee desde el archivo de texto, despues de cada signo "=" y almacena los tokens
     * en el arreglo de datos para luego realizar su respectiva conversion para crear un objeto de tipo Estado
     * 
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public void leer() throws IOException {
        BufferedReader bfr = new BufferedReader(this.fr);
        String linea;
        while ((linea = bfr.readLine()) != null) {
            StringTokenizer stk = new StringTokenizer(linea, "=");
            if (stk.countTokens() >= 2) {
                stk.nextToken();
                datos.add(stk.nextToken().trim());
            }
        }
        for (int i = 0; i + 4 < datos.size(); i += 5) {
            Estado temp = new Estado(datos.get(i),
                    Double.parseDouble(datos.get(i + 1)),
                    Double.parseDouble(datos.get(i + 2)),
                    Double.parseDouble(datos.get(i + 3)),
                    Integer.parseInt(datos.get(i + 4)));
            ciclo.add(temp);
        }
    }

    /**
     * Imprime en la consola los objetos Estado almacenados en la lista ciclo y los datos almacenados en la lista datos.
     */
    public void imprimirEstados() {
        for (Estado estado : ciclo) {
            System.out.println(estado.toString());
        }
        System.out.println(datos.toString());
    }

    /**
     * Obtiene la lista de objetos Estado almacenada en la clase.
     * 
     * @return La lista de estados.
     */
    public List<Estado> getEstados() {
        return this.ciclo;
    }

    /**
     * Cierra el FileReader utilizado para leer el archivo.
     * 
     * @throws IOException Si ocurre un error al cerrar el FileReader.
     */
    public void cerrar() throws IOException {
        if (fr != null) {
            fr.close();
        }
    }
}

