
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class Cafe implements Comida{
    //variable global
    //las variables son publicas solo en la calse base
    double costo;
    String descripcion;
    
    //dentro de constructor se inicializan los datos
    //tambien se puede usar un SET

    public Cafe() {
        costo = 2.20;
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
