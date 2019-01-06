package cz.srnet.pisqorker.game;

import java.util.List;

public final class TransferableGame {

	private String id;
	private Rules rules;
	private GameState state;
	private List<Coordinates> moves;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Rules getRules() {
		return rules;
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public List<Coordinates> getMoves() {
		return moves;
	}

	public void setMoves(List<Coordinates> moves) {
		this.moves = moves;
	}

}
