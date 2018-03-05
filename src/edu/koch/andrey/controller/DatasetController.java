package edu.koch.andrey.controller;

import java.awt.Polygon;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import edu.koch.andrey.data.Dataset;

/**
 * Class for handling different scenarios related to data processing
 */

public class DatasetController
{
	public static void scenarioLoadFile (String filePath)
	{
		Mat matrix = Imgcodecs.imread(filePath);

		Path path = Paths.get(filePath);

		Dataset.setInputFilePathe(filePath);
		Dataset.setInputFileName(path.getFileName().toString());

		Dataset.setImageMatrix(matrix);

		Dataset.setOutputFileFolderPath(path.getParent().toString());

		Dataset.setIsLoaded(true);
	}

	public static void scenarioSetOutputFolder (String folderPath)
	{
		Dataset.setOutputFileFolderPath(folderPath);
	}


	public static void scenarioSaveFile (Polygon polygon)
	{
		if (polygon == null) { return; }
		if (polygon.npoints <= 2) { return; }

		Mat subImage = ImageExtractor.getSubImage(Dataset.getInputMatrix(), polygon);

		Imgcodecs.imwrite(Dataset.getOutputFilePath(), subImage);

		Dataset.changeIndex();
	}

	public static String getInputFilePath ()
	{
		return Dataset.getInputFilePath();
	}
}
