/*
 * Representa una molecula de un gas contenido en un piston. Se mueve en
 * funcion de la temperatura del gas y colisiona tanto con las paredes del
 * sistema como con otras moleculas.
 *
 * Además tratamos a la clase molecula como un hilo, es decir cada vez
 * que se cree una molécula y se ejecute esta funcionara en un hilo aparte.
 *
 * Autor: Manuel Eduardo Gortarez Blanco.
 */

package SistemaTermodinamico;

import GUI.Figura;
import GUI.VentanaDibujo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Molecula extends Thread implements Figura {

    //Posicion y tamaño de la molecula
    private int alto, ancho;
    private Point posicion;

    // Representamos a la velocidad de la molecula como
    // un vector el cual tiene componentes i y j.
    private double velocidad[] = {1, 1};

    // Representamos la hitbox de la molecula con un cuadrado.
    private Rectangle hitbox;

    // Panel en donde pintamos las moléculas.
    private VentanaDibujo panel;

    // Constructor de la clase molecula
    public Molecula(Point posicion, int alto, int ancho, VentanaDibujo panel) {

        this.alto = alto;
        this.ancho = ancho;
        this.panel = panel;
        this.posicion = posicion;
        this.setPriority(Thread.MAX_PRIORITY);

        inicializar();
    }

    // Inicializamos las variables.
    private void inicializar() {
        hitbox = new Rectangle(posicion.x, posicion.y, ancho, alto);
    }

    // Lo sobreescribimos de la interfaz figura, lo implementamos
    // para que una molecula en forma de círculo sea pintada.
    @Override
    public void pintar(Graphics grafico){
        grafico.setColor(Color.RED);
        grafico.fillOval(posicion.x, posicion.y , ancho, alto);
    }

    // esta funcion hace que se ejecute un nuevo hilo.
    @Override
    public void run(){
        while(true){
            actualizarMovimiento();
            panel.repaint();
            try{
                sleep(16); // simulamos 60 fps: 1000 milisegundos entre 60.
            }catch(InterruptedException ie){
                System.out.println("Error: " + ie);
            }
        }
    }

    // Este metodo actualiza el movimiento en base al
    // entorno de la molecula, puede ser afectado por una colisión
    // con una pared o con otra molecula.
    public void actualizarMovimiento(){
        posicion.translate((int)velocidad[0] * 1, 0);
        if(posicion.x == 400)
            velocidad[0] = -1;
        if(posicion.x == 30)
            velocidad[0] = 1;
    }

    // Predicado que indica si hay una interseccion entre un
    // entorno y la molécula.
    public boolean verificarColision(Rectangle objeto){
        if(this.hitbox.intersects(objeto))
            return true;
        return false ;
    }
}

                     
