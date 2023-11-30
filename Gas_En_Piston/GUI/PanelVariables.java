

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
import SistemaTermodinamico.Molecula;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

/**
 * JPanel que muestra los valores de las variables termodinamicas, permite 
 * su modificacion y que se fijen. Muestra creditos en la esquina inferior derecha.
 */
public class PanelVariables extends JPanel{

    // Declaracion de variables.
    /**
     * Arrelo que contiene las variables a imprimir
     * */
    private JLabel impresionVariables[];
  /**
   * Arreglo que contiene las variables a modificar
   * */
    private JSlider variables[];
  /**
   * Arreglo que contiene los procesos que se pueden dar en el piston
   * */
    private String procesos[] = { "Isotermico", "Isobarico", "Isometrico" },ciclos[]={"------","Ericsson","Stirling"};
    /**
     * Lista que despliega los procesos 
     * */
    private JComboBox<String> listaDeProcesos;
  /**
   * Lista que despliega los ciclos disponibles que se pueden ejecutar
   * */
    private JComboBox<String> listadoCiclos;
  /**
   * Temperatura del gas
   * */
    private double temperatura;
  /**
   * Presion del gas
   * */
    private double presion;
  /**
   * Volumen del gas
   * */
    private double volumen;
  /**
   * Gas del sistema termodinamico
   * */
    private Gas gas;
  /**
   * Piston que representa las paredes del sistema
   * */
    private Piston piston;
  /**
   *Escuchador para la presion 
   * */
    EscuchadorPresion escPresion;
  /**
   *Escuchador de la temperatura
   * */
    EscuchadorTemperatura escTemperatura;
  /**
   *Escuchador del volumen
   * */
    EscuchadorVolumen escVolumen;
    
  /**
   * Valor máximo permitido para la presión en el estado, 
   */
  private final double P_MAX = 5;

  /**
   * Valor máximo permitido para el volumen en el estado, 
   */
  private final double V_MAX = 50;

  /**
   * Valor máximo permitido para la temperatura en el estado.
   */
  private final double T_MAX = 600;

  /**
   * Valor mínimo permitido para la presión en el estado, 
   */
  private final double P_MIN = 1;

  /**
   * Valor mínimo permitido para el volumen en el estado, 
   */
  private final double V_MIN = 2;

  /**
   * Valor mínimo permitido para la temperatura en el estado.
   */
  private final double T_MIN = 135;
  /**
   * Lienzo sobre el que se pinta la representacion grafica del sistema
   * */
    private VentanaDibujo lienzo;
  /**
   * Lector que lee el archivo y almacena sus valores para generar ciclos
   * */
    private Lector lector;
  /**
   * Manejador de ciclos del sistema
   * */
    private ManejadorCiclos manCiclos;

    /**
     * Arreglo de moleculas 
     * */
    private Molecula[] moleculas;
    // Constructor del panel de variables.
    /**
     * Constructor del panel de variables
     * @param ventanaDibujo Panel sobre el cual se va a dibujar la representacion grafica del sistema
     * */
    public PanelVariables(VentanaDibujo ventanaDibujo){

                
        gas= ventanaDibujo.getGas();
        piston= ventanaDibujo.getPiston();
        moleculas = ventanaDibujo.getMoleculas();
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
        add(new JLabel("CICLOS:"));
        add(listadoCiclos);
        add(new JLabel("Elaborado por:"));
        add(new JLabel("Manuel Eduardo Gortarez Blanco"));
        add(new JLabel("Fausto Misael Medina Lugo"));
        add(new JLabel("Alan David Torres Flores"));
    }
    /**
     * Escuchador del valor de la presion
     * */
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
                        for(Molecula molecula: moleculas)
                            molecula.setTemperatura(variables[Constantes.TEMPERATURA].getValue()/100.00);
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
    /**
     * Escuchador sobre el valor de temperatura
     * */
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

            for(Molecula molecula: moleculas)
                molecula.setTemperatura(variables[Constantes.TEMPERATURA].getValue()/100.00);                
        }
    }
    /**
     * Escuchador sobre el valor del volumen
     * */
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
                for(Molecula molecula: moleculas)
                molecula.setTemperatura(variables[Constantes.TEMPERATURA].getValue()/100.00);
            } 
        }
    }
    // Se inicializan las variables.
     /**
      *Metodo para inicializar variables
      * */
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
        variables[Constantes.TEMPERATURA].setEnabled(false);
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

        //Creamos la seccion de ciclos
        listadoCiclos= new JComboBox<>(ciclos);
        listadoCiclos.setSelectedItem(ciclos[0]);
        //Comenzamos el manejador de ciclos, que estara pendiente
        //De cuando se desee realizar uno
        manCiclos=new ManejadorCiclos();
        manCiclos.start();
    }

    // vemos que proceso fue seleccionado, retornamos un valor
    // entre el cero y el 2 donde
    // 0 para proceso isotérmico
    // 1 para proceso isobárico
    // 2 para proceso isométrico
    /**
     * Metodo que determina cual proceso fue seleccionado
     * 0 para Isotermico
     * 1 para Isobarico
     * 2 para Isometrico
     * @return El tipo de proceso seleccionado
     * */
    public int cualProceso(){
        return listaDeProcesos.getSelectedIndex();
    }

    // getters y setter de las variables del sistema.
    /**
     *Retorna el valor de la temperatura mostrada en el panel
     *@return Valor de la temperatura mostrada en el panel
     * */
    public float getTemperaturaPanel() {
        return variables[Constantes.TEMPERATURA].getValue()/100.f; //volvemos a la escala normal.
    }
    /**
     * Retorna el valor de la presion mostrada en el panel
     * @return Presion mostrada en el panel
     * */ 
    public float getPresionPanel() {
        return variables[Constantes.PRESION].getValue()/100.f;
    }
    /**
     *Retorna el valor del volumen mostrado
     *@return Volumen mostrado en el panel
     * */ 
    public float getVolumenPanel() {
        return variables[Constantes.TEMPERATURA].getValue()/100.f;
    }
    /**
     *Establece un nuevo valor para la temperatura mostrada en el panel
     *@param temperatura Nuevo valor de la temperatura
     * */
    public void setTemperaturaPanel(int temperatura){
        variables[Constantes.TEMPERATURA].setValue(temperatura*100);
    }
    /**
     * Establece un nuevo valor para la presion mostrada en el panel
     * @param presion Nuevo valor de la presion
     * */
    public void setPresionPanel(int presion){
        variables[Constantes.PRESION].setValue(presion*100);
    }
    /**
     * Establece un nuevo valor de la temperatura mostrada en el panel
     * @param volumen Nuevo valor del volumen
     * */
    public void setVolumenPanel(int volumen){
        variables[Constantes.VOLUMEN].setValue(volumen*100);
    }
/**
 * Clase encargada de manejar los ciclos en el sistema termodinamico
 * */
class ManejadorCiclos extends Thread
{
    public void run()
    {
        String seleccionado= listadoCiclos.getSelectedItem().toString();
        while(true)
        {
            seleccionado= listadoCiclos.getSelectedItem().toString();
            try{
            Thread.sleep(30);
            }catch(InterruptedException ie)
            {

            }
            if(seleccionado=="------") continue;
            else
            {
                //Intentamos leer el archivo
                try
                {
                    lector=new Lector("Gas_En_Piston/Ciclos/"+listadoCiclos.getSelectedItem().toString()+".txt");
                }catch(IOException ioe)
                {
                    System.out.println("Error al leer el archivo "+ioe);
                }
                int iEstado=0;
                Estado estadoActual= lector.getEstados().get(iEstado);
                Estado estadoAnterior;
                gas.setVariables(estadoActual.getPresion(), estadoActual.getVolumen(), estadoActual.getTemperatura());
                //Colocar el estado inicial en el gas y en el panel
                variables[Constantes.PRESION].removeChangeListener(escPresion);
                variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                variables[Constantes.PRESION].setValue((int)(100*estadoActual.getPresion()));
                variables[Constantes.VOLUMEN].setValue((int)(100*estadoActual.getVolumen()));
                variables[Constantes.TEMPERATURA].setValue((int)(100*estadoActual.getTemperatura()));
                variables[Constantes.PRESION].addChangeListener(escPresion);
                variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                presion=estadoActual.getPresion();
                volumen=estadoActual.getVolumen();
                temperatura=estadoActual.getTemperatura();
                System.out.println(""+variables[Constantes.PRESION].getValue()+variables[Constantes.VOLUMEN].getValue()+variables[Constantes.TEMPERATURA].getValue());
                boolean cicloActivo=true;
                while(cicloActivo)
                {
                    estadoAnterior=estadoActual;
                    iEstado=++iEstado%4;
                    System.out.println(iEstado);
                    estadoActual=lector.getEstados().get(iEstado);
                    listaDeProcesos.setSelectedItem(estadoActual.getTipoDeProceso());
                    System.out.println(listaDeProcesos.getSelectedItem());
                    System.out.println(estadoActual.getVariableModificada());
                    switch(estadoActual.getVariableModificada())
                    {
                        case Gas.PRESION:
                            double cambioPresion=estadoActual.getPresion()-estadoAnterior.getPresion();
                            for(int i=0;i<60;i++)
                            {
                                variables[Constantes.PRESION].setValue((int)(variables[Constantes.PRESION].getValue()+cambioPresion*1.666666));
                                System.out.println("Presion modificada: "+variables[Constantes.PRESION].getValue());
                                try
                                {
                                    Thread.sleep(16);
                                }catch(InterruptedException ie)
                                {
                                    System.out.println("Error: "+ie);
                                }
                            }
                            
                        break;

                        case Gas.TEMPERATURA:
                            double cambioTemperatura= estadoActual.getTemperatura()-estadoAnterior.getTemperatura();
                            for(int i=0;i<60;i++)
                            {
                                variables[Constantes.TEMPERATURA].setValue((int)(variables[Constantes.TEMPERATURA].getValue()+cambioTemperatura*1.666666));
                                System.out.println("Temperatura modificada: "+variables[Constantes.TEMPERATURA].getValue()); 
                                try
                                {
                                    Thread.sleep(16);
                                }catch(InterruptedException ie)
                                {
                                    System.out.println("Error: "+ie);
                                }
                            }
                        break;

                        case Gas.VOLUMEN:
                            double cambioVolumen=estadoActual.getVolumen()-estadoAnterior.getVolumen();
                            for(int i=0;i<60;i++)
                            {
                                variables[Constantes.VOLUMEN].setValue((int)(variables[Constantes.VOLUMEN].getValue()+cambioVolumen*1.666666));
                                System.out.println("Volumen modificado: "+variables[Constantes.VOLUMEN].getValue());
                                try
                                {
                                    Thread.sleep(16);
                                }catch(InterruptedException ie)
                                {
                                    System.out.println("Error: "+ie);
                                }
                            }
                            break;
                        }
                        if(listadoCiclos.getSelectedItem()=="------") cicloActivo=false;
                    }
                }
        }

    }
}
}
/**
 * Clase que contiene constantes para referenciar a ciertas variables
 * */
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


