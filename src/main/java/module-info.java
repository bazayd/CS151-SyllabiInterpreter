module com.app.cs151synter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires stanford.corenlp;
    requires java.logging;
    requires convertapi;
    requires pdfbox;

    opens com.app.cs151synter to javafx.fxml;
    exports com.app.cs151synter;
}
