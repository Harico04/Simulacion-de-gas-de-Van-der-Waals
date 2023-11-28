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

    // Temperatura del sistema
    private double temperatura = 273;
    private double temperaturaaux;

    // Para normalizar el vector de velocidad
    private double escalarNormalizador;

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
    public Molecula(Point posicion, double radio, double[] paredes,VentanaDibujo panel) {
            
        this.panel = panel;
        this.posicion[X] = posicion.getX();
        this.posicion[Y] = posicion.getY();
        this.velocidad[X] = 1;
        this.velocidad[Y] = 1;
        this.paredes = paredes;
        this.radio = radio;
    }

    // Lo sobreescribimos de la interfaz figura, lo implementamos
    // para que una molecula en forma de círculo sea pintada.
    @Override
    public void pintar(Graphics grafico){
        grafico.setColor(cambiarColor());
        grafico.fillOval((int)posicion[X], (int)posicion[Y] , (int)radio, (int)radio);
        grafico.setColor(Color.BLACK);
        grafico.drawOval((int)posicion[X], (int)posicion[Y] , (int)radio, (int)radio);
    }

    // Esta funcion hace que se ejecute un nuevo hilo.
    @Override
    public void run(){
        while(true){
            panel.repaint();
            actualizarVelocidad();
            try{
                sleep(16); // Simulamos 60 fps -> (1000 / 60)
            }catch(InterruptedException ie){
                System.out.println("Error:" + ie);
            }
        }
    } 
    
    /*
     * Actualiza la velocidad en base al tiempo y
     * además verifica colisiones.
     */
    public void actualizarVelocidad(){

        temperaturaaux = temperatura / 100.00; // [.35 - 60]
        System.out.println("temperatura: " + temperatura);
        System.out.println("temperaturaaux: " + temperaturaaux);

        System.out.println("X1: " + velocidad[X]);
        System.out.println("Y1: " + velocidad[Y]);
        
        // Normalizando el vector de velocidad
        escalarNormalizador = Math.sqrt(Math.pow(velocidad[X], 2.0) + Math.pow(velocidad[Y], 2.0));
        System.out.println("Norma: " + escalarNormalizador);

        velocidad[X] /= escalarNormalizador;
        velocidad[Y] /= escalarNormalizador;

        // Ajustamos la magnitud del vector de velocidad en funcion de temperatura.
        velocidad[X] *= temperaturaaux;
        velocidad[Y] *= temperaturaaux;        

        System.out.println("X: " + velocidad[X]);
        System.out.println("Y: " + velocidad[Y]);
        
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

        // Actualizamos la posicion.
        posicion[X] += velocidad[X];
        posicion[Y] += velocidad[Y];
    }

    //Método para cambiar el color de las moléculas en cuestión de su temperatura
    private Color cambiarColor(){
        if(temperatura<=260)
        {
            return new Color(0,255,255,150);
        }
        if(temperatura<=280)
        {
            return new Color(0,255,255-(int)(7.75*(temperatura-260)),150);
        }
        if(temperatura<=305)
        {
            return new Color((int)(100+6.2*(temperatura-280)),255,0,150); 
        }
        if(temperatura<=373)
        {
            return new Color(255,255-(int)(3.75*(temperatura-305)),0,150); 
        }
        return new Color(255,0,0,150);
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

    // getter para el radio
    public double getRadio(){
        return this.radio;
    }

    // getter para la posición
    public Point2D.Double getPosicion(){
        return new Point2D.Double(posicion[X], posicion[Y]);
    }

    // setter para la temperatura.
    public void setTemperatura(double temperatura){
        this.temperatura = temperatura;
    }
}

                     
