package com.aaupush.com;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class UserTest {
	final static Logger logger = Logger.getLogger(UserTest.class);

	@Test
	public void addUser() {

		try {
			InputStream input = new FileInputStream("src/main/resources/model.properties");
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and connect to the folder
			File sourceFolder = new File(prop.getProperty("source_directory"));
			logger.info("Working on the source project in:" + sourceFolder);

			// getting the programming language associated files
			String[] extensions = new String[] { prop.getProperty("program_language") };

			// list all source files in the source folder
			List<File> files = (List<File>) FileUtils.listFiles(sourceFolder, extensions, true);

			logger.info("========Found Source Items=======");
			for (File file : files) {
				logger.info("file: " + file.getCanonicalPath());
			}
			logger.info("========End Source Items=======");

			// reading design variable lists
			DesignNameLog designNameLog = new DesignNameLog();
			///////////// the source code variable lists
			logger.info("========Found variables names=======");
			ArrayList<String> wordList = null;
			StringSimilarity stringSimilarity = new StringSimilarity();
			for (File file : files) {

				wordList = designNameLog.getNames(file.getCanonicalPath());

				for (String word : wordList) {

					logger.info(word);
					/*
					 * calculating the similarity between variables from the dictionary and
					 * variables in the source code using Levenshtein distance algorithm
					 */
					for (String var : designNameLog.nameList("src/main/resources/VariableDictionary.txt")) {
						double similarityValue = stringSimilarity.similarity(var.toLowerCase(), word.toLowerCase());

						/*
						 * gets the words thats have similarity value greater than 0.7 and are not of
						 * the same syntax
						 */
						if (similarityValue > 0.7 && !var.equals(word)) {
							logger.warn(String.format("%.3f is the similarity between \"%s\" in the file \"%s\"",
									similarityValue, var + " " + word, file.getCanonicalPath()));
						}
					}
				}

			}
			logger.info("========End variables names=======");
			input.close();
			sourceFolder.delete();
		} catch (IOException ex) {
			logger.error("This is error : " + ex.getMessage());
		}
		assertTrue(true);

	}
}
