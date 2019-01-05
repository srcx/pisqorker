package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferableRules implements TransferableIn<Rules> {

	private int boardSize;
	private int connectToWin;

	public TransferableRules() {
		// for jackson
	}

	public TransferableRules(int boardSize, int connectToWin) {
		this.boardSize = boardSize;
		this.connectToWin = connectToWin;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getConnectToWin() {
		return connectToWin;
	}

	public void setConnectToWin(int connectToWin) {
		this.connectToWin = connectToWin;
	}

	@Override
	@NonNull
	public Rules transferIn() {
		return new RulesImpl(boardSize, connectToWin);
	}

}
