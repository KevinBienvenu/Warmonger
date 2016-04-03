package main;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import board.Board;
import player.Player;

public class Game extends BasicGame{
	
	public static Game game;
	
	private Board board;
	

	public Game(int resX, int resY) {
		super("Warmonger");
		Constants.resX = resX;
		Constants.resY = resY;
		Constants.playerNeutral = new Player("neutral",0);
		Constants.playerNeutral.setColor(Color.darkGray,Color.gray);
		game = this;
		this.board = new Board(15, 15);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.black);
		g.fillRect(0, 0, Constants.resX, Constants.resY);
		g.translate(-board.getXcam(), -board.getYcam());
		board.draw(g);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		this.board.update(gc);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
