
/*
 * Representa el piston en un sistema termodinamico cerrado con 
 * un gas. Se expande y contrae en funcion del volumen del sistema
 */

package SistemaTermodinamico;

import GUI.VentanaDibujo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
public class Piston extends Thread implements GUI.Figura

{
  private final Color COLOR_CONTENEDOR = new Color(77, 71, 70), COLOR_PISTON = new Color(130,126,125), COLOR_TUBO = new Color(173,169,168);
  private final int GROSOR_PISTON=40;
  private double volumen,altura_piston,radio;
  private int grosor;
  private int escalaPixeles;
  Point c1,c2,p1,p2;
  private double[] coordenadas;

  public Piston(Point c1, Point c2,double volumen, VentanaDibujo panel,double radio){ 
    this.c1 = c1;
    this.c2 = c2;
    this.volumen=volumen;
    this.radio = radio;
     escalaPixeles=(int)(c2.getY()-c1.getY())/50;
     altura_piston = escalaPixeles*(1000*this.volumen)/((Math.PI)*(Math.pow(this.radio, 2)));
     coordenadas= new double[4];
     coordenadas[0]=c2.getY()-altura_piston;
     coordenadas[1]=c2.getX();
     coordenadas[2]=c2.getY();
     coordenadas[3]=c1.getX();
  }
  public void setVolumen(double volumen, double radio){
    this.volumen = volumen;
    this.radio = radio;
    altura_piston = escalaPixeles*(1000*this.volumen)/(Math.PI)*(Math.pow(this.radio, 2));
  }

public void pintar(Graphics g){
    altura_piston = escalaPixeles*(1000*this.volumen)/(Math.PI*Math.pow(this.radio, 2));
    Graphics2D g1 = (Graphics2D) g;
    grosor = (int)(c2.getX()-c1.getX());
    //Pintar el contenedor
    g1.setColor(COLOR_CONTENEDOR);
    g1.drawRect((int) c1.getX(), (int)c1.getY(),grosor,(int)(c2.getY()-c1.getY()));
    //Pintar el piston
    g1.setColor(COLOR_PISTON);
    g1.fillRect((int)c1.getX(), (int)(c2.getY()-altura_piston-GROSOR_PISTON), grosor, GROSOR_PISTON); 
    //Pintar el tubo
    g1.setColor(COLOR_TUBO);
    g1.fillRect((int) ((c1.getX() + c2.getX()) / 2 - 30), (int) (c1.getY()), 60, (int) (c2.getY() - altura_piston - 3.5*GROSOR_PISTON));
    coordenadas[0]=c2.getY()-altura_piston;
}
    
  public double[] getParedes(){
      return coordenadas;
  }
}

  


