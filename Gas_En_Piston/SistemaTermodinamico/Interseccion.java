
/*
 * Clase que funciona como un hilo que trata las intersecciones de las moleculas con el entorno
 * y/o con ellas mismas
 */

package SistemaTermodinamico;

import java.awt.Rectangle;

public class Interseccion extends Thread{

    private Molecula moleculas[];
    private Rectangle paredes[];

    public Interseccion(Molecula moleculas[], Rectangle paredes[]){
        
        this.moleculas = moleculas;
        this.paredes = paredes;
    }

    public void run(){
        while(true){
            for(Molecula molecula1: moleculas){
                for(Rectangle pared: paredes){
                    if(molecula1.verificarColision(pared))
                        molecula1.modificarDireccion();
                }
                for(Molecula molecula2: moleculas){
                    if(molecula1 == molecula2)
                        continue;
                    if(molecula1.verificarColision(molecula2.getHitbox())){
                        molecula1.modificarDireccion();
                        molecula2.modificarDireccion();
                    }
                }
            }
        }
    }
}
