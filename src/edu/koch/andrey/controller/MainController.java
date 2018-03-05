package edu.koch.andrey.controller;

import java.awt.Rectangle;

import edu.koch.andrey.exception.ExceptionHandler;
import edu.koch.andrey.form.FormMain;
import edu.koch.andrey.form.FormMainHandler;
import edu.koch.andrey.form.FormMainMouse;

/**
 * This class handles all the operations with the main data storages. 
 */

public class MainController
{
	private static MainController controller = null;

	//---- Main GUI form of the application and its controllers (handlers)
	private FormMain windowMain = null;
	private FormMainHandler windowMainHandler = null;
	private FormMainMouse windowMainMouse = null;

	//----------------------------------------------------------------

	private MainController ()
	{

	}

	public static MainController getInstance ()
	{
		if (controller == null) { controller = new MainController(); }

		return controller;
	}

	//----------------------------------------------------------------

	public void launchMainWindow (Rectangle screenResolution)
	{
		//---- Init handlers, create window, launch.
		if (windowMain == null) 
		{ 
			//DebugLogger.switchDebugModeON();
			//DebugLogger.switchDebugMatOutputON();
			//DebugLogger.logMessage("Launching app", DebugLogger.LOG_MESSAGE_TYPE.INFO);

			ExceptionHandler.setModeOutput(true, true, true);

			windowMainHandler = new FormMainHandler();
			windowMainMouse = new FormMainMouse();

			windowMain = FormMain.getInstance(screenResolution, windowMainHandler, windowMainMouse); 

			windowMainHandler.init(windowMain);
		}
	}

}
