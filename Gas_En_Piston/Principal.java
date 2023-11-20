/*
 * Ejecucion de la simulacion de gas
 * Elaborado por Fausto Medina, Manuel Gortarez y Alan Torres
 * A 27 de Noviembre de 2023
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import GUI.PanelVariables;
import GUI.VentanaDibujo;

public class Principal
{
    public static void main(String[] args) {
        JFrame ven = new JFrame();
        ven.setLayout(new BorderLayout());
        ven.add(new PanelVariables(), BorderLayout.EAST);
        ven.add(new VentanaDibujo(), BorderLayout.CENTER);
        ven.setSize(new Dimension(1000, 1000));
        ven.setLocationRelativeTo(null);
        ven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ven.setVisible(true);  
    }
}
