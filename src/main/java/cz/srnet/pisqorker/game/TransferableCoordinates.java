package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferableCoordinates implements TransferableIn<Coordinates> {

	private final int x;
	private final int y;

	@JsonCreator
	public TransferableCoordinates(@JsonProperty(value = "x", required = true) int x,
			@JsonProperty(value = "y", required = true) int y) {
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

	public int getY() {
		return y;
	}

}
