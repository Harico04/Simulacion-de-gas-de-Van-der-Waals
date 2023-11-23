package Escuchadores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Escuchadores.CThread;
public class EscuchadorVentana extends WindowAdapter
{
  Thread[] hilos;
  public EscuchadorVentana(Thread[] hilos){
    this.hilos = hilos;
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

public void freezeAll() {
    for (Thread thread : hilos) {
        if (thread != null) {
            synchronized (thread) {
                    thread.wait();
        }
    }
  }
}
public void unfreezeAll() {
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
    
  }

    @Override
    public void windowClosed(WindowEvent e) {
      this.stopAll();
  }

    @Override
    public void windowIconified(WindowEvent e) {
      freezeAll();
      
  }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("Window Deiconified (Restored)");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("Window Activated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("Window Deactivated");
    }
}


