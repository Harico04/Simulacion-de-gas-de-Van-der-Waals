
/*
 * Representa el piston en un sistema termodinamico cerrado con 
 * un gas. Se expande y contrae en funcion del volumen del sistema
 */

package SistemaTermodinamico;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;
import java.awt.BasicStroke;
public class Piston implements GUI.Figura

{

  //private final double VOLUMEN_MAXIMO,TEMPERATURA_MAXIMA,PRESION_MAXIMA;
private final int GROSOR = 20;
  private final Color COLOR_CONTENEDOR = Color, COLOR_PISTON = new Color(rgb);
  private double volumen, temperatura, presion;
public Piston(Point p1, Point p2, ){

  }
  // Getter para volumen
    public double getVolumen() {
        return volumen;
    }

    // Setter para volumen
    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    // Getter para temperatura
    public double getTemperatura() {
        return temperatura;
    }

    // Setter para temperatura
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    // Getter para presion
    public double getPresion() {
        return presion;
    }

    // Setter para presion
    public void setPresion(double presion) {
        this.presion = presion;
    }

public void pintar(double volumen,Graphics g){
    Graphics2D g1 = (Graphics2D) g;
    g1.setColor(Color.GRAY);
    g1.setStroke();
 
 
  altura_piston = this.volumen/(Math.PI)*(200*200);
         
}

}
  


