package com.aaupush.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

public class DesignNameLog {
	final static Logger logger = Logger.getLogger(UserTest.class);

	// returns words from a source program
	public ArrayList<String> nameList(String filePath) {
		ArrayList<String> result = new ArrayList<String>();
		try {
			// reading the file which contains the design variables list
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			while (br.ready()) {
				result.add(br.readLine());
			}

			br.close();

		} catch (FileNotFoundException e) {
			logger.error("This is FileNotFoundException error : " + e.getMessage());

		} catch (IOException e) {
			logger.error("This is IOException error : " + e.getMessage());

		}

		return result;
	}

	// returns words from a source program
	public ArrayList<String> getNames(String filePath) {
		ArrayList<String> wordList = new ArrayList<String>();
		try {
			CompilationUnit compilationUnit = StaticJavaParser.parse(this.getFilestring(filePath));
			List<TypeDeclaration<?>> vars = compilationUnit.getTypes();
			for (TypeDeclaration type : vars) {
				List<BodyDeclaration> members = type.getMembers();
				for (BodyDeclaration member : members) {
					if (member instanceof FieldDeclaration) {
						FieldDeclaration myType = (FieldDeclaration) member;
						List<VariableDeclarator> myFields = myType.getVariables();
						for (VariableDeclarator var : myFields) {
							wordList.add(var.toString());
						}
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wordList;
	}

	private String getFilestring(String filePath) {
		byte[] encoded = null;

		try {
			encoded = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(encoded);
	}
}
