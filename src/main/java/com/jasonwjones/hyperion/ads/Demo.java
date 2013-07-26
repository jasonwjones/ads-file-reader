package com.jasonwjones.hyperion.ads;

import java.util.Map;

/**
 * Just a lightweight class for showing off the app in case someone runs the JAR
 * file. Normally this app will be used as a library in a Java project.
 * 
 * @author Jason W. Jones
 * 
 */
public class Demo {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("This program is meant to be used as a library in a project, ");
			System.out.println("but you can run this program with the name of an ADS file for a demo.");
		} else {
			try {
				AdsContents contents = AdsContents.create(args[1]);
				demoIteration(contents);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	public static void demoIteration(AdsContents contents) {
		for (Section section : contents) {
			System.out.println(section);
			for (Map<String, String> line : section) {
				System.out.println(line);
			}
			System.out.println();
		}
	}

}
