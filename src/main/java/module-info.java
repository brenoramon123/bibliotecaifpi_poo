module org.ifpi.bibliotecaif {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;


    opens org.ifpi.bibliotecaif to javafx.fxml, javafx.base;
    opens org.ifpi.bibliotecaif.model.entities to javafx.base;
    exports org.ifpi.bibliotecaif;
}