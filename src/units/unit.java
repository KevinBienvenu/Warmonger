package units;

import org.newdawn.slick.Image;

import player.Player;

public abstract class unit {
	
	private float x, y;
	private String name;
	private Player owner;
	
	private int mp, mpmax;
	private int hp, hpmax;
	
	private int cost;
	
	private Image image;
	
	private int strength, rangeStrength;
	
	private boolean isSelected;
	
	public abstract void update();
	public abstract void draw();
}
