package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

public enum GameState {

	notStarted, started, draw, wonByX, wonByO;

	static @NonNull GameState wonBy(@NonNull Player player) {
		switch (player) {
		case O:
			return wonByO;
		case X:
			return wonByX;
		default:
			throw new IllegalArgumentException("Unknown player " + player);
		}
	}

}
