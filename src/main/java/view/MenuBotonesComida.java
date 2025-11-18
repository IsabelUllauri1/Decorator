
package view;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modeloDecorador.Comida;
import modeloDecorador.Cafe;
import modeloDecorador.EnsaladaSencilla;
import modeloDecorador.PizzaSencilla;
import modeloDecorador.DecAzucar;
import modeloDecorador.DecCrema;
import modeloDecorador.DecTomate;
import modeloDecorador.DecVinagreta;
import modeloDecorador.DecPollo;
import modeloDecorador.DecQueso;
import modeloDecorador.DecSalami;
import modeloDecorador.DecPepperoni;
import modeloDecorador.DecComida;


/**
 *
 * @author isaul
 */
public class MenuBotonesComida extends javax.swing.JFrame  {
    
    // para la FACTURA
    private DefaultTableModel modeloFactura;

    // para saber que tipo de plato esta disponidble
    private enum TipoPlato {
        CAFE, ENSALADA, PIZZA
    }
    private TipoPlato tipoPlatoActual = null;

    // Comida decorado actual (base + adicionales)
    private Comida comidaActual = null;

    // fila de la tabla donde está el producto base actual
    private int filaProductoActual = -1;

    // formatear precios
    private String formatear(double v) {
        return String.format("%.2f$", v);
    }


    /**
     * Creates new form MenuBotonesComida
     */
    public MenuBotonesComida() {
        initComponents(); 

        // panel con fondo
        view.FotoFondo fondo = new view.FotoFondo("/decorador/paper2.jpg");
        fondo.setLayout(new java.awt.BorderLayout());

        //quitar panelPrincipal del contentPane actual
        java.awt.Container cp = getContentPane();
        cp.remove(panelPrincipal);

        //transparencia para que se vea el fondo a través de panelPrincipal
        panelPrincipal.setOpaque(false);

        //poner panelPrincipal dentro del fondo
        fondo.add(panelPrincipal, java.awt.BorderLayout.CENTER);

        //poner el fondo como contentPane
        setContentPane(fondo);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        
        //tabla factura
        configurarTablaFactura();

    }
    
    private void configurarTablaFactura() {
        //crea el modelo de la tabla con las 4 columnas
        modeloFactura = new DefaultTableModel(
                new Object[]{"Producto", "Adicional", "Precio", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //no se puede editar la tabla con el teclado
            }
        };
        //asigna el modelo a la tabla de la vista
        tblFactura.setModel(modeloFactura);
        //agrega al final la fila de total
        agregarFilaTotal();
    }

    private void agregarFilaTotal() {
        //anade una fila con el texto total y el valor 0 formateado
        modeloFactura.addRow(new Object[]{"TOTAL:", "", "", formatear(0)});
    }

    private void actualizarTotal() {
        //acumula el total de todos los subtotales
        double total = 0.0;
        //obtiene cuantas filas hay en la tabla
        int filas = modeloFactura.getRowCount();
        //recorre todas las filas menos la ultima de TOTAL
        for (int i = 0; i < filas - 1; i++) { //todas menos la ultima TOTAL
            //toma el valor de la columna subtotal de esa fila
            Object val = modeloFactura.getValueAt(i, 3); //subtotal
            if (val != null) {
                //convierte el texto quitando el $
                String txt = val.toString().replace("$", "").trim();
                if (!txt.isEmpty()) {
                    try {
                        //suma el número al total
                        total += Double.parseDouble(txt.replace(",", "."));
                    } catch (NumberFormatException ex) {
                        //ignora si el texto no se puede convertir a numero
                    }
                }
            }
        }
        //escribe el total calculado en la ultima fila
        modeloFactura.setValueAt(formatear(total), filas - 1, 3);
    }

    private void seleccionarCafe() {
        //crea un nuevo objeto cafe como comida actual
        comidaActual = new Cafe();
        //marca que el tipo  actual es cafe
        tipoPlatoActual = TipoPlato.CAFE;

        //obtiene la posición de la fila total
        int filaTotal = modeloFactura.getRowCount() - 1; //ultima es TOTAL
        //la nueva fila del producto ira antes de la de total
        filaProductoActual = filaTotal;

        //inserta la fila del cafe en la factura
        modeloFactura.insertRow(filaProductoActual, new Object[]{
            "café", //producto
            "", //adicional vacio
            formatear(comidaActual.getCosto()), //precio base del cafe
            formatear(comidaActual.getCosto()) //subtotal igual al precio base
        });

        //actualiza el total general
        actualizarTotal();
    }

    private void seleccionarEnsalada() {
        //crea una nueva ensalada como comida actual
        comidaActual = new EnsaladaSencilla();
        //marca que el tipo actual es ensalada
        tipoPlatoActual = TipoPlato.ENSALADA;

        //posicion de la fila total
        int filaTotal = modeloFactura.getRowCount() - 1;
        //fila donde se guarda este producto
        filaProductoActual = filaTotal;

        //inserta la fila de la ensalada en la factura
        modeloFactura.insertRow(filaProductoActual, new Object[]{
            "ensalada sencilla",
            "",
            formatear(comidaActual.getCosto()),
            formatear(comidaActual.getCosto())
        });

        //actualiza el total general
        actualizarTotal();
    }

    private void seleccionarPizza() {
        //crea una nueva pizza como comida actual
        comidaActual = new PizzaSencilla();
        //marca que el tipo actual es pizza
        tipoPlatoActual = TipoPlato.PIZZA;

        //posicion de la fila total
        int filaTotal = modeloFactura.getRowCount() - 1;
        //fila donde se guarda esta pizza
        filaProductoActual = filaTotal;

        //inserta la fila de la pizza en factura
        modeloFactura.insertRow(filaProductoActual, new Object[]{
            "pizza sencilla",
            "",
            formatear(comidaActual.getCosto()),
            formatear(comidaActual.getCosto())
        });

        //actualiza el total general
        actualizarTotal();
    }

    //validador
    private boolean validarAdicional(TipoPlato esperado) {
        //si no hay comida o el tipo no coincide o no hay fila: error
        if (comidaActual == null || tipoPlatoActual != esperado || filaProductoActual < 0) {
            JOptionPane.showMessageDialog(this,
                    "NO puede seleccionar la opción adicional",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //si todo bien, true
        return true;
    }

    //agregar adicional
    private void agregarAdicional(DecComida decorador, String nombreAdicional) {
        //guarda el costo antes de aplicar el decorador
        double costoAntes = comidaActual.getCosto();
        //aplica el decorador a la comida actual
        comidaActual = decorador; //encadena el decorador
        //obtiene el nuevo costo con el adicional
        double costoDespues = comidaActual.getCosto();
        //calcula cuanto cuesta solo el adicional
        double costoExtra = costoDespues - costoAntes;

        //fila donde se pone el adicional (antes del total)
        int filaTotal = modeloFactura.getRowCount() - 1;
        //inserta una nueva fila solo con el adicional
        modeloFactura.insertRow(filaTotal, new Object[]{"",nombreAdicional,formatear(costoExtra), //producto vacio +nombre adicional+precio adicional+
            "" //subtotal solo en la fila del producto base
        });

        //actualiza el subtotal en la fila del producto base
        modeloFactura.setValueAt(formatear(costoDespues), filaProductoActual, 3);

        //vuelve a calcular el total general
        actualizarTotal();
    }

    //metodos para botones de adicionales
    private void accionAzucar() {
        //verifica que el adicional corresponda a un cafe
        if (!validarAdicional(TipoPlato.CAFE)) {
            return;
        }
        //agrega el decorador azúcar a la comida actual
        agregarAdicional(new DecAzucar(comidaActual), "azucar");
    }

    private void accionCrema() {
        if (!validarAdicional(TipoPlato.CAFE)) {
            return;
        }
        agregarAdicional(new DecCrema(comidaActual), "crema");
    }

    private void accionTomate() {
        if (!validarAdicional(TipoPlato.ENSALADA)) {
            return;
        }
        agregarAdicional(new DecTomate(comidaActual), "tomate");
    }

    private void accionVinagreta() {
        if (!validarAdicional(TipoPlato.ENSALADA)) {
            return;
        }
        agregarAdicional(new DecVinagreta(comidaActual), "vinagreta");
    }

    private void accionPollo() {
        if (!validarAdicional(TipoPlato.ENSALADA)) {
            return;
        }
        agregarAdicional(new DecPollo(comidaActual), "pollo");
    }

    private void accionQueso() {
        if (!validarAdicional(TipoPlato.PIZZA)) {
            return;
        }
        agregarAdicional(new DecQueso(comidaActual), "queso");
    }

    private void accionSalami() {
        if (!validarAdicional(TipoPlato.PIZZA)) {
            return;
        }
        agregarAdicional(new DecSalami(comidaActual), "salami");
    }

    private void accionPepperoni() {
        if (!validarAdicional(TipoPlato.PIZZA)) {
            return;
        }
        agregarAdicional(new DecPepperoni(comidaActual), "pepperoni");
    }
    
    //FOTO FONDO
    private void hacerTransparentes(java.awt.Container c) {
        for (java.awt.Component comp : c.getComponents()) {
            if (comp instanceof javax.swing.JPanel) {
                ((javax.swing.JPanel) comp).setOpaque(false);
            }
            if (comp instanceof java.awt.Container) {
                hacerTransparentes((java.awt.Container) comp);
            }
        }
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelFactura = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFactura = new javax.swing.JTable();
        lblSubTFactura = new javax.swing.JLabel();
        pFCafe = new javax.swing.JPanel();
        lblFCafe = new javax.swing.JLabel();
        panelFCema = new javax.swing.JPanel();
        lblFCrema = new javax.swing.JLabel();
        panelFAzucar = new javax.swing.JPanel();
        lblFAzucar = new javax.swing.JLabel();
        panelFEnsalada = new javax.swing.JPanel();
        lblFEnsalada = new javax.swing.JLabel();
        panelFPizza = new javax.swing.JPanel();
        lblFPizza = new javax.swing.JLabel();
        panelFQueso = new javax.swing.JPanel();
        lblFQueso = new javax.swing.JLabel();
        paneleFVinagreta = new javax.swing.JPanel();
        lblFVinagreta = new javax.swing.JLabel();
        panelFPollo = new javax.swing.JPanel();
        lblFPollo = new javax.swing.JLabel();
        panelFTomate = new javax.swing.JPanel();
        lblFTomate = new javax.swing.JLabel();
        panelFSalami = new javax.swing.JPanel();
        lblFSalami = new javax.swing.JLabel();
        txtfCafe = new javax.swing.JTextField();
        txtfEnsalada = new javax.swing.JTextField();
        tctfEnsalada = new javax.swing.JTextField();
        txtfCream = new javax.swing.JTextField();
        txtfAzucar = new javax.swing.JTextField();
        txtfVinagreta = new javax.swing.JTextField();
        txtfPollo = new javax.swing.JTextField();
        txtfTomate = new javax.swing.JTextField();
        txtfSalami = new javax.swing.JTextField();
        txtfPepperoni = new javax.swing.JTextField();
        txtfQueso = new javax.swing.JTextField();
        botonTomate = new javax.swing.JButton();
        botonPollo = new javax.swing.JButton();
        botonVinagreta = new javax.swing.JButton();
        botonEnsalada = new javax.swing.JButton();
        botonAzucar = new javax.swing.JButton();
        botonCrema = new javax.swing.JButton();
        botonCafe = new javax.swing.JButton();
        botonSalami = new javax.swing.JButton();
        botonPepperoni = new javax.swing.JButton();
        botonQueso = new javax.swing.JButton();
        botonPizza = new javax.swing.JButton();
        panelFPeperoni = new javax.swing.JPanel();
        lblFPepperoni = new javax.swing.JLabel();
        panelBanner = new javax.swing.JPanel();
        lblTituloMenu = new javax.swing.JLabel();

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(1500, 1120));

        panelFactura.setBackground(new java.awt.Color(224, 239, 255));

        tblFactura.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        tblFactura.setForeground(new java.awt.Color(153, 204, 255));
        tblFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblFactura);

        lblSubTFactura.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        lblSubTFactura.setForeground(new java.awt.Color(153, 204, 255));
        lblSubTFactura.setText("FACTURA");

        javax.swing.GroupLayout panelFacturaLayout = new javax.swing.GroupLayout(panelFactura);
        panelFactura.setLayout(panelFacturaLayout);
        panelFacturaLayout.setHorizontalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFacturaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSubTFactura)
                .addGap(149, 149, 149))
        );
        panelFacturaLayout.setVerticalGroup(
            panelFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFacturaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblSubTFactura)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        pFCafe.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFCafe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/cafesimple.jpg"))); // NOI18N

        javax.swing.GroupLayout pFCafeLayout = new javax.swing.GroupLayout(pFCafe);
        pFCafe.setLayout(pFCafeLayout);
        pFCafeLayout.setHorizontalGroup(
            pFCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pFCafeLayout.createSequentialGroup()
                .addComponent(lblFCafe)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pFCafeLayout.setVerticalGroup(
            pFCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pFCafeLayout.createSequentialGroup()
                .addComponent(lblFCafe)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFCema.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFCrema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/cremacafe.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFCemaLayout = new javax.swing.GroupLayout(panelFCema);
        panelFCema.setLayout(panelFCemaLayout);
        panelFCemaLayout.setHorizontalGroup(
            panelFCemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFCemaLayout.createSequentialGroup()
                .addComponent(lblFCrema)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFCemaLayout.setVerticalGroup(
            panelFCemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFCemaLayout.createSequentialGroup()
                .addComponent(lblFCrema)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFAzucar.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFAzucar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/azucarr.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFAzucarLayout = new javax.swing.GroupLayout(panelFAzucar);
        panelFAzucar.setLayout(panelFAzucarLayout);
        panelFAzucarLayout.setHorizontalGroup(
            panelFAzucarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFAzucarLayout.createSequentialGroup()
                .addComponent(lblFAzucar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFAzucarLayout.setVerticalGroup(
            panelFAzucarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFAzucarLayout.createSequentialGroup()
                .addComponent(lblFAzucar)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFEnsalada.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFEnsalada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/ensalada.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFEnsaladaLayout = new javax.swing.GroupLayout(panelFEnsalada);
        panelFEnsalada.setLayout(panelFEnsaladaLayout);
        panelFEnsaladaLayout.setHorizontalGroup(
            panelFEnsaladaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFEnsaladaLayout.createSequentialGroup()
                .addComponent(lblFEnsalada)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFEnsaladaLayout.setVerticalGroup(
            panelFEnsaladaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFEnsaladaLayout.createSequentialGroup()
                .addComponent(lblFEnsalada)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFPizza.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFPizza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/pizzaSimple.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFPizzaLayout = new javax.swing.GroupLayout(panelFPizza);
        panelFPizza.setLayout(panelFPizzaLayout);
        panelFPizzaLayout.setHorizontalGroup(
            panelFPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFPizzaLayout.createSequentialGroup()
                .addComponent(lblFPizza)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFPizzaLayout.setVerticalGroup(
            panelFPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFPizzaLayout.createSequentialGroup()
                .addComponent(lblFPizza)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFQueso.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFQueso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/queso.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFQuesoLayout = new javax.swing.GroupLayout(panelFQueso);
        panelFQueso.setLayout(panelFQuesoLayout);
        panelFQuesoLayout.setHorizontalGroup(
            panelFQuesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFQuesoLayout.createSequentialGroup()
                .addComponent(lblFQueso)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFQuesoLayout.setVerticalGroup(
            panelFQuesoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFQuesoLayout.createSequentialGroup()
                .addComponent(lblFQueso)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        paneleFVinagreta.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFVinagreta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/vinagreta.jpg"))); // NOI18N

        javax.swing.GroupLayout paneleFVinagretaLayout = new javax.swing.GroupLayout(paneleFVinagreta);
        paneleFVinagreta.setLayout(paneleFVinagretaLayout);
        paneleFVinagretaLayout.setHorizontalGroup(
            paneleFVinagretaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneleFVinagretaLayout.createSequentialGroup()
                .addComponent(lblFVinagreta)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        paneleFVinagretaLayout.setVerticalGroup(
            paneleFVinagretaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneleFVinagretaLayout.createSequentialGroup()
                .addComponent(lblFVinagreta)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFPollo.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFPollo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/pollo.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFPolloLayout = new javax.swing.GroupLayout(panelFPollo);
        panelFPollo.setLayout(panelFPolloLayout);
        panelFPolloLayout.setHorizontalGroup(
            panelFPolloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFPolloLayout.createSequentialGroup()
                .addComponent(lblFPollo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFPolloLayout.setVerticalGroup(
            panelFPolloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFPolloLayout.createSequentialGroup()
                .addComponent(lblFPollo)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFTomate.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFTomate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/tomate.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFTomateLayout = new javax.swing.GroupLayout(panelFTomate);
        panelFTomate.setLayout(panelFTomateLayout);
        panelFTomateLayout.setHorizontalGroup(
            panelFTomateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFTomateLayout.createSequentialGroup()
                .addComponent(lblFTomate)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFTomateLayout.setVerticalGroup(
            panelFTomateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFTomateLayout.createSequentialGroup()
                .addComponent(lblFTomate)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelFSalami.setPreferredSize(new java.awt.Dimension(100, 130));

        lblFSalami.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/salami.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFSalamiLayout = new javax.swing.GroupLayout(panelFSalami);
        panelFSalami.setLayout(panelFSalamiLayout);
        panelFSalamiLayout.setHorizontalGroup(
            panelFSalamiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFSalamiLayout.createSequentialGroup()
                .addComponent(lblFSalami)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFSalamiLayout.setVerticalGroup(
            panelFSalamiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFSalamiLayout.createSequentialGroup()
                .addComponent(lblFSalami)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        txtfCafe.setBackground(new java.awt.Color(153, 204, 255));
        txtfCafe.setText("$");

        txtfEnsalada.setBackground(new java.awt.Color(153, 204, 255));
        txtfEnsalada.setText("$");

        tctfEnsalada.setBackground(new java.awt.Color(153, 204, 255));
        tctfEnsalada.setText("$");
        tctfEnsalada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tctfEnsaladaActionPerformed(evt);
            }
        });

        txtfCream.setBackground(new java.awt.Color(224, 239, 255));
        txtfCream.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        txtfCream.setText("$");

        txtfAzucar.setBackground(new java.awt.Color(153, 204, 255));
        txtfAzucar.setText("$");
        txtfAzucar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfAzucarActionPerformed(evt);
            }
        });

        txtfVinagreta.setBackground(new java.awt.Color(224, 239, 255));
        txtfVinagreta.setText("$");

        txtfPollo.setBackground(new java.awt.Color(153, 204, 255));
        txtfPollo.setText("$");

        txtfTomate.setBackground(new java.awt.Color(224, 239, 255));
        txtfTomate.setText("$");

        txtfSalami.setBackground(new java.awt.Color(224, 239, 255));
        txtfSalami.setText("$");

        txtfPepperoni.setBackground(new java.awt.Color(153, 204, 255));
        txtfPepperoni.setText("$");

        txtfQueso.setBackground(new java.awt.Color(224, 239, 255));
        txtfQueso.setText("$");
        txtfQueso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfQuesoActionPerformed(evt);
            }
        });

        botonTomate.setBackground(new java.awt.Color(255, 255, 204));
        botonTomate.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonTomate.setText("+    Tomate");
        botonTomate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTomateActionPerformed(evt);
            }
        });

        botonPollo.setBackground(new java.awt.Color(255, 255, 204));
        botonPollo.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonPollo.setText("+    Pollo");
        botonPollo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPolloActionPerformed(evt);
            }
        });

        botonVinagreta.setBackground(new java.awt.Color(255, 255, 204));
        botonVinagreta.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonVinagreta.setText("+  Vinagreta");
        botonVinagreta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVinagretaActionPerformed(evt);
            }
        });

        botonEnsalada.setBackground(new java.awt.Color(255, 255, 204));
        botonEnsalada.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonEnsalada.setText("+    Ensalada");
        botonEnsalada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnsaladaActionPerformed(evt);
            }
        });

        botonAzucar.setBackground(new java.awt.Color(255, 255, 204));
        botonAzucar.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonAzucar.setText("+    Azucar");
        botonAzucar.setToolTipText("");
        botonAzucar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAzucarActionPerformed(evt);
            }
        });

        botonCrema.setBackground(new java.awt.Color(255, 255, 204));
        botonCrema.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonCrema.setText("+    Crema");
        botonCrema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCremaActionPerformed(evt);
            }
        });

        botonCafe.setBackground(new java.awt.Color(255, 255, 204));
        botonCafe.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonCafe.setText("+    Cafe");
        botonCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCafeActionPerformed(evt);
            }
        });

        botonSalami.setBackground(new java.awt.Color(255, 255, 204));
        botonSalami.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonSalami.setText("+    Salami");
        botonSalami.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalamiActionPerformed(evt);
            }
        });

        botonPepperoni.setBackground(new java.awt.Color(255, 255, 204));
        botonPepperoni.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonPepperoni.setText("+  Pepperoni");
        botonPepperoni.setPreferredSize(new java.awt.Dimension(91, 23));
        botonPepperoni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPepperoniActionPerformed(evt);
            }
        });

        botonQueso.setBackground(new java.awt.Color(255, 255, 204));
        botonQueso.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonQueso.setText("+    Queso");
        botonQueso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonQuesoActionPerformed(evt);
            }
        });

        botonPizza.setBackground(new java.awt.Color(255, 255, 204));
        botonPizza.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        botonPizza.setText("+    Pizza");
        botonPizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPizzaActionPerformed(evt);
            }
        });

        lblFPepperoni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/decorador/pepperoni.jpg"))); // NOI18N

        javax.swing.GroupLayout panelFPeperoniLayout = new javax.swing.GroupLayout(panelFPeperoni);
        panelFPeperoni.setLayout(panelFPeperoniLayout);
        panelFPeperoniLayout.setHorizontalGroup(
            panelFPeperoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFPeperoniLayout.createSequentialGroup()
                .addComponent(lblFPepperoni)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelFPeperoniLayout.setVerticalGroup(
            panelFPeperoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFPeperoniLayout.createSequentialGroup()
                .addComponent(lblFPepperoni)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelBanner.setBackground(new java.awt.Color(153, 204, 255));
        panelBanner.setPreferredSize(new java.awt.Dimension(1043, 50));

        lblTituloMenu.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N
        lblTituloMenu.setForeground(new java.awt.Color(255, 255, 204));
        lblTituloMenu.setText("MENU");

        javax.swing.GroupLayout panelBannerLayout = new javax.swing.GroupLayout(panelBanner);
        panelBanner.setLayout(panelBannerLayout);
        panelBannerLayout.setHorizontalGroup(
            panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBannerLayout.createSequentialGroup()
                .addGap(682, 682, 682)
                .addComponent(lblTituloMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBannerLayout.setVerticalGroup(
            panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBannerLayout.createSequentialGroup()
                .addComponent(lblTituloMenu)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(panelFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pFCafe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFPizza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFEnsalada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtfCafe)
                    .addComponent(txtfEnsalada)
                    .addComponent(tctfEnsalada)
                    .addComponent(botonCafe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonEnsalada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonPizza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(126, 126, 126)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(paneleFVinagreta, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(txtfVinagreta)
                            .addComponent(botonVinagreta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonCrema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfCream))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelFPollo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfPollo)
                            .addComponent(botonPollo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonAzucar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfAzucar, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(panelFCema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelFAzucar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelFQueso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfQueso)
                            .addComponent(botonQueso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(117, 117, 117)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonPepperoni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtfPepperoni)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addComponent(panelFPeperoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(117, 117, 117)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFTomate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFSalami, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtfTomate)
                    .addComponent(txtfSalami, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonTomate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonSalami, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(133, Short.MAX_VALUE))
            .addComponent(panelBanner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addComponent(panelBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelFTomate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(panelFAzucar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfAzucar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonAzucar)
                                .addGap(18, 18, 18)
                                .addComponent(panelFPollo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(pFCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonCafe)
                                .addGap(18, 18, 18)
                                .addComponent(panelFEnsalada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(panelFCema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfCream, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonCrema)
                                .addGap(18, 18, 18)
                                .addComponent(paneleFVinagreta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtfTomate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfEnsalada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfVinagreta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfPollo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonTomate)
                            .addComponent(botonPollo)
                            .addComponent(botonVinagreta)
                            .addComponent(botonEnsalada))
                        .addGap(31, 31, 31)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelFQueso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelFPizza, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelFPeperoni, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelFSalami, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tctfEnsalada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfQueso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfPepperoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfSalami, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonSalami)
                            .addComponent(botonPepperoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonQueso)
                            .addComponent(botonPizza))))
                .addGap(1170, 1170, 1170))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAzucarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAzucarActionPerformed
        accionAzucar();
    }//GEN-LAST:event_botonAzucarActionPerformed

    private void txtfAzucarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfAzucarActionPerformed
    }//GEN-LAST:event_txtfAzucarActionPerformed

    private void tctfEnsaladaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tctfEnsaladaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tctfEnsaladaActionPerformed

    private void txtfQuesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfQuesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfQuesoActionPerformed

    private void botonEnsaladaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnsaladaActionPerformed
seleccionarEnsalada();
    }//GEN-LAST:event_botonEnsaladaActionPerformed

    private void botonVinagretaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVinagretaActionPerformed
accionVinagreta();    }//GEN-LAST:event_botonVinagretaActionPerformed

    private void botonPolloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPolloActionPerformed
accionPollo();    }//GEN-LAST:event_botonPolloActionPerformed

    private void botonTomateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTomateActionPerformed
accionTomate();    }//GEN-LAST:event_botonTomateActionPerformed

    private void botonPizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPizzaActionPerformed
seleccionarPizza();    }//GEN-LAST:event_botonPizzaActionPerformed

    private void botonQuesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonQuesoActionPerformed
accionQueso();    }//GEN-LAST:event_botonQuesoActionPerformed

    private void botonPepperoniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPepperoniActionPerformed
accionPepperoni();    }//GEN-LAST:event_botonPepperoniActionPerformed

    private void botonSalamiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalamiActionPerformed
accionSalami();    }//GEN-LAST:event_botonSalamiActionPerformed

    private void botonCremaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCremaActionPerformed
accionCrema();    }//GEN-LAST:event_botonCremaActionPerformed

    private void botonCafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCafeActionPerformed
seleccionarCafe();    }//GEN-LAST:event_botonCafeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAzucar;
    private javax.swing.JButton botonCafe;
    private javax.swing.JButton botonCrema;
    private javax.swing.JButton botonEnsalada;
    private javax.swing.JButton botonPepperoni;
    private javax.swing.JButton botonPizza;
    private javax.swing.JButton botonPollo;
    private javax.swing.JButton botonQueso;
    private javax.swing.JButton botonSalami;
    private javax.swing.JButton botonTomate;
    private javax.swing.JButton botonVinagreta;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFAzucar;
    private javax.swing.JLabel lblFCafe;
    private javax.swing.JLabel lblFCrema;
    private javax.swing.JLabel lblFEnsalada;
    private javax.swing.JLabel lblFPepperoni;
    private javax.swing.JLabel lblFPizza;
    private javax.swing.JLabel lblFPollo;
    private javax.swing.JLabel lblFQueso;
    private javax.swing.JLabel lblFSalami;
    private javax.swing.JLabel lblFTomate;
    private javax.swing.JLabel lblFVinagreta;
    private javax.swing.JLabel lblSubTFactura;
    private javax.swing.JLabel lblTituloMenu;
    private javax.swing.JPanel pFCafe;
    private javax.swing.JPanel panelBanner;
    private javax.swing.JPanel panelFAzucar;
    private javax.swing.JPanel panelFCema;
    private javax.swing.JPanel panelFEnsalada;
    private javax.swing.JPanel panelFPeperoni;
    private javax.swing.JPanel panelFPizza;
    private javax.swing.JPanel panelFPollo;
    private javax.swing.JPanel panelFQueso;
    private javax.swing.JPanel panelFSalami;
    private javax.swing.JPanel panelFTomate;
    private javax.swing.JPanel panelFactura;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel paneleFVinagreta;
    private javax.swing.JTable tblFactura;
    private javax.swing.JTextField tctfEnsalada;
    private javax.swing.JTextField txtfAzucar;
    private javax.swing.JTextField txtfCafe;
    private javax.swing.JTextField txtfCream;
    private javax.swing.JTextField txtfEnsalada;
    private javax.swing.JTextField txtfPepperoni;
    private javax.swing.JTextField txtfPollo;
    private javax.swing.JTextField txtfQueso;
    private javax.swing.JTextField txtfSalami;
    private javax.swing.JTextField txtfTomate;
    private javax.swing.JTextField txtfVinagreta;
    // End of variables declaration//GEN-END:variables



}
