/*
 * Representa una molecula de un gas contenido en un piston. Se mueve en
 * funcion de la temperatura del gas y colisiona tanto con las paredes del
 * sistema como con otras moleculas.
 */
package SistemaTermodinamico;

import GUI.Figura;

import java.util.Vector;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Molecula extends Thread implements Figura {

    //Posicion y tamaño de la molecula
    private int x, y, alto, ancho;

    // Representamos a la velocidad de la molecul como
    // un vector el cual tiene componentes i y j.
    Vector<Float> velocidad = new Vector<>(2);

    // Representamos la hitbox de la molecula con un cuadrado.
    Rectangle hitbox;

    // Constructor de la clase molecula
    public Molecula(int x, int y, int alto, int ancho) {
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
    }

    protected void inicializar() {
        hitbox = new Rectangle(x - 2, y - 2, ancho, alto);
    }

    @Override
    public void pintar(Graphics g){
        g.setColor(Color.RED);
        g.drawOval(x - 2, y - 2, ancho, alto);
    }

    // Se encarga de hacer todos los calculos
    // de movimiento al igual que detectar las colisiones.
    @Override
    public void run(){

        
    }   
}

                     
