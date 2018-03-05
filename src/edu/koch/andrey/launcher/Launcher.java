package edu.koch.andrey.launcher;

import java.awt.Rectangle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import edu.koch.andrey.controller.MainController;

public class Launcher
{
	public static void main(String args[])
	{
		//---- Load opencv libraries
		System.loadLibrary("opencv_java310");

		//---- Launch the main GUI window
		SwingUtilities.invokeLater(new Runnable() {public void run() {launch();}});
	}

	public static void launch () 
	{
		//---- Set default fonts for the application
		UIManager.getLookAndFeelDefaults().put("defaultFont", new java.awt.Font("Times New Roman", 0, 12));

		
		//---- Grab the maximum screen resolution
		Rectangle screenResolution = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		MainController controller = MainController.getInstance();
		controller.launchMainWindow(screenResolution);

	}
}
