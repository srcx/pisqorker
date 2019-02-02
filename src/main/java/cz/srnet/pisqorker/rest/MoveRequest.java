package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Piece;

final class MoveRequest {

	private final @NonNull Piece piece;
	private final @NonNull Coordinates xy;

	@JsonCreator
	public MoveRequest(@JsonProperty(value = "piece", required = true) @NonNull Piece piece,
			@JsonProperty(value = "xy", required = true) @NonNull Coordinates xy) {
		this.piece = piece;
		this.xy = xy;
	}

	public @NonNull Move execute(@NonNull Game game) {
		return game.moves().move().as(piece).to(xy);
	}

}
