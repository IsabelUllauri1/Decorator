
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecCrema extends DecComida{

    public DecCrema(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
         return comida.getCosto() + 1.20;
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion() + ", crema";
    }
    
    
}
