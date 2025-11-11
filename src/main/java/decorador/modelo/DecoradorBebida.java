package decorador.modelo;
/**
 *
 * @author isaul
 */
public abstract class DecoradorBebida implements Bebida {
    private Bebida bebida;

    public DecoradorBebida(Bebida bebida) {
        this.bebida = bebida;
    }
    
    
    @Override
    public String getDescripcion() {
        return bebida.getDescripcion();
    }

    @Override
    public double getCosto() {
        return bebida.getCosto();
    }
    
}
