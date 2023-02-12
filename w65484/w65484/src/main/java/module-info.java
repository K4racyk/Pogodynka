module com.example.w65484 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.w65484 to javafx.fxml;
    exports com.example.w65484;
}