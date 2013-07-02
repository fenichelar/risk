package main.java.edu.gatech.cs2340.risk.model;
/**
 * A class that creates a list of dice that can be 
 * compared to other dice during attacks
 * 
 * @author Andrew Osborn
 *
 */
public class Dice {
	private int count;
	private int list[];
	/**
	 * Creates one dice that is random
	 */
	public Dice (){
		count = 1;
		list = new int[1];
		roll();
	}
	/**
	 * Creates num dice given, unless number is not positive or 
	 * greater than three
	 * @param count
	 * @throws UnsupportedOperationException
	 */
	public Dice (int count) throws UnsupportedOperationException{
		if(count > 3 || count < 1)
			throw new UnsupportedOperationException("NumDice must be positive and < 3");
		this.count = count;
		list = new int[count];
		roll();
	}
	/**
	 * Randomizes dice and resorts them from greatest to least
	 */
	public void roll(){
		for(int i = 0; i < count; i++){
			list[i] = (int)(Math.random()*6 + 1);
		}
		for(int i = 0; i < count; i++){
			if(i+1 < count && list[i] < list[i+1]){
				int temp = list[i];
				list[i] = list[i+1];
				list[i+1] = temp;
			}
				
		}
	}
	public int numDice(){
		return count;
	}
	
	public int get(int i){
		return list[i];
	}
	/**
	 * Compares attackers dice with defenders dice returns array
	 * containing losses and wins
	 * @param defender
	 * @return array [numArmiesLost, numArmiesBeaten]
	 */
	public int[] attackComparison(Dice defender){
		int[] toReturn = new int[]{0,0};
		for(int i = 0; i < count; i++){
			if(defender.numDice() > i){
				//defender wins
				if(defender.get(i) > this.get(i))
					toReturn[0]++;
				//attacker wins
				else if(defender.get(i) < this.get(i))
					toReturn[1]++;
				//defender wins tie
				else
					toReturn[0]++;
			}
		}
		return toReturn;
	}
}
