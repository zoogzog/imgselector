package edu.koch.andrey.form;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * Class for creating the menu of the main window
 * @author Andrey G.
 */

public class FormMainMenu
{
	private JMenuBar menu = null;

	//----------------------------------------------------------------

	public FormMainMenu (FormMainHandler controllerButton)
	{
		menu = new JMenuBar();
		menu.setBackground(FormStyle.COLOR_MENU);
		menu.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		createMenu(controllerButton);
	}

	//----------------------------------------------------------------

	public JMenuBar get ()
	{
		return menu;
	}

	private void createMenu (FormMainHandler controllerButton)
	{
		menuFile(controllerButton);
	}

	//----------------------------------------------------------------
	//---- Components of the menu

	/**
	 * Menu File: New project, Open file, Add file, Open Folder, Save output, Export
	 * @param controllerButton
	 */
	private void menuFile (FormMainHandler controllerButton)
	{
		JMenu menuFile = new JMenu("File");
		menuFile.setFont(FormStyle.DEFAULT_FONT);
		menuFile.setBorder(new EmptyBorder(5, 5, 5, 5));
		menuFile.setMnemonic(KeyEvent.VK_F);
		menu.add(menuFile);

		//---- Menu for creating a new project and opening a new file
		JMenuItem menuFileOpen = new JMenuItem(FormStyle.MENU_FILE_OPENFILE);
		menuFileOpen.addActionListener(controllerButton);
		menuFileOpen.setFont(FormStyle.DEFAULT_FONT);
		menuFileOpen.setBorder(FormStyle.DEFAULT_MENU_BORDER);
		menuFileOpen.setBackground(FormStyle.COLOR_MENU);
		menuFileOpen.setActionCommand(FormMainHandlerCommands.AC_ADDFILE);
		menuFile.add(menuFileOpen);


		//---- Menu for adding all files in an existing folder to the current project
		JMenuItem menuSelectOutputFolder = new JMenuItem(FormStyle.MENU_FILE_OPENFOLDER);
		menuSelectOutputFolder.addActionListener(controllerButton);
		menuSelectOutputFolder.setFont(FormStyle.DEFAULT_FONT);
		menuSelectOutputFolder.setBorder(FormStyle.DEFAULT_MENU_BORDER);
		menuSelectOutputFolder.setBackground(FormStyle.COLOR_MENU);
		menuSelectOutputFolder.setActionCommand(FormMainHandlerCommands.AC_SELECTFOLDER);
		menuFile.add(menuSelectOutputFolder);

		//-----------------------------------------------
		menuFile.addSeparator();
		//-----------------------------------------------

		//---- Menu for saving output for the currently selected image
		JMenuItem menuFileSaveFile = new JMenuItem(FormStyle.MENU_FILE_SAVEOUTPUT);
		menuFileSaveFile.addActionListener(controllerButton);
		menuFileSaveFile.setFont(FormStyle.DEFAULT_FONT);
		menuFileSaveFile.setBorder(FormStyle.DEFAULT_MENU_BORDER);
		menuFileSaveFile.setBackground(FormStyle.COLOR_MENU);
		menuFile.add(menuFileSaveFile);

		//---- Menu for saving output for all images in the current project
		JMenuItem menuFileSaveAllFile = new JMenuItem(FormStyle.MENU_FILE_SAVEALL);
		menuFileSaveAllFile.addActionListener(controllerButton); 
		menuFileSaveAllFile.setFont(FormStyle.DEFAULT_FONT);
		menuFileSaveAllFile.setBorder(FormStyle.DEFAULT_MENU_BORDER);
		menuFileSaveAllFile.setBackground(FormStyle.COLOR_MENU);
		menuFile.add(menuFileSaveAllFile);

	}
}
