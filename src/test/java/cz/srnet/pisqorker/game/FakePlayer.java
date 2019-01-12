package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

final class FakePlayer implements Player {

	public static final @NonNull FakePlayer X = new FakePlayer(Piece.X);
	public static final @NonNull FakePlayer O = new FakePlayer(Piece.O);

	private final @NonNull Piece piece;

	public FakePlayer(@NonNull Piece piece) {
		this.piece = piece;
	}

	@Override
	@NonNull
	public Piece piece() {
		return piece;
	}

}
