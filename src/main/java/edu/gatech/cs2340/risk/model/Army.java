package main.java.edu.gatech.cs2340.risk.model;

/**
 * @author Caroline Paulus
 *
 */
public class Army {
	
	private int armyId;
	private Player player;
	private Territory territory;
	
	public Army(int armyId, Player player) {
		this.armyId = armyId;
		this.player = player;
	}

	public int getArmyId() {
		return armyId;
	}

	public void setArmyId(int armyId) {
		this.armyId = armyId;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	@Override
	public String toString() {
		return "[ID: " + armyId + ", player: " + player.getPlayerName() + "]";
	}
}
