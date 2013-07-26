package com.jasonwjones.hyperion.ads;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AdsContentsTest {

	private AdsContents ads;

	@Before
	public void setUp() throws Exception {
		ads = AdsContents.create(getClass().getResourceAsStream("/scenario"));
	}

	@Test
	@Ignore
	public void testSectionIteration() {
		Section sectionDimensions = ads.getSection("Section", "DimensionAssociations");
		for (Map<String, String> member : sectionDimensions) {
			System.out.println("Member: " + member);
		}
	}

	@Test
	@Ignore
	public void testCompact() {
		AdsContents scenario = AdsContents.create(getClass().getResourceAsStream("/scenario"));
		for (Map<String, String> line : scenario.getSection("Section", "DimensionAssociations")) {
			System.out.println("Line: " + line);
		}
	}

	@Test
	@Ignore
	public void testContentsIteration() {
		for (Section section : ads) {
			System.out.println(section);
			for (Map<String, String> item : section) {
				System.out.println(item);
			}
		}
	}

}
