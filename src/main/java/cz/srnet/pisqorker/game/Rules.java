package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableOut;

public interface Rules extends TransferableOut<TransferableRules> {

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
