/**
 *Una implementacion de WindowAdapter que permite manejar de forma
 *sencilla, hilos que se encuentren dentro de la ventana
 * */
package Escuchadores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class EscuchadorVentana extends WindowAdapter
{
  Thread[] hilos;
  boolean iconified = false;
  public EscuchadorVentana(Thread[] hilos){
    this.hilos = hilos;
  }
  public void restoreThreadState(Thread[] threads){
      for (Thread thread : threads) {
       if(thread.isInterrupted()){
        thread.notifyAll();
      } 
      }
  }
  public void stopAll(){
    for (Thread thread : hilos) {
     if(thread != null){
        thread.interrupt();
      } 
    }
  }
  public void startAll(){
    for (Thread thread : hilos) {
     thread.start();
    }
  }

public void freezeAll() throws InterruptedException{
    for (Thread thread : hilos) {
        if (thread != null) {
          
            synchronized (thread) {
                    thread.wait();
            }
          }
                  }
  }

public void unfreezeAll() throws InterruptedException {
    for (Thread thread : hilos) {
        if (thread != null) {
            synchronized (thread) {
                thread.notifyAll();
            }
        }
    }
}
  public void windowOpened(WindowEvent e) {
       startAll(); 
  }

    @Override
    public void windowClosing(WindowEvent e) {
      this.stopAll();    
  }

    @Override
    public void windowClosed(WindowEvent e) {
      this.stopAll();
  }

    @Override
    public void windowIconified(WindowEvent e) {
      this.iconified = true;
      
  }

    @Override
    public void windowDeiconified(WindowEvent e) {
      this.iconified = false;
  }

    @Override
    public void windowActivated(WindowEvent e) {
      this.iconified = false;
      try{
        this.unfreezeAll();
      }    
      catch(InterruptedException ie){
        System.out.println("El hilo fue interrumpido durante la espera"+ie.toString());
      stopAll();
     }

  }

    @Override
    public void windowDeactivated(WindowEvent e) {
      this.iconified = true;
      try{
            this.freezeAll();
      }
      catch(InterruptedException ie){
            System.out.println("El hilo fue interrumpido"+ie.toString());
            stopAll();
       }
     
  }
}

