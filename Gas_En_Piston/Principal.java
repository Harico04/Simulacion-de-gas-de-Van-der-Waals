/*
 * Ejecucion de la simulacion de gas
 * Elaborado por Fausto Medina, Manuel Gortarez y Alan Torres
 * A 27 de Noviembre de 2023
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

import SistemaTermodinamico.Gas;

class dibujoPrueba extends JPanel
{
    private Gas gasPruebita;
    public dibujoPrueba()
    {
        gasPruebita=new Gas(null, 4, 20.0, 4.0, 370.0, 1.363, 0.03219);
    }
    @Override
    public void paintComponent(Graphics g)
    {
        gasPruebita.pintar(g);
    }
}
public class Principal
{
    public static void main(String[] args)
    {
        JFrame ventanaPruebita= new JFrame("pruebita");
        Dimension dimension=new Dimension(400, 400);
        ventanaPruebita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dibujoPrueba panelPruebita=new dibujoPrueba();
        ventanaPruebita.setLayout(new BorderLayout());
        ventanaPruebita.add(panelPruebita,BorderLayout.CENTER);
        ventanaPruebita.setPreferredSize(dimension);
        ventanaPruebita.setVisible(true);
    }
}