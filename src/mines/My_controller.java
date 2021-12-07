package mines;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class My_controller {
	private boolean end = false;
	private Mines mine;
	private Image mineIcon = new Image(getClass().getResource("img/mine.png").toString());
	private int h, w, m;
	private Image flag = new Image(getClass().getResource("img/flag.png").toString());

	@FXML
	public Button resetBtn;

	@FXML
	private TextField wTxt;

	@FXML
	private TextField hTxt;

	@FXML
	private TextField mTxt;

	@FXML
	private Pane gPane;

	@FXML // create new game and updates the board
	void ClickResetBtn(ActionEvent event) {
		if (!scanVals())
			return;
		mine = new Mines(h, w, m);
		gridCreate();
		end = false;
	}

	// scan values and show error if needed
	private boolean scanVals() {
		int lasth = h, lastw = w, lastm = m;
		try {
			h = Integer.parseInt(hTxt.getText());
			w = Integer.parseInt(wTxt.getText());
			m = Integer.parseInt(mTxt.getText());
			if (h <= 0 || w <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			h = lasth;
			w = lastw;
			m = lastm;
			PopupMsg.showMsg("Wrong Values", "all Values need to be numbers grater than 0");
			return false;
		}
		if (m < 0) {
			PopupMsg.showMsg("Warning", "mines lower than 0\n mine values set to 0");
			mTxt.setText("0");
		}
		if (m > h * w) {
			PopupMsg.showMsg("Warning", "Too many mines\n mine values set to " + (h * w));
			mTxt.setText((h * w) + "");
		}
		return true;
	}

	// create the board
	private void gridCreate() {
		GridPane grid = new GridPane();
		gPane.getChildren().clear();
		gPane.getChildren().add(grid);
		for (int i = 0; i < h; i++)
			for (int j = 0; j < w; j++)
				grid.add(new MyMinePlace(i, j), j, i);
		gPane.getScene().getWindow().sizeToScene();
	}

	// update the board
	private void gridRefresh() {
		ObservableList<Node> l = ((GridPane) gPane.getChildren().get(0)).getChildren();
		for (Node n : l)
			((MyMinePlace) n).refreshTxt();
	}

	// class for board button
	private class MyMinePlace extends Button {
		private int x, y;

		public MyMinePlace(int x, int y) {
			super();
			this.x = x;
			this.y = y;
			refreshTxt();
			this.setOnMouseClicked(new PlaceHandler());
			this.setMinSize(45, 35);
			this.setStyle("-fx-font-weight: bold");
			this.setFont(new Font(16));
		}

		// set the text based on the gameBoard value
		public void refreshTxt() {
			String val = mine.get(x, y);
			switch (val) {// set Color based on the number

			case "1":
				this.setTextFill(Color.BLUE);
				break;
			case "2":
				this.setTextFill(Color.GREEN);
				break;
			case "3":
				this.setTextFill(Color.RED);
				break;
			case "4":
				this.setTextFill(Color.DARKBLUE);
				break;
			case "5":
				this.setTextFill(Color.DARKRED);
				break;
			case "6":
				this.setTextFill(Color.DARKCYAN);
				break;
			case "7":
				this.setTextFill(Color.BLACK);
				break;
			case "8":
				this.setTextFill(Color.GRAY);
				break;
			case "F":// set Flag pic
				this.setText("");
				ImageView iv = new ImageView(flag);
				iv.setFitHeight(25);
				iv.setFitWidth(24);
				this.setGraphic(iv);
				return;
			}
			this.setGraphic(null);
			this.setText(val);
		}

		// class to handle what the board button do
		private class PlaceHandler implements EventHandler<MouseEvent> {

			@Override
			public void handle(MouseEvent event) {
				if (end)// if the game ended, do nothing
					return;
				MyMinePlace clickedBtn = MyMinePlace.this;
				if (event.getButton() == MouseButton.PRIMARY) {// if left click
					if (!mine.open(x, y)) {// open and check if mine
						// ---set button to mine
						ImageView iv = new ImageView(mineIcon);
						iv.setFitHeight(25);
						iv.setFitWidth(24);
						clickedBtn.setStyle("-fx-background-color: ff0000");
						clickedBtn.setGraphic(iv);
						clickedBtn.setText("");
						// end of ---set button to mine
						lost();
						return;
					}
				} else if (event.getButton() == MouseButton.SECONDARY) {// if right click
					mine.toggleFlag(x, y);
					clickedBtn.refreshTxt();
					return;
				}

				gridRefresh();
				if (mine.isDone()) {// check if the move won the game
					end = true;// set as the end of the game

					PopupMsg.showMsg("WINNER", "You Won The Game!!");
				}

			}

			// if lost, open all mines, and show message
			private void lost() {
				ImageView iv;
				ObservableList<Node> l = ((GridPane) gPane.getChildren().get(0)).getChildren();
				for (Node n : l) {
					MyMinePlace tmp = ((MyMinePlace) n);
					if (mine.isMine(tmp.x, tmp.y)) {
						iv = new ImageView(mineIcon);
						iv.setFitHeight(25);
						iv.setFitWidth(24);
						tmp.setGraphic(iv);
						tmp.setText("");
					}
				}
				end = true;// set as the end of the game
				PopupMsg.showMsg("Lost", "You Lost The Game!!\n Try again");
				return;
			}

		}
	}

	// class to show PopUps
	private static class PopupMsg {

		public static void showMsg(String head, String body) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(head);
			alert.setHeaderText(null);
			alert.setContentText(body);
			alert.showAndWait();
		}
	}
}