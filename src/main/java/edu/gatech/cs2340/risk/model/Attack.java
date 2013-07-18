package main.java.edu.gatech.cs2340.risk.model;

import main.java.edu.gatech.cs2340.risk.util.DiceUtil;

public class Attack {

	Territory attackingTerritory;
	Territory defendingTerritory;
	int[] attackingArmyDice;
	int[] defendingArmyDice;
	int attackingArmyNum;
	int defendingArmyNum;
	boolean attackerWinOne;
        boolean attackerWinTwo;

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

	public Territory getDefendingTerritory() {
		return defendingTerritory;
	}

	public int[] getAttackingArmyDice() {
		return attackingArmyDice;
	}

	public int[] getDefendingArmyDice() {
		return defendingArmyDice;
	}

	private void calculateAttackWinners() {

		int attackingDiceOne = attackingArmyDice[0];
		int defendingDiceOne = defendingArmyDice[0];
                attackerWinOne = attackingDiceOne > defendingDiceOne;
                if (defendingArmyNum > 1 && attackingArmyNum > 1) {
                        int attackingDiceTwo = attackingArmyDice[1];
                        int defendingDiceTwo = defendingArmyDice[1];
                        attackerWinTwo = attackingDiceTwo > defendingDiceTwo;
                }
	}

	public String doAttack() {

		attackingArmyDice = DiceUtil.rollDice(Math.min(attackingArmyNum, 3));
		defendingArmyDice = DiceUtil.rollDice(Math.min(defendingTerritory.getNumberOfArmies(), 2));

		calculateAttackWinners();
		String attackResultsMessage = "";
                if (defendingArmyNum > 1 && attackingArmyNum > 1) {
		        if (attackerWinOne && attackerWinTwo) {
			        attackResultsMessage = "Attacker wins! ";
			        defendingTerritory.removeNumberOfArmies(2);
			        attackResultsMessage += "Two armies removed.";
			} else if (attackerWinOne != attackerWinTwo) {
                                attackResultsMessage = "Attacker wins one round, and defender wins one round. ";
                                defendingTerritory.removeNumberOfArmies(1);
                                attackResultsMessage += "Defending army removed. ";
                                attackingTerritory.removeNumberOfArmies(1);
                                attackResultsMessage += "Attacking army removed. ";
			} else if (!attackerWinOne && !attackerWinTwo) {
                                attackResultsMessage = "Attack failed. Two armies removed.";
                                attackingTerritory.removeNumberOfArmies(2);
                        }
		} else {
                        if (attackerWinOne) {
                               attackResultsMessage = "Attacker wins! ";
                               defendingTerritory.removeNumberOfArmies(1);
                               attackResultsMessage += "One army removed.";
                        } else {
                               attackResultsMessage = "Attack unsuccessful. ";
                               attackingTerritory.removeNumberOfArmies(1);
                               attackResultsMessage += "One army removed.";
                        }
                }
                if (defendingTerritory.getNumberOfArmies() < 1) {
                        attackResultsMessage = "Attacker wins! Territory acquired.";
                        defendingTerritory.getOwner().removeTerritory(defendingTerritory);
		        attackingTerritory.getOwner().addTerritory(defendingTerritory);
                }

		return attackResultsMessage;
	}

	public boolean isConquered() {
		return defendingTerritory.getNumberOfArmies() < 1;
	}

}

