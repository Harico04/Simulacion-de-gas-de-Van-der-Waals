package Ciclos;

public class Estado {
    private double temperatura;
    private double presion;
    private double volumen;
    private String tipoDeProceso;

    public Estado(String tipoDeProceso, double temperatura, double volumen, double presion) {
        this.temperatura = temperatura;
        this.presion = presion;
        this.volumen = volumen;
        this.tipoDeProceso = tipoDeProceso;
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

    @Override
    public String toString() {
        return "Tipo de proceso: " + tipoDeProceso + "\nTemperatura: " + temperatura +
               "\nVolumen: " + volumen + "\nPresion: " + presion;
    }
}
