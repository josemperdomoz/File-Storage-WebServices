package org.alfalfa.fx;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/bootstrap2overview.fxml"));
        
        loader.load();
        //primaryStage.getIcons().add(new Image("server.png"));
        Image applicationIcon = new Image(getClass().getResourceAsStream("/fxml/server.png"));
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setTitle("File Storage Service");
        primaryStage.setScene(new Scene(loader.<ScrollPane>getRoot(), 900, 700));
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
