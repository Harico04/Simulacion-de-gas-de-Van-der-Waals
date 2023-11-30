
package ciclos;

/**
 * Representa el estado en el que se encuentra el piston
 * y el tipo de proceso que se uso para llegar a el
 * @author Alan Torres
 */
public class Estado {
    private double temperatura;
    private double presion;
    private double volumen;
    private String tipoDeProceso;
    private int variableModificada;
    /**
     *Crea un estado basandose en sus valores de temperatura, volumen y presion, tomando en cuenta el tipo de proceso
     *que se utilizo para llegar al estado actual y la variable que se modifico para llegar a el
     *@param tipoDeProceso    El tipo de proceso por el cual se ha llegado al estado actual
     *@param temperatura      La temperatura del gas en el estado actual
     *@param volumen          El Volumen del gas en el estado actual
     *@param presion          La presion del estado actual
     * @param variableModificada      La variable que se ha modificado para llegar al estado actual
     * */
    public Estado(String tipoDeProceso, double temperatura, double volumen, double presion, int variableModificada) {
        this.temperatura = temperatura;
        this.presion = presion;
        this.volumen = volumen;
        this.tipoDeProceso = tipoDeProceso;
        this.variableModificada = variableModificada;
  }

    // Getters
 /**
     * Obtiene la temperatura del estado.
     *
     * @return La temperatura del estado.
     */
    public double getTemperatura() {
        return temperatura;
    }

    /**
     * Obtiene la presión del estado.
     *
     * @return La presión del estado.
     */
    public double getPresion() {
        return presion;
    }

    /**
     * Obtiene el volumen del estado.
     *
     * @return El volumen del estado.
     */
    public double getVolumen() {
        return volumen;
    }

    /**
     * Obtiene el tipo de proceso por el cual se ha llegado al estado.
     *
     * @return El tipo de proceso del estado.
     */
    public String getTipoDeProceso() {
        return tipoDeProceso;
    }

    /**
     * Obtiene el valor de la variable modificada para llegar al estado.
     *
     * @return El valor de la variable modificada en el estado.
     */
    public int getVariableModificada() {
        return variableModificada;
    }

    // Setters

    /**
     * Establece la temperatura del estado.
     *
     * @param temperatura Nuevo valor de la temperatura.
     */
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    /**
     * Establece la presión del estado.
     *
     * @param presion Nuevo valor de la presión.
     */
    public void setPresion(double presion) {
        this.presion = presion;
    }

    /**
     * Establece el volumen del estado.
     *
     * @param volumen Nuevo valor del volumen.
     */
    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    /**
     * Establece el tipo de proceso asociado al estado.
     *
     * @param tipoDeProceso Nuevo valor del tipo de proceso.
     */
    public void setTipoDeProceso(String tipoDeProceso) {
        this.tipoDeProceso = tipoDeProceso;
    }

    /**
     * Establece el valor de la variable modificada en el estado.
     *
     * @param variableModificada Nuevo valor de la variable modificada.
     */
    public void setVariableModificada(int variableModificada) {
        this.variableModificada = variableModificada;
    }

    /**
     * Retorna los datos del estado en forma de cadena
     *
     * @return Una cadena de texto con la información sobre el estado.
     */
    @Override
    public String toString() {
        return "Tipo de proceso: " + tipoDeProceso + "\nTemperatura: " + temperatura +
               "\nVolumen: " + volumen + "\nPresión: " + presion + "\nVariable Modificada: " + variableModificada;
    }
}
