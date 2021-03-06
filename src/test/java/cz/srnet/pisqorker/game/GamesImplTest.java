package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

final class GamesImplTest {

	@Test
	void testNewGame() {
		WinConditionCheckers winConditionCheckers = (rules) -> (move) -> true;
		FakeRules rules = new FakeRules(3, 3);
		GamesRepository repo = new InMemoryGamesRepository(winConditionCheckers);

		Games impl = new GamesImpl(repo);
		Game game = impl.newGame(rules, FakePlayers.XO);

		assertEquals(GameState.notStarted, game.state());
		game.moves().move().to(0, 0);
		assertEquals(GameState.wonBy(Piece.X), game.state());
	}

	@Test
	void testGame() {
		WinConditionCheckers winConditionCheckers = (rules) -> (move) -> true;
		FakeRules rules = new FakeRules(3, 3);
		GamesRepository repo = new InMemoryGamesRepository(winConditionCheckers);

		Games impl = new GamesImpl(repo);
		Game expected = impl.newGame(rules, FakePlayers.XO);
		Game actual = impl.game(expected.id()).get();

		assertEquals(expected, actual);
	}

	@Test
	void testDifferentGameIds() {
		WinConditionCheckers winConditionCheckers = (rules) -> (move) -> true;
		FakeRules rules = new FakeRules(3, 3);
		GamesRepository repo = new InMemoryGamesRepository(winConditionCheckers);

		Games impl = new GamesImpl(repo);
		Set<String> ids = new HashSet<>();
		int toCreate = 10;
		for (int i = 0; i < toCreate; i++) {
			Game game = impl.newGame(rules, FakePlayers.XO);
			ids.add(game.id());
		}

		assertEquals(toCreate, ids.size());
	}

}
