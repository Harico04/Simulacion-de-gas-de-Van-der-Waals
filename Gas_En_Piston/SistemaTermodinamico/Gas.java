
package SistemaTermodinamico;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import GUI.VentanaDibujo;

/**
 * Representa un gas contenido en un sistema termodinamico cerrado con un piston.
 * Se expande, contrae, enfria, calienta y varia su presion en base a la ecuacion
 * de Van Der Waals.
 */
public class Gas extends Thread implements GUI.Figura
{
    /**
     * Constante de los gases
     * */
    private final double R=0.08206;
  /**
   * Constante de interaccion molecular 
   * */
    private double constante_a;
  /**
   * Constante del volumen de las moleculas
   * */
    private double constante_b;
  /**
   * Presion del gas
   * */
    private double presion;
  /**
   * Volumen del gas
   * */
    private double volumen;
  /**
   * Volumen maximo que puede ocupar 
   * */
    private double V_MAX;
  /**
   * Temperatura del gas
   * */
    private double temperatura;
  /**
   *Punto donde se empieza a dibujar el contenedor
   * */
    private Point puntoInicio;
  /**
   * Punto donde termina de dibujarse el contenedor 
   * */
    private Point puntoFinal;
  /**
   * Valor para identificar a la presion como variable modificada
   * */
    public final static int PRESION = 0;
  /**
   * Valor para identificar al volumen como variable modificada
   * */
    public final static int VOLUMEN = 1;
  /**
   * Valor para identificar a la temperatura como variable modificada 
   * */
    public final static int TEMPERATURA = 2;
  /**
   * La altura maxima que puede alcanzar el piston
   * */
    private double alturaMaxima=50;
  /**
   * El radio que ocupa el contenedor del piston
   * */
    private final int RADIO_PISTON=20;
  /**
   * La altura actual del piston
   * */
    private double altura;
  /**
   * Escala de pixeles
   * */
    private int escalaPixeles;
    /**
     * Panel sobre el que se va a dibujar la representacion grafica del sistema
     * */
    private VentanaDibujo panel;
    /*******************************************************************************/
    /**
     * Constructor del gas
     * @param v Volumen del gas
     * @param p Presion del gas
     * @param T Temperatura del as
     * @param a constante de interaccion molecular dada por el gas particular
     * @param b Volumen de las moleculas dado por el gas particular
     * @param pi Punto inicial donde se empieza a dibujar el contenedor
     * @param pf Punto final donde se termina de dibujar el contenedor
     * @param panel Panel donde se dibuja la representacion grafica
     * @param VMX Volumen maximo que puede alcanzar el gas
     * */
    public Gas(double v, double p, double T,double a, double b,Point pi,Point pf, VentanaDibujo panel,double VMX)
    {
        this.panel = panel;
        this.setVariables(p,v,T);
        puntoInicio=pi;
        puntoFinal=pf;
        constante_a=a;
        constante_b=b;
        escalaPixeles=(int)((puntoFinal.getY()-puntoInicio.getY())/alturaMaxima);
        V_MAX=VMX;
    }
  /**
   *Metodo para aproximar el volumen numericamente por el metodo de Newton-Raphson
   * */
    private double aproximarVolumen()
    {
        double valor=V_MAX;
        for(int i=0;i<10;i++)
        {
            valor=valor-(presion*Math.pow(valor, 3)-(constante_b*presion+R*temperatura)*Math.pow(valor, 2)+constante_a*valor-constante_a*constante_b)/
            (3*presion*Math.pow(valor, 2)+2*-(constante_b*presion+R*temperatura)*valor+constante_a);
        }
        return valor;
    }
  /**
   * Metodo que cambia el color del gas en base a la temperatura para una representacion visual
   * */
    private GradientPaint cambiarColor()
    {
        if(temperatura<=260)
        {
            return new GradientPaint((float)puntoInicio.getX(),(float)(puntoFinal.getY()-altura),new Color(0,0,0,0),
            (float)puntoInicio.getX(),(float)puntoFinal.getY(),new Color(0,255,255,150));
        }
        if(temperatura<=280)
        {
            return new GradientPaint((float)puntoInicio.getX(),(float)(puntoFinal.getY()-altura),new Color(0,0,0,0),
            (float)puntoInicio.getX(),(float)puntoFinal.getY(),new Color(0,255,255-(int)(7.75*(temperatura-260)),150));
        }
        if(temperatura<=305)
        {
        return new GradientPaint((float)puntoInicio.getX(),(float)(puntoFinal.getY()-altura),new Color(0,0,0,0),
        (float)puntoInicio.getX(),(float)puntoFinal.getY(),new Color((int)(100+6.2*(temperatura-280)),255,0,150)); 
        }
        if(temperatura<=373)
        {
        return new GradientPaint((float)puntoInicio.getX(),(float)(puntoFinal.getY()-altura),new Color(0,0,0,0),
        (float)puntoInicio.getX(),(float)puntoFinal.getY(),new Color(255,255-(int)(3.75*(temperatura-305)),0,150)); 
        }
        return new GradientPaint((float)puntoInicio.getX(),(float)(puntoFinal.getY()-altura),new Color(0,0,0,0),(float)puntoInicio.getX(),(float)puntoFinal.getY(),new Color(255,0,0,150));
    }
    /*
     * Obtiene los valores de cada una de las variables termodinamicas.
     */
  /**
   * Metodo que establece el valor de la presion, el volumen y la temperatura del gas
   * @param p Nuevo valor de la presion del gas
   * @param v Nuevo valor del volumen del gas
   * @param T Nuevo valor de la temperatura del gas
   * */
    public void setVariables(double p,double v,double T)
    {
        this.presion=p;
        this.volumen=v;
        this.temperatura=T;
    }
    /**
     * Metodo que retorna el valor de la presion
     * @return Retorna el valor de presion actual
     */
    public double getPresion()
    {
        return presion;
    }
    /**
     * Metodo que retorna la temperatura
     * @return Temperatura actual del gas
     */
    public double getTemperatura()
    {
        return temperatura;
    }
    /**
     * Metodo que retorna el volumen 
     * @return Volumen actual del gas
     */
    public double getVolumen()
    {
        return volumen;
    }
    /**
     * Calcula como se modifica la variable que no se modifico o fijo, en base
     * al tipo de proceso termodinamico que se realiza.
     * @param tipoProceso El tipo de proceso que se realizo para llegar a ese estado
     * @param variableModificada La variable que se modifico 
     */
    public void calcularVariables(String tipoProceso,int variableModificada)
    {
         switch(variableModificada)
         {
            case PRESION:
                if(tipoProceso=="Isometrico")
                {
                    temperatura=((presion+constante_a/(volumen*volumen))*(volumen-constante_b))/R;
                }
                else
                {
                    volumen=aproximarVolumen();
                }
                break;
            case TEMPERATURA:
                if(tipoProceso=="Isobarico")
                {
                    volumen=aproximarVolumen();
                }
                else
                {
                    presion=(R*temperatura)/(volumen-constante_b)-constante_a/(volumen*volumen);
                }
                break;
            case VOLUMEN:
                if(tipoProceso=="Isotermico")
                {
                    presion=(R*temperatura)/(volumen-constante_b)-constante_a/(volumen*volumen);
                }
                else
                {
                    temperatura=((presion+constante_a/(volumen*volumen))*(volumen-constante_b))/R;
                }
                break;
         }
    }
    /**
     * Pinta el gas en VentanaDibujo
     * @param g Componente grafico
     */
    public void pintar(Graphics g)
    {
        Graphics2D grafico=(Graphics2D) g;
        altura=escalaPixeles*((1000*volumen)/(Math.PI*RADIO_PISTON*RADIO_PISTON));
        grafico.setPaint(this.cambiarColor());
        grafico.fillRect((int)puntoInicio.getX(), (int)(puntoFinal.getY()-altura), (int)(puntoFinal.getX()-puntoInicio.getX()), (int)altura);
    }
    /**
     * Cuando se inicializa el hilo se empieza a pintar constantemente
     * */
    public void run(){
        while(true){
            panel.repaint();
        }
    }
}
