
package decorador.modelo;

/**
 *
 * @author isaul
 */
public class Cafe implements Bebida{
    //variable global
    //las variables son publicas solo en la calse base
    double costo;
    String descripcion;
    
    //dentro de constructor se inicializan los datos
    //tambien se puede usar un SET

    public Cafe() {
        costo = 1.25;
        descripcion = "Cafe";
    }
    

    @Override
    public String getDescripcion() {
        
        return descripcion + "-"+costo;
    }

    @Override
    public double getCosto() {
        return costo;

    }
    
}
