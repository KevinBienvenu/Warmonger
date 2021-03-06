package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;


public class Main {
	// A REGLER \\
	public static int framerate = 60;
	///////\\\\\\\\\
	
	public static boolean fullScreen = false;
	
	public static void main(String[] args) {
		//Log.setLogSystem(new NullLogSystem()); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
//		System.out.println(LWJGLUtil.getPlatformName());
		int resolutionX;
		int resolutionY;
		if(fullScreen){
			resolutionX = (int)screenSize.getWidth();		
			resolutionY = (int)screenSize.getHeight();
		} else {
			resolutionX = 1200;		
			resolutionY = 800;
		}
		try {
			Game game = new Game(resolutionX,resolutionY);
			AppGameContainer app = new AppGameContainer( game );
//			app.setDisplayMode(resolutionX, resolutionY,true);
			app.setShowFPS(false);
			app.setDisplayMode(resolutionX, resolutionY,fullScreen);
			app.setAlwaysRender(false);
			app.setTargetFrameRate(framerate);
			app.setUpdateOnlyWhenVisible(false);
			app.setClearEachFrame(true);
			app.setVSync(true);
			//app.setSmoothDeltas(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	

}
