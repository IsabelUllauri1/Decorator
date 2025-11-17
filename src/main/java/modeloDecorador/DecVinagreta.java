
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecVinagreta extends DecComida {
    
    public DecVinagreta(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        return comida.getCosto()+0.50 ; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+ ", vinagreta"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
}
