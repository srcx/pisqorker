package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

enum Player {

	X {
		@Override
		@NonNull
		Player next() {
			return O;
		}
	},
	O {
		@Override
		@NonNull
		Player next() {
			return X;
		}
	};

	abstract @NonNull Player next();

	static @NonNull Player defaultFirst() {
		return X;
	}

}
