package modelo;

import vista.GUI;

//Elaborado por:
//David Santiago Arias Rojas 0222510022
//Alex David Florez Cerro 0222510031

public class main {
   
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            GUI ventana = new GUI();
            ventana.setLocationRelativeTo(null); 
            ventana.setVisible(true);
        });
        
    }
}
