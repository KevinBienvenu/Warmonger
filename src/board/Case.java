package board;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;

import main.Constants;
import main.Game;
import player.Player;

public class Case {
	
	private int i,j;
	private Improvement improvement;
	private Terrain terrain;
	private Player owner;
	private Polygon polygon;
	private float x,y;
	private boolean hasRoad;
	private boolean[] roads;
	private boolean[] rivers;
	private Case[] neighbours;
	
	public Color debugColor = null;
	
	boolean mouseOnIt = false;
	
	public Case(int i, int j){
		this.setI(i);
		this.setJ(j);
		float h = (float) (Math.sqrt(3f)*Constants.radiusCase/2f),a=Constants.radiusCase;
		if(j%2==0){
			x = (float) (3.0*j*(Constants.radiusCase)/2f);
			y = 2f*h*i;
		} else {
			x = 3f*j*(Constants.radiusCase)/2f;
			y = 2f*h*(i+0.5f);		
		}
		this.polygon = new Polygon();
		this.polygon.addPoint(x+a, y);
		this.polygon.addPoint(x+a/2, y-h);
		this.polygon.addPoint(x-a/2, y-h);
		this.polygon.addPoint(x-a, y);
		this.polygon.addPoint(x-a/2, y+h);
		this.polygon.addPoint(x+a/2, y+h);
		this.owner = Constants.playerNeutral;
		//this.terrain = Terrain.getRandomTerrain();
		this.rivers = new boolean[6];
		this.neighbours = new Case[6];
	}

	
	public void setImprovement(Improvement imp){
		this.improvement = imp;
	}
	
	public void draw(Graphics g){
		g.setColor(terrain.getColor());
		g.fill(polygon);
		owner.getColor2().a = 0.3f;
		if(mouseOnIt)
			g.setColor(Color.white);
		else
			g.setColor(owner.getColor2());
		g.fill(polygon);
		owner.getColor2().a = 1f;
		g.setColor(owner.getColor1());
		g.setLineWidth(2);
		g.draw(polygon);
		g.setLineWidth(1);
		// drawing roads
		g.setColor(Color.red);
		g.setLineWidth(2);
		if(hasRoad){
			g.fillOval(x-2, y-2, 4, 4);
			for(int i=0; i<6; i++){
				if(roads[i]){
					float angle = -(float) (Math.PI*(i/3f+1f/6f));
					g.drawLine(x, y, (float)(x+Constants.radiusCase*Math.cos(angle)), (float)(y+Constants.radiusCase*Math.sin(angle)));
				}
			}
		}
		g.setLineWidth(1);
		if(improvement!=null){
			g.setColor(Color.black);
			g.setLineWidth(2);
			g.fillOval(x-Constants.radiusCase/4, y-Constants.radiusCase/4, Constants.radiusCase/2, Constants.radiusCase/2);
			switch(improvement.getName()){
			case "Farms" : g.setColor(Color.green);break;
			case "Village" : g.setColor(Color.yellow);break;
			case "University" : g.setColor(Color.blue);break;
			case "Factory" : g.setColor(Color.orange);break;
			}
			g.fillOval(x-Constants.radiusCase/4, y-Constants.radiusCase/4, Constants.radiusCase/2, Constants.radiusCase/2);
			g.setLineWidth(1);
			
		}
		// drawing rivers
		g.setColor(Color.cyan);
		g.setLineWidth(5);
		
//		g.drawLine(this.polygon.getPoint(0)[0], this.polygon.getPoint(0)[1], this.polygon.getPoint(1)[0], this.polygon.getPoint(1)[1]);
		for(int i=0; i<6; i++){
			if(this.rivers[i]){
				g.drawLine(this.polygon.getPoint(i)[0], this.polygon.getPoint(i)[1], this.polygon.getPoint((i+1)%6)[0], this.polygon.getPoint((i+1)%6)[1]);
			}
		}
		g.setLineWidth(1f);
		
		if(this.debugColor!=null){
			g.setColor(this.debugColor);
			g.drawString("X", this.x, this.y);
		}
//		g.setColor(Color.black);
//		g.drawString(""+i+" "+j, x, y);
	}
	
	public void updateRoads(Board b){
		Case c;
		if(this.getTerrain()!=Terrain.WATER){
			for(int i=0; i<6; i++){
				c = b.getNeighbor(this,i);
				roads[i] = c!=null && c.hasRoad;
			}
		}
	}
	
	public void setRoad(boolean b){
		this.hasRoad = b;
		this.roads = new boolean[6];
	}

	public void setNeighbour(int i, Case c){
		this.neighbours[i] = c;
	}
	
	public Case getNeighbour(int i){
		return this.neighbours[(i+6)%6];
	}
	
	public int getI() {return i;}
	public void setI(int i) {this.i = i;}
	public int getJ() {return j;}
	public void setJ(int j) {this.j = j;}
	public Polygon getPolygon() {return this.polygon;}
	public float getX() {return x;}
	public float getY() {return y;}
	public void setRiver(int i, boolean b){
		this.rivers[(i+6)%6] = b;
	}
	
	public Terrain getTerrain() {return terrain;}
	public void setTerrain(Terrain t) {this.terrain = t;}

	public String toString(){
		return ""+i+" "+j;
	}
	
	public boolean contains(int x1, int y1){
		return this.polygon.contains(new Point(x1,y1));
	}
}
