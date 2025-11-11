
package decorador.modelo;

/**
 *
 * @author isaul
 */
public class DecCrema extends DecoradorBebida{

    public DecCrema(Bebida bebida) {
        super(bebida);
    }

    @Override
    public double getCosto() {
        double costo= super.getCosto()+0.5; 
        return costo;
    }

    @Override
    public String getDescripcion() {
        String descripcion= super.getDescripcion()+ ", Crema";
        return descripcion;
    }
    
    
}
