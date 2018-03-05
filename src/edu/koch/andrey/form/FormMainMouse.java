package edu.koch.andrey.form;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import edu.koch.andrey.data.Dataset;

/**
 * Class for handling mouse related events
 * @author Andrey G.
 */

public class FormMainMouse implements MouseMotionListener, MouseWheelListener, MouseListener
{
	public final static double DEFAULT_ZOOM_DELTA = 0.05;
	public final static double DEFAULT_ZOOM_MAX = 5;
	public final static double DEFAULT_ZOOM_MIN = 0.1;

	public enum MouseMode {MODE_SELECT_RECTANGLE, MODE_SELECT_POLY, MODE_MOVE_SAMPLE, MODE_MOVE_IMAGE}

	//----------------------------------------------------------------

	//---- Form to control 
	private FormMain mainFormLink;

	private Point pointStart = new Point(-1, -1);
	private Point handlerMouseListenerPoint = new Point(-1, -1);

	public static MouseMode mouseMode; 

	//---- Current scale of the displayed image
	public static double imageViewZoomScale = 1.0;

	//----------------------------------------------------------------

	public FormMainMouse ()
	{
		mouseMode = MouseMode.MODE_MOVE_IMAGE;
	}

	public void init (FormMain mainForm)
	{
		mainFormLink = mainForm;
	}

	public MouseMode getMode ()
	{
		return mouseMode;
	}
	
	//----------------------------------------------------------------

	@Override
	/**
	 * Handler for mouse dragging motion. If currently sample selection is turned on, then
	 * allow user to chose the area of the sample. If sample selection is turned off, but moving
	 * sample is turned on, then move the sample. Otherwise, move the entire image.
	 */
	public void mouseDragged(MouseEvent e) 
	{
		switch (mouseMode)
		{

		//---------------------------------------------------
		case MODE_MOVE_IMAGE: 
		{
			
			Point end = e.getPoint();

			int moveX = end.x - handlerMouseListenerPoint.x;
			int moveY = end.y - handlerMouseListenerPoint.y;

			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().transformMove(moveX, moveY);
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();

			handlerMouseListenerPoint = end;

			break;
		}
		//---------------------------------------------------
		case MODE_MOVE_SAMPLE: 
		{
			Point end = e.getPoint();

			Point realPointStart = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(pointStart);
			Point realPointEnd = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(end);

			int deltaX = realPointEnd.x - realPointStart.x;
			int deltaY = realPointEnd.y - realPointStart.y;

			if (mainFormLink.getComponentPanelCenter().getComponentPanelImageView().isInPolygon(realPointStart.x, realPointStart.y))
			{
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().setCursor(new Cursor(Cursor.HAND_CURSOR));	
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().transformMovePolygon(deltaX, deltaY);
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();
			}

			pointStart = end;	
			
			break;
		}
		//---------------------------------------------------
		case MODE_SELECT_RECTANGLE: 
		{
			Point end = e.getPoint();

			Point realPointStart = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(pointStart);
			Point realPointEnd = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(end);

			Point[] sampleSelectBox = new Point[4]; 
			sampleSelectBox[0] = new Point (realPointStart.x, realPointStart.y);
			sampleSelectBox[1] = new Point (realPointStart.x, realPointEnd.y);
			sampleSelectBox[2] = new Point (realPointEnd.x, realPointEnd.y);
			sampleSelectBox[3] = new Point (realPointEnd.x, realPointStart.y);

			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().polygonDisplay(sampleSelectBox);
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();
			
			break;
		}
		//---------------------------------------------------
		case MODE_SELECT_POLY: break;
		}
	}

	@Override
	/**
	 * Responds to mouse moving. Displays coordinates of the mouse pointer, changes cursors if it is in the are 
	 * of a movable sample.
	 */
	public void mouseMoved(MouseEvent e) 
	{
		pointStart = e.getPoint();

		//---- Display coordinates of the mouse mosition in the down panel of the application
		String pointX = String.valueOf(mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(e.getPoint()).x);
		String pointY = String.valueOf(mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(e.getPoint()).y);

		mainFormLink.getComponentPanelDown().getComponentLabelX().setText(pointX);
		mainFormLink.getComponentPanelDown().getComponentLabelY().setText(pointY);

		if (mouseMode == MouseMode.MODE_MOVE_SAMPLE)
		{
			Point pointToCheck = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(pointStart);

			if (mainFormLink.getComponentPanelCenter().getComponentPanelImageView().isInPolygon(pointToCheck.x, pointToCheck.y))
			{
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else
			{
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	@Override
	/**
	 * Responds to moving the mouse wheel. Zooms out or in the displayed picture.
	 */
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		int mouseWheelDirection = e.getWheelRotation();

		if (mouseWheelDirection < 0)
		{

			if (!Dataset.getIsLoaded()) { return; }

			if (imageViewZoomScale - DEFAULT_ZOOM_DELTA > DEFAULT_ZOOM_MIN)
			{
				double exportZoom = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().transformZoom(-DEFAULT_ZOOM_DELTA);
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();

				imageViewZoomScale = exportZoom;

				mainFormLink.getComponentPanelDown().getComponentLabelZoom().setText(String.valueOf((double)Math.round(exportZoom * 100) / 100));
			}
		}
		else
		{
			if (!Dataset.getIsLoaded()) { return; }

			if (imageViewZoomScale + DEFAULT_ZOOM_DELTA < DEFAULT_ZOOM_MAX)
			{
				double exportZoom = mainFormLink.getComponentPanelCenter().getComponentPanelImageView().transformZoom(+DEFAULT_ZOOM_DELTA);
				mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();

				imageViewZoomScale = exportZoom;

				mainFormLink.getComponentPanelDown().getComponentLabelZoom().setText(String.valueOf((double)Math.round(exportZoom * 100) / 100));
			}

		}
	}

	//----------------------------------------------------------------

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (mouseMode == MouseMode.MODE_SELECT_POLY)
		{
			Point newPoint = e.getPoint();
			
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().polygonAddPoint(mainFormLink.getComponentPanelCenter().getComponentPanelImageView().getRealPoint(newPoint));
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().repaint();
		}
	}

	@Override 
	public void mouseEntered(MouseEvent e) 
	{

	}

	@Override
	public void mouseExited(MouseEvent e) 
	{ 

	}

	//----------------------------------------------------------------

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getButton() == MouseEvent.BUTTON1) 
		{

			if (!Dataset.getIsLoaded()) { return; }
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().setCursor(new Cursor(Cursor.MOVE_CURSOR)); 

			handlerMouseListenerPoint.x = e.getX();
			handlerMouseListenerPoint.y = e.getY();

		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1) 
		{
			if (!Dataset.getIsLoaded()) { return; }
			mainFormLink.getComponentPanelCenter().getComponentPanelImageView().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			handlerMouseListenerPoint.x = e.getX();
			handlerMouseListenerPoint.y = e.getY();

		}
	}

	
}
