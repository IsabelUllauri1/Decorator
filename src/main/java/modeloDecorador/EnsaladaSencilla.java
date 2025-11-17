package modeloDecorador;

/**
 *
 * @author isaul
 */
public class EnsaladaSencilla implements Comida {

    private String descripcion;
    private double costo;

    public EnsaladaSencilla() {
        this.descripcion = "Ensalada sencilla";
        this.costo = 3.00;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public double getCosto() {
        return costo;
    }
}
