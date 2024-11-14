module com.example.salary_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.salary_app to javafx.fxml;
    exports com.example.salary_app;
}