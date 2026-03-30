package name.ulbricht.convert.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import name.ulbricht.convert.fxml.ui.MainWindow;

public final class App extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final var fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
        fxmlLoader.load();

        final var root = fxmlLoader.<Parent>getRoot();
        final var scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fluent UI System Icons - FXML Converter");
        primaryStage.show();
    }
}
