package cz.srnet.pisqorker;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.lang.NonNull;

public interface Move {

	int turn();

	@NonNull
	GameState state();

	@NonNull
	Player player();

	int x();

	int y();

	@NonNull
	Optional<Move> previous();

	@NonNull
	Optional<Move> next();

	@NonNull
	Move move(int x, int y);

	@NonNull
	Player nextPlayer();

	@NonNull
	Move move(@NonNull Player player, int x, int y);

	void forEachPrevious(@NonNull Consumer<Move> action);

}
