package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public final class IllegalPieceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalPieceException(@NonNull Piece piece, @NonNull String reason) {
		super(String.format("Cannot play %s, because %s", piece, reason));
	}

}
