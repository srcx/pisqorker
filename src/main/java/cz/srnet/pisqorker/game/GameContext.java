package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

interface GameContext {

	@NonNull
	Rules rules();

	@NonNull
	WinConditionChecker winConditionChecker();

}
