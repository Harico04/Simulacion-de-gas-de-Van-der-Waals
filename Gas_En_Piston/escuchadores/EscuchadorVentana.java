

package escuchadores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
/**
 *Una implementacion de WindowAdapter
 *@author Alan Torres
 */
public class EscuchadorVentana extends WindowAdapter {
    /**
     * Constructor default de EscuchadorVentana
     * */
    public EscuchadorVentana(){}

    // Métodos de WindowAdapter

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
      JOptionPane.showMessageDialog(null, "Gracias por usar nuestro programa");
      JOptionPane.showMessageDialog(null,"\tAutores:\nManuel Eduardo Gortarez Blanco\nFausto Misael Medina Lugo\nAlan David Torres Flores");
    }

    @Override
    public void windowClosed(WindowEvent e) {
    
  }


      
      
    

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
  }

    @Override
    public void windowDeactivated(WindowEvent e) {
}

}
