package com.jasonwjones.hyperion.ads;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AdsFileReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@Ignore
	public void testRead() {
		AdsFileReader reader = new AdsFileReader();
		InputStream scenarios = getClass().getResourceAsStream("/scenario");
		AdsContents contents = reader.read(scenarios);

		Section scenario = contents.getSection("Section", "Scenario");
		int lines = scenario.getCountOfLines();
		System.out.println("Scenario directive has " + lines + " lines");
	}
}
