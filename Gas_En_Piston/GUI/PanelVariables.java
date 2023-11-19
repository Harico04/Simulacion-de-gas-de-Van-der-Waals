/*
 * JPanel que muestra los valores de las variables termodinamicas, permite 
 * su modificacion y que se fijen. Muestra creditos en la esquina inferior derecha.
 */

package GUI;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;

public class PanelVariables extends JPanel{

    // Declaracion de variables.
    private JLabel impresionVariables[];
    private JSlider variables[];
    private String procesos[] = { "Isotérmico", "Isobárico", "Isométrico" };
    private JComboBox<String> listaDeProcesos;
    private float temperatura;
    private float presion;
    private float volumen;
    
    
    // Constructor del panel de variables.
    public PanelVariables(){

        inicializar();

        setLayout(new GridLayout(0, 1));

        add(new JLabel("--Tipo de Proceso--"));
        add(listaDeProcesos);
        add(new JLabel("--Temperatura (K)--[260, 400]"));
        add(variables[Constantes.TEMPERATURA]);
        add(impresionVariables[Constantes.TEMPERATURA]);
        add(new JLabel("--Presión (atm)--[.1, 11]"));
        add(variables[Constantes.PRESION]);
        add(impresionVariables[Constantes.PRESION]);
        add(new JLabel("--Volumen (L)--[3, 50]"));
        add(variables[Constantes.VOLUMEN]);
        add(impresionVariables[Constantes.VOLUMEN]);
        add(new JLabel("Elaborado por:"));
        add(new JLabel("Manuel Eduardo Gortarez Blanco"));
        add(new JLabel("Fausto Misael Medina Lugo"));
        add(new JLabel("Alan David Torres Flores"));
    }

    // Se inicializan las variables.
    protected void inicializar(){

        impresionVariables = new JLabel[Constantes.CANTIDAD_VARIABLES];
        for(int i = 0; i < Constantes.CANTIDAD_VARIABLES; ++i)
            impresionVariables[i] = new JLabel();
 
        listaDeProcesos = new JComboBox<>(procesos);

        variables = new JSlider[Constantes.CANTIDAD_VARIABLES];

        for(int i = 0; i < Constantes.CANTIDAD_VARIABLES; ++i)
            variables[i] = new JSlider();

        // Configuramos el deslizador de la temperatura en
        // base a la escala kelvin.
        variables[Constantes.TEMPERATURA].setMinimum(26000);
        variables[Constantes.TEMPERATURA].setMaximum(40000);
        variables[Constantes.TEMPERATURA].setValue(273);
        variables[Constantes.TEMPERATURA].setMajorTickSpacing(2000);
        variables[Constantes.TEMPERATURA].setMinorTickSpacing(500);
        variables[Constantes.TEMPERATURA].setPaintTicks(true);
        variables[Constantes.TEMPERATURA].setPaintLabels(false);
        variables[Constantes.TEMPERATURA].addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e){
                    temperatura = variables[Constantes.TEMPERATURA].getValue()/100.f;
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: " + temperatura);
                }
            });

        // Configuramos el deslizador de la presion en 
        // atmosferas x 10^1.
        variables[Constantes.PRESION].setMinimum(70);
        variables[Constantes.PRESION].setMaximum(1100);
        variables[Constantes.PRESION].setValue(600);
        variables[Constantes.PRESION].setMajorTickSpacing(100);
        variables[Constantes.PRESION].setMinorTickSpacing(25);
        variables[Constantes.PRESION].setPaintTicks(true);
        variables[Constantes.PRESION].setPaintLabels(false);
        variables[Constantes.PRESION].addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e){
                    presion = variables[Constantes.PRESION].getValue()/100.f;
                    impresionVariables[Constantes.PRESION].setText("Valor: " + presion);
                }
            });

        // Configuramos el deslizador del volumen en litros.
        variables[Constantes.VOLUMEN].setMinimum(3);
        variables[Constantes.VOLUMEN].setMaximum(5000);
        variables[Constantes.VOLUMEN].setValue(1000);
        variables[Constantes.VOLUMEN].setMajorTickSpacing(400);
        variables[Constantes.VOLUMEN].setMinorTickSpacing(100);
        variables[Constantes.VOLUMEN].setPaintTicks(true);
        variables[Constantes.VOLUMEN].setPaintLabels(false);
        variables[Constantes.VOLUMEN].addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e){
                    volumen = variables[Constantes.VOLUMEN].getValue()/100.f;
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: " + volumen);
                }
            });
    }

    // vemos que proceso fue seleccionado, retornamos un valor
    // entre el cero y el 2 donde
    // 0 para proceso isotérmico
    // 1 para proceso isobárico
    // 2 para proceso isométrico
    public int cualProceso(){
        return listaDeProcesos.getSelectedIndex();
    }

    // getters y setter de las variables del sistema.
    public float getTemperaturaPanel() {
        return variables[Constantes.TEMPERATURA].getValue()/100.f; //volvemos a la escala normal.
    }
    
    public float getPresionPanel() {
        return variables[Constantes.PRESION].getValue()/100.f;
    }
    
    public float getVolumenPanel() {
        return variables[Constantes.TEMPERATURA].getValue()/100.f;
    }

    public void setTemperaturaPanel(int temperatura){
        variables[Constantes.TEMPERATURA].setValue(temperatura*100);
    }

    public void setPresionPanel(int presion){
        variables[Constantes.PRESION].setValue(presion*100);
    }

    public void setVolumenPanel(int volumen){
        variables[Constantes.VOLUMEN].setValue(volumen*100);
    }
}

class Constantes{
    
    //Enumeraciones
    static final int TEMPERATURA  = 0;
    static final int PRESION = 1;
    static final int VOLUMEN = 2;
    static final int CANTIDAD_VARIABLES = 3;

    static final int ISOMETRICO = 0;
    static final int ISOBARICO = 1;
    static final int ISOTERMICO = 2;
    static final int CANTIDAD_PROCESOS = 3;
}


