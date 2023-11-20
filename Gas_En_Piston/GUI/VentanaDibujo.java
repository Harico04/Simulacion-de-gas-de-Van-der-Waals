/*
 * JPanel en el cual se muestran las figuras dibujadas para la representacion grafica de la simulacion
 */
package GUI;

import SistemaTermodinamico.Molecula;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;
import SistemaTermodinamico.Interseccion;

public class VentanaDibujo extends JPanel
{
    private Gas gas;
    private Piston piston;
    private Molecula moleculas[];
    private Interseccion colisiones;
    private Rectangle pistonPared, pA, pD, pI;
    private Rectangle paredes[] = {pistonPared, pA, pD, pI};
    
    public VentanaDibujo()
    {
        this.gas = new Gas(35.0,6.0,273,1.363,0.03219,new Point(50,100),new Point(700,750), this);
        this.piston= new Piston(new Point(50,100), new Point(700,750), 35.0, this);
        moleculas = new Molecula[1];
        moleculas[0] = new Molecula(new Point(10, 10), 30, 30, this);

        pistonPared = piston.getPiston();
        pA = piston.getPA();
        pD = piston.getPD();
        pI = piston.getPI();
        this.colisiones = new Interseccion(moleculas, paredes);
        colisiones.start();
        moleculas[0].start();
        gas.start();
        piston.start();
        setPreferredSize(new Dimension(700, 700));
    }

    @Override
    public void paintComponent(Graphics grafico)
    {
        super.paintComponent(grafico);
        Graphics2D g2 = (Graphics2D) grafico;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(g2.getBackground());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Dibuja la molecula.
        piston.pintar(grafico);
        gas.pintar(grafico);
        moleculas[0].pintar(g2);
    }

    public Gas getGas(){
        return this.gas;
    }

    public Piston getPiston(){
        return this.piston;
    }
}
