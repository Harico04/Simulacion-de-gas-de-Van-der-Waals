/*
 * Ejecucion de la simulacion de gas
 * Elaborado por Fausto Medina, Manuel Gortarez y Alan Torres
 * A 27 de Noviembre de 2023
 */
import javax.swing.JFrame;
import GUI.PanelVariables;

public class Principal
{
    public static void main(String[] args) {


        JFrame ven = new JFrame();
        ven.add(new PanelVariables());
        ven.setVisible(true);
    }
}
