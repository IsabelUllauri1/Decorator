/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloDecorador;

/**
 *
 * @author isaul
 */
public abstract class DecComida implements Comida {
    
    protected Comida comida;

    public DecComida(Comida comida) {
        this.comida = comida;
    }

    @Override
    public String getDescripcion() {
        return comida.getDescripcion();
    }

    @Override
    public double getCosto() {
        return comida.getCosto();
    }
    
    
}
