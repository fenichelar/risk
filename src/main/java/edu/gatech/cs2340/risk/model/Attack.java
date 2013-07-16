package main.java.edu.gatech.cs2340.risk.model;

import main.java.edu.gatech.cs2340.risk.util.DiceUtil;

public class Attack {

	Territory attackingTerritory;
	Territory defendingTerritory;
	int[] attackingArmyDice;
	int[] defendingArmyDice;
	int attackingArmyNum;
	int defendingArmyNum;
	boolean attackerWin;

	public Attack (Territory attackingTerritory) {
		this.attackingTerritory = attackingTerritory;
	}

	public void setDefendingTerritory (Territory defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}

	public void setAttackingArmyNum (int attackingArmyNum) {
		this.attackingArmyNum = attackingArmyNum;
	}

	public void setDefendingArmyNum (int defendingArmyNum) {
		this.defendingArmyNum = defendingArmyNum;
	}

	public Territory getAttackingTerritory() {
		return attackingTerritory;
	}

	public int[] getAttackingArmyDice() {
		return attackingArmyDice;
	}

	public int[] getDefendingArmyDice() {
		return defendingArmyDice;
	}

	private void calculateAttackWinner() {

		int attackingDiceMax = attackingArmyDice[0];
		int defendingDiceMax = defendingArmyDice[0];

		attackerWin = attackingDiceMax > defendingDiceMax;
	}

	public String doAttack() {

		attackingArmyDice = DiceUtil.rollDice(Math.min(attackingArmyNum, 3));
		defendingArmyDice = DiceUtil.rollDice(Math.min(defendingTerritory.getNumberOfArmies(), 2));

		calculateAttackWinner();
		String attackResultsMessage = "";

		if (attackerWin) {
			attackResultsMessage = "Attacker wins! ";
			if (defendingArmyNum > 1) {
				defendingTerritory.removeNumberOfArmies(2);
				attackResultsMessage += "Two Armies Removed.";
			} else {
				defendingTerritory.removeNumberOfArmies(1);
				attackResultsMessage += "One Army Removed.";
			}
			if (defendingTerritory.getNumberOfArmies() < 1) {
				attackResultsMessage = "Attacker wins! Territory acquired.";
				defendingTerritory.getOwner().removeTerritory(defendingTerritory);
				attackingTerritory.getOwner().addTerritory(defendingTerritory);
				defendingTerritory.setNumberOfArmies(attackingArmyNum);
				attackingTerritory.removeNumberOfArmies(attackingArmyNum);
			} 
		} else {
			attackResultsMessage = "Attack unsuccessful. ";
			if (attackingArmyNum > 1) {
				attackingTerritory.removeNumberOfArmies(2);
				attackResultsMessage += "Two Armies Removed.";
			} else {
				attackingTerritory.removeNumberOfArmies(1);
				attackResultsMessage += "One Army Removed.";
			}
		}

		return attackResultsMessage;
	}

}

