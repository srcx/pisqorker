package cz.srnet.pisqorker;

public interface Rules {

	/**
	 * @return board size, always odd, both X and Y coordinates are inside
	 *         [-(boardSize-1)/2, +(boardSize-1)/2]
	 */
	int boardSize();

	boolean legalCoordinates(int x, int y);

	int connectToWin();

}
