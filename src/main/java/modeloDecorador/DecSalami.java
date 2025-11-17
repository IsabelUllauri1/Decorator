/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecSalami extends DecComida{

    public DecSalami(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        return comida.getCosto()+3.00; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+", salami"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
