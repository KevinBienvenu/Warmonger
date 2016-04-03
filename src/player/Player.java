package player;

import org.newdawn.slick.Color;

public class Player {
	
	private String nickname;
	private int id;
	private Color color1, color2;
	private int gold, science;
	
	public Player(String nickname, int id){
		this.nickname = nickname;
		this.id = id;
		this.color1 = new Color((float)Math.random(),(float)Math.random(),(float)Math.random());
		this.color2 = new Color((float)Math.random(),(float)Math.random(),(float)Math.random());
		this.gold = 0;
		this.science = 0;
	}

	public void setColor(Color color1, Color color2) {
		this.color1 = color1;
		this.color2 = color2;
	}
	
	public String getNickname() {return nickname;}
	public int getId() {return id;}
	public Color getColor1() {return color1;}
	public Color getColor2() {return color2;}
	public int getGold() {return gold;}
	public int getScience() {return science;}
	

}
