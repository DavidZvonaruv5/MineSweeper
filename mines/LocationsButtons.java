package mines;

import javafx.scene.control.Button;

public class LocationsButtons extends Button{
	private int x, y;

	public LocationsButtons(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}