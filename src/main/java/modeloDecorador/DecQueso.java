
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecQueso extends DecComida {

    public DecQueso(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        return comida.getCosto()+ 1.00; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+ ", queso"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
