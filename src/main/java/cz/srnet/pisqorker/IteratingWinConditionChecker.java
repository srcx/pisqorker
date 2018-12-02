package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.function.UnaryOperator;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

final class IteratingWinConditionChecker implements WinConditionChecker {

	private final @NotNull GameContext context;

	public IteratingWinConditionChecker(@NotNull GameContext context) {
		this.context = context;
	}

	@Override
	public boolean isWon(@NonNull Move move) {
		if (winning(move, Coordinates::n, Coordinates::s)) {
			return true;
		}
		if (winning(move, Coordinates::ne, Coordinates::sw)) {
			return true;
		}
		if (winning(move, Coordinates::e, Coordinates::w)) {
			return true;
		}
		if (winning(move, Coordinates::se, Coordinates::nw)) {
			return true;
		}
		return false;
	}

	private boolean winning(@NonNull Move lastMove, @NonNull UnaryOperator<Coordinates> direction1,
			@NonNull UnaryOperator<Coordinates> direction2) {
		int connected = countWinning(lastMove, direction1) + 1 + countWinning(lastMove, direction2);
		return connected >= context.rules().connectToWin();
	}

	private int countWinning(@NonNull Move lastMove, @NonNull UnaryOperator<Coordinates> direction) {
		Player player = lastMove.player();
		Coordinates xy = Objects.requireNonNull(direction.apply(lastMove.xy()));
		int count = 0;
		while (field(lastMove, xy) == player) {
			count++;
			xy = Objects.requireNonNull(direction.apply(xy));
		}
		return count;
	}

	private @Nullable Player field(@NonNull Move lastMove, @NonNull Coordinates xy) {
		return lastMove.previousStream().filter(m -> m.xy().equals(xy)).findAny().map(Move::player).orElse(null);
	}

}
