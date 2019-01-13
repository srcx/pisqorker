package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

final class PlayersImpl implements Players {

	private final @NonNull Player first;
	private final @NonNull Player second;

	public PlayersImpl(@NonNull Player first, @NonNull Player second) {
		if (first.piece() == second.piece()) {
			throw new IllegalArgumentException("Players need to use different game pieces: " + first + " vs " + second);
		}
		this.first = first;
		this.second = second;
	}

	@Override
	@NonNull
	public Player first() {
		return first;
	}

	@Override
	@NonNull
	public Player second() {
		return second;
	}

	@Override
	@NonNull
	public TransferablePlayers transferOut() {
		TransferablePlayers out = new TransferablePlayers();
		out.setFirst(first);
		out.setSecond(second);
		return out;
	}

}
