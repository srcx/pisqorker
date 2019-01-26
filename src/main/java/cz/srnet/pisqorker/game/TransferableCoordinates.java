package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferableCoordinates implements TransferableIn<Coordinates> {

	private int x;
	private int y;

	public TransferableCoordinates() {
		// for jackson
	}

	public TransferableCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	@NonNull
	public Coordinates transferIn() {
		return Coordinates.of(x, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
