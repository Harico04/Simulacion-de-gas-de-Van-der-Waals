/*
 * Representa un gas contenido en un sistema termodinamico cerrado con un piston.
 * Se expande, contrae, enfria, calienta y varia su presion en base a la ecuacion
 * de Van Der Waals.
 */
package SistemaTermodinamico;

import java.awt.Color;
import java.awt.Graphics;

public class Gas extends GUI.Figura
{
    private final double R=0.08206;
    private double constante_a;
    private double constante_b;
    private double presion;
    private double volumen;
    private double temperatura;
    final static int PRESION=0,VOLUMEN=1,TEMPERATURA=2;
    private final int RADIO_PISTON=20;
    /*******************************************************************************/
    public Gas(Color c, int gr, double v, double p, double T,double a, double b)
    {
        this.color=c;
        this.grosor=gr;
        this.setVariables(p,v,T);
        constante_a=a;
        constante_b=b;
    }
    /*
     * aproximarVolumen() utiliza el metodo de Newton-Raphson para obtener
     *  un valor aproximado del volumen.
     */
    private static double aproximarVolumen(double a,double b,double c, double d)
    {
        double valor=25.0;
        for(int i=0;i<5;i++)
        {
            valor=valor-(a*Math.pow(valor, 3)+b*Math.pow(valor, 2)+c*valor+d)/
            (3*a*Math.pow(valor, 2)+2*b*valor+c);
        }
        return valor;
    }
    /*
     * cambiarColor() crea un nuevo color en base a la temeperatura, para que al pintar el gas
     * se tenga una mejor representación de la misma.
     */
    public Color cambiarColor()
    {
        if(temperatura<281)
        {
            return new Color(0,255,(int)(255-12.75*(temperatura%260)),150);
        }
        if(temperatura<341)
        {
            return new Color((int)(4.25*(temperatura%280)),255,0,150);
        }
        return new Color(255,(int)(255-4.25*(temperatura%340)),0,150);
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
    public void calcularVariables(int variableFijada,int variableModificada)
    {
         switch(variableModificada)
         {
            case PRESION:
                if(temperatura!=variableFijada)
                {
                    temperatura=((presion+constante_a/(volumen*volumen))*(volumen-constante_b))/R;
                }
                else
                {
                    volumen=aproximarVolumen(presion, -(R*temperatura+presion*constante_b), constante_a, -constante_a*constante_b);
                }
                break;
            case TEMPERATURA:
                if(volumen!=variableFijada)
                {
                    volumen=aproximarVolumen(presion, -(R*temperatura+presion*constante_b), constante_a, -constante_a*constante_b);;
                }
                else
                {
                    presion=(R*temperatura)/(volumen-constante_b)-constante_a/(volumen*volumen);
                }
                break;
            case VOLUMEN:
                if(temperatura!=variableFijada)
                {
                    temperatura=((presion+constante_a/(volumen*volumen))*(volumen-constante_b))/R;
                }
                else
                {
                    presion=(R*temperatura)/(volumen-constante_b)-constante_a/(volumen*volumen);

                }
                break;
         }
    }
    /*
     * Pinta el gas en el DibujoPanel
     */
    @Override
    public void pintar(Graphics grafico)
    {
        double altura=volumen/(Math.PI*RADIO_PISTON*RADIO_PISTON);
        grafico.setColor(this.cambiarColor());
        grafico.fillRect(50, (int)altura, 100, 100);
    }
}
