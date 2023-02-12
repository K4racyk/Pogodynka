package com.example.w65484;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DbAccessor.createConnection();
        Create.createTable();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Aplikacja pogodowa");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop(){
        DbAccessor.closeConnection();
    }

    public static void main(String[] args) {
        launch();
    }

}