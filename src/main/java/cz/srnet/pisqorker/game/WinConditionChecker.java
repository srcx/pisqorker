package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public interface WinConditionChecker {

	boolean isWon(@NonNull Move move);

}
