package edu.koch.andrey.form;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

/**
 * Class helper for the FormMain class. Sets up the components for displaying 
 * toolbar of the main window of the application
 * @author Andrey G
 */

public class FormMainToolbar
{
	private JToolBar toolbar;

	private JButton buttonAddFile;
	private JButton buttonAddFolder;
	private JButton buttonSave;
	private JButton buttonSelect;
	private JButton buttonSelectPath;
	private JButton buttonMove;

	//----------------------------------------------------------------

	public FormMainToolbar (FormMainHandler controllerButton, int width)
	{
		toolbar = new JToolBar();
		toolbar.setLocation(0,0);
		toolbar.setSize(width, 35);
		toolbar.setFloatable(false);
		toolbar.setBorderPainted(true);
		toolbar.setRollover(true);
		toolbar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		toolbar.setBackground(FormStyle.COLOR_TOOLBAR);

		createToolbar(controllerButton);
	}

	public JToolBar get()
	{
		return toolbar;
	}

	//----------------------------------------------------------------
	//---- GET COMPONENT BUTTON
	//----------------------------------------------------------------
	public JButton getComponentButtonAddFile()
	{
		return buttonAddFile;
	}

	public JButton getComponentButtonAddFolder()
	{
		return buttonAddFolder;
	}

	public JButton getComponentButtonSave()
	{
		return buttonSave;
	}

	public JButton getComponentButtonSelect()
	{
		return buttonSelect;
	}

	public JButton getComponentButtonMove()
	{
		return buttonMove;
	}
	
	public JButton getComponentButtonSelectPath ()
	{
		return buttonSelectPath;
	}

	//----------------------------------------------------------------

	private void createToolbar(FormMainHandler controllerButton)
	{

		componentButtonAddFile(controllerButton);
		componentButtonAddFolder(controllerButton);

		toolbar.addSeparator();

		componentButtonZoomIn(controllerButton);
		componentButtonZoomOut(controllerButton);

		toolbar.addSeparator();

		componentButtonSelect(controllerButton);
		componentButtonSelectPath(controllerButton);
		componentButtonMove(controllerButton);
		componentButtonResetView(controllerButton);

		toolbar.addSeparator();

		componentButtonSave(controllerButton);

	}

	//----------------------------------------------------------------
	/**
	 * Button: Add File. Display a dialog box to select an input file. Add this file to the current
	 * project. Also works if it is a first file in a project.
	 * @param controllerButton
	 */
	private void componentButtonAddFile (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_SELECT_FILE_ADD);

		buttonAddFile = new JButton(iconButton);
		buttonAddFile.addActionListener (null);
		buttonAddFile.setSize(30, 30);
		buttonAddFile.setToolTipText("Add file");
		buttonAddFile.setBackground(FormStyle.COLOR_TOOLBAR);
		buttonAddFile.setActionCommand(FormMainHandlerCommands.AC_ADDFILE);
		buttonAddFile.addActionListener(controllerButton);

		toolbar.add(buttonAddFile);
	}

	//----------------------------------------------------------------
	/**
	 * Button: Add Folder. Display a dialog box to select a folder. All files (jpg/bmp) from 
	 * the selected folder will be added to the current project.
	 * @param controllerButton
	 */
	private void componentButtonAddFolder (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_SELECT_FOLDER);

		buttonAddFolder = new JButton(iconButton);
		buttonAddFolder.addActionListener (null);
		buttonAddFolder.setSize(30, 30);
		buttonAddFolder.setToolTipText("Open folder");
		buttonAddFolder.setBackground(FormStyle.COLOR_TOOLBAR);
		buttonAddFolder.setActionCommand(FormMainHandlerCommands.AC_SELECTFOLDER);
		buttonAddFolder.addActionListener(controllerButton);

		toolbar.add(buttonAddFolder);
	}

	//----------------------------------------------------------------
	/**
	 * Button: Zoom In. Zooms in the currently displayed image. 
	 * @param controllerButton
	 */
	private void componentButtonZoomIn (FormMainHandler controllerButton)
	{		
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_ZOOMIN);

		JButton buttonZoomIn = new JButton(iconButton);
		buttonZoomIn.addActionListener (null);
		buttonZoomIn.setSize(30, 30);
		buttonZoomIn.setToolTipText("Zoom in");
		buttonZoomIn.setBackground(FormStyle.COLOR_TOOLBAR);
		buttonZoomIn.setActionCommand(FormMainHandlerCommands.AC_ZOOMIN);
		buttonZoomIn.addActionListener(controllerButton);

		toolbar.add(buttonZoomIn);
	}

	//----------------------------------------------------------------
	/**
	 * Button: Zoom out. Zooms out the currently displayed image.
	 * @param controllerButton
	 */
	private void componentButtonZoomOut (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_ZOOMOUT);

		JButton buttonZoomOut = new JButton(iconButton);
		buttonZoomOut.addActionListener (null);
		buttonZoomOut.setSize(30, 30);
		buttonZoomOut.setToolTipText("Zoom out");
		buttonZoomOut.setBackground(FormStyle.COLOR_TOOLBAR);
		buttonZoomOut.setActionCommand(FormMainHandlerCommands.AC_ZOOMOUT);
		buttonZoomOut.addActionListener(controllerButton);

		toolbar.add(buttonZoomOut);
	}

	//----------------------------------------------------------------
	/**
	 * Button: Select. Allows user to select an area in the displayed image.The selected area can 
	 * be further added as a sample in case if the processing is planned to be performed in the manual mode.
	 * @param controllerButton
	 */
	private void componentButtonSelect (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_SELECT);

		buttonSelect = new JButton(iconButton);
		buttonSelect.addActionListener (null);
		buttonSelect.setSize(30, 30);
		buttonSelect.setToolTipText("Select sample");
		buttonSelect.addActionListener(controllerButton);
		buttonSelect.setActionCommand(FormMainHandlerCommands.AC_SAMPLE_SELECT);
		buttonSelect.setBackground(FormStyle.COLOR_TOOLBAR);

		toolbar.add(buttonSelect);
	}

	//----------------------------------------------------------------
	
	/**
	 * Button: Select Path. Allows user to select area, by specifying a polygon
	 */
	
	private void componentButtonSelectPath (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_POLY);
		
		buttonSelectPath = new JButton(iconButton);
		buttonSelectPath.addActionListener (null);
		buttonSelectPath.setSize(30, 30);
		buttonSelectPath.setToolTipText("Select sample via path");
		buttonSelectPath.addActionListener(controllerButton);
		buttonSelectPath.setActionCommand(FormMainHandlerCommands.AC_SAMPLE_SELECT_PATH);
		buttonSelectPath.setBackground(FormStyle.COLOR_TOOLBAR);

		toolbar.add(buttonSelectPath);
	}
	
	
	//----------------------------------------------------------------
	/**
	 * Button: Move. Allows to move the area, selected with the Select tool. This area can be added as 
	 * a sample for further processing in the manual mode.
	 * @param controllerButton
	 */
	private void componentButtonMove (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_MOVE);

		buttonMove = new JButton(iconButton);
		buttonMove.addActionListener (null);
		buttonMove.setSize(30, 30);
		buttonMove.setToolTipText("Move sample");
		buttonMove.addActionListener(controllerButton);
		buttonMove.setActionCommand(FormMainHandlerCommands.AC_SAMPLE_MOVE);
		buttonMove.setBackground(FormStyle.COLOR_TOOLBAR);

		toolbar.add(buttonMove);
	}

	//----------------------------------------------------------------
	/**
	 * Button: Reset/Refresh. Resets the view point of the images, forces to display the 
	 * original input image.
	 * @param controllerButton
	 */
	private void componentButtonResetView (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_REFRESH);

		JButton buttonRefresh = new JButton(iconButton);
		buttonRefresh.addActionListener (null);
		buttonRefresh.setSize(30, 30);
		buttonRefresh.setToolTipText("Refresh image");
		buttonRefresh.addActionListener(controllerButton);
		buttonRefresh.setActionCommand(FormMainHandlerCommands.AC_RESETVIEW);
		buttonRefresh.setBackground(FormStyle.COLOR_TOOLBAR);

		toolbar.add(buttonRefresh);
	}

	//----------------------------------------------------------------
	/**
	 * Button: Save. Displays a dialog box for specifying desired location and name of the output
	 * file. Only output of the currently selected image will be saved.
	 * @param controllerButton
	 */
	private void componentButtonSave (FormMainHandler controllerButton)
	{
		ImageIcon iconButton = FormUtils.getIconResource(FormStyle.RESOURCE_PATH_ICO_SAVE);

		buttonSave = new JButton(iconButton);
		buttonSave.addActionListener (null);
		buttonSave.setSize(30, 30);
		buttonSave.setToolTipText("Save current output");
		buttonSave.addActionListener(controllerButton);
		buttonSave.setBackground(FormStyle.COLOR_TOOLBAR);
		buttonSave.setActionCommand(FormMainHandlerCommands.AC_SAVESELECTEDIMG);

		toolbar.add(buttonSave);
	}
}
