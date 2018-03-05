package edu.koch.andrey.form;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import edu.koch.andrey.controller.DatasetController;
import edu.koch.andrey.data.Dataset;
import edu.koch.andrey.debug.DebugLogger;
import edu.koch.andrey.debug.DebugLogger.LOG_MESSAGE_TYPE;
import edu.koch.andrey.form.FormMainMouse.MouseMode;
import edu.koch.andrey.utils.OpencvConverter;



/**
 * Handler class for the main window of the application. Handles low level (graphical)
 * responses to the clicked buttons and pressed keys. More complex scenarios which
 * involve data manipulation atre processed by the ActionController.
 */

public class FormMainHandler  implements ActionListener
{
	//---- Form to control 
	private FormMain mainFormLink = null;

	boolean isDisplaySamples = false;

	//----------------------------------------------------------------

	public FormMainHandler ()
	{

	}

	public void init (FormMain mainForm)
	{
		mainFormLink = mainForm;


	}

	//----------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent source)
	{
		if (mainFormLink != null)
		{
			String commandCodeName = source.getActionCommand();

			/*!*/DebugLogger.logMessage("Action command: " + commandCodeName, LOG_MESSAGE_TYPE.INFO);

			switch (commandCodeName)
			{
			case FormMainHandlerCommands.AC_ADDFILE: 		actionAddFile(); 	break;

			case FormMainHandlerCommands.AC_SELECTFOLDER:		actionSetOutputFolder(); break;

			case FormMainHandlerCommands.AC_ZOOMIN:			actionZoomIn();		break;
			case FormMainHandlerCommands.AC_ZOOMOUT: 		actionZoomOut(); 	break;
			case FormMainHandlerCommands.AC_RESETVIEW:		actionResetView(); 	break;

			case FormMainHandlerCommands.AC_SAMPLE_MOVE:		actionSwitchMove();    	break;
			case FormMainHandlerCommands.AC_SAMPLE_SELECT:		actionSwitchSelect(); 	break;
			case FormMainHandlerCommands.AC_SAMPLE_SELECT_PATH: actionSwitchSelectPoly(); break;

			case FormMainHandlerCommands.AC_SAVESELECTEDIMG:	actionSaveSelectedPart(); break;

			}
		}
		else
		{
			//---- Handler was not initialized. Somewhere the code is wrong.
			// ExceptionHandler.processException(new ExceptionMessage(EXCEPTION_CODE.EXCODE_HANDLER_INIT));
		}
	}


	//-----------------------------------------------------------------------------------------
	/**
	 * Action which is invoked when the user clicks the 'Add file' button or chooses 'Add file'
	 * option from the menu. Shows user a dialog box for choosing an input file. When the input
	 * file is selected, adds it to the project.
	 */
	private void actionAddFile ()
	{

		JFileChooser fileChooserDriver = new JFileChooser();

		int isFileSelected =  fileChooserDriver.showOpenDialog(fileChooserDriver);

		if (isFileSelected == JFileChooser.APPROVE_OPTION)
		{
			String filePath = fileChooserDriver.getSelectedFile().getPath();

			DatasetController.scenarioLoadFile(filePath);

			helperSetTextData();

			helperDisplayInputImage();
		}


	}


	/**
	 * Set the folder to output
	 */
	private void actionSetOutputFolder ()
	{
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int isFolderSelected  = folderChooser.showOpenDialog(folderChooser);

		if (isFolderSelected == JFileChooser.APPROVE_OPTION)
		{
			String folderPath = folderChooser.getSelectedFile().getPath();

			DatasetController.scenarioSetOutputFolder(folderPath);

			helperSetTextData();
		}

	}

	//-----------------------------------------------------------------------------------------

	/**
	 * Zooms in the currently displayed image.
	 */
	private void actionZoomIn ()
	{

		if (!Dataset.getIsLoaded()) { return; }

		//---- Calculate zoom scale factor
		double zoom = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().transformZoom(+FormMainMouse.DEFAULT_ZOOM_DELTA);    
		mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();

		//---- Set the current zoom, display the zoom scale factor in the GUI
		FormMainMouse.imageViewZoomScale = zoom;
		mainFormLink.getComponentPanelDown().getComponentLabelZoom().setText(String.valueOf(zoom));

	}

	/**
	 * Zooms out the currently displayed image.
	 */
	private void actionZoomOut ()
	{
		if (!Dataset.getIsLoaded()) { return; }

		//---- Calculate zoom scale factor
		double zoom = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().transformZoom(-FormMainMouse.DEFAULT_ZOOM_DELTA);    
		mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();

		//---- Set the current zoom, display the zoom scale factor in the GUI
		FormMainMouse.imageViewZoomScale = zoom;
		mainFormLink.getComponentPanelDown().getComponentLabelZoom().setText(String.valueOf(zoom));

	}

	/**
	 * Resets the viewpoint of the currently displayed image.
	 */
	private void actionResetView ()
	{
		if (!Dataset.getIsLoaded()) { return; }

		helperSwitchSelectOff();
		helperSwitchMoveOff();


		mainFormLink.getComponentPanelCenter().getComponentPanelImageView().displayPolygonStop();
		mainFormLink.getComponentPanelCenter().getComponentPanelImageView().resetImagePosition();


	}

	private void actionSaveSelectedPart ()
	{
		if (!Dataset.getIsLoaded()) { return; }

		Polygon polygon = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getDisplayedPolygon();


		//Check if the template has been changed
		String template = mainFormLink.getComponentPanelLeft().getTextfieldOutputFileTemplate().getText();
		template.replaceAll(" ", "");

		if (!template.equals("")) { Dataset.setOutputFileNameTemplate(template); }

		DatasetController.scenarioSaveFile(polygon);

		helperSetTextData();
	}

	//-----------------------------------------------------------------------------------------
	//---- HELPERS
	//-----------------------------------------------------------------------------------------

	private void helperSetTextData ()
	{
		mainFormLink.getComponentPanelLeft().getTextfieldInputFileName().setText(Dataset.getInputFileName());
		mainFormLink.getComponentPanelLeft().getTextfieldOutputFolderPath().setText(Dataset.getOutputFileFolderPath());
		mainFormLink.getComponentPanelLeft().getTextfieldOutputFileName().setText(Dataset.getOutputFilePath());
		mainFormLink.getComponentPanelLeft().getTextfieldOutputFileTemplate().setText(Dataset.getOutputFileNameTemplate());


	}

	/**
	 * Display the selected file in the image viewer panel
	 */
	private void helperDisplayInputImage()
	{
		BufferedImage inputImage = OpencvConverter.convertMatToBImage(Dataset.getInputMatrix());


		mainFormLink.getComponentPanelCenter().getComponentPanelImageView().displayPolygonStop();
		mainFormLink.getComponentPanelCenter().getComponentPanelImageView().loadImage(inputImage);

	}

	/**

    /**
	 * Switches between sample selection modes.
	 */
	private void actionSwitchSelect()
	{
		if (!Dataset.getIsLoaded()) { return; }

		if (FormMainMouse.mouseMode == MouseMode.MODE_SELECT_RECTANGLE)
		{
			helperSwitchSelectOff();
		}
		else
		{
			helperSwitchMoveOff();
			helperSwitchSelectOn();
		}
	}

	/**
	 * Turns on the sample area selection.
	 */
	private void helperSwitchSelectOn()
	{
		if (!Dataset.getIsLoaded()) { return; }

		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_SELECT_PRESSED);

		mainFormLink.getComponentToolbar().getComponentButtonSelect().setIcon(iconButton);

		FormMainMouse.mouseMode = MouseMode.MODE_SELECT_RECTANGLE;
	}

	/**
	 * Turns off the sample area selection.
	 */
	private void helperSwitchSelectOff()
	{
		if (!Dataset.getIsLoaded()) { return; }

		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_SELECT);

		mainFormLink.getComponentToolbar().getComponentButtonSelect().setIcon(iconButton);

		FormMainMouse.mouseMode = MouseMode.MODE_MOVE_IMAGE;
	}

	/**
	 * Switches between sample move modes.
	 */
	private void actionSwitchMove()
	{
		if (!Dataset.getIsLoaded()) { return; }

		if (FormMainMouse.mouseMode == MouseMode.MODE_MOVE_SAMPLE)
		{
			helperSwitchMoveOff();
		}
		else
		{
			helperSwitchSelectOff();
			helperSwitchMoveOn();
		}
	}

	/**
	 * Turns on sample moving.
	 */
	private void helperSwitchMoveOn()
	{
		if (!Dataset.getIsLoaded()) { return; }

		FormMainMouse.mouseMode = MouseMode.MODE_MOVE_SAMPLE;

		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_MOVE_ACTIVE);

		mainFormLink.getComponentToolbar().getComponentButtonMove().setIcon(iconButton);
	}

	/**
	 * Turns off sample moving
	 */
	private void helperSwitchMoveOff()
	{

		if (!Dataset.getIsLoaded()) { return; }

		FormMainMouse.mouseMode = MouseMode.MODE_MOVE_IMAGE;

		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_MOVE);

		mainFormLink.getComponentToolbar().getComponentButtonMove().setIcon(iconButton);
	}

	private void actionSwitchSelectPoly ()
	{
		if (!Dataset.getIsLoaded()) { return; }

		if (FormMainMouse.mouseMode == MouseMode.MODE_SELECT_POLY)
		{
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().polygonRemoveAll();

			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().invalidate();
			
			//FormMainMouse.mouseMode = MouseMode.MODE_MOVE_IMAGE;
		}
		else
		{
			helperSwitchMoveOff();
			helperSwitchSelectOff();

			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().polygonRemoveAll();

			FormMainMouse.mouseMode = MouseMode.MODE_SELECT_POLY;
		}
	}

}


