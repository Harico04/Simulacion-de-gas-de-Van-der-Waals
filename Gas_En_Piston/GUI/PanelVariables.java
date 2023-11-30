

package GUI;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ciclos.Estado;
import ciclos.Lector;
import sistemaTermodinamico.Gas;
import sistemaTermodinamico.Piston;
import sistemaTermodinamico.Molecula;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

/**
 * JPanel que muestra los valores de las variables termodinamicas, permite 
 * su modificacion y que se fijen. Muestra creditos en la esquina inferior derecha.
 * @author Fausto Medina
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
   * Lector de archivos utilizado para obtener los estado y procesos
   * de los ciclos
   */
    private Lector lector;
  /**
   * Lector que lee el archivo y almacena sus valores para generar ciclos
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
        add(new JLabel("CICLOS: (Dejar vacio para quitar ciclo)"));
        add(listadoCiclos);
        add(new JLabel("Elaborado por:"));
        add(new JLabel("Manuel Eduardo Gortarez Blanco"));
        add(new JLabel("Fausto Misael Medina Lugo"));
        add(new JLabel("Alan David Torres Flores"));
    }
    //Escuchador para el JSlider de presion
    class EscuchadorPresion implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            presion = variables[Constantes.PRESION].getValue()/100.0f;
            impresionVariables[Constantes.PRESION].setForeground(Color.BLACK);
            impresionVariables[Constantes.PRESION].setText("Valor: " + presion);
            gas.setVariables(presion, volumen, temperatura);
            gas.calcularVariables(listaDeProcesos.getSelectedItem().toString(),Gas.PRESION);
            if(listaDeProcesos.getSelectedItem().toString()=="Isometrico")
            {
                //Verificamos que la temperatura no se haya salido de los limites
                if(gas.getTemperatura()>T_MAX)
                {
                    gas.setVariables(presion, volumen, T_MAX);
                    gas.calcularVariables("Isometrico", Gas.TEMPERATURA);
                    presion=gas.getPresion();
                    variables[Constantes.PRESION].removeChangeListener(escPresion);
                    variables[Constantes.PRESION].setValue((int)(presion*100));
                    impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                    impresionVariables[Constantes.TEMPERATURA].setForeground(Color.red);
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+T_MAX);
                    variables[Constantes.PRESION].addChangeListener(escPresion);
                    return;
                }
                if(gas.getTemperatura()<T_MIN)
                {
                    gas.setVariables(presion, volumen, T_MIN);
                    gas.calcularVariables("Isometrico", Gas.TEMPERATURA);
                    presion=gas.getPresion();
                    variables[Constantes.PRESION].removeChangeListener(escPresion);
                    variables[Constantes.PRESION].setValue((int)(presion*100));
                    impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                    impresionVariables[Constantes.TEMPERATURA].setForeground(Color.red);
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+T_MIN);
                    variables[Constantes.PRESION].addChangeListener(escPresion);
                    return;
                }
                //Actualizamos la temperatura si se encontraba dentro de los limites
                variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                temperatura=gas.getTemperatura();
                variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                impresionVariables[Constantes.TEMPERATURA].setForeground(Color.BLACK);
                impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                for(Molecula molecula: moleculas)
                    molecula.setTemperatura(variables[Constantes.TEMPERATURA].getValue()/100.00);
            } 
            else
            {
                //Verificamos que el volumen no se haya salido de los limites
                if(gas.getVolumen()>V_MAX)
                {
                    gas.setVariables(presion, V_MAX, temperatura);
                    gas.calcularVariables("Isotermico", Gas.VOLUMEN);
                    presion=gas.getPresion();
                    variables[Constantes.PRESION].removeChangeListener(escPresion);
                    variables[Constantes.PRESION].setValue((int)(presion*100));
                    impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                    impresionVariables[Constantes.VOLUMEN].setForeground(Color.red);
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+V_MAX);
                    variables[Constantes.PRESION].addChangeListener(escPresion);
                    return;
                }
                if(gas.getVolumen()<V_MIN)
                {
                    gas.setVariables(presion, V_MIN, temperatura);
                    gas.calcularVariables("Isotermico", Gas.VOLUMEN);
                    presion=gas.getPresion();
                    variables[Constantes.PRESION].removeChangeListener(escPresion);
                    variables[Constantes.PRESION].setValue((int)(presion*100));
                    impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                    impresionVariables[Constantes.VOLUMEN].setForeground(Color.red);
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+V_MIN);
                    variables[Constantes.PRESION].addChangeListener(escPresion);
                    return;
                }
                //Actualizamos el volumen si se encontraba dentro del rango
                variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                volumen=gas.getVolumen();
                variables[Constantes.VOLUMEN].setValue((int)(volumen*100.0));
                impresionVariables[Constantes.VOLUMEN].setForeground(Color.BLACK);
                impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                piston.setVolumen(volumen,20);
                variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
            } 
        }
    }
    //Escuchador para el JSLider de temperatura
    class EscuchadorTemperatura implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            temperatura = variables[Constantes.TEMPERATURA].getValue()/100.0f;
            impresionVariables[Constantes.TEMPERATURA].setForeground(Color.BLACK);
            impresionVariables[Constantes.TEMPERATURA].setText("Valor: " + temperatura);
            gas.setVariables(presion, volumen, temperatura);
            gas.calcularVariables(listaDeProcesos.getSelectedItem().toString(),Gas.TEMPERATURA);
            if(listaDeProcesos.getSelectedItem().toString()=="Isobarico")
            { 
                //Verificamos que el volumen no se haya salido de los limites establecidos
                if(gas.getVolumen()>V_MAX)
                {
                    gas.setVariables(presion, V_MAX, temperatura);
                    gas.calcularVariables("Isobarico", Gas.VOLUMEN);
                    temperatura=gas.getTemperatura();
                    variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                    variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                    impresionVariables[Constantes.VOLUMEN].setForeground(Color.red);
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+V_MAX);
                    variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                    return;
                }
                if(gas.getVolumen()<V_MIN)
                {
                    gas.setVariables(presion, V_MIN, temperatura);
                    gas.calcularVariables("Isobarico", Gas.VOLUMEN);
                    temperatura=gas.getTemperatura();
                    variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                    variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                    impresionVariables[Constantes.VOLUMEN].setForeground(Color.red);
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+V_MIN);
                    variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                    return;
                }
                variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                volumen=gas.getVolumen();
                variables[Constantes.VOLUMEN].setValue((int)(volumen*100.0));
                impresionVariables[Constantes.VOLUMEN].setForeground(Color.BLACK);
                impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                piston.setVolumen(volumen,20);
                variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
            }
            else
            {
                if(gas.getPresion()>P_MAX)
                {
                    gas.setVariables(P_MAX, volumen, temperatura);
                    gas.calcularVariables("Isometrico", Gas.PRESION);
                    temperatura=gas.getTemperatura();
                    variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                    variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                    impresionVariables[Constantes.PRESION].setForeground(Color.red);
                    impresionVariables[Constantes.PRESION].setText("Valor: "+P_MAX);
                    variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                    return;
                }
                if(gas.getPresion()<P_MIN)
                {
                    gas.setVariables(P_MIN, volumen, temperatura);
                    gas.calcularVariables("Isometrico", Gas.PRESION);
                    temperatura=gas.getTemperatura();
                    variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                    variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+temperatura);
                    impresionVariables[Constantes.PRESION].setForeground(Color.red);
                    impresionVariables[Constantes.PRESION].setText("Valor: "+P_MIN);
                    variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                    return;
                }
                variables[Constantes.PRESION].removeChangeListener(escPresion);
                presion=gas.getPresion();
                variables[Constantes.PRESION].setValue((int)(presion*100.0));
                impresionVariables[Constantes.PRESION].setForeground(Color.BLACK);
                impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                variables[Constantes.PRESION].addChangeListener(escPresion);
            }

            for(Molecula molecula: moleculas)
                molecula.setTemperatura(variables[Constantes.TEMPERATURA].getValue()/100.00);                
        }
    }
    //Escuchador para el JSlider de volumen
    class EscuchadorVolumen implements ChangeListener
    {

        public void stateChanged(ChangeEvent e)
        {
            volumen = variables[Constantes.VOLUMEN].getValue()/100.0f;
            impresionVariables[Constantes.VOLUMEN].setForeground(Color.BLACK);
            impresionVariables[Constantes.VOLUMEN].setText("Valor: " + volumen);
            gas.setVariables(presion, volumen, temperatura);
            piston.setVolumen(volumen,20);
            gas.calcularVariables(listaDeProcesos.getSelectedItem().toString(),Gas.VOLUMEN);
            if(listaDeProcesos.getSelectedItem().toString()=="Isotermico")
            {
                //Verificamos que la presion no se saliera de los limites
                if(gas.getPresion()>P_MAX)
                {
                    gas.setVariables(P_MAX, volumen, temperatura);
                    gas.calcularVariables("Isotermico", Gas.PRESION);
                    volumen=gas.getVolumen();
                    variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                    variables[Constantes.VOLUMEN].setValue((int)(volumen*100));
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                    impresionVariables[Constantes.PRESION].setForeground(Color.red);
                    impresionVariables[Constantes.PRESION].setText("Valor: "+P_MAX);
                    piston.setVolumen(volumen,20);
                    variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                    return;
                }
                if(gas.getPresion()<P_MIN)
                {
                    gas.setVariables(P_MIN, volumen, temperatura);
                    gas.calcularVariables("Isotermico", Gas.PRESION);
                    volumen=gas.getVolumen();
                    variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                    variables[Constantes.VOLUMEN].setValue((int)(volumen*100));
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                    impresionVariables[Constantes.PRESION].setForeground(Color.red);
                    impresionVariables[Constantes.PRESION].setText("Valor: "+P_MIN);
                    piston.setVolumen(volumen,20);
                    variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                    return;
                }
                //Actualizamos el valor de presion si se mantuvo dentro de los limites
                variables[Constantes.PRESION].removeChangeListener(escPresion);
                presion=gas.getPresion();
                variables[Constantes.PRESION].setValue((int)(presion*100.0));
                impresionVariables[Constantes.PRESION].setForeground(Color.BLACK);
                impresionVariables[Constantes.PRESION].setText("Valor: "+presion);
                variables[Constantes.PRESION].addChangeListener(escPresion);
            } 
            else
            {
                //Verificamos que la temperatura no se haya salido de los limites
                if(gas.getTemperatura()>T_MAX)
                {
                    gas.setVariables(presion, volumen, T_MAX);
                    gas.calcularVariables("Isobarico", Gas.TEMPERATURA);
                    volumen=gas.getVolumen();
                    variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                    variables[Constantes.VOLUMEN].setValue((int)(volumen*100));
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                    impresionVariables[Constantes.TEMPERATURA].setForeground(Color.red);
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+T_MAX);
                    piston.setVolumen(volumen,20);
                    variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                    return;
                }
                if(gas.getTemperatura()<T_MIN)
                {
                    gas.setVariables(presion, volumen, T_MIN);
                    gas.calcularVariables("Isobarico", Gas.TEMPERATURA);
                    volumen=gas.getVolumen();
                    variables[Constantes.VOLUMEN].removeChangeListener(escVolumen);
                    variables[Constantes.VOLUMEN].setValue((int)(volumen*100));
                    impresionVariables[Constantes.VOLUMEN].setText("Valor: "+volumen);
                    impresionVariables[Constantes.TEMPERATURA].setForeground(Color.red);
                    impresionVariables[Constantes.TEMPERATURA].setText("Valor: "+T_MIN);
                    piston.setVolumen(volumen,20);
                    variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                    return;
                }
                //Actualizamos el valor de temperatura si se mantuvo dentro de los limites
                variables[Constantes.TEMPERATURA].removeChangeListener(escTemperatura);
                temperatura=gas.getTemperatura();
                variables[Constantes.TEMPERATURA].setValue((int)(temperatura*100));
                impresionVariables[Constantes.TEMPERATURA].setForeground(Color.BLACK);
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
                for(Molecula molecula: moleculas)
                molecula.setTemperatura(variables[Constantes.TEMPERATURA].getValue()/100.00);
                variables[Constantes.PRESION].addChangeListener(escPresion);
                variables[Constantes.VOLUMEN].addChangeListener(escVolumen);
                variables[Constantes.TEMPERATURA].addChangeListener(escTemperatura);
                presion=estadoActual.getPresion();
                volumen=estadoActual.getVolumen();
                temperatura=estadoActual.getTemperatura();
                boolean cicloActivo=true;
                while(cicloActivo)
                {
                    estadoAnterior=estadoActual;
                    iEstado=++iEstado%4;
                    estadoActual=lector.getEstados().get(iEstado);
                    listaDeProcesos.setSelectedItem(estadoActual.getTipoDeProceso());
                    switch(estadoActual.getVariableModificada())
                    {
                        case Gas.PRESION:
                            double cambioPresion=estadoActual.getPresion()-estadoAnterior.getPresion();
                            for(int i=0;i<100;i++)
                            {
                                variables[Constantes.PRESION].setValue((int)(variables[Constantes.PRESION].getValue()+Math.ceil(cambioPresion)));
                                try
                                {
                                    Thread.sleep(16);
                                }catch(InterruptedException ie)
                                {
                                    System.out.println("Error: "+ie);
                                }
                            }
                            variables[Constantes.PRESION].setValue((int)(estadoActual.getPresion()*100));
                        break;

                        case Gas.TEMPERATURA:
                            double cambioTemperatura= estadoActual.getTemperatura()-estadoAnterior.getTemperatura();
                            for(int i=0;i<100;i++)
                            {
                                variables[Constantes.TEMPERATURA].setValue((int)(variables[Constantes.TEMPERATURA].getValue()+Math.ceil(cambioTemperatura)));
                                try
                                {
                                    Thread.sleep(16);
                                }catch(InterruptedException ie)
                                {
                                    System.out.println("Error: "+ie);
                                }
                            }
                            variables[Constantes.TEMPERATURA].setValue((int)(estadoActual.getTemperatura()*100));
                        break;

                        case Gas.VOLUMEN:
                            double cambioVolumen=estadoActual.getVolumen()-estadoAnterior.getVolumen();
                            for(int i=0;i<100;i++)
                            {
                                variables[Constantes.VOLUMEN].setValue((int)(variables[Constantes.VOLUMEN].getValue()+Math.ceil(cambioVolumen)));
                                try
                                {
                                    Thread.sleep(16);
                                }catch(InterruptedException ie)
                                {
                                    System.out.println("Error: "+ie);
                                }
                            }
                            variables[Constantes.VOLUMEN].setValue((int)(estadoActual.getVolumen()*100));
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


