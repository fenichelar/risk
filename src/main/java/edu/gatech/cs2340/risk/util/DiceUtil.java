package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import main.java.edu.gatech.cs2340.risk.model.Territory;

import org.apache.log4j.Logger;

/**
 * A class that creates a list of dice that can be 
 * compared to other dice during attacks
 */
public class DiceUtil {
	
	private static final Random RANDOM = new Random();
	private static Logger log = Logger.getLogger(DiceUtil.class);
	
	/**
	 * Simulates rolling a dice
	 * 
	 * @return Random number between 1 and 6
	 */
	public static int rollDie() {
		return RANDOM.nextInt(6) + 1;
	}

	/**
	 * Randomizes dice and resorts them from greatest to least
	 */
	public static int[] rollDice(int count){
		int[] rollResults = new int[count];
		
		for (int i = 0; i < count; i++){
			rollResults[i] = rollDie();
		}
		Arrays.sort(rollResults);
		return rollResults;
	}

}
