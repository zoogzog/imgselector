
package edu.koch.andrey.data;

import org.opencv.core.Mat;

/**
 * Information about the input and output file name, folder etc
 */

public class Dataset
{
	private static final String DEFAULT_OUT_FILE_TEMPLATE = "output-";
	private static final String DEFAULT_OUT_FILE_EXTENSION = ".tif";

	private static String inputFilePath = "";
	private static String inputFileName = "";

	private static String outputFileFolderPath = "";

	private static String outputFileNameTemplate = DEFAULT_OUT_FILE_TEMPLATE;

	private static int outputFileIndex = 1;

	private static Mat imageMatrix;

	private static boolean isLoaded;

	//----------------------------------------------------------------

	public static String getInputFilePath ()
	{
		return inputFilePath;
	}

	public static String getInputFileName ()
	{
		return inputFileName;
	}

	public static String getOutputFileFolderPath ()
	{
		return outputFileFolderPath;
	}

	public static String getOutputFileName ()
	{
		return outputFileNameTemplate + outputFileIndex;
	}

	public static String getOutputFileNameTemplate ()
	{
		return outputFileNameTemplate;
	}

	public static String getOutputFilePath ()
	{
		return outputFileFolderPath + "\\" + outputFileNameTemplate + outputFileIndex + DEFAULT_OUT_FILE_EXTENSION;
	}

	public static boolean getIsLoaded ()
	{
		return isLoaded;
	}

	public static Mat getInputMatrix ()
	{
		return imageMatrix;
	}

	//----------------------------------------------------------------

	public static void setInputFilePathe (String value)
	{
		inputFilePath = value;
	}

	public static void setInputFileName (String value)
	{
		inputFileName = value;
	}

	public static void setOutputFileFolderPath (String value)
	{
		outputFileFolderPath = value;
	}

	public static void setOutputFileNameTemplate (String value)
	{
		outputFileNameTemplate = value;
	}

	public static void setImageMatrix (Mat matrix)
	{
		imageMatrix = matrix;
	}

	public static void setIsLoaded (boolean value)
	{
		isLoaded = value;
	}

	//----------------------------------------------------------------

	public static void changeIndex()
	{
		outputFileIndex++;
	}
}
