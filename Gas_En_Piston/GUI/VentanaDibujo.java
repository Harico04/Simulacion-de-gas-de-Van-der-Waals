/*
 * JPanel en el cual se muestran las figuras dibujadas para la representacion grafica de la simulacion
 */
package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import SistemaTermodinamico.Molecula;
import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;
import SistemaTermodinamico.Colision;

public class VentanaDibujo extends JPanel
{
    private Gas gas;
    private Piston piston;
    private Molecula moleculas[];
    private Colision colision;

    public VentanaDibujo()
    {
        this.gas = new Gas(35.0,6.0,273,1.363,0.03219,new Point(50,100),new Point(700,750), this,50.0);
        this.piston= new Piston(new Point(50,100), new Point(700,750), 22.27, this,20);
        moleculas = new Molecula[20];
        int indiceAux=0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<5;j++)
            {
                moleculas[indiceAux]=new Molecula(new Point(250+30*j, 600+30*i), 10, piston.getParedes(), this);
                indiceAux++;
            }
        }          
        colision = new Colision(moleculas);

        gas.start();
        piston.start();
        for(Molecula molecula: moleculas)
            molecula.start();
        colision.start();        
      
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
        piston.pintar(g2);
        gas.pintar(g2);
         for(Molecula molecula: moleculas)
            molecula.pintar(g2);
    }

    public Gas getGas(){
        return this.gas;
    }

    public Piston getPiston(){
        return this.piston;
    }

    public Molecula[] getMoleculas(){
        return this.moleculas;
    }
}
