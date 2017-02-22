package controllers;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Start extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Start.class.getResource("MainGUI.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root,660,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Daily Planner");
		primaryStage.show();	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
		
}

