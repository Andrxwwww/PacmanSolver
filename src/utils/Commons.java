package utils;

import pacman.PacmanBoard;

public interface Commons {
    
	public static final int PACMAN_NUM_ACTIONS = 4;
	public static final int PACMAN_STATE_SIZE = PacmanBoard.N_BLOCKS * PacmanBoard.N_BLOCKS * 2 + 2 + PacmanBoard.MAX_GHOSTS * 2;


}
