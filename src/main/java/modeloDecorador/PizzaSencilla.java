package modeloDecorador;

/**
 *
 * @author isaul
 */
public class PizzaSencilla implements Comida{
    double costo;
    String descripcion;

    public PizzaSencilla() {
        this.costo = 3.00;
        this.descripcion = "Pizza Sencilla";
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

    
