package cz.srnet.pisqorker;

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

}
