package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

interface WinConditionChecker {

	boolean isWon(@NonNull Move move);

}
