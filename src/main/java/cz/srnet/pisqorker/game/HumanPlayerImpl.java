package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

final class HumanPlayerImpl implements HumanPlayer {

	private final @NonNull Piece piece;

	public HumanPlayerImpl(@NonNull Piece piece) {
		this.piece = piece;
	}

	@Override
	@NonNull
	public Piece piece() {
		return piece;
	}

	@Override
	@NonNull
	public TransferableHumanPlayer transferOut() {
		TransferableHumanPlayer out = new TransferableHumanPlayer();
		out.setPiece(piece);
		return out;
	}

}
