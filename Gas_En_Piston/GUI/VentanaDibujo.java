/*
 * JPanel en el cual se muestran las figuras dibujadas para la representacion grafica de la simulacion
 */
package GUI;

import SistemaTermodinamico.Molecula;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;

public class VentanaDibujo extends JPanel
{
    Gas gas;
    Piston piston;
    private Molecula molecula;
    
    public VentanaDibujo(Gas g, Piston p)
    {
        gas=g;
        piston=p;
        this.molecula = new Molecula(new Point(10, 10), 30, 30, this);
        setPreferredSize(new Dimension(700, 700));
        molecula.start();
    }

    @Override
    public void paintComponent(Graphics grafico)
    {
        super.paintComponent(grafico);
        piston.pintar(grafico);
        gas.pintar(grafico);
    }
}
