package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

final class FakePlayers implements Players {

	public static final @NonNull FakePlayers XO = new FakePlayers(FakePlayer.X, FakePlayer.O);

	private final @NonNull FakePlayer first;
	private final @NonNull FakePlayer second;

	public FakePlayers(@NonNull FakePlayer first, @NonNull FakePlayer second) {
		this.first = first;
		this.second = second;
	}

	public FakePlayers(@NonNull Piece first, @NonNull Piece second) {
		this(new FakePlayer(first), new FakePlayer(second));
	}

	@Override
	@NonNull
	public FakePlayer first() {
		return first;
	}

	@Override
	@NonNull
	public FakePlayer second() {
		return second;
	}

	@Override
	@NonNull
	public TransferablePlayers transferOut() {
		throw new UnsupportedOperationException();
	}

}
