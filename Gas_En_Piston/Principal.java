/*
 * Ejecucion de la simulacion de gas
 * Elaborado por Fausto Medina, Manuel Gortarez y Alan Torres
 * A 27 de Noviembre de 2023
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import GUI.PanelVariables;
import GUI.VentanaDibujo;
import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;

public class Principal
{
    public static void main(String[] args) {
        JFrame ven = new JFrame();
        ven.setLayout(new BorderLayout());
        Gas gas=new Gas(35.0,6.0,273,1.363,0.03219,new Point(50,100),new Point(700,750));
        Piston piston=new Piston(new Point(50,100), new Point(700,750), 35.0);
        ven.add(new PanelVariables(gas,piston), BorderLayout.EAST);
        ven.add(new VentanaDibujo(gas,piston), BorderLayout.CENTER);
        ven.setSize(new Dimension(1000, 1000));
        ven.setLocationRelativeTo(null);
        ven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ven.setVisible(true);  
    }
}
