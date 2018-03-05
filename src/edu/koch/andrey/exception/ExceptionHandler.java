package edu.koch.andrey.exception;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.koch.andrey.debug.DebugLogger;
import edu.koch.andrey.debug.DebugLogger.LOG_MESSAGE_TYPE;


/**
 * Class for handling custom exception
 */

public class ExceptionHandler
{
	private static boolean isModeOutputConsole = false;
	private static boolean isModeOutputGui = false;
	private static boolean isModeOutputFile = false;

	private static JFrame frameLink = null;

	//----------------------------------------------------------------

	/**
	 * Specifies the way where the exception will be shown.
	 * Console: the exception is displayed in console
	 * Gui: the exception is displayed in a separate window.
	 * File: the exception log is saved in a file error_log_$data.txt
	 */
	public static void setModeOutput (boolean isConsole, boolean isGui, boolean isFile)
	{
		isModeOutputConsole = isConsole;
		isModeOutputGui = isGui;
		isModeOutputFile = isFile;
	}

	public static void setJFrameLinkt (JFrame frame)
	{
		frameLink = frame;
	}

	//----------------------------------------------------------------

	public static void processException (ExceptionMessage exception)
	{
		/*!*/DebugLogger.logMessage("Exception has been caught. Code: " + exception.getCode(), LOG_MESSAGE_TYPE.WARNING);

		switch (exception.getCode())
		{
		//---- Fatal errors caused by a lazy programmer
		case EXCODE_INDEXOFRANGE: 			fatalError(exception);	break;
		case EXCODE_DATACONTROLLER_INIT: 	fatalError(exception);	break;
		case EXCODE_HANDLER_INIT: 			fatalError(exception);	break;
		case EXCODE_TABLEUNCOSISTENCY: 		fatalError(exception);	break;
		case EXCODE_FILENOTFOUND:			fatalError(exception); 	break;
		case EXCODE_FILEWRITEFAIL:			fatalError(exception); 	break;
		case EXCODE_MATRIXISNULL: 			fatalError(exception); 	break;
		case EXCODE_CELLDETECTION:			fatalError(exception); 	break;

		case EXCODE_SAMPLENOTDETECTED:		warningError(exception); break;
		case EXCODE_SETTINGSERROR:			warningError(exception); break;
		case EXCODE_SETTINGSINDEXCONTROL:	warningError(exception); break;
		case EXCODE_SETTINGSMANUALMODE:		warningError(exception); break;
		case EXCODE_SETTINGSSAMPLESCOUNT:	warningError(exception); break;

		}


	}

	//----------------------------------------------------------------

	/**
	 * Process fatal exception.
	 * @param exception
	 */
	private static void fatalError (ExceptionMessage exception)
	{
		/*!*/DebugLogger.logMessage("Exception " + exception.getCode(), exception);

		if (isModeOutputConsole) { displayExceptionConsole(exception, true); }
		if (isModeOutputGui) { displayExceptionGui(exception, true); }
		if (isModeOutputFile) { displayExceptionFile(exception, true); }
		
		//---- TERMINATE EXECUTION OF THIS APPLICATION
		System.exit(-1);
	}

	private static void warningError (ExceptionMessage exception)
	{
		/*!*/DebugLogger.logMessage("Exception " + exception.getCode(), exception);

		if (isModeOutputConsole) { displayExceptionConsole(exception, false); }
		if (isModeOutputGui) { displayExceptionGui(exception, false); }
	}

	//----------------------------------------------------------------

	/**
	 * Displays information about the occurred exception in the console box.
	 * @param exception
	 */
	private static void displayExceptionConsole (ExceptionMessage exception, boolean isFatal)
	{
		try
		{
			if (isFatal)
			{
				System.out.println("Exception code: " + exception.getCode().toString());
		
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			/*!*/DebugLogger.debugDisplayMessage("Problem with handling an exception. " + exception.getCode());
		}
	}

	/**
	 * Displays information about the occurred exception as a dialog box.
	 * @param exception
	 */
	private static void displayExceptionGui (ExceptionMessage exception, boolean isFatal)
	{
		if (frameLink != null)
		{
			if (isFatal)
			{
				String exceptionDialogMessage = "";

				exceptionDialogMessage += "Fatal exception occured. Terminated.\n\n";
				exceptionDialogMessage += "Exception code: " + exception.getCode().toString() + "\n";

				//---- Make a message to display
				exceptionDialogMessage += "Description: " + exception.getInfo();

				if (isModeOutputFile)
				{
					exceptionDialogMessage += "\n" + "Stack is dumped into an error log file.\n";
				}

				exceptionDialogMessage += "Please contact the developer for more information.";

				JOptionPane.showMessageDialog(frameLink,exceptionDialogMessage, "Fatal error", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			{
				String exceptionDialogMessage = "";
				
				exceptionDialogMessage += exception.getInfo();
				
				JOptionPane.showMessageDialog(frameLink,exceptionDialogMessage, "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Stores the information about the occurred exception in a file.
	 * @param exception
	 */
	private static void displayExceptionFile (ExceptionMessage exception, boolean isFatal)
	{  
		if (isFatal)
		{
			try
			{
				DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd-HHmmss");

				String fileName = "err-" + dateFormat.format(new Date()) + ".log";

				FileWriter outputFile = new FileWriter(fileName, false);
				PrintWriter outputStream = new PrintWriter(outputFile);

				outputStream.println("Exception code: " + exception.getCode().toString());


				outputStream.println("--------------------------------------\n");


				outputStream.println("Exception stack trace dump.");

				//----- Convert stack trace to a string and output to the file
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exception.printStackTrace(pw);

				String exceptionStackTrace = sw.toString();

				outputStream.println(exceptionStackTrace);

				outputStream.close();
				outputFile.close();
			}
			catch (Exception e)
			{
				/*!*/DebugLogger.logMessage("Fatal exception: saving exception file", LOG_MESSAGE_TYPE.SEVERE);
			}
		}
	}

}
