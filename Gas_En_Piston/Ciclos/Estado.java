/**
 * Representa el estado en el que se encuentra el piston
 * y el tipo de proceso que se uso para llegar a el
 */
package Ciclos;

public class Estado {
    private double temperatura;
    private double presion;
    private double volumen;
    private String tipoDeProceso;
    private int variableModificada;

    public Estado(String tipoDeProceso, double temperatura, double volumen, double presion, int variableModificada) {
        this.temperatura = temperatura;
        this.presion = presion;
        this.volumen = volumen;
        this.tipoDeProceso = tipoDeProceso;
        this.variableModificada = variableModificada;
  }

    // Getters
    public double getTemperatura() {
        return temperatura;
    }

    public double getPresion() {
        return presion;
    }

    public double getVolumen() {
        return volumen;
    }

    public String getTipoDeProceso() {
        return tipoDeProceso;
    }
    public int getVariableModificada(){
        return variableModificada;
    }
  
    // Setters
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public void setTipoDeProceso(String tipoDeProceso) {
        this.tipoDeProceso = tipoDeProceso;
    }
    public void setVariableModificada(int variableModificada){
    this.variableModificada = variableModificada;
  }

    @Override
    public String toString() {
        return "Tipo de proceso: " + tipoDeProceso + "\nTemperatura: " + temperatura +
               "\nVolumen: " + volumen + "\nPresion: " + presion + "\nVariable Modificada: " + variableModificada;

    }
}
