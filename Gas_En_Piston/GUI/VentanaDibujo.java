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
        piston.pintar(grafico);
        gas.pintar(grafico);
         Graphics2D g2 = (Graphics2D) grafico;

        // hace mas suave el dibujo de la molecula
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Limpiamos la pantalla para que la molecula se dibuje en un panel limpio.
        g2.setPaint(g2.getBackground());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                
        // Dibuja la molecula.
         molecula.pintar(g2);    
       
    }
}
