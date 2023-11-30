

package SistemaTermodinamico;

import GUI.VentanaDibujo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 * Representa el piston en un sistema termodinamico cerrado con 
 * un gas. Se expande y contrae en funcion del volumen del sistema
 */
public class Piston extends Thread implements GUI.Figura


{
  /**
     * Color del contenedor del sistema termodinámico.
     */
    private final Color COLOR_CONTENEDOR = new Color(77, 71, 70);

    /**
     * Color del pistón en el sistema.
     */
    private final Color COLOR_PISTON = new Color(130, 126, 125);

    /**
     * Color del tubo conectado al pistón.
     */
    private final Color COLOR_TUBO = new Color(173, 169, 168);

    /**
     * Grosor del pistón utilizado en la representación gráfica.
     */
    private final int GROSOR_PISTON = 40;

    /**
     * Volumen del sistema termodinámico.
     */
    private double volumen;
    /**
     * Altura actual del pistón en la representación gráfica.
     */
    private double altura_piston;

    /**
     * Radio del pistón.
     */
    private double radio;

    /**
     * Grosor general utilizado en la representación gráfica.
     */
    private int grosor;

    /**
     * Escala de pixeles utilizada para calcular la altura del pistón en la representación gráfica.
     */
    private int escalaPixeles;

    /**
     * Puntos y rectángulos utilizados para la representación gráfica del pistón y sus componentes.
     */
    private Point c1, c2, p1, p2;

    /**
     * Coordenadas utilizadas para almacenar las posiciones de las paredes del pistón.
     */
    private double[] coordenadas;
    
  /**
   * Inicializa una instancia de la clase Piston con las coordenadas, volumen y radio dados.
   *
   * @param c1     Punto de inicio del pistón.
   * @param c2     Punto final del pistón.
   * @param volumen Volumen del sistema termodinámico.
   * @param panel  Panel de dibujo donde se representará el pistón.
   * @param radio  Radio del pistón.
   */
    public Piston(Point c1, Point c2, double volumen, VentanaDibujo panel, double radio) {
        this.c1 = c1;
        this.c2 = c2;
        this.volumen = volumen;
        this.radio = radio;
        
        // Cálculo de la escala de pixeles y la altura del pistón.
        escalaPixeles = (int) (c2.getY() - c1.getY()) / 50;
        altura_piston = escalaPixeles * (1000 * this.volumen) / ((Math.PI) * (Math.pow(this.radio, 2)));
        
        // Inicialización de las coordenadas utilizadas para almacenar las posiciones de las paredes del pistón.
        coordenadas = new double[4];
        coordenadas[0] = c2.getY() - altura_piston;
        coordenadas[1] = c2.getX();
        coordenadas[2] = c2.getY();
        coordenadas[3] = c1.getX();
    }

    /**
     * Establece el volumen y el radio del pistón y recalcula la altura del pistón.
     *
     * @param volumen Nuevo volumen del sistema termodinámico.
     * @param radio   Nuevo radio del pistón.
     */
    public void setVolumen(double volumen, double radio) {
        this.volumen = volumen;
        this.radio = radio;
        altura_piston = escalaPixeles * (1000 * this.volumen) / (Math.PI * Math.pow(this.radio, 2));
    }

    /**
     * Dibuja la representación gráfica del pistón en el panel de dibujo.
     *
     * @param g Objeto Graphics utilizado para dibujar.
     */
    public void pintar(Graphics g) {
        altura_piston = escalaPixeles * (1000 * this.volumen) / (Math.PI * Math.pow(this.radio, 2));
        Graphics2D g1 = (Graphics2D) g;
        grosor = (int) (c2.getX() - c1.getX());

        // Pintar el contenedor
        g1.setColor(COLOR_CONTENEDOR);
        g1.drawRect((int) c1.getX(), (int) c1.getY(), grosor, (int) (c2.getY() - c1.getY()));

        // Pintar el pistón
        g1.setColor(COLOR_PISTON);
        g1.fillRect((int) c1.getX(), (int) (c2.getY() - altura_piston - GROSOR_PISTON), grosor, GROSOR_PISTON);

        // Pintar el tubo
        g1.setColor(COLOR_TUBO);
        g1.fillRect((int) ((c1.getX() + c2.getX()) / 2 - 30), (int) (c1.getY()), 60, (int) (c2.getY() - altura_piston - 3.5 * GROSOR_PISTON));

        // Actualizar las coordenadas de las paredes del pistón.
        coordenadas[0] = c2.getY() - altura_piston;
    }

    /**
     * Obtiene las coordenadas de las paredes del pistón.
     *
     * @return Un array de double con las coordenadas de las paredes del pistón.
     */
    public double[] getParedes() {
        return coordenadas;
    }
  }

  


