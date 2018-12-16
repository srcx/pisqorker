package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class RulesImplTest {

	@Test
	void testBasicInfo() {
		int boardSize = 5;
		int connectToWin = 3;

		Rules impl = new RulesImpl(boardSize, connectToWin);

		assertEquals(boardSize, impl.boardSize());
		assertEquals(connectToWin, impl.connectToWin());
	}

	@Test
	void testLegalCoordinates() {
		int boardSize = 5;
		int connectToWin = 3;

		Rules impl = new RulesImpl(boardSize, connectToWin);

		for (int x = -10; x <= 10; x++) {
			for (int y = -10; y <= 10; y++) {
				Coordinates xy = Coordinates.of(x, y);
				assertEquals(x >= -2 && x <= 2 && y >= -2 && y <= 2, impl.legalCoordinates(xy),
						"Wrongly determined legality of coordinate " + xy);
			}
		}
	}

}
