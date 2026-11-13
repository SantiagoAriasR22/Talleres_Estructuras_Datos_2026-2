package vista;

import controlador.Controlador;
import java.io.File;
import modelo.Registro;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI.class.getName());
    private final Controlador controlador = new Controlador();
 
    public GUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jblHash = new javax.swing.JComboBox<>();
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

        jComboBox1.setBackground(new java.awt.Color(224, 242, 254));
        jComboBox1.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(15, 23, 42));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "File", "Open", "Save", "Close" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jComboBox2.setBackground(new java.awt.Color(224, 242, 254));
        jComboBox2.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(15, 23, 42));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Operations", "Insert", "Delete", "Search" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);

        jComboBox3.setBackground(new java.awt.Color(224, 242, 254));
        jComboBox3.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(15, 23, 42));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data Structure", "List", "Tree" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);

        jblHash.setBackground(new java.awt.Color(224, 242, 254));
        jblHash.setFont(new java.awt.Font("Microsoft Tai Le", 1, 14)); // NOI18N
        jblHash.setForeground(new java.awt.Color(15, 23, 42));
        jblHash.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hash Method", "Division", "Multiplication" }));
        jblHash.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblHash, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jblHash))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(lblRutaArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
        String opcion = (String) jComboBox1.getSelectedItem();
        
        // Se devuelve el combo a "File" de inmediato: cambiar la seleccion por codigo
        // vuelve a disparar este mismo evento, y asi la re-entrada ya no hace nada.
        jComboBox1.setSelectedIndex(0);
        
        if("Open".equals(opcion)) abrirArchivo();
        
        actualizarColisiones();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    // Pide estructura y metodo hash, abre el explorador, crea la tabla y la llena.
    // No se permite cargar sin haber elegido las dos cosas: si no, la tabla se
    // construiria con unos valores por defecto que la GUI no esta mostrando, y las
    // colisiones que aparecerian no corresponderian a lo que el usuario cree eligio.
    private void abrirArchivo(){
        
        String estructura = (String) jComboBox3.getSelectedItem();
        String metodoHash = (String) jblHash.getSelectedItem();
        
        boolean esLista;
        
        if("List".equals(estructura)){
            esLista = true;
        }
        else if("Tree".equals(estructura)){
            esLista = false;
        }
        else{
            aviso("Seleccione la estructura (List o Tree) antes de cargar el archivo.",
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
            aviso("Seleccione el metodo hash (Division o Multiplication) antes de cargar el archivo.",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser explorador = new JFileChooser();
        explorador.setDialogTitle("Seleccionar archivo CSV de placas");
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        explorador.setFileFilter(filtro);
        
        if (explorador.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        
        File archivoSeleccionado = explorador.getSelectedFile();
        
        lblRutaArchivo.setText(archivoSeleccionado.getAbsolutePath());
        
        controlador.crearTabla(esLista, esDivision);
        int cargados = controlador.cargarDatos(archivoSeleccionado);
        
        if(cargados > 0){
            aviso("El archivo se cargo con exito\n\nRegistros cargados: " + cargados,
                  javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            aviso("No se pudo cargar ningun registro\n"
                + "Verifique que el archivo exista y que tenga el formato\n"
                + "plate;first_name;last_name;gender",
                  javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        
        String operacion = (String) jComboBox2.getSelectedItem();
        
        // Igual que el combo de File: se regresa a "Operations" de una vez para que
        // la re-entrada del evento no vuelva a abrir el dialogo.
        jComboBox2.setSelectedIndex(0);
        
        if("Operations".equals(operacion)) return;
        
        if(!controlador.hayTabla()){
            aviso("Primero cargue un archivo CSV con File > Open.", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if("Insert".equals(operacion)) insertar();
        else if("Delete".equals(operacion)) eliminar();
        else if("Search".equals(operacion)) buscar();
    }//GEN-LAST:event_jComboBox2ActionPerformed

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
                "Insertar registro", javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE);
        
        if(respuesta != javax.swing.JOptionPane.OK_OPTION) return;
        
        String plate = txtPlate.getText().trim();
        
        if(plate.isEmpty()){
            aviso("El plate no puede estar vacio.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // El plate es el id: si ya existe, insertar duplicaria el registro.
        if(controlador.buscarRegistro(plate) != null){
            aviso("Ya existe un registro con el plate " + plate + ".",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Registro nuevo = new Registro(plate, txtNombre.getText().trim(),
                txtApellido.getText().trim(), (String) cbGenero.getSelectedItem());
        
        controlador.insertarRegistro(nuevo);
        actualizarColisiones();
        
        aviso("Registro insertado: " + plate, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminar(){
        
        String plate = pedirPlate("Eliminar registro", "Plate del registro a eliminar:");
        if(plate == null) return;
        
        boolean borrado = controlador.eliminarRegistro(plate);
        
        actualizarColisiones();
        
        if(borrado){
            aviso("Registro eliminado: " + plate, javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            aviso("No existe un registro con el plate " + plate + ".",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void buscar(){
        
        String plate = pedirPlate("Buscar registro", "Plate del registro a buscar:");
        if(plate == null) return;
        
        Registro encontrado = controlador.buscarRegistro(plate);
        
        if(encontrado == null){
            aviso("No existe un registro con el plate " + plate + ".",
                  javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        aviso("Plate:      " + encontrado.getPlate() + "\n"
            + "First name: " + encontrado.getFirtsName() + "\n"
            + "Last name:  " + encontrado.getLastName() + "\n"
            + "Gender:     " + encontrado.getGenero(),
              javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Pide un plate por teclado. Devuelve null si se cancela o si queda vacio.
    private String pedirPlate(String titulo, String mensaje){
        
        String texto = javax.swing.JOptionPane.showInputDialog(this, mensaje, titulo,
                javax.swing.JOptionPane.QUESTION_MESSAGE);
        
        if(texto == null) return null;
        
        texto = texto.trim();
        
        if(texto.isEmpty()){
            aviso("El plate no puede estar vacio.", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return texto;
    }
    
    private void actualizarColisiones(){
        jblColisiones.setText(String.valueOf(controlador.cargarColisiones()));
    }
    
    private void aviso(String mensaje, int tipo){
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Taller 8", tipo);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new GUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel jblColisiones;
    private javax.swing.JComboBox<String> jblHash;
    private javax.swing.JLabel lblRutaArchivo;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
