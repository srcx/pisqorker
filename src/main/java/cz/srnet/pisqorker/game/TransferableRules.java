package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferableRules implements TransferableIn<Rules> {

	private final int boardSize;
	private final int connectToWin;

	@JsonCreator
	public TransferableRules(@JsonProperty(value = "boardSize", required = true) int boardSize,
			@JsonProperty(value = "connectToWin", required = true) int connectToWin) {
		this.boardSize = boardSize;
		this.connectToWin = connectToWin;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public int getConnectToWin() {
		return connectToWin;
	}

	@Override
	@NonNull
	public Rules transferIn() {
		return new RulesImpl(boardSize, connectToWin);
	}

}
