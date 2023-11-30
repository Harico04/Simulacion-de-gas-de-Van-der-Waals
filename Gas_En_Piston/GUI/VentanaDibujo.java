
package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import sistemaTermodinamico.Molecula;
import sistemaTermodinamico.Gas;
import sistemaTermodinamico.Piston;
import sistemaTermodinamico.Colision;

/**
 * JPanel en el cual se muestran las figuras dibujadas para la representacion grafica de la simulacion
 * @author Fausto Medina
 */
public class VentanaDibujo extends JPanel
{
    /**El gas a dibujar*/
    private Gas gas;
    /**El piston a dibujar*/
    private Piston piston;
    /**Arreglo que contiene las moleculas a dibujar*/
    private Molecula moleculas[];
    /**Objeto para calcular las colisiones*/
    private Colision colision;
    /**Crea un panel VentanaDibujo*/
    public VentanaDibujo()
    {
        this.gas = new Gas(35.0,6.0,273,1.363,0.03219,new Point(50,100),new Point(700,750), this,50.0);
        this.piston= new Piston(new Point(50,100), new Point(700,750), 22.27, this,20);
        moleculas = new Molecula[30];
        int indiceAux=0;
        for(int i=0;i<6;i++)
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
    /**
     * Metodo para pintar en el panel 
     * @param grafico Componente grafico
     * */
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
    /**
     * Retorna el gas a dibujar
     * @return El gas a dibujar
     * */
    public Gas getGas(){
        return this.gas;
    }
    /**
     * Retorna el piston a dibujar
     *@return El piston a dibujar
     *
     * */
    public Piston getPiston(){
        return this.piston;
    }
    /**
     * Retorna el arreglo de moleculas a dibujar
     *@return El arreglo de moleculas que se dibujan en el panel
     * */
    public Molecula[] getMoleculas(){
        return this.moleculas;
    }
}
