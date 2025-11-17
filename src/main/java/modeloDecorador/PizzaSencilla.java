package modeloDecorador;

/**
 *
 * @author isaul
 */
public class PizzaSencilla implements Comida{
    double costo;
    String descripcion;

    @Override
    public String getDescripcion() {
        return descripcion + "-"+costo;

    }

    @Override
    public double getCosto() {
        return costo;
    }

    
            
} 

    
