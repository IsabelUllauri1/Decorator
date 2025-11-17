/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author isaul
 */
public class DecAzucar extends DecComida{
    
    public DecAzucar(Comida comida) {
        super(comida);
    }

    @Override
    public double getCosto() {
        
        return comida.getCosto()+0.50;
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion()+ ", Azucar"; 
    }
    
    
    
}
