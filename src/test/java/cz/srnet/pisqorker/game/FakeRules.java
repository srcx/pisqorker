package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

final class FakeRules implements Rules {

	private final int boardSize;
	private final int connectToWin;

	public FakeRules(int boardSize, int connectToWin) {
		this.boardSize = boardSize;
		this.connectToWin = connectToWin;
	}

	@Override
	public int boardSize() {
		return boardSize;
	}

	@Override
	public int connectToWin() {
		return connectToWin;
	}

	@Override
	@NonNull
	public TransferableRules transferOut() {
		throw new UnsupportedOperationException();
	}

}
