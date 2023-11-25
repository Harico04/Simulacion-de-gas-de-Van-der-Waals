package Ciclos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Lector {
    FileReader fr;
    List<Estado> ciclo;
    List<String> datos;

    public Lector(String archivo) {
        try {
            this.fr = new FileReader(archivo);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        this.ciclo = new ArrayList<>();
        this.datos = new ArrayList<>();
        leer();

    }

    public void leer() {
        BufferedReader bfr = new BufferedReader(this.fr);
            String linea;
    try{
            while ((linea = bfr.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(linea, "=");
                stk.nextToken();
                datos.add(stk.nextToken().trim());
            
        }
      }
    catch(IOException ioe){
      System.out.println(ioe.toString());
    }
     for(int i = 0; i<datos.size();i+=4){
      Estado temp = new Estado(datos.get(i),
        Double.parseDouble(datos.get(i+1)),
        Double.parseDouble(datos.get(i+2)),
        Double.parseDouble(datos.get(i+3)));
      ciclo.add(temp);
    }   
  }
  public List<Estado> getEstado(){
    return this.ciclo;
  }
}
class Estado{
  double temperatura,presion,volumen;
  String tipoDeProceso;
  public Estado(String tipoDeProceso,double temperatura,double volumen, double presion){
    this.temperatura = temperatura;
    this.presion = presion;
    this.volumen = volumen;
    this.tipoDeProceso = tipoDeProceso;
  }
}
