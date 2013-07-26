package com.jasonwjones.hyperion.ads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A Section models a section within an ADS file, such as !Section=Foo and the
 * contents below it.
 * 
 * @author Jason W. Jones
 * 
 */
public class Section implements Iterable<Map<String, String>> {

	private static class SectionIterator implements Iterator<Map<String, String>> {

		private Section section;
		private int iteratorIndex = 1;

		public SectionIterator(Section section) {
			this.section = section;
		}

		public boolean hasNext() {
			return iteratorIndex < section.getCountOfLines();
		}

		public Map<String, String> next() {
			String headerLine = section.getFirst();
			String line = section.getLines().get(iteratorIndex);

			String[] headers = headerLine.split("\\|");
			String[] fields = line.split("\\|");

			Map<String, String> result = new LinkedHashMap<String, String>(headers.length);
			for (int index = 0; index < headers.length; index++) {
				result.put(headers[index], fields[index]);
			}
			iteratorIndex++;
			return result;
		}

		public void remove() {
		}

	}

	private SectionIdentifier id;

	private List<String> lines;

	public Section(String line) {
		if (!line.startsWith("!")) {
			throw new IllegalArgumentException("Sections must start with an exclamation. (!)");
		} else {
			String removedEx = line.substring(1);
			String vals[] = removedEx.split("=");
			id = new SectionIdentifier(vals[0], vals[1]);
		}
		lines = new ArrayList<String>();
	}

	public SectionIdentifier getSectionIdentifier() {
		return id;
	}

	public void addLine(String line) {
		lines.add(line);
	}

	public List<String> getLines() {
		return lines;
	}

	public int getCountOfLines() {
		return lines.size();
	}

	public String getFirst() {
		return lines.get(0);
	}

	public List<String> getAllButFirst() {
		if (lines.size() > 1) {
			return lines.subList(1, lines.size());
		} else {
			return Collections.emptyList();
		}
	}

	public Iterator<Map<String, String>> iterator() {
		return new SectionIterator(this);
	}

	@Override
	public String toString() {
		return "Section [type=" + id.getType() + ", name=" + id.getName() + ", lines=" + lines.size() + "]";
	}

}
