package cz.srnet.pisqorker.game;

import java.util.Objects;

import org.springframework.lang.NonNull;

public final class Coordinates {

	public static @NonNull Coordinates of(int x, int y) {
		return new Coordinates(x, y);
	}

	private final int x;
	private final int y;

	private Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isIn(int minX, int maxX, int minY, int maxY) {
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}

	public @NonNull Coordinates n() {
		return new Coordinates(x, y - 1);
	}

	public @NonNull Coordinates ne() {
		return new Coordinates(x + 1, y - 1);
	}

	public @NonNull Coordinates e() {
		return new Coordinates(x + 1, y);
	}

	public @NonNull Coordinates se() {
		return new Coordinates(x + 1, y + 1);
	}

	public @NonNull Coordinates s() {
		return new Coordinates(x, y + 1);
	}

	public @NonNull Coordinates sw() {
		return new Coordinates(x - 1, y + 1);
	}

	public @NonNull Coordinates w() {
		return new Coordinates(x - 1, y);
	}

	public @NonNull Coordinates nw() {
		return new Coordinates(x - 1, y - 1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Coordinates other = (Coordinates) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

}
