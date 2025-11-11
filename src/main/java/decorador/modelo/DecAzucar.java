/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package decorador.modelo;

/**
 *
 * @author isaul
 */
public class DecAzucar extends DecoradorBebida{
    
    public DecAzucar(Bebida bebida) {
        super(bebida);
    }

    @Override
    public double getCosto() {
        double costo = super.getCosto()+1.0;
        return costo;

    }

    @Override
    public String getDescripcion() {
        String descripcion = super.getDescripcion()+ ", Azucar"; 
        return descripcion;
    }
    
    
    
}
