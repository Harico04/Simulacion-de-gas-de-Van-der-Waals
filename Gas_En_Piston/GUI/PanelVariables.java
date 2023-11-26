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

import Ciclos.Estado;
import Ciclos.Lector;
import SistemaTermodinamico.Gas;
import SistemaTermodinamico.Piston;

import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class PanelVariables extends JPanel{

    // Declaracion de variables.
    private JLabel impresionVariables[];
    private JSlider variables[];
    private String procesos[] = { "Isotermico", "Isobarico", "Isometrico" },ciclos[]={"------","Ericsson","Stirling"};
    private JComboBox<String> listaDeProcesos;
    private JComboBox<String> listadoCiclos;
    private double temperatura;
    private double presion;
    private double volumen;
    private Gas gas;
    private Piston piston;
    EscuchadorPresion escPresion;
    EscuchadorTemperatura escTemperatura;
    EscuchadorVolumen escVolumen;
    private final double P_MAX=5,V_MAX=50,T_MAX=600;
    private final double P_MIN=1,V_MIN=2,T_MIN=135;
    private Lector lector;
    private boolean cicloActivo=false;
    // Constructor del panel de variables.
    public PanelVariables(VentanaDibujo ventanaDibujo){

                
        gas= ventanaDibujo.getGas();
        piston= ventanaDibujo.getPiston();
        
        inicializar();

        setLayout(new GridLayout(0, 1));

        add(new JLabel("--Tipo de Proceso--"));
        add(listaDeProcesos);
        add(new JLabel("--Temperatura (K)--[135, 600]"));
        add(variables[Constantes.TEMPERATURA]);
        add(impresionVariables[Constantes.TEMPERATURA]);
        add(new JLabel("--Presión (atm)--[1, 5]"));
        add(variables[Constantes.PRESION]);
        add(impresionVariables[Constantes.PRESION]);
        add(new JLabel("--Volumen (L)--[1, 50]"));
        add(variables[Constantes.VOLUMEN]);
        add(impresionVariables[Constantes.VOLUMEN]);
        add(new JLabel("Elaborado por:"));
        add(new JLabel("Manuel Eduardo Gortarez Blanco"));
        add(new JLabel("Fausto Misael Medina Lugo"));
        add(new JLabel("Alan David Torres Flores"));
    }
    class EscuchadorPresion implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            double auxPresion=presion;
             presion = variables[Constantes.PRESION].getValue()/100.0f;
                    impresionVariables[Constantes.PRESION].setText("Valor: " + presion);
                    gas.setVariables(presion, volumen, temperatura);
                    gas.calcularVariables(listaDeProcesos.getSelectedItem().toString(),Gas.PRESION);
                    if(gas.getVolumen()>V_MAX||gas.getVolumen()<V_MIN||gas.getTemperatura()>T_MAX||gas.getTemperatura()<T_MIN)
                    {
                        variables[Constantes.PRESION].setValue((int)(auxPresion*100));
                        gas.setVariables(presion, volumen, temperatura);
                        return;
                    }
                    if(listaDeProcesos.getSelectedItem().toString()=="Isometrico")
                    {
                        variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                        temperatura=gas.getTemperatura();
                        variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                        impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                        variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                    } 
                    else
                    {
                        variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                        volumen=gas.getVolumen();
                        variables[Constantes.VOLUMEN].setValue((int)(volumen*100.0));
                        impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                        piston.setVolumen(volumen,20);
                        variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                    } 
        }
    }
    class EscuchadorTemperatura implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            double auxTemperatura=temperatura;
            temperatura = variables[Constantes.TEMPERATURA].getValue()/100.0f;
            impresionVariables[Constantes.TEMPERATURA].setText("Valor: " + temperatura);
            gas.setVariables(presion, volumen, temperatura);
            gas.calcularVariables(listaDeProcesos.getSelectedItem().toString(),Gas.TEMPERATURA);
            if(gas.getVolumen()>V_MAX||gas.getVolumen()<V_MIN||gas.getPresion()>P_MAX||gas.getPresion()<P_MIN)
            {
                variables[Constantes.TEMPERATURA].setValue((int)(auxTemperatura*100));
                gas.setVariables(presion, volumen, temperatura);
                return;
            }
            if(listaDeProcesos.getSelectedItem().toString()=="Isobarico")
            { 
                variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                volumen=gas.getVolumen();
                variables[Constantes.VOLUMEN].setValue((int)(volumen*100.0));
                impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                piston.setVolumen(volumen,20);
                variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
            }
            else
            {
                variables[Constantes.PRESION].removeChangeListener(escPresion);
                presion=gas.getPresion();
                variables[Constantes.PRESION].setValue((int)(presion*100.0));
                impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                variables[Constantes.PRESION].addChangeListener(escPresion);
            } 
        }
    }
    class EscuchadorVolumen implements ChangeListener
    {

        public void stateChanged(ChangeEvent e)
        {
            double auxVolumen=volumen;
             volumen = variables[Constantes.VOLUMEN].getValue()/100.0f;
            impresionVariables[Constantes.VOLUMEN].setText("Valor: " + volumen);
            gas.setVariables(presion, volumen, temperatura);
            piston.setVolumen(volumen,20);
            gas.calcularVariables(listaDeProcesos.getSelectedItem().toString(),Gas.VOLUMEN);
            if(gas.getTemperatura()>T_MAX||gas.getTemperatura()<T_MIN||gas.getPresion()>P_MAX||gas.getPresion()<P_MIN)
            {
                variables[Constantes.VOLUMEN].setValue((int)(auxVolumen*100));
                gas.setVariables(presion, volumen, temperatura);
                return;
            }
            if(listaDeProcesos.getSelectedItem().toString()=="Isotermico")
            {
                variables[Constantes.PRESION].removeChangeListener(escPresion);
                presion=gas.getPresion();
                variables[Constantes.PRESION].setValue((int)(presion*100.0));
                impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                variables[Constantes.PRESION].addChangeListener(escPresion);
            } 
            else
            {
                variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                temperatura=gas.getTemperatura();
                variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
            } 
        }
    }
    // Se inicializan las variables.
    protected void inicializar(){

        impresionVariables = new JLabel[Constantes.CANTIDAD_VARIABLES];
        for(int i = 0; i < Constantes.CANTIDAD_VARIABLES; ++i)
            impresionVariables[i] = new JLabel();
 
        listaDeProcesos = new JComboBox<>(procesos);

        /*
         * Se otorga un ItemListener a listaDeProcesos, para poder establecer el tipo de proceso con el que se esta tratando.
         */
        listaDeProcesos.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange()==ItemEvent.SELECTED){
                    String seleccionado= listaDeProcesos.getSelectedItem().toString();
                    switch(seleccionado)
                    {
                        case "Isotermico":
                            variables[Constantes.TEMPERATURA].setEnabled(false);
                            variables[Constantes.PRESION].setEnabled(true);
                            variables[Constantes.VOLUMEN].setEnabled(true);
                            break;
                        case "Isobarico":
                            variables[Constantes.TEMPERATURA].setEnabled(true);
                            variables[Constantes.PRESION].setEnabled(false);
                            variables[Constantes.VOLUMEN].setEnabled(true);
                            break;
                        case "Isometrico":
                            variables[Constantes.TEMPERATURA].setEnabled(true);
                            variables[Constantes.PRESION].setEnabled(true);
                            variables[Constantes.VOLUMEN].setEnabled(false);
                            break;
                    }
                }
            }
        });
        variables = new JSlider[Constantes.CANTIDAD_VARIABLES];

        for(int i = 0; i < Constantes.CANTIDAD_VARIABLES; ++i)
            variables[i] = new JSlider();

        //Inicializamos los escuchadores. Se mantiene un puntero a ellos para
        //poder removerlos y quitarlos.
        escPresion= new EscuchadorPresion();
        escTemperatura=new EscuchadorTemperatura();
        escVolumen=new EscuchadorVolumen();

        // Configuramos el deslizador de la temperatura en
        // base a la escala kelvin.
        variables[Constantes.TEMPERATURA].setMinimum((int)(T_MIN*100));
        variables[Constantes.TEMPERATURA].setMaximum((int)(T_MAX*100));
        temperatura=273;
        variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100.0));
        variables[Constantes.TEMPERATURA].setMajorTickSpacing(2000);
        variables[Constantes.TEMPERATURA].setMinorTickSpacing(500);
        variables[Constantes.TEMPERATURA].setPaintTicks(true);
        variables[Constantes.TEMPERATURA].setPaintLabels(false);
        variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
        impresionVariables[Constantes.TEMPERATURA].setText("Valor: " + temperatura);


        // Configuramos el deslizador de la presion en 
        // atmosferas x 10^1.
        variables[Constantes.PRESION].setMinimum((int)(P_MIN*100));
        variables[Constantes.PRESION].setMaximum((int)(P_MAX*100));
        presion=1;
        variables[Constantes.PRESION].setValue((int)(presion*100.0));
        variables[Constantes.PRESION].setMajorTickSpacing(100);
        variables[Constantes.PRESION].setMinorTickSpacing(25);
        variables[Constantes.PRESION].setPaintTicks(true);
        variables[Constantes.PRESION].setPaintLabels(false);
        variables[Constantes.PRESION].addChangeListener(escPresion);
        impresionVariables[Constantes.PRESION].setText("Valor: " + presion);

        // Configuramos el deslizador del volumen en litros.
        variables[Constantes.VOLUMEN].setMinimum((int)(V_MIN*100));
        variables[Constantes.VOLUMEN].setMaximum((int)(V_MAX*100));
        volumen=22.27;
        variables[Constantes.VOLUMEN].setValue((int)(volumen*100));
        variables[Constantes.VOLUMEN].setMajorTickSpacing(400);
        variables[Constantes.VOLUMEN].setMinorTickSpacing(100);
        variables[Constantes.VOLUMEN].setPaintTicks(true);
        variables[Constantes.VOLUMEN].setPaintLabels(false);
        variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
        impresionVariables[Constantes.VOLUMEN].setText("Valor: " + volumen);
        
        gas.setVariables(presion, volumen, temperatura);
        piston.setVolumen(volumen,20.0);

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


