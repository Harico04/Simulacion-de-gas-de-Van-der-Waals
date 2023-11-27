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
import java.awt.geom.Point2D;

public class Molecula extends Thread implements Figura {

    // Tamaño de la molecula
    private double radio;

    // Coordenadas de las paredes del contenedor.
    private double paredes[];

    /*
     * Movemos a la molecula con los vectores de posición y
     * velocidad, consideramos que no tiene aceleración.
     * Por lo tanto la velocidad es constante y la posición
     * depende de a velocidad.
     */
    private double posicion[] = new double[2];
    private double velocidad[] = new double[2];
    private final int X = 0;
    private final int Y = 1;
    private final int ARRIBA = 0;
    private final int DERECHA = 1;
    private final int ABAJO = 2;
    private final int IZQUIERDA = 3;

    // Panel en donde pintamos las moléculas.
    private VentanaDibujo panel;

    // Constructor de la clase molecula
    public Molecula(Point posicion, double radio, double temperatura, double[] paredes,VentanaDibujo panel) {
            
        this.panel = panel;
        this.posicion[X] = posicion.getX();
        this.posicion[Y] = posicion.getY();
        this.velocidad[X] = temperatura/500000.0;
        this.velocidad[Y] = temperatura/500000.0;
        this.paredes = paredes;
        this.radio = radio;
    }

    // Lo sobreescribimos de la interfaz figura, lo implementamos
    // para que una molecula en forma de círculo sea pintada.
    @Override
    public void pintar(Graphics grafico){
        grafico.setColor(Color.RED);
        grafico.fillOval((int)posicion[X], (int)posicion[Y] , (int)radio, (int)radio);
    }

    // Esta funcion hace que se ejecute un nuevo hilo.
    @Override
    public void run(){
        while(true){
            panel.repaint();
            actualizarVelocidad();
        }
    } 
    
    /*
     * Actualiza la velocidad en base al tiempo y
     * además verifica colisiones.
     */
    public void actualizarVelocidad(){

        // Verificacion de colisiones con las paredes.
        if((paredes[ARRIBA] > posicion[Y] - radio)){            
            if(velocidad[Y] == 0) velocidad[Y] = 100;
            if(velocidad[Y] * (-1) > 0)
                velocidad[Y] *= -1;
        }

        if(posicion[Y] < paredes[ARRIBA] - radio)
            posicion[Y] = paredes[ARRIBA] + 2*radio;            

        if(paredes[ABAJO] < posicion[Y] + radio){
            if(velocidad[Y] == 0) velocidad[Y] = -100;
            if(velocidad[Y] * (-1) < 0)
                velocidad[Y] *= -1;
        }

        if(paredes[DERECHA] < posicion[X] + radio){
            if(velocidad[X] == 0) velocidad[X] = -100;
            if(velocidad[X] * (-1) < 0)
                velocidad[X] *= -1;
        }
        
        if(paredes[IZQUIERDA] > posicion[X] - radio){
            if (velocidad[X] == 0) velocidad[X] = 100;
            if(velocidad[X] * (-1) > 0)
                velocidad[X] *= -1;
        } 

        // Actualizamos la velocidad.
        posicion[X] += velocidad[X];
        posicion[Y] += velocidad[Y];
    }

    // getter para el vector de velocidad.
    public double[] getVelocidad(){
        return this.velocidad;
    }

    // setter para el vector de velocidad.
    public void setVelocidad(double Vx, double Vy){
        this.velocidad[X] = Vx;
        this.velocidad[Y] = Vy;
    }

    public double getRadio(){
        return this.radio;
    }

    public Point2D.Double getPosicion(){
        return new Point2D.Double(posicion[X], posicion[Y]);
    }
}

                     
