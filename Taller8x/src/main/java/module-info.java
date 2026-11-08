module org.example.taller8x {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.taller8x to javafx.fxml;
    exports org.example.taller8x;
}