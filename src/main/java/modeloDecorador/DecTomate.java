
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecTomate extends DecComida
{
    
    public DecTomate(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        return comida.getCosto()+ 1.10; 
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+", tomate"; 
    }
    
}
