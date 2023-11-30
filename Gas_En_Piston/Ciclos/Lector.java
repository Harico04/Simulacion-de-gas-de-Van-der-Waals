
package Ciclos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Lector {
    private FileReader fr;
    private List<Estado> ciclo;
    private List<String> datos;

    public Lector(String archivo) throws IOException{
        this.fr = new FileReader(archivo);
        this.ciclo = new ArrayList<>();
        this.datos = new ArrayList<>();
        leer();
        cerrar();

    }

    public void leer() throws IOException{
        BufferedReader bfr = new BufferedReader(this.fr);
            String linea;
            while ((linea = bfr.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(linea, "=");
        if(stk.countTokens()>=2){
                stk.nextToken();
                datos.add(stk.nextToken().trim());
          }
        }
     for(int i = 0; i+4<datos.size();i+=5){
      Estado temp = new Estado(datos.get(i),
        Double.parseDouble(datos.get(i+1)),
        Double.parseDouble(datos.get(i+2)),
        Double.parseDouble(datos.get(i+3)),
        Integer.parseInt(datos.get(i+4)));
      ciclo.add(temp);
    }   
  }
    public void imprimirEstados(){
      for (Estado estado : ciclo) {
       System.out.println(estado.toString());
      }
    System.out.println(datos.toString());
  }
  public List<Estado> getEstados(){
    return this.ciclo;
  }
  public void cerrar() throws IOException{
      if(fr!= null){
        fr.close();
      }
   }
}
