package mines;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Controller {
	private LocationsButtons[][] boardButtons;
	private HBox hbox;
	private Mines board;
	@SuppressWarnings("unused")
	private Stage stage;
	private Label msg;
	private int width, height, numOfMines;

	@FXML
	private TextField Twidth;

	@FXML
	private TextField Theight;

	@FXML
	private TextField mines;

	@FXML
	void pressReset(ActionEvent event) {
		//the reset button will open new game.
		openGame();
	}

	public void openGame() {
		GridPane gridPane = new GridPane();
		// create two lists for column constraints and row constraints.
		getInputForMines();
		// get input from user.

		board = new Mines(height, width, numOfMines); // initialize board like we did in class "Mines".
		boardButtons = new LocationsButtons[height][width];// create array of buttons, with its total height and width.

		startGame(gridPane);

		// apply the column constraints on the board in gridPane.
		for (int i = 0; i < width; i++)
			gridPane.getColumnConstraints().add(new ColumnConstraints(40));
		for (int i = 0; i < height; i++)
			gridPane.getRowConstraints().add(new RowConstraints(40));

		hbox.getChildren().remove(
				hbox.getChildren().size() - 1); /*
												 * after we reset, remove the previous board and start a new one.
												 */
		hbox.getChildren().add(gridPane); // add the board to the screen.
	}

	private void getInputForMines() {
		// save the height, number of mines, width as integers.
		height = Integer.parseInt(Theight.getText());
		width = Integer.parseInt(Twidth.getText());
		numOfMines = Integer.parseInt(mines.getText());
	}

	private void startGame(GridPane gridPane) {
		//set each [i][j] index of the grid with LocationsButtons.
		//if the left click is pressed - either the player loses if he presses on a mine.
		//else we will open all the possible locations which does not have mines in it
		//and update the LocationsButtons accordingly.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				boardButtons[i][j] = new LocationsButtons(i, j);
				boardButtons[i][j].setMaxWidth(Double.MAX_VALUE);
				boardButtons[i][j].setMaxHeight(Double.MAX_VALUE);
				boardButtons[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {
							// left click - will open the block that the user wants.
							boolean isMine = board.open(((LocationsButtons) event.getSource()).getX(),
									((LocationsButtons) event.getSource()).getY());
							update();
							if (!isMine) {
								// if we pressed on a mine - we reveal all the board
								// and show a losing msg.
								board.setShowAll(true);
								update();
								showMsg(true, false);
							} else if (board.isDone()) {
								//if the number of mines we reavlen equals to numOfMines
								//show a winning msg.
								showMsg(false, true);
							}
						}
						if (event.getButton() == MouseButton.SECONDARY) {
							// press the right side of the mouse and it will show the flag.
							int x = ((LocationsButtons) event.getSource()).getX();
							int y = ((LocationsButtons) event.getSource()).getY();
							board.toggleFlag(x, y);
							update();
						}
					}
				});
				gridPane.add(boardButtons[i][j], j, i);
			}
		}
	}

	private void update() {
		// updates all the buttons on the board.
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				boardButtons[i][j].setText(board.get(i, j));
				if (boardButtons[i][j].getText().equals("X")) {
					boardButtons[i][j].setStyle("-fx-background-color:red");
				}
			}
	}

	private void showMsg(boolean isOver, boolean isDone) {
		//show new stage with a scene on the screen.
		//the scene will consist of an HBox with a winning or losing msg to the user.
		Scene popUp = new Scene(createMsg(isOver, isDone), 300, 100);
		Stage popUpStage = new Stage();
		popUpStage.setScene(popUp);
		popUpStage.show();
	}


	private HBox createMsg(boolean isOver, boolean isDone) {
		//create the HBox node with the msg to the user.
		HBox hbox = new HBox();
		if (isOver)
			msg = new Label("You Lost!");
		else if (isDone)
			msg = new Label("You Won!");
		hbox.setPadding(new Insets(30, 20, 20, 80));
		msg.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 20));
		hbox.getChildren().addAll(msg);
		return hbox;
	}

	public void setHbox(HBox hbox) {
		// set the hbox.
		this.hbox = hbox;
	}
	public void setStage(Stage stage) {
		//initialize the stage with the given stage.
		this.stage = stage;
	}

	//getters for the input from the user in the TextBoxes.
	public TextField getTextFieldMines() {
		return mines;
	}

	public TextField getTextFieldWidth() {
		return Twidth;
	}

	public TextField getTextFieldHeight() {
		return Theight;
	}

}
