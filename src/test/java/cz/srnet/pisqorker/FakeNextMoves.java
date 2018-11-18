package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.Move;
import cz.srnet.pisqorker.NextMoves;

final class FakeNextMoves implements NextMoves {

	private @NonNull Function<Move, Optional<Move>> _next = whatever -> {
		throw new UnsupportedOperationException("next");
	};

	@NonNull
	FakeNextMoves _next(@NonNull Function<Move, Optional<Move>> _next) {
		this._next = _next;
		return this;
	}

	@Override
	@NonNull
	public Optional<Move> next(@NonNull Move move) {
		return Objects.requireNonNull(_next.apply(move));
	}

}
