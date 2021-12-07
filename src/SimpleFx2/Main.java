package SimpleFx2;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Load the FXML file and set the Stage
		VBox vbox;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("bettersinger.fxml"));
			vbox = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene s = new Scene(vbox);

		primaryStage.setScene(s);
		primaryStage.setTitle("Voting Machine");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}