package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

public interface Rules {

	/**
	 * @return board size, always odd, both X and Y coordinates are inside
	 *         [-(boardSize-1)/2, +(boardSize-1)/2]
	 */
	int boardSize();

	int connectToWin();

	default boolean legalCoordinates(@NonNull Coordinates xy) {
		int boundary = (boardSize() - 1) / 2;
		return xy.isIn(-boundary, boundary, -boundary, boundary);
	}

}
