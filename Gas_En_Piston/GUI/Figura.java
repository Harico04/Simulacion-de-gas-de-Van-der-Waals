
package GUI;
import java.awt.Graphics;

/**
 * Contiene lo esencial para que se realice un dibujo en el panel de dibujo.
 * @author Fausto Medina
 */
public interface Figura
{
  /**
   *
   * Este metodo pinta los componentes
   * @param grafico Componente grafico
   * */
    public void pintar(Graphics grafico);
}
