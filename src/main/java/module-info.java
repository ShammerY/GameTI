module com.example.tigame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tigame to javafx.fxml;
    exports com.example.tigame;
}