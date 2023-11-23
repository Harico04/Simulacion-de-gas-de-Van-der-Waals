/*
 * Representa un gas contenido en un sistema termodinamico cerrado con un piston.
 * Se expande, contrae, enfria, calienta y varia su presion en base a la ecuacion
 * de Van Der Waals.
 */
package SistemaTermodinamico;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import GUI.VentanaDibujo;

public class Gas extends Thread implements GUI.Figura
{
    private final double R=0.08206;
    private double constante_a;
    private double constante_b;
    private double presion;
    private double volumen;
    private double V_MAX;
    private double temperatura;
    private Point puntoInicio,puntoFinal;
    public final static int PRESION=0,VOLUMEN=1,TEMPERATURA=2;
    private double alturaMaxima=50;
    private final int RADIO_PISTON=20;
    private double altura;
    private int escalaPixeles;

    private VentanaDibujo panel;
    /*******************************************************************************/
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
    /*
     * aproximarVolumen() utiliza el metodo de Newton-Raphson para obtener
     *  un valor aproximado del volumen.
     */
    private double aproximarVolumen()
    {
        double valor=V_MAX/2.0;
        for(int i=0;i<5;i++)
        {
            valor=valor-(presion*Math.pow(valor, 3)-(constante_b*presion+R*temperatura)*Math.pow(valor, 2)+constante_a*valor-constante_a*constante_b)/
            (3*presion*Math.pow(valor, 2)+2*-(constante_b*presion+R*temperatura)*valor+constante_a);
        }
        return valor;
    }
    /*
     * cambiarColor() crea un nuevo color en base a la temperatura, para que al pintar el gas
     * se tenga una mejor representación de la misma.
     */
    public GradientPaint cambiarColor()
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
    public void setVariables(double p,double v,double T)
    {
        this.presion=p;
        this.volumen=v;
        this.temperatura=T;
    }
    /*
     * retorna el valor de presion actual
     */
    public double getPresion()
    {
        return presion;
    }
    /*
     * retorna el valor de temperatura actual
     */
    public double getTemperatura()
    {
        return temperatura;
    }
    /*
     * retorna el valor del volumen actual
     */
    public double getVolumen()
    {
        return volumen;
    }
    /*
     * Calcula como se modifica la variable que no se modifico o fijo, en base
     * al tipo de proceso termodinamico que se realiza.
     */
    public void calcularVariables(String tipoProceso,int variableModificada)
    {
         switch(variableModificada)
         {
            case PRESION:
                if(tipoProceso=="Isometrico")
                {
                    temperatura=((presion+constante_a/(volumen*volumen))*(volumen-constante_b))/R;
                    System.out.println("Temperatura calculada: "+temperatura);
                }
                else
                {
                    volumen=aproximarVolumen();
                    System.out.println("volumen calculado: "+volumen);
                }
                break;
            case TEMPERATURA:
                if(tipoProceso=="Isobarico")
                {
                    volumen=aproximarVolumen();
                    System.out.println("volumen calculado: "+volumen);
                }
                else
                {
                    presion=(R*temperatura)/(volumen-constante_b)-constante_a/(volumen*volumen);
                    System.out.println("presion calculada: "+presion);
                }
                break;
            case VOLUMEN:
                if(tipoProceso=="Isotermico")
                {
                    presion=(R*temperatura)/(volumen-constante_b)-constante_a/(volumen*volumen);
                    System.out.println("presion calculada: "+presion);
                }
                else
                {
                    temperatura=((presion+constante_a/(volumen*volumen))*(volumen-constante_b))/R;
                    System.out.println("Temperatura calculada: "+temperatura);
                }
                break;
         }
    }
    /*
     * Pinta el gas en VentanaDibujo
     */
    public void pintar(Graphics g)
    {
        Graphics2D grafico=(Graphics2D) g;
        altura=escalaPixeles*((1000*volumen)/(Math.PI*RADIO_PISTON*RADIO_PISTON));
        grafico.setPaint(this.cambiarColor());
        grafico.fillRect((int)puntoInicio.getX(), (int)(puntoFinal.getY()-altura), (int)(puntoFinal.getX()-puntoInicio.getX()), (int)altura);
    }

    public void run(){
        while(true){
            panel.repaint();
        }
    }
}
