/// FXML Converter module.
module name.ulbricht.convert.fxml {

    requires java.xml;

    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens name.ulbricht.convert.fxml.ui to javafx.base, javafx.fxml;

    // Public application API (for the entry point)
    exports name.ulbricht.convert.fxml;
}
