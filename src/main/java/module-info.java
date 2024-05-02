module com.app.cs151synter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires stanford.corenlp;
    requires java.logging;
    requires convertapi;
    requires pdfbox;
    requires java.sql;

    opens com.app.cs151synter to javafx.fxml, javafx.graphics;
    opens com.app.cs151synter.ui to javafx.fxml, javafx.graphics;
//    exports com.app.cs151synter to javafx.fxml, javafx.graphics;
}
