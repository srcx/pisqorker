package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

interface GameContext {

	@NonNull
	Rules rules();

	@NonNull
	WinConditionChecker winConditionChecker();

	@NonNull
	NextMoves nextMoves();

}
