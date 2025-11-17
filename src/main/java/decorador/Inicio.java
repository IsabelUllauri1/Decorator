package decorador;

import view.MenuBotonesComida;
import javax.swing.UIManager;

/**
 * Punto de inicio del programa.
 * Abre la ventana principal (MenuBotonesComida).
 * @author isaul
 */
public class Inicio {

    public static void main(String[] args) {
        try {
            // Establecer look & feel del sistema (opcional)
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Asegurar que todo corra en el hilo de interfaz gráfica
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuBotonesComida frame = new MenuBotonesComida();
                frame.setLocationRelativeTo(null); // centrar en pantalla
                frame.setVisible(true);             // mostrar ventana
            }
        });
    }
}
