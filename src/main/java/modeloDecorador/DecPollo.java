/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecPollo extends DecComida {

    public DecPollo(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        return comida.getCosto()+1.5; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+ ", pollo"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
