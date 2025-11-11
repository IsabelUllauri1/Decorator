package decorador.modelo;

/**
 *
 * @author isaul
 */
public abstract class Pizza implements Comida{
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

    
