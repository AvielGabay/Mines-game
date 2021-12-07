package simpleFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BetterSinger extends Application {

	private int i;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(makeGrid());
		stage.setScene(scene);
		stage.setTitle("Voting Machine");
		stage.show();
	}

	private GridPane makeGrid() {
		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(6));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		Label counter = new Label("0");
		Button ofra = new Button("Ofra Haza");
		Button yardena = new Button("Yardena Arazi");

		class LableIncreaser implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				i++;
				counter.setText("" + i + "");
			}

		}
		class LableDecreaser implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				i--;
				counter.setText("" + i + "");

			}
		}
		ofra.setOnAction(new LableIncreaser());
		yardena.setOnAction(new LableDecreaser());
		counter.setStyle("-fx-background-color: red");
		counter.prefWidthProperty().bind(gridpane.widthProperty());
		counter.setMinHeight(30);
		counter.setAlignment(Pos.CENTER);
		
		gridpane.add(ofra, 0, 0);
		gridpane.add(yardena, 1, 0);
		gridpane.add(counter, 0, 2, 2, 1);

		ColumnConstraints c = new ColumnConstraints();
		c.setHalignment(HPos.RIGHT);
		gridpane.getColumnConstraints().add(c);

		return gridpane;
	}
}