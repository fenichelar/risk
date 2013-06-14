package main.java.edu.gatech.cs2340.risk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class RiskUtilMock  {

	private static Logger log = Logger.getLogger(RiskUtilMock.class);

	private static Gson gson = new Gson();

	public static String convertObjectToJsonFile(Object obj) {
		return gson.toJson(obj);
	}

	public static Object convertJsonFileToObject(String file, Class model) {

		Object obj = null;

		try {
			// FIXME: Add the class path to this so it can find the correct file
			BufferedReader br = new BufferedReader(
					new FileReader("resources/json/" + file));


			//convert the json string back to object
			obj = gson.fromJson(br, model);

			// close the reader
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static void createFileFromJson(String file, Object obj) {
		
		String json = gson.toJson(obj); 
		log.debug("Json created for file: " + json);

		String fileLoc = "resources/json/" + file;
		log.debug("File location for file: " + fileLoc);
		File newFile = new File(fileLoc);
		if (newFile.exists()) {
			log.debug("Deleting existing file at " + fileLoc);
			newFile.delete();
		}

		try {
			FileWriter fileWriter = new FileWriter(newFile);
			fileWriter.write(json);
			fileWriter.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			return;

		}
	}

	public static int getFileCountInPackage(String location) {
		String fileLoc = "resources/json/" + location;
		File packageLoc = new File(fileLoc);
		int count = 0;

		for (File file : packageLoc.listFiles()) {
			if (file.isFile()) {
				count ++;
			}
		}
		log.debug("Number of files in " + location + ": " + count);
		return count;
	}
	
	public static void restoreDefaults() {
	
		File folder = new File("resources/json/player");
		
		for (File file : folder.listFiles()) {
			System.out.println("Deleting " + file.toString());
			file.delete();
		}
	}
}
