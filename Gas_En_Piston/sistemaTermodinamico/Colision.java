package sistemaTermodinamico;

import java.awt.geom.Point2D;
import sistemaTermodinamico.Molecula; 
/**
 * Clase destinada a verificar a colision entre moleculas y proceder a cambiar
 * el vector de velocidad.
 * @author Manuel Gortarez
 */

public class Colision extends Thread{
    /**
     *
     * */
    private final int X = 0;
    private final int Y = 1;
    private Molecula[] moleculas;
    /**
     *
     *Crea un nuevo objeto de colision
     * @param moleculas    Arreglo de moleculas cuyas colisiones se deben verificar
     * */
    public Colision(Molecula[] moleculas){
        this.moleculas = moleculas;
    }

    @Override
    /**
     *Cuando se inicializa el hilo empieza a verificar las colisiones
     * */
    public void run(){
        while(true){
            Colisiones();
 
        }
    }
    
    /**
     *Se encarga de realizar la colision entre las moleculas para
     *cada una de ellas.
     * */
    private void Colisiones(){        
        for(Molecula molecula1: moleculas){                       
            for(Molecula molecula2: moleculas){
                if(molecula1 == molecula2)
                    continue;
                if (verificarColision(molecula1, molecula2)) modificarVelocidad(molecula1, molecula2);
            }
        }
    }

    
    /**
     *Predicado que nos dice si hay colision o no entre dos moleculas.
     *@param molecula1 molecula cuya colision se debe verificar con la molecula2
     *@param molecula2 molecula cuya colision se debe verificar con la molecula1
     * */
    private boolean verificarColision(Molecula molecula1, Molecula molecula2){
        Point2D.Double centro1 = molecula1.getPosicion();
        Point2D.Double centro2 = molecula2.getPosicion();
        double radio1 = molecula1.getRadio();
        double radio2 = molecula2.getRadio();
        double deltax = centro1.x - centro2.x;
        double deltay = centro1.y - centro2.y;
        if(Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2)) <= (radio1 + radio2))
            return true;
        return false;
    }
    /**
     * Modifica la velocidad de las moleculas despues de la colision
     * @param molecula1 molecula a la cual se le modifica la velocidad
     * @param molecula2 molecula a la cual se le modifica la velocidad
     * */
    private void modificarVelocidad(Molecula molecula1, Molecula molecula2){
        //velocidades finales después de los calculos.
        double v1_f[] = new double[2];
        double v2_f[] = new double[2];
        
        // Obtenemos vectores de posicion y velocidad de la molecula1.
        double v1[] = molecula1.getVelocidad();
        double c1[] = new double[2];
        c1[X] = molecula1.getPosicion().x;
        c1[Y] = molecula1.getPosicion().y;
        
        // Obtenemos vectores de posicion y velocidad de la molecula2.
        double v2[] = molecula2.getVelocidad();
        double c2[] = new double[2];
        c2[X] = molecula2.getPosicion().x;
        c2[Y] = molecula2.getPosicion().y;

        //guardamos calculos en variables para simplificar el codigo.
        double deltaC_1[] = { c1[X] - c2[X], c1[Y] - c2[Y] }; // C1 -C2
        double deltaC_2[] = { (-1) * deltaC_1[X], (-1) * deltaC_1[Y] }; // C2 - C1
        double deltaV_1[] = { v1[X] - v2[X], v1[Y] - v2[Y] }; // V1 - V2
        double deltaV_2[] = { (-1) * deltaV_1[X], (-1) * deltaV_1[Y] }; // V2 - vv1

        // Calculamos v1_f
        v1_f[X] = v1[X] - (productoPunto(deltaV_1, deltaC_1) / normaAlCuadrado(deltaC_1)) * deltaC_1[X];
        v1_f[Y] = v1[Y] - (productoPunto(deltaV_1, deltaC_1) / normaAlCuadrado(deltaC_1)) * deltaC_1[Y];

        // Calculamos v_2f
          v2_f[X] = v2[X] - (productoPunto(deltaV_2, deltaC_2) / normaAlCuadrado(deltaC_2)) * deltaC_2[X];
        v2_f[Y] = v2[Y] - (productoPunto(deltaV_2, deltaC_2) / normaAlCuadrado(deltaC_2)) * deltaC_2[Y];

        // Asignamos las velocidades actualizadas a las moleculas
        molecula1.setVelocidad(v1_f[X], v1_f[Y]);
        molecula2.setVelocidad(v2_f[X], v2_f[Y]);
    }
    /**
     * Metodo que hace producto punto entre 2 vectores
     * @param v1 vector de velocidad de la molecula1
     * @param v2 vector de velocidad de la molecula2
     * */
    private double productoPunto(double[] v1,  double [] v2){
        return (v1[X] * v2[X] + v1[Y] * v2[Y]);
    }
    /**
     * Eleva la norma del vector al cuadrado
     * @param v vector al cual se le saca la norma  
     * */
    private double normaAlCuadrado(double[] v){
        return (v[X] * v[X] + v[Y] * v[Y]);
    }
}
