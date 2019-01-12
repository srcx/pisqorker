package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public enum Piece {

	X {
		@Override
		@NonNull
		Piece other() {
			return O;
		}
	},
	O {
		@Override
		@NonNull
		Piece other() {
			return X;
		}
	};

	abstract @NonNull Piece other();

}
