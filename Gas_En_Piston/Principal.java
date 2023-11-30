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
        //Crea una nueva ventana
        JFrame ven = new JFrame();
        //Crea un nuevo panel sobre el que se va a pintar la representacion grafica del piston 
        VentanaDibujo ventanaDibujo = new VentanaDibujo();
    //Crea un nuevo JPanel sobre el que se muestran los valores de las variables y los sliders para modificar sus valores, adicionalmente muestra los creditos en la esquina inferior derecha
        PanelVariables panelVariables = new PanelVariables(ventanaDibujo);
    //Establece el layout
        ven.setLayout(new BorderLayout());
    //Agrega el panel de dibujo a la ventana
        ven.add(ventanaDibujo, BorderLayout.CENTER);
    //Agrega el panel donde se muestran las variables a la ventana
        ven.add(panelVariables, BorderLayout.EAST);
    //Establece el tamaño de la ventana
        ven.setSize(new Dimension(1000, 1000));
    //Establece la localizacion de la ventana en el centro
        ven.setLocationRelativeTo(null);
    //Habilita el boton de cerrar la ventana 
        ven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Hace que la ventana se muestre
        ven.setVisible(true);  
    }
}
