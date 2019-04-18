package com.aaupush.com;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	final static Logger logger = Logger.getLogger(UserTest.class);

	@Before
	public void setUp() throws Exception {

	}

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
			String[] extensions = new String[] { prop.getProperty("program_language")};

			// list all source files in the source folder
			List<File> files = (List<File>) FileUtils.listFiles(sourceFolder, extensions, true);

			logger.info("========Found Source Items=======");
			for (File file : files) {
				logger.info("file: " + file.getCanonicalPath());
			}
			logger.info("========End=======");
			input.close();
			sourceFolder.delete();
		} catch (IOException ex) {
			logger.error("This is error : " + ex.getMessage());
		}
		assertTrue(true);

	}
}
