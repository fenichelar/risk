package main.java.edu.gatech.cs2340.risk.model;

import main.java.edu.gatech.cs2340.risk.util.DiceUtil;

public class Attack {

	Territory attackingTerritory;
	Territory defendingTerritory;
	int[] attackingArmyDice;
	int[] defendingArmyDice;
	int attackingArmyNum;
	int defendingArmyNum;

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



	public String doAttack() {

		attackingArmyDice = DiceUtil.rollDice(Math.min(attackingArmyNum, 3));
		defendingArmyDice = DiceUtil.rollDice(Math.min(defendingTerritory.getNumberOfArmies(), 2));

		int[] results = calculateAttackWinners();
		this.removeArmies(results);

		return createResultsMessage(results);
	}

	private int[] calculateAttackWinners() {

		int defendingLoss = 0;
		int attackingLoss = 0;

		for (int i = 0; i < Math.max(attackingArmyDice.length, defendingArmyDice.length); i++) {
			if (i == Math.min(attackingArmyDice.length, defendingArmyDice.length)) {
				break;
			}
			if (attackingArmyDice[i] > defendingArmyDice[i]) {
				defendingLoss++;
			} else {
				attackingLoss++;
			}
		}

		int[] results = {defendingLoss, attackingLoss};
		return results;
	}

	private String createResultsMessage(int[] results) {
		if (this.isConquered()) {
			return "Attack Successful! Territory acquired.";
		}
		String resultsMessage = "";
		String attackSide;
		for (int i = 0; i < results.length; i++) {
			attackSide = (i == 0 ? "defending" : "attacking");
			if (results[i] > 0) {
				resultsMessage += "" + results[i] + " " + attackSide + " arm" + (results[i] < 2 ? "y" : "ies") + " removed. ";
			}
		}

		return resultsMessage;
	}

	private void removeArmies(int[] results) {
		defendingTerritory.removeNumberOfArmies(results[0]);
		attackingTerritory.removeNumberOfArmies(results[1]);

		if (this.isConquered()) {
			this.conquerTerritory();
		}
	}

	private void conquerTerritory() {
		defendingTerritory.getOwner().removeTerritory(defendingTerritory);
		attackingTerritory.getOwner().addTerritory(defendingTerritory);
	}

	public boolean isConquered() {
		return defendingTerritory.getNumberOfArmies() < 1;
	}

}

