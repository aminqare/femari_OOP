module Femari.Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;

    opens stronghold.controller.graphical to javafx.fxml, javafx.graphics;
    opens stronghold.model.components to com.google.gson;
    opens stronghold.model.cards to com.google.gson;
    opens stronghold.model.specialCards to com.google.gson;
    exports stronghold.view to javafx.graphics, javafx.fxml;
    exports stronghold to javafx.graphics;
    exports stronghold.view.graphics to javafx.graphics, javafx.fxml;
    //exports stronghold.model.components.game.graphical to javafx.graphics;
    exports stronghold.controller to javafx.fxml;
    exports stronghold.controller.graphical to javafx.fxml, javafx.graphics;
    exports stronghold.model.components;

}