package modelo;

import vista.GUI;

public class main {
    
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            GUI ventana = new GUI();
            ventana.setLocationRelativeTo(null); 
            ventana.setVisible(true);
        });
        
    }
}
