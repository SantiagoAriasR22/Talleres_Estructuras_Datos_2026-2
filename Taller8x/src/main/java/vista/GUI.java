package vista;

import controlador.Controlador;
import estadisticas.Estadisticas;
import java.io.File;
import modelo.Registro;
import modelo.SlotVista;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI.class.getName());
    private final Controlador controlador = new Controlador();
    
    // Contenedor donde se apilan las filas de la vista de slots.
    private final javax.swing.JPanel contenedorSlots = new javax.swing.JPanel();
   
    public GUI() {
        initComponents();
        configurarVistaSlots();
        
        // El combo del metodo hash no tiene EventHandler en el .form, asi que se
        // conecta aqui, fuera del bloque generado, para que NetBeans no lo borre
        // al regenerar initComponents().
        jblHash.addActionListener(e -> actualizarEstructura());
        
        // Cerrar con la X de la ventana tambien debe preguntar por los cambios
        // sin guardar, asi que se desactiva el cierre automatico y se maneja aparte.
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrarAplicacion();
            }
        });
    }
    
    // Coloca la vista de slots en el area libre de la ventana, encima de la barra
    // de estado y dentro de un JScrollPane con scroll en los dos ejes.
    private void configurarVistaSlots(){
        
        contenedorSlots.setLayout(new javax.swing.BoxLayout(contenedorSlots, javax.swing.BoxLayout.Y_AXIS));
        contenedorSlots.setBackground(new java.awt.Color(248, 250, 252));
        contenedorSlots.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(contenedorSlots);
        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new java.awt.Color(248, 250, 252));
        scroll.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getHorizontalScrollBar().setUnitIncrement(24);
        scroll.getVerticalScrollBar().setUnitIncrement(24);
        
        // Un JScrollPane sin tamano preferido adopta el del contenido, que aqui
        // puede medir decenas de miles de pixeles. Se le fija uno razonable para
        // que no infle el tamano de la ventana; el BorderLayout lo estira igual.
        scroll.setPreferredSize(new java.awt.Dimension(640, 360));
        
        // jPanel3 solo tenia la barra de estado: se reemplaza su contenido por el
        // scroll de slots en el medio y la barra de estado (ruta del archivo) abajo.
        jPanel3.removeAll();
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(scroll, java.awt.BorderLayout.CENTER);
        jPanel3.add(panel, java.awt.BorderLayout.SOUTH);
        
        jPanel3.revalidate();
        jPanel3.repaint();
        
        actualizarSlots();
    }
    
    // Corre la prueba de rendimiento y muestra el informe en una ventana emergente.
    // Tarda decimas de segundo, por eso corre directo en el hilo de la interfaz.
    private void ejecutarPrueba(){
        
        // No se puede medir si no hay datos cargados: la prueba trabaja sobre el
        // archivo que el usuario abrio con File > Open.
        if(!controlador.hayTabla()){
            aviso("Load a CSV file first using File > Open, then run the test.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        File archivo = controlador.getArchivoActual();
        
        if(archivo == null || !archivo.exists()){
            aviso("The loaded file could not be found.",
                  javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Estadisticas.Informe informe;
        
        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        try {
            informe = Estadisticas.calcular(archivo);
        }
        catch(RuntimeException ex){
            aviso("The test failed:\n" + ex.getMessage(),
                  javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        finally {
            setCursor(java.awt.Cursor.getDefaultCursor());
        }
        
        if(informe.registros == 0){
            aviso("No records could be read from:\n" + archivo.getAbsolutePath(),
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        javax.swing.JEditorPane vista = new javax.swing.JEditorPane("text/html", construirInformeHTML(informe));
        vista.setEditable(false);
        vista.setCaretPosition(0);
        vista.setBackground(java.awt.Color.WHITE);
        
        javax.swing.JScrollPane scrollInforme = new javax.swing.JScrollPane(vista);
        scrollInforme.setPreferredSize(new java.awt.Dimension(740, 520));
        scrollInforme.getViewport().setBackground(java.awt.Color.WHITE);
        
        javax.swing.JOptionPane.showMessageDialog(this, scrollInforme,
                "Performance test", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Arma el informe como HTML para que se vea como tablas, no como texto plano.
    private String construirInformeHTML(Estadisticas.Informe informe){
        
        StringBuilder h = new StringBuilder();
        
        h.append("<html><body><font face='Segoe UI' size='3'>");
        h.append("<h3>Performance test</h3>");
        h.append("<p>File: <b>").append(informe.archivo).append("</b><br>");
        h.append("Records: ").append(informe.registros)
         .append(" &nbsp;|&nbsp; Warm-up: ").append(informe.calentamiento)
         .append(" runs &nbsp;|&nbsp; Repetitions: ").append(informe.repeticiones)
         .append(" runs (median)</p>");
        
        h.append("<b>Average time per operation</b>");
        h.append("<table border='1' cellspacing='0' cellpadding='5'>");
        h.append("<tr bgcolor='#dbeafe'>")
         .append("<th align='left'>Method</th>")
         .append("<th align='left'>Operation</th>")
         .append("<th>List (ns)</th>")
         .append("<th>Tree (ns)</th>")
         .append("<th>Improvement</th>")
         .append("</tr>");
        
        for(Estadisticas.Fila f : informe.filas){
            
            double mejora = f.mejora();
            String color = (mejora >= 0) ? "#15803d" : "#b91c1c";
            String signo = (mejora >= 0) ? "+" : "";
            
            h.append("<tr>")
             .append("<td>").append(f.metodo).append("</td>")
             .append("<td>").append(f.operacion).append("</td>")
             .append("<td align='right'>").append(numero(f.lista)).append("</td>")
             .append("<td align='right'>").append(numero(f.arbol)).append("</td>")
             .append("<td align='right'><font color='").append(color).append("'>")
             .append(signo).append(numero(mejora)).append(" %</font></td>")
             .append("</tr>");
        }
        
        h.append("</table>");
        
        h.append("<br><b>Resulting structure</b>");
        h.append("<table border='1' cellspacing='0' cellpadding='5'>");
        h.append("<tr bgcolor='#dbeafe'>")
         .append("<th align='left'>Method</th>")
         .append("<th align='left'>Structure</th>")
         .append("<th>Slots</th>")
         .append("<th>Avg chain</th>")
         .append("<th>Avg height</th>")
         .append("<th>Max</th>")
         .append("</tr>");
        
        for(Estadisticas.Estructura e : informe.estructura){
            h.append("<tr>")
             .append("<td>").append(e.metodo).append("</td>")
             .append("<td>").append(e.tipo).append("</td>")
             .append("<td align='right'>").append(e.slots).append("</td>")
             .append("<td align='right'>").append(e.cadena < 0 ? "-" : numero(e.cadena)).append("</td>")
             .append("<td align='right'>").append(e.altura < 0 ? "-" : numero(e.altura)).append("</td>")
             .append("<td align='right'>").append(e.maximo).append("</td>")
             .append("</tr>");
        }
        
        h.append("</table>");
        h.append("</font></body></html>");
        
        return h.toString();
    }
    
    private String numero(double valor){
        return String.format(java.util.Locale.US, "%.1f", valor);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxFile = new javax.swing.JComboBox<>();
        jComboBoxOperations = new javax.swing.JComboBox<>();
        jComboBoxDataEstruc = new javax.swing.JComboBox<>();
        jblHash = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        lblRutaArchivo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jblColisiones = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(226, 232, 240));

        jPanel2.setBackground(new java.awt.Color(248, 250, 252));
        jPanel2.setForeground(new java.awt.Color(255, 250, 250));

        jComboBoxFile.setBackground(new java.awt.Color(224, 242, 254));
        jComboBoxFile.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jComboBoxFile.setForeground(new java.awt.Color(15, 23, 42));
        jComboBoxFile.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "File", "Open", "Save", "Close" }));
        jComboBoxFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jComboBoxFile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBoxFile.addActionListener(this::jComboBoxFileActionPerformed);

        jComboBoxOperations.setBackground(new java.awt.Color(224, 242, 254));
        jComboBoxOperations.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jComboBoxOperations.setForeground(new java.awt.Color(15, 23, 42));
        jComboBoxOperations.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Operations", "Insert", "Delete", "Search" }));
        jComboBoxOperations.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jComboBoxOperations.addActionListener(this::jComboBoxOperationsActionPerformed);

        jComboBoxDataEstruc.setBackground(new java.awt.Color(224, 242, 254));
        jComboBoxDataEstruc.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jComboBoxDataEstruc.setForeground(new java.awt.Color(15, 23, 42));
        jComboBoxDataEstruc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data Structure", "List", "Tree" }));
        jComboBoxDataEstruc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jComboBoxDataEstruc.addActionListener(this::jComboBoxDataEstrucActionPerformed);

        jblHash.setBackground(new java.awt.Color(224, 242, 254));
        jblHash.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jblHash.setForeground(new java.awt.Color(15, 23, 42));
        jblHash.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hash Method", "Division", "Multiplication" }));
        jblHash.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));

        jPanel5.setBackground(new java.awt.Color(248, 250, 252));

        jButton2.setBackground(new java.awt.Color(224, 242, 254));
        jButton2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jButton2.setText("Run a performance test");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBoxFile, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxOperations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxDataEstruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblHash, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxFile)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxOperations, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxDataEstruc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jblHash))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(248, 250, 252));

        panel.setBackground(new java.awt.Color(226, 232, 240));

        lblRutaArchivo.setFont(new java.awt.Font("Microsoft Tai Le", 2, 14)); // NOI18N
        lblRutaArchivo.setText("Waiting file...");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRutaArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRutaArchivo)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(490, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(248, 250, 252));

        jLabel1.setFont(new java.awt.Font("Microsoft Tai Le", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Collisions");

        jPanel6.setBackground(new java.awt.Color(224, 242, 254));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(15, 23, 42), 2, true));

        jblColisiones.setBackground(new java.awt.Color(173, 216, 230));
        jblColisiones.setFont(new java.awt.Font("Microsoft Tai Le", 0, 48)); // NOI18N
        jblColisiones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jblColisiones.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jblColisiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jblColisiones, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFileActionPerformed
        
        String opcion = (String) jComboBoxFile.getSelectedItem();
        
        // Se devuelve el combo a "File" de inmediato: cambiar la seleccion por codigo
        // vuelve a disparar este mismo evento, y asi la re-entrada ya no hace nada.
        jComboBoxFile.setSelectedIndex(0);
        
        if("Open".equals(opcion)) abrirArchivo();
        else if("Save".equals(opcion)) save();
        else if("Close".equals(opcion)) cerrarAplicacion();
        actualizarVista();
    }//GEN-LAST:event_jComboBoxFileActionPerformed
    
    // Pide estructura y metodo hash, abre el explorador, crea la tabla y la llena.
    // No se permite cargar sin haber elegido las dos cosas: si no, la tabla se
    // construiria con unos valores por defecto que la GUI no esta mostrando, y las
    // colisiones que aparecerian no corresponderian a lo que el usuario cree eligio.
    private void abrirArchivo(){
        
        String estructura = (String) jComboBoxDataEstruc.getSelectedItem();
        String metodoHash = (String) jblHash.getSelectedItem();
        
        boolean esLista;
        
        if("List".equals(estructura)){
            esLista = true;
        }
        else if("Tree".equals(estructura)){
            esLista = false;
        }
        else{
            aviso("Select the data structure (List or Tree) before loading the file.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean esDivision;
        
        if("Division".equals(metodoHash)){
            esDivision = true;
        }
        else if("Multiplication".equals(metodoHash)){
            esDivision = false;
        }
        else{
            aviso("Select the hash method (Division or Multiplication) before loading the file.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser explorador = new JFileChooser();
        explorador.setDialogTitle("Select CSV file");
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        explorador.setFileFilter(filtro);
        
        if (explorador.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        
        File archivoSeleccionado = explorador.getSelectedFile();
        
        lblRutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
        
        controlador.crearTabla(esLista, esDivision);
        int cargados = controlador.cargarDatos(archivoSeleccionado);
        
        if(cargados > 0){
            aviso("The file was loaded successfully.\n\nRecords loaded: " + cargados,
                  javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            aviso("No records could be loaded\n"
                + "Check that the file exists and has the format\n"
                + "plate;first_name;last_name;gender",
                  javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jComboBoxDataEstrucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDataEstrucActionPerformed
        actualizarEstructura();
    }//GEN-LAST:event_jComboBoxDataEstrucActionPerformed
    
    // Se dispara al cambiar la estructura o el metodo hash.
    // Reconstruye la tabla en memoria, sin tocar el archivo: los registros viven
    // en el Controlador, asi que no se pierde nada aunque no se haya guardado.
    private void actualizarEstructura(){
        
        if(!controlador.hayTabla()) return;   // todavia no hay nada que reconstruir
        
        String estructura = (String) jComboBoxDataEstruc.getSelectedItem();
        String metodoHash = (String) jblHash.getSelectedItem();
        
        boolean esLista;
        if("List".equals(estructura)) esLista = true;
        else if("Tree".equals(estructura)) esLista = false;
        else return;                          // placeholder: se ignora sin molestar
        
        boolean esDivision;
        if("Division".equals(metodoHash)) esDivision = true;
        else if("Multiplication".equals(metodoHash)) esDivision = false;
        else return;                          // placeholder: se ignora sin molestar
        
        controlador.cambiarEstructura(esLista, esDivision);
        actualizarVista();
    }

    private void jComboBoxOperationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOperationsActionPerformed
        
        String operacion = (String) jComboBoxOperations.getSelectedItem();
        
        // Igual que el combo de File: se regresa a "Operations" de una vez para que
        // la re-entrada del evento no vuelva a abrir el dialogo.
        jComboBoxOperations.setSelectedIndex(0);
        
        if("Operations".equals(operacion)) return;
        
        if(!controlador.hayTabla()){
            aviso("Load a CSV file first using File > Open.", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if("Insert".equals(operacion)) insertar();
        else if("Delete".equals(operacion)) eliminar();
        else if("Search".equals(operacion)) buscar();
    }//GEN-LAST:event_jComboBoxOperationsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ejecutarPrueba();
    }//GEN-LAST:event_jButton2ActionPerformed

    // ------------------------------------------------------------------
    // Operaciones del menu Operations
    // ------------------------------------------------------------------
    
    private void insertar(){
        
        javax.swing.JTextField txtPlate = new javax.swing.JTextField(12);
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(12);
        javax.swing.JTextField txtApellido = new javax.swing.JTextField(12);
        
        String[] generos = {"Female", "Male", "Binary", "Helicopter"};
        javax.swing.JComboBox<String> cbGenero = new javax.swing.JComboBox<>(generos);
        
        javax.swing.JPanel formulario = new javax.swing.JPanel(new java.awt.GridLayout(0, 2, 6, 6));
        formulario.add(new javax.swing.JLabel("Plate:"));
        formulario.add(txtPlate);
        formulario.add(new javax.swing.JLabel("First name:"));
        formulario.add(txtNombre);
        formulario.add(new javax.swing.JLabel("Last name:"));
        formulario.add(txtApellido);
        formulario.add(new javax.swing.JLabel("Gender:"));
        formulario.add(cbGenero);
        
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this, formulario,
                "Insert record", javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE);
        
        if(respuesta != javax.swing.JOptionPane.OK_OPTION) return;
        
        String plate = txtPlate.getText().trim();
        
        if(plate.isEmpty()){
            aviso("The plate cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // El plate es el id: si ya existe, insertar duplicaria el registro.
        if(controlador.buscarRegistro(plate) != null){
            aviso("A record with plate " + plate + " already exists.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Registro nuevo = new Registro(plate, txtNombre.getText().trim(),
                txtApellido.getText().trim(), (String) cbGenero.getSelectedItem());
        
        controlador.insertarRegistro(nuevo);
        actualizarVista();
        
        aviso("Record inserted: " + plate, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminar(){
        
        String plate = pedirPlate("Delete record", "Plate of the record to delete:");
        if(plate == null) return;
        
        boolean borrado = controlador.eliminarRegistro(plate);
        
        actualizarVista();
        
        if(borrado){
            aviso("Record deleted: " + plate, javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            aviso("No record found with plate " + plate + ".",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void buscar(){
        
        String plate = pedirPlate("Search record", "Plate of the record to search:");
        if(plate == null) return;
        
        Registro encontrado = controlador.buscarRegistro(plate);
        
        if(encontrado == null){
            aviso("No record found with plate " + plate + ".",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        aviso("Plate:      " + encontrado.getPlate() + "\n"
            + "First name: " + encontrado.getFirtsName() + "\n"
            + "Last name:  " + encontrado.getLastName() + "\n"
            + "Gender:     " + encontrado.getGenero(),
              javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Guarda el estado actual de la tabla. Ya no cambia la estructura: eso lo hace
    // el combo Data Structure / Hash Method al seleccionarse.
    // Se pide el destino para no sobrescribir por accidente el dataset original.
    public boolean save(){
        
        if(!controlador.hayTabla()){
            aviso("Load a CSV file first using File > Open.", 
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        JFileChooser explorador = new JFileChooser();
        explorador.setDialogTitle("Save table state");
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
        explorador.setFileFilter(filtro);
        
        if(controlador.getArchivoActual() != null){
            explorador.setSelectedFile(controlador.getArchivoActual());
        }
        
        if(explorador.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return false;
        
        File destino = explorador.getSelectedFile();
        
        if(!destino.getName().toLowerCase().endsWith(".csv")){
            destino = new File(destino.getAbsolutePath() + ".csv");
        }
        
        if(controlador.guardarDatos(destino)){
            aviso("File saved to:\n" + destino.getAbsolutePath(),
                  javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            aviso("The file could not be saved.\nThe table in memory was not modified.", 
                  javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Cierra la aplicacion. Si hay cambios sin guardar, pregunta antes.
    // Save -> guarda y cierra (si el guardado falla o se cancela, no cierra).
    // Don't save -> descarta y cierra.
    // Cancel -> no hace nada.
    private void cerrarAplicacion(){
        
        if(controlador.hayCambios()){
            
            int opcion = javax.swing.JOptionPane.showConfirmDialog(this,
                    "There are unsaved changes.\nDo you want to save them before closing?",
                    "Unsaved changes",
                    javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            
            if(opcion == javax.swing.JOptionPane.YES_OPTION){
                if(!save()){
                    return;              
                }
            }
            else if(opcion != javax.swing.JOptionPane.NO_OPTION){
                return;                  // cancelo: no se cierra
            }
        }
        
        System.exit(0);
    }
    
    // Pide un plate por teclado. Devuelve null si se cancela o si queda vacio.
    private String pedirPlate(String titulo, String mensaje){
        
        String texto = javax.swing.JOptionPane.showInputDialog(this, mensaje, titulo,
                javax.swing.JOptionPane.QUESTION_MESSAGE);
        
        if(texto == null) return null;
        
        texto = texto.trim();
        
        if(texto.isEmpty()){
            aviso("The plate cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return texto;
    }
    
    // Refresca todo lo que depende del estado de la tabla.
    private void actualizarVista(){
        actualizarColisiones();
        actualizarSlots();
    }
    
    private void actualizarColisiones(){
        jblColisiones.setText(String.valueOf(controlador.cargarColisiones()));
    }
    
    private void actualizarSlots(){
        
        contenedorSlots.removeAll();
        
        if(!controlador.hayTabla()){
            contenedorSlots.add(mensajeLista("Load a CSV file using File > Open to view the slots"));
        }
        else{
            
            java.util.List<SlotVista> slots = controlador.slotsConVarios();
            
            if(slots.isEmpty()){
                contenedorSlots.add(mensajeLista("No slot has more than one record"));
            }
            else{
                for(int i = 0; i < slots.size(); i++){
                    
                    SlotVista s = slots.get(i);
                    
                    javax.swing.JComponent fila = s.esLista
                            ? new PanelLista(s.indice, s.placas)
                            : new PanelArbol(s.indice, s.raiz);
                    
                    fila.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                    contenedorSlots.add(fila);
                    
                    if(i < slots.size() - 1){
                        contenedorSlots.add(separador());
                    }
                }
            }
        }
        
        contenedorSlots.revalidate();
        contenedorSlots.repaint();
    }
    
    private javax.swing.JLabel mensajeLista(String texto){
        
        javax.swing.JLabel etiqueta = new javax.swing.JLabel(texto);
        
        etiqueta.setFont(new java.awt.Font("Microsoft Tai Le", java.awt.Font.PLAIN, 14));
        etiqueta.setForeground(new java.awt.Color(100, 116, 139));
        etiqueta.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 10, 0, 0));
        etiqueta.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        
        return etiqueta;
    }
    
    private javax.swing.JSeparator separador(){
        
        javax.swing.JSeparator linea = new javax.swing.JSeparator();
        
        linea.setForeground(new java.awt.Color(226, 232, 240));
        linea.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 1));
        linea.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        
        return linea;
    }
    
    private void aviso(String mensaje, int tipo){
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Hash Table", tipo);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBoxDataEstruc;
    private javax.swing.JComboBox<String> jComboBoxFile;
    private javax.swing.JComboBox<String> jComboBoxOperations;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel jblColisiones;
    private javax.swing.JComboBox<String> jblHash;
    private javax.swing.JLabel lblRutaArchivo;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
