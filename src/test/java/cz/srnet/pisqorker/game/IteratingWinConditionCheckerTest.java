package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.IteratingWinConditionChecker;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Piece;

final class IteratingWinConditionCheckerTest {

	@ParameterizedTest
	@EnumSource(Piece.class)
	void testWinOnFirstMove(@NonNull Piece player) {
		FakeRules rules = new FakeRules(1, 1);
		FakeMove move = move(player, 0, 0, Collections.emptyList());

		assertTrue(new IteratingWinConditionChecker(rules).isWon(move));
	}

	private @NonNull FakeMove move(@NonNull Piece player, int x, int y, @NonNull List<Move> previous) {
		return new FakeMove()._piece(player)._xy(Coordinates.of(x, y))
				._previousStreamSupplier(() -> previous.stream());
	}

	@ParameterizedTest
	@EnumSource(Piece.class)
	void testNoWinOnFirstMove(@NonNull Piece player) {
		FakeRules rules = new FakeRules(2, 2);
		FakeMove move = move(player, 0, 0, Collections.emptyList());

		assertFalse(new IteratingWinConditionChecker(rules).isWon(move));
	}

	@ParameterizedTest
	@EnumSource(Piece.class)
	void testWinOn3x3Variant1(@NonNull Piece player) {
		FakeRules rules = new FakeRules(3, 3);
		IteratingWinConditionChecker impl = new IteratingWinConditionChecker(rules);
		List<Move> moves = new ArrayList<>();
		moves.add(assertNoWin(impl, move(player, 0, 0, moves)));
		moves.add(assertNoWin(impl, move(player.other(), -1, -1, moves)));
		moves.add(assertNoWin(impl, move(player, -1, 0, moves)));
		moves.add(assertNoWin(impl, move(player.other(), -1, 1, moves)));

		assertTrue(impl.isWon(move(player, 1, 0, moves)));
	}

	@ParameterizedTest
	@EnumSource(Piece.class)
	void testWinOn3x3Variant2(@NonNull Piece player) {
		FakeRules rules = new FakeRules(3, 3);
		IteratingWinConditionChecker impl = new IteratingWinConditionChecker(rules);
		List<Move> moves = new ArrayList<>();
		moves.add(assertNoWin(impl, move(player, 0, 0, moves)));
		moves.add(assertNoWin(impl, move(player.other(), -1, -1, moves)));
		moves.add(assertNoWin(impl, move(player, 0, 1, moves)));
		moves.add(assertNoWin(impl, move(player.other(), 1, 1, moves)));

		assertTrue(impl.isWon(move(player, 0, -1, moves)));
	}

	private @NonNull FakeMove assertNoWin(@NonNull IteratingWinConditionChecker impl, @NonNull FakeMove move) {
		assertFalse(impl.isWon(move));
		return move;
	}
}
