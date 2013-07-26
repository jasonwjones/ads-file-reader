package com.jasonwjones.hyperion.ads;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AdsContents models the entire contents of an ADS file. This includes any
 * parameters in the top section such as the file format/version and all of the
 * sections within the file.
 * 
 * @author Jason W. Jones
 * 
 */
public class AdsContents implements Iterable<Section> {

	private Map<SectionIdentifier, Section> sections;

	private Map<String, String> headers;

	private final static List<String> HEADERS = Arrays.asList("FILE_FORMAT", "VERSION");

	public static AdsContents create(InputStream inputStream) {
		AdsFileReader reader = new AdsFileReader();
		return reader.read(inputStream);
	}

	public static AdsContents create(String filename) throws FileNotFoundException {
		AdsFileReader reader = new AdsFileReader();
		return reader.read(filename);
	}

	public AdsContents() {
		sections = new LinkedHashMap<SectionIdentifier, Section>();
		headers = new HashMap<String, String>();
	}

	public AdsContents(List<Section> sectionsToAdd) {
		this();
		for (Section section : sectionsToAdd) {
			addSection(section);
		}
	}

	public void addSection(Section section) {
		SectionIdentifier id = section.getSectionIdentifier();
		if (HEADERS.contains(id.getType())) {
			headers.put(id.getType(), id.getName());
		} else {
			sections.put(id, section);
		}
	}

	public Section getSection(String type, String name) {
		SectionIdentifier sectionIdentifier = new SectionIdentifier(type, name);
		return sections.get(sectionIdentifier);
	}

	public Set<Section> getSectionsWithType(String type) {
		Set<Section> sectionsWithType = new HashSet<Section>();
		for (Map.Entry<SectionIdentifier, Section> sectionEntry : sections.entrySet()) {
			if (sectionEntry.getKey().getType().equalsIgnoreCase(type)) {
				sectionsWithType.add(sectionEntry.getValue());
			}
		}
		return sectionsWithType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Iterator<Section> iterator() {
		return sections.values().iterator();
	}

	@Override
	public String toString() {
		return "AdsContents [headers=" + headers + ", section-count=" + sections.size() + "]";
	}

}
