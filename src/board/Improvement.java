package board;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public enum Improvement {

	FARMS("Farms",2,0,0),
	VILLAGE("Village",1,1,1),
	UNIVERISTY("University",0,0,2),
	FACTORY("Factory",0,2,0);
	
	
	private Improvement(String name, int food, int gold, int science){
		this.name = name;
		this.food = food;
		this.science = science;
	}
	
	public int getFood() {return food;}
	public int getGold() {return gold;}
	public int getScience() {return science;}
	public String getName() {return name;}

	private int food, gold, science;
	private String name;
	
	
	
}
