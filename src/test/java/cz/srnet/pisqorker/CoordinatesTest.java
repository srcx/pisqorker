package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

final class CoordinatesTest {

	private @NonNull Coordinates c(int x, int y) {
		return Coordinates.of(x, y);
	}

	@Test
	void testEqualsAndHashCode() {
		assertEquals(c(0, 0), c(0, 0));
		assertNotEquals(c(0, 0), c(1, 1));
		assertEquals(c(0, 0).hashCode(), c(0, 0).hashCode());
	}

	@Test
	void testIsIn() {
		assertTrue(c(0, 0).isIn(-1, 1, -1, 1));
		assertTrue(c(-1, -1).isIn(-1, 1, -1, 1));
		assertTrue(c(1, 1).isIn(-1, 1, -1, 1));
		assertTrue(c(-1, 1).isIn(-1, 1, -1, 1));
		assertTrue(c(1, -1).isIn(-1, 1, -1, 1));
		assertTrue(c(1, -1).isIn(0, 1, -1, 0));
		assertFalse(c(-1, 1).isIn(0, 1, -1, 0));
		assertTrue(c(0, 0).isIn(0, 0, 0, 0));
		assertFalse(c(1, 1).isIn(0, 0, 0, 0));
	}

	@Test
	void testN() {
		assertEquals(c(0, -1), c(0, 0).n());
	}

	@Test
	void testNe() {
		assertEquals(c(1, -1), c(0, 0).ne());
	}

	@Test
	void testE() {
		assertEquals(c(1, 0), c(0, 0).e());
	}

	@Test
	void testSe() {
		assertEquals(c(1, 1), c(0, 0).se());
	}

	@Test
	void testS() {
		assertEquals(c(0, 1), c(0, 0).s());
	}

	@Test
	void testSw() {
		assertEquals(c(-1, 1), c(0, 0).sw());
	}

	@Test
	void testW() {
		assertEquals(c(-1, 0), c(0, 0).w());
	}

	@Test
	void testNw() {
		assertEquals(c(-1, -1), c(0, 0).nw());
	}

}
