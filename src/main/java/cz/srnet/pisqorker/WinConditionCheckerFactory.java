package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

@FunctionalInterface
interface WinConditionCheckerFactory {

	@NonNull
	WinConditionChecker create(@NonNull Rules rules);

}
