
package Ciclos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import Ciclos.Estado;

public class Lector {
    private FileReader fr;
    private List<Estado> ciclo;
    private List<String> datos;

    public Lector(String archivo) {
        try {
            this.fr = new FileReader(archivo);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        this.ciclo = new ArrayList<>();
        this.datos = new ArrayList<>();
        leer();
        cerrar();

    }

    public void leer() {
        BufferedReader bfr = new BufferedReader(this.fr);
            String linea;
    try{
            while ((linea = bfr.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(linea, "=");
        if(stk.countTokens()>=2){
                stk.nextToken();
                datos.add(stk.nextToken().trim());
          }
        }
      }

    catch(IOException ioe){
      System.out.println(ioe.toString());
    }
     for(int i = 0; i+4<datos.size();i+=5){
      Estado temp = new Estado(datos.get(i),
        Double.parseDouble(datos.get(i+1)),
        Double.parseDouble(datos.get(i+2)),
        Double.parseDouble(datos.get(i+3)));
      ciclo.add(temp);
    }   
  }
    public void imprimirEstados(){
      for (Estado estado : ciclo) {
       System.out.println(estado.toString());
      }
    System.out.println(datos.toString());
  }
  public List<Estado> getEstado(){
    return this.ciclo;
  }
  public void cerrar(){
    try{
      if(fr!= null){
        fr.close();
      }
    }
    catch(IOException ioe){
      System.out.println("No se pudo cerrar el archivo "+ ioe.toString());
    }
    
  }
}
