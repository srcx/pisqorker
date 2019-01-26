package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Piece;

final class MoveRequest {

	private Piece piece;
	private Coordinates xy;

	public @NonNull Move execute(@NonNull Game game) {
		// TODO better validation
		return game.moves().move().as(Objects.requireNonNull(piece)).to(Objects.requireNonNull(xy));
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Coordinates getXy() {
		return xy;
	}

	public void setXy(Coordinates xy) {
		this.xy = xy;
	}

}
