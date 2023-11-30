

package Escuchadores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *Una implementacion de WindowAdapter que permite manejar de forma
 *sencilla, hilos que se encuentren dentro de la ventana
 *
 */
public class EscuchadorVentana extends WindowAdapter {

    /**Arreglo de hilos asociados a la ventana. */
    private Thread[] hilos;

    /** Indica si la ventana está minimizada. */
    private boolean iconified = false;

    /**
     * Crea una instancia de EscuchadorVentana con los hilos dados.
     *
     * @param hilos 
     * Arreglo de hilos asociados a la ventana.
     */
    public EscuchadorVentana(Thread[] hilos) {
        this.hilos = hilos;
    }

    /**
     * Restaura el estado de los hilos.
     *
     * @param threads Arreglo de hilos a restaurar.
     */
    public void restoreThreadState(Thread[] threads) {
        for (Thread thread : threads) {
            if (thread.isInterrupted()) {
                thread.notifyAll();
            }
        }
    }

    /**
     * Detiene todos los hilos asociados a la ventana.
     */
    public void stopAll() {
        for (Thread thread : hilos) {
            if (thread != null) {
                thread.interrupt();
            }
        }
    }

    /**
     * Inicia todos los hilos asociados a la ventana.
     */
    public void startAll() {
        for (Thread thread : hilos) {
            if (thread != null) {
                thread.start();
            }
        }
    }

    /**
     * Congela la ejecución de todos los hilos asociados a la ventana.
     * 
     * @throws InterruptedException Si se produce una interrupción durante la espera.
     */
    public void freezeAll() throws InterruptedException {
        for (Thread thread : hilos) {
            if (thread != null) {
                synchronized (thread) {
                    thread.wait();
                }
            }
        }
    }

    /**
     * Reanuda la ejecución de todos los hilos asociados a la ventana.
     * 
     * @throws InterruptedException Si se produce una interrupción durante la espera.
     */
    public void unfreezeAll() throws InterruptedException {
        for (Thread thread : hilos) {
            if (thread != null) {
                synchronized (thread) {
                    thread.notifyAll();
                }
            }
        }
    }

    // Métodos de WindowAdapter

    @Override
    public void windowOpened(WindowEvent e) {
        startAll();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        stopAll();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        stopAll();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        iconified = true;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        iconified = false;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        iconified = false;
        try {
            unfreezeAll();
        } catch (InterruptedException ie) {
            System.out.println("El hilo fue interrumpido durante la espera" + ie.toString());
            stopAll();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        iconified = true;
        try {
            freezeAll();
        } catch (InterruptedException ie) {
            System.out.println("El hilo fue interrumpido" + ie.toString());
            stopAll();
        }
    }
}
