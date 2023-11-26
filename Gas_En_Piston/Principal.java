/*
 * Ejecucion de la simulacion de gas
 * Elaborado por Fausto Medina, Manuel Gortarez y Alan Torres
 * A 27 de Noviembre de 2023
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import GUI.PanelVariables;
import GUI.VentanaDibujo;
import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;

public class Principal
{
    public static void main(String[] args) {
        JFrame ven = new JFrame();
        VentanaDibujo ventanaDibujo = new VentanaDibujo();
        PanelVariables panelVariables = new PanelVariables(ventanaDibujo);
        ven.setLayout(new BorderLayout());
        ven.add(ventanaDibujo, BorderLayout.CENTER);
        ven.add(panelVariables, BorderLayout.EAST);
        ven.setSize(new Dimension(1000, 1000));
        ven.setLocationRelativeTo(null);
        ven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Inicio del timer para manejar fps
        int fps=120;
        Timer timer=new Timer(1000/fps,new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               ventanaDibujo.repaint();
            }
        });
        timer.start();
        ven.setVisible(true);  
    }
}
