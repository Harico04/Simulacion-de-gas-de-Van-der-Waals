
/*
 * Representa el piston en un sistema termodinamico cerrado con 
 * un gas. Se expande y contrae en funcion del volumen del sistema
 */

package SistemaTermodinamico;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
public class Piston implements GUI.Figura

{
  private final Color COLOR_CONTENEDOR = new Color(77, 71, 70), COLOR_PISTON = new Color(130,126,125);
  private final int GROSOR_PISTON=40;
  private double volumen,altura_piston;
  private int grosor;
  Point c1,c2,p1,p2;
  private Rectangle piston,pA,pD,pI;
  public Piston(Point c1, Point c2,double volumen ){ 
    this.c1 = c1;
    this.c2 = c2;
    this.volumen=volumen;
    altura_piston = this.volumen/(Math.PI)*(200*200);
     piston = new Rectangle ((int)c1.getX(), (int) altura_piston, (int)(c2.getX()-c1.getX()), (int) (altura_piston-20));
     pI = new Rectangle ((int) c1.getX(),(int) c1.getY(), 1, (int) (c2.getY() - c1.getY()));
     pD = new Rectangle ((int) c2.getX(),(int) c1.getY(), 1, (int) (c2.getY() - c1.getY()));
     pA = new Rectangle((int) c1.getX(), (int)c2.getY(),(int)(c2.getX()-c1.getX()) , 1);
  }
  public void setVolumen(double volumen){
    this.volumen = volumen;
    altura_piston = this.volumen/(Math.PI)*(200*200);
  }

public void pintar(Graphics g){
    double altura_piston = (1000*this.volumen)/(Math.PI*20*20);
    Graphics2D g1 = (Graphics2D) g;
    grosor = (int)(c2.getX()-c1.getX());
    g1.setColor(COLOR_CONTENEDOR);
    g1.drawRect((int) c1.getX(), (int)c1.getY(),grosor,(int)(c2.getY()-c1.getY()));
    g1.setColor(COLOR_PISTON);
    g1.fillRect((int)c1.getX(), (int)(c1.getY()+altura_piston-GROSOR_PISTON), grosor, GROSOR_PISTON); 
}
public void getParedes(Rectangle piston, Rectangle pA, Rectangle pD, Rectangle pI){
   this.piston = piston;
    this.pA = pA;
    this.pD = pD;
    this.pI = pI;
  }
}
  


