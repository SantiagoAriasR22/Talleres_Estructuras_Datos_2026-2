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
    
    private final javax.swing.JPanel contenedorSlots = new javax.swing.JPanel();
   
    public GUI() {
        initComponents();
        configurarVistaSlots();
        
        jblHash.addActionListener(e -> actualizarEstructura());
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cerrarAplicacion();
            }
        });
    }
    
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
        
        scroll.setPreferredSize(new java.awt.Dimension(640, 360));
        
        jPanel3.removeAll();
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(scroll, java.awt.BorderLayout.CENTER);
        jPanel3.add(panel, java.awt.BorderLayout.SOUTH);
        
        jPanel3.revalidate();
        jPanel3.repaint();
        
        actualizarSlots();
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

        jComboBoxFile.setSelectedIndex(0);
        
        if("Open".equals(opcion)) abrirArchivo();
        else if("Save".equals(opcion)) save();
        else if("Close".equals(opcion)) cerrarAplicacion();
        actualizarVista();
    }//GEN-LAST:event_jComboBoxFileActionPerformed
    
    private void abrirArchivo(){
        
        //pide la estructura y el metodo hash
        String estructura = (String) jComboBoxDataEstruc.getSelectedItem();
        String metodoHash = (String) jblHash.getSelectedItem();
        
        boolean esLista;
        
        //valida que se haya seleccionado una estructura antes de abrir el archivo
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
        
        //valida que se haya seleccionado un metodo de hash antes de abrir el archivo
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
        
        //se actualiza el label con la ruta del archivo
        lblRutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
        
        //Se ajustan los parametros de la tabla
        controlador.crearTabla(esLista, esDivision);
        //se cargan todos los registros a la tabla
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
    
    //se ejecuta al cambiar la estructura o el metodo hash
    private void actualizarEstructura(){
        
        if(!controlador.hayTabla()) return; 
        
        String estructura = (String) jComboBoxDataEstruc.getSelectedItem();
        String metodoHash = (String) jblHash.getSelectedItem();
        
        boolean esLista;
        if("List".equals(estructura)) esLista = true;
        else if("Tree".equals(estructura)) esLista = false;
        else return;                          
        
        boolean esDivision;
        if("Division".equals(metodoHash)) esDivision = true;
        else if("Multiplication".equals(metodoHash)) esDivision = false;
        else return;                          
        
        controlador.cambiarEstructura(esLista, esDivision);
        actualizarVista();
    }

    private void jComboBoxOperationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxOperationsActionPerformed
        
        String operacion = (String) jComboBoxOperations.getSelectedItem();
        
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

        // No se puede medir si no hay una tabla cargada.
        if(!controlador.hayTabla()){
            aviso("Load a CSV file first using File > Open.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.util.ArrayList<Registro> registros = controlador.getRegistros();

        if(registros.isEmpty()){
            aviso("The loaded file has no records to measure.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        String reporte;

        try {
            reporte = Estadisticas.generarReporte(registros);
        }
        catch(RuntimeException ex){
            setCursor(java.awt.Cursor.getDefaultCursor());
            aviso("The test failed:\n" + ex.getMessage(),
                  javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        finally {
            setCursor(java.awt.Cursor.getDefaultCursor());
        }

        javax.swing.JEditorPane vista = new javax.swing.JEditorPane("text/html", reporte);
        vista.setEditable(false);
        vista.setCaretPosition(0);
        vista.setBackground(java.awt.Color.WHITE);
        vista.setBorder(null);

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(vista);
        scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        scroll.setPreferredSize(new java.awt.Dimension(480, 540));
        scroll.getViewport().setBackground(java.awt.Color.WHITE);

        javax.swing.JOptionPane.showMessageDialog(this, scroll,
                "Part 2: Linked List vs. Binary Tree", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void insertar(){
        
        javax.swing.JTextField txtPlate = new javax.swing.JTextField(12);
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(12);
        javax.swing.JTextField txtApellido = new javax.swing.JTextField(12);
        
        String[] generos = {"Female", "Male", "Non-binary", "Bigender", "Genderqueer"};
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
        
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this, formulario,"Insert record", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
        
        if(respuesta != javax.swing.JOptionPane.OK_OPTION) return;
        
        String plate = txtPlate.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        
        if(plate.isEmpty()){
            aviso("The plate cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(nombre.isEmpty()){
            aviso("The first name cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(apellido.isEmpty()){
            aviso("The last name cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //se valida que no se inserte un plate que ya existe
        if(controlador.buscarRegistro(plate) != null){
            aviso("A record with plate " + plate + " already exists.", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Registro nuevo = new Registro(plate, txtNombre.getText().trim(), txtApellido.getText().trim(), (String) cbGenero.getSelectedItem());
        
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
            aviso("No record found with plate " + plate + ".", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        aviso("Plate:      " + encontrado.getPlate() + "\n" + "First name: " + encontrado.getFirtsName() + "\n" + "Last name:  " + encontrado.getLastName() + "\n"
            + "Gender:     " + encontrado.getGenero(), javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean save(){
        
        //se valida por si se quiere dar al boton de save antes de cargar un archivo
        if(!controlador.hayTabla()){
            aviso("Load a CSV file first using File > Open.", javax.swing.JOptionPane.WARNING_MESSAGE);
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
            aviso("File saved to:\n" + destino.getAbsolutePath(), javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            aviso("The file could not be saved.\nThe table in memory was not modified.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    //cierra la aplicacion, si hay cambios sin guardar pregunta antes
    private void cerrarAplicacion(){
        
        if(controlador.hayCambios()){
            
            int opcion = javax.swing.JOptionPane.showConfirmDialog(this,
                    "There are unsaved changes.\nDo you want to save them before closing?",
                    "Unsaved changes",
                    javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            
            if(opcion == javax.swing.JOptionPane.YES_OPTION){
                //si hubo un error al guarda, no se cierra la aplicacion
                if(!save()){
                    return;              
                }
            }
            else if(opcion != javax.swing.JOptionPane.NO_OPTION){
                return;
            }
        }
        
        System.exit(0);
    }
    
    private String pedirPlate(String titulo, String mensaje){
        
        String texto = javax.swing.JOptionPane.showInputDialog(this, mensaje, titulo, javax.swing.JOptionPane.QUESTION_MESSAGE);
        
        if(texto == null) return null;
        
        texto = texto.trim();
        
        if(texto.isEmpty()){
            aviso("The plate cannot be empty.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return texto;
    }
    
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
            
            java.util.ArrayList<SlotVista> slots = controlador.slotsConVarios();
            
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
