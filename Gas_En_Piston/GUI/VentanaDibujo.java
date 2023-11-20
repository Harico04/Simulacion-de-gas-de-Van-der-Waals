/*
 * JPanel en el cual se muestran las figuras dibujadas para la representacion grafica de la simulacion
 */
package GUI;

import java.awt.Graphics;

import javax.swing.JPanel;

import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;

public class VentanaDibujo extends JPanel
{
    Gas gas;
    Piston piston;
    public VentanaDibujo(Gas g, Piston p)
    {
        gas=g;
        piston=p;
    }
    @Override
    public void paintComponent(Graphics grafico)
    {
        piston.pintar(grafico);
        gas.pintar(grafico);
    }
}
