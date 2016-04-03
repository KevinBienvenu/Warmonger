package board;

import org.newdawn.slick.Color;

public enum Terrain {
	
	
	PLAINS("Plains", 1, 2, 0, new Color(162,245,90)),
	FOREST("Forest", 2, 1, 1, new Color(15,160,17)),
	HILL("Hill", 2, 0, 2, new Color(255,165,79)),
	MARSH("Marsh", 3, 0, 0, new Color(122,122,56)),
	JUNGLE("Jungle", 3, 1, 0, new Color(0,100,0)),
	WATER("Water", 1, 0, 1, new Color(72,209,204));
	
	private Terrain(String name, int mp, int food, int gold, Color color){
		this.name = name;
		this.mp = mp;
		this.food = food;
		this.gold = gold;
		this.color = color;
	}
	

	private static double probPlains = 5.0;
	private static double probForest = 3.0;
	private static double probHill = 2.0;
	private static double probMarsh = 1.0;
	private static double probJungle = 1.0;
	private static double probSnow = 1.0;

	private String name;
	private int mp;
	private int food, gold;
	private Color color;
	
	
	public String getName() {return name;}
	public int getMp() {return mp;}
	public int getFood() {return food;}
	public int getGold() {return gold;}
	public Color getColor() {return color;}
	
	public static Terrain getRandomTerrain(){
		double total = probPlains+probForest+probHill+probJungle+probMarsh+probSnow;
		double rdm = Math.random()*total;
		double temp = probPlains;
		if(rdm<temp)
			return Terrain.PLAINS;
		temp += probForest;
		if(rdm<temp)
			return Terrain.FOREST;
		temp += probHill;
		if(rdm<temp)
			return Terrain.HILL;
		temp += probMarsh;
		if(rdm<temp)
			return Terrain.MARSH;
		temp += probJungle;
		if(rdm<temp)
			return Terrain.JUNGLE; 
		return Terrain.WATER;
	}
	
}
