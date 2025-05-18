module eseo.cpoo.jfx.echecpointexe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens eseo.cpoo.jfx.echecpointexe to javafx.fxml;
    exports eseo.cpoo.jfx.echecpointexe;
}