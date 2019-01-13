package cz.srnet.pisqorker.game;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.springframework.lang.NonNull;

final class FakePlayer implements Player {

	public static final @NonNull FakePlayer X = new FakePlayer(Piece.X)._isPlayableNowProvider(() -> true);
	public static final @NonNull FakePlayer O = new FakePlayer(Piece.O)._isPlayableNowProvider(() -> true);

	private final @NonNull Piece piece;
	private @NonNull Optional<BooleanSupplier> isPlayableNowProvider = Optional.empty();

	public FakePlayer(@NonNull Piece piece) {
		this.piece = piece;
	}

	public @NonNull FakePlayer _isPlayableNowProvider(BooleanSupplier provider) {
		this.isPlayableNowProvider = Optional.of(provider);
		return this;
	}

	@Override
	@NonNull
	public Piece piece() {
		return piece;
	}

	@Override
	public boolean isPlayableNow() {
		return isPlayableNowProvider.orElseThrow(() -> new UnsupportedOperationException()).getAsBoolean();
	}

}
