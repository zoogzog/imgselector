
package edu.koch.andrey.controller;

import java.awt.Polygon;

import org.opencv.core.Mat;

/**
 * Extract selected region into a separate matrix
 */

public class ImageExtractor
{
	public static Mat getSubImage (Mat inputImage, Polygon polygon)
	{
		int minX = inputImage.width(), minY = inputImage.height();
		int maxX = 0, maxY = 0;

		int[] pointsX = polygon.xpoints;
		int[] pointsY = polygon.ypoints;

		for (int i = 0; i < pointsX.length; i++)
		{
			if (pointsX[i] != 0 && pointsY[i] != 0)
			{
				if (minX >= pointsX[i]) { minX = pointsX[i]; }
				if (maxX <= pointsX[i]) { maxX = pointsX[i]; }
				if (minY >= pointsY[i]) { minY = pointsY[i]; }
				if (maxY <= pointsY[i]) { maxY = pointsY[i]; }
			}
		}

		int width = maxX - minX;
		int height = maxY - minY;

		if (width <= 0 || height <= 0) { return inputImage; }

		Mat outputMat = new Mat (height+1, width+1, inputImage.type());

		for (int row = 0, y = minY; row <= height; row++, y++)
		{
			for (int col = 0, x = minX; col <= width; col++, x++)
			{


				if (polygon.contains(x, y))
				{
					outputMat.put(row, col, inputImage.get(y, x));
				}
				else
				{
					outputMat.put(row, col, new double[] {255, 255, 255});
				}
			}
		}

		return outputMat;
	}
}
