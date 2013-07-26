package com.jasonwjones.hyperion.ads;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Takes care of parsing the input file or stream. Handles recognizing sections,
 * line continuations, and so on. The parsing logic is intentionally kept in
 * this class in order to keep the
 * 
 * @author Jason W. Jones
 * 
 */
public class AdsFileReader {

	public AdsContents read(String filename) throws FileNotFoundException {
		return readSections(preprocessFile(filename));
	}

	public AdsContents read(InputStream inputStream) {
		return readSections(preprocessStream(inputStream));
	}

	/**
	 * Reads the directives from the file
	 * 
	 * @param lines
	 * @return
	 */
	private AdsContents readSections(List<String> lines) {
		List<Section> directives = new ArrayList<Section>();
		Section currentDirective = null;

		for (String line : lines) {
			if (line.startsWith("!")) {
				if (currentDirective != null) {
					directives.add(currentDirective);
				}
				currentDirective = new Section(line);
			} else {
				currentDirective.addLine(line);
			}
		}
		directives.add(currentDirective);
		return new AdsContents(directives);
	}

	/**
	 * Read through the entire file and do preprocessing. This handles reading
	 * in the lines that have a continuation character at their end.
	 * 
	 * @param filename The name of the file to read
	 * @return a list of strings in the file with contiuned lines merged into a
	 *         single string
	 */
	private List<String> preprocessFile(String filename) throws FileNotFoundException {
		List<String> lines = new ArrayList<String>();

		Scanner scanner = new Scanner(new File(filename));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			while (line.endsWith("_")) {
				line += scanner.next();
			}
			if (!line.trim().isEmpty()) {
				lines.add(line);
			}
		}

		return lines;
	}

	private List<String> preprocessStream(InputStream inputStream) {
		List<String> lines = new ArrayList<String>();

		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			while (line.endsWith("_")) {
				line += scanner.next();
			}
			if (!line.trim().isEmpty()) {
				lines.add(line);
			}
		}

		return lines;
	}

}
