package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FotoFondo extends JPanel {

    private Image imagenFondo;

    public FotoFondo(String ruta) {
        java.net.URL url = getClass().getResource(ruta);
        if (url == null && ruta.startsWith("/")) {
            url = ClassLoader.getSystemResource(ruta.substring(1));
        }
        if (url != null) {
            imagenFondo = new ImageIcon(url).getImage();
            System.out.println("Cargado: " + url);
        } else {
            System.err.println("No se encontró la imagen: " + ruta);
        }
        setDoubleBuffered(true);
        setOpaque(true); // este panel sí pinta
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
