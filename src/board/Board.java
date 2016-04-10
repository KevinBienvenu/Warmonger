package board;

import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import main.Constants;
import main.Game;

public class Board {

	private int sizeX, sizeY;
	private Vector<Case> cases;
	private int Xcam, Ycam;


	public Board(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.cases = new Vector<Case>();
		Vector<Case> temp;
		double rdm;
		Case c;
		int compt =0;
		while(compt<sizeX*sizeY){
			int i = (int)(Math.random()*sizeX);
			int j = (int)(Math.random()*sizeY);
			if(this.getCase(i, j)==null){
				this.cases.add(new Case(i,j));
				c = this.cases.lastElement();
				temp = getNeighbors(i,j);
				rdm = Math.random();
				double compteur = 1.0;
				for(Case c1 : temp){
					if(rdm<compteur/(temp.size()+1))
						c.setTerrain(c1.getTerrain());
					compteur+=1.0;
				}
				if(c.getTerrain()==null)
					c.setTerrain(Terrain.getRandomTerrain());
				compt++;
			}
		}
		for(Case c1 : this.cases){
			if(c1.getTerrain()!=Terrain.WATER){
				c1.setRoad(Math.random()<0.3);
			}
		}
		for(Case c1 : this.cases)
			c1.updateRoads(this);
		this.initializeCaseNeighbours();
		this.initializeRivers();
	}

	public void draw(Graphics g){
		for(Case c : this.cases){
			c.draw(g);
		}
		for(Case c : this.cases){
			if(c.mouseOnIt){
				int compt =0;
				for(Case c1 : this.getNeighbors(c.getI(), c.getJ())){
					g.setColor(Color.white);
					g.fill(c1.getPolygon());	
					g.setColor(Color.black);
					g.drawString(""+compt, c1.getX(), c1.getY());
					compt++;
				}
			}
		}
	}

	public void update(GameContainer gc){
		Input in = gc.getInput();
		//		for(Case c :this.cases)
		//			if(c.contains(in.getAbsoluteMouseX()+Xcam, in.getAbsoluteMouseY()+Ycam))
		//				c.mouseOnIt = true;
		//			else
		//				c.mouseOnIt = false;
		if(in.isKeyDown(Input.KEY_LEFT)){
			setXcam((int) Math.max(getXcam()-Constants.mouvementCam, -Constants.borderCam));
		}
		if(in.isKeyDown(Input.KEY_UP)){
			setYcam((int) Math.max(getYcam()-Constants.mouvementCam, -Constants.borderCam));
		}
		if(in.isKeyDown(Input.KEY_DOWN)){
			setYcam((int) (Math.min(getYcam()+Constants.mouvementCam, sizeY*1.5f*Constants.radiusCase+Constants.borderCam-Constants.resY)));
		}
		if(in.isKeyDown(Input.KEY_RIGHT)){
			setXcam((int) (Math.min(getXcam()+Constants.mouvementCam, sizeX*Math.sqrt(3f)*Constants.radiusCase+50f-Constants.resX)));
		}

	}

	public Case getCase(int i, int j){
		for(Case c : this.cases){
			if(c.getI() == i && c.getJ()==j){
				return c;
			}
		}
		return null;
	}

	public Vector<Case> getNeighbors(int i, int j){
		Case c = this.getCase(i, j);
		Vector<Case> v = new Vector<Case>();
		for(int id=0; id<6; id++)
			v.add(getNeighbor(c, id));
		while(v.contains(null))
			v.remove(null);
		return v;
	}

	public Case getNeighbor(Case c, int id){
		if(id<0 || id>6)
			return null;
		int i=c.getI(), j=c.getJ();
		if(j%2==0){	
			if(id==0)
				return this.getCase(i-1,j+1);
			if(id==1)
				return this.getCase(i-1,j);			
			if(id==2)
				return this.getCase(i-1,j-1);			
			if(id==3)
				return this.getCase(i,j-1);	
			if(id==4)
				return this.getCase(i+1,j);	
			if(id==5)
				return this.getCase(i,j+1);			
		} else {
			if(id==0)
				return this.getCase(i,j+1);		
			if(id==1)
				return this.getCase(i-1,j);		
			if(id==2)
				return this.getCase(i,j-1);		
			if(id==3)
				return this.getCase(i+1,j-1);
			if(id==4)
				return this.getCase(i+1,j);			
			if(id==5)
				return this.getCase(i+1,j+1);		
		}
		return null;
	}

	public void initializeCaseNeighbours(){
		for(Case c : this.cases){
			for(int i=0; i<6; i++){
				c.setNeighbour(i, this.getNeighbor(c, i));
			}
		}
	}

	public int getNumberOfWaterNeighbours(Case c){
		int compt = 0;
		for(int i=0; i<6; i++){
			if(this.getNeighbor(c, i)!=null && this.getNeighbor(c, i).getTerrain()==Terrain.WATER){
				compt ++;
			}
		}
		return compt;
	}

	public void createRiver(){
		float duree = 0.7f;
		Case depart = null, caseTravail = null;
		int rand = 0;
		int intTravail;
		do{
			depart = this.cases.get((int)(Math.random()*this.cases.size()));
			System.out.println("1");
		} while (depart.getTerrain()!=Terrain.WATER 
				|| depart.getI()<2 
				|| depart.getI()>this.sizeX-2
				|| depart.getJ()<2
				|| depart.getJ()>this.sizeY-2);
		do{
			intTravail = (int)(Math.random()*6);
			caseTravail = depart.getNeighbour(intTravail);
			System.out.println("2");
		} while (caseTravail==null 
				|| caseTravail.getTerrain()==Terrain.WATER
				|| this.getNumberOfWaterNeighbours(caseTravail)>2);
		int compt = 0;
		do{
			compt ++;
			rand = ((intTravail+3)%6+(int)(Math.random()*2)*2-1)%6;
			System.out.println("3");
		} while (compt<10 && (caseTravail.getNeighbour(rand)==null || caseTravail.getNeighbour(rand).getTerrain()==Terrain.WATER) );
		if(compt<10){
			caseTravail.setRiver(rand, true );
			caseTravail.getNeighbour(rand).setRiver((rand+3)%6, true);
			
		}
	}

	public int getXcam() {return Xcam;}
	public void setXcam(int xcam) {Xcam = xcam;}
	public int getYcam() {return Ycam;}
	public void setYcam(int ycam) {Ycam = ycam;}

}
