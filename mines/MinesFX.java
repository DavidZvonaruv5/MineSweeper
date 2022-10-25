package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MinesFX extends Application{
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
    	HBox hbox;
    	Controller controller;
    	stage.setTitle("The Amazing Minesweeper");
    	try {
    	    FXMLLoader loader = new FXMLLoader();
    	    loader.setLocation(getClass().getResource("Screen.fxml"));
    	    hbox = loader.load();
    	    controller = loader.getController();
    	    GridPane grindPane = new GridPane();
    	    controller.setHbox(hbox);
    	    /*when the user will open the game, by default it will be height=width=mines=10.
    	     * The user can change the parameters by inserting new values and clicking on the reset button.*/
        	TextField textFieldWidth = controller.getTextFieldWidth();
            TextField textFieldHeight = controller.getTextFieldHeight();
            TextField textFieldMines = controller.getTextFieldMines();
            textFieldWidth.setText("10"); //start with default value for the grid
            textFieldHeight.setText("10"); //start with default value for the grid
            textFieldMines.setText("10"); //start with default value for the mines
    	    controller.setStage(stage);
    	    BackgroundFill bgfill = new BackgroundFill(Color.KHAKI, null, null); //set background color.
			hbox.setBackground(new Background(bgfill)); 
    	    hbox.getChildren().add(grindPane); //start the new board
    	    controller.openGame();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    return;
    	}
        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        stage.show();
	}
    
}

