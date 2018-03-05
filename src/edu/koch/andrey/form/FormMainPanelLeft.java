package edu.koch.andrey.form;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


/**
 * Class helper for the FormMain class. Sets up the components for displaying 
 * in the left panel of the main window of the application
 * @author Andrey G
 */

public class FormMainPanelLeft
{
    private JPanel panel;

    private JTextField textfiedlInputFileName;
    private JTextField textfieldOutputFolderPath;
    private JTextField textfieldOutputFileTemplate;
    private JTextField textfieldOutputFileName;

    //----------------------------------------------------------------

    public FormMainPanelLeft (FormMainHandler controllerButton)
    {
	panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.setBackground(FormStyle.COLOR_MAIN);

	createPanel(controllerButton);
    }

    public JPanel get ()
    {
	return panel;
    }

    //----------------------------------------------------------------

    private void createPanel (FormMainHandler controllerButton)
    {
	JPanel displayPanel = new JPanel();

	displayPanel.setBackground(FormStyle.COLOR_MENU);
	displayPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	displayPanel.setLayout(new GridBagLayout());
	displayPanel.setSize(new Dimension (265, 140));
	displayPanel.setMinimumSize(displayPanel.getSize());
	displayPanel.setMaximumSize(displayPanel.getSize());
	displayPanel.setPreferredSize(displayPanel.getSize());
	GridBagConstraints layoutConstraits = new GridBagConstraints();
	
	//--------------------------------------------------------------
	
	JLabel labelInputFileName = new JLabel(" Input file:");
	labelInputFileName.setFont(FormStyle.DEFAULT_FONT);
	labelInputFileName.setSize(new Dimension(100, 25));
	labelInputFileName.setPreferredSize(labelInputFileName.getSize());
	layoutConstraits.gridx = 0;
	layoutConstraits.gridy = 0;
	layoutConstraits.anchor = GridBagConstraints.LINE_START;
	layoutConstraits.weighty = 0.2;
	layoutConstraits.weightx = 0.2;
	displayPanel.add(labelInputFileName, layoutConstraits);
	
	textfiedlInputFileName = new JTextField();
	textfiedlInputFileName.setSize(new Dimension(140, 25));
	textfiedlInputFileName.setPreferredSize(textfiedlInputFileName.getSize());
	textfiedlInputFileName.setEditable(false);
	layoutConstraits.gridx = 1;
	layoutConstraits.gridy = 0;
	displayPanel.add(textfiedlInputFileName, layoutConstraits);
	
	//--------------------------------------------------------------
	
	JLabel labelOutputFilePath = new JLabel(" Output folder:");
	labelOutputFilePath.setFont(FormStyle.DEFAULT_FONT);
	labelOutputFilePath.setSize(new Dimension(100, 25));
	labelOutputFilePath.setPreferredSize(labelOutputFilePath.getSize());
	layoutConstraits.gridx = 0;
	layoutConstraits.gridy = 1;
	layoutConstraits.anchor = GridBagConstraints.LINE_START;
	displayPanel.add(labelOutputFilePath, layoutConstraits);
	
	textfieldOutputFolderPath = new JTextField();
	textfieldOutputFolderPath.setSize(new Dimension(140, 25));
	textfieldOutputFolderPath.setPreferredSize(textfiedlInputFileName.getSize());
	textfieldOutputFolderPath.setEditable(false);
	layoutConstraits.gridx = 1;
	layoutConstraits.gridy = 1;
	displayPanel.add(textfieldOutputFolderPath, layoutConstraits);
	
	//--------------------------------------------------------------
	
	JLabel labelOutputFileNameTemplate = new JLabel (" Name template:");
	labelOutputFileNameTemplate.setFont(FormStyle.DEFAULT_FONT);
	labelOutputFileNameTemplate.setSize(new Dimension(100, 25));
	labelOutputFileNameTemplate.setPreferredSize(labelInputFileName.getSize());
	layoutConstraits.gridx = 0;
	layoutConstraits.gridy = 2;
	layoutConstraits.anchor = GridBagConstraints.LINE_START;
	displayPanel.add(labelOutputFileNameTemplate, layoutConstraits);
	
	textfieldOutputFileTemplate = new JTextField();
	textfieldOutputFileTemplate.setSize(new Dimension(140, 25));
	textfieldOutputFileTemplate.setPreferredSize(textfieldOutputFileTemplate.getSize());
	layoutConstraits.gridx = 1;
	layoutConstraits.gridy = 2;
	displayPanel.add(textfieldOutputFileTemplate, layoutConstraits);
	
	//--------------------------------------------------------------
	//---- Display current output file name 
	
	JLabel labelOutputFileName = new JLabel (" Output file:");
	labelOutputFileName.setFont(FormStyle.DEFAULT_FONT);
	labelOutputFileName.setSize(new Dimension(100, 25));
	labelOutputFileName.setPreferredSize(labelOutputFileName.getSize());
	layoutConstraits.gridx = 0;
	layoutConstraits.gridy = 3;
	displayPanel.add(labelOutputFileName, layoutConstraits);
	
	textfieldOutputFileName = new JTextField();
	textfieldOutputFileName.setSize(new Dimension(140, 25));
	textfieldOutputFileName.setPreferredSize(textfieldOutputFileTemplate.getSize());
	layoutConstraits.gridx = 1;
	layoutConstraits.gridy = 3;
	displayPanel.add(textfieldOutputFileName, layoutConstraits);
	
	
	panel.add(displayPanel);
    }

    //----------------------------------------------------------------

    public JTextField getTextfieldInputFileName ()
    {
	return textfiedlInputFileName;
    }
    
    public JTextField getTextfieldOutputFolderPath ()
    {
	return textfieldOutputFolderPath;
    }
    
    public JTextField getTextfieldOutputFileTemplate ()
    {
	return textfieldOutputFileTemplate;
    }
    
    public JTextField getTextfieldOutputFileName ()
    {
	return textfieldOutputFileName;
    }

    public void reset ()
    {

    }

}
