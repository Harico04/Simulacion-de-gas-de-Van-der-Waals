package Escuchadores;
public class CThread {
  private Thread[] hilos;
  public CThread(Thread[] hilos){
    this.hilos = hilos; 
  }
  public void startAll(){
    for (Thread thread : hilos) {
     thread.start(); 
    }
  }
  public void stopAll(){
    for (Thread thread : hilos) {
     thread.interrupt(); 
    }
  }
  
}
