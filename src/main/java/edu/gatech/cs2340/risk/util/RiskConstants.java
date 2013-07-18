package main.java.edu.gatech.cs2340.risk.util;

public interface RiskConstants {
	
	// stages
	public static final int INITIALIZE = 1;
	public static final int ROTATE_PLAYERS = 2;
	public static final int ATTACK = 3;
	public static final int FORTIFY = 4;
	public static final int SELECT_OPTIONS = 5;
	
	public static final int NO_STEP = 0;
	
	// steps corresponding to stage 2 (rotate players)
	public static final int BEGINNING_OF_TURN = 1;
	public static final int DURING_TURN = 2;
	
	// steps corresponding to stage 3 (attack)
	public static final int SELECT_ATTACKING_TERRITORY = 1;
	public static final int SELECT_DEFENDING_TERRITORY = 2;
	public static final int SELECT_DEFENDING_ARMIES = 3;
	public static final int DO_ATTACK = 4;
	
	// steps corresponding to stage 4 (fortify)
	public static final int SELECT_FORTIFYING_TERRITORY = 1;
	public static final int SELECT_FORTIFIED_TERRITORY = 2;
	public static final int DO_FORTIFY = 3;

	// direction numbers
	public static final int NO_DIRECTION = 0;
	public static final int INITIAL_DIRECTION = 1;
	public static final int ADDITIONAL_ARMIES_DIRECTIONS = 2;
}
