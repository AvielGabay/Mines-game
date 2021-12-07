package mines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application {

	My_controller controller;
	
	@Override
	public void start(Stage primaryStage) {
	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("mine.fxml"));
			HBox root = loader.load();
			Scene scene = new Scene(root);
			controller = loader.getController();
			primaryStage.setScene(scene);
			primaryStage.setTitle("The Amazing Mines Sweeper");
			controller.resetBtn.fire();
			primaryStage.show(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}