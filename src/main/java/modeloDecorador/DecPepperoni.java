/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecPepperoni extends DecComida
{

    public DecPepperoni(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        return comida.getCosto()+1.1; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+", pepperoni"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
