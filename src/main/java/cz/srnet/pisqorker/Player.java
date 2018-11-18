package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

public enum Player {

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

}
