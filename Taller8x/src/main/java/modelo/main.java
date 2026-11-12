package modelo;

import vista.GUI;

public class main {
    //recordatorio para no olvidar el boolean y la ventana emergente de "Guardar Cambios/descartar" y elaborado por:
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            GUI ventana = new GUI();
            ventana.setLocationRelativeTo(null); 
            ventana.setVisible(true);
        });
        
    }
}
