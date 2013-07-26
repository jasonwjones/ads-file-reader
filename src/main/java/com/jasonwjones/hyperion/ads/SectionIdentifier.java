package com.jasonwjones.hyperion.ads;

/**
 * Lightweight class used for identifying a section. It consists of the name
 * (the thing on the left side of the equals sign and the exclamation mark, and
 * the type (the thing to the right of the equals sign).
 * 
 * SectionIdentifiers are used keys into the AdsContents section map. You don't
 * need to worry about this per se but just know that the identity of this class
 * is important, hence the hashCode and equality implementation.
 * 
 * @author Jason W. Jones
 * 
 */
public class SectionIdentifier {

	/* Type of the section, typically things like Section, Members, Hierarchies */
	private String type;

	/* The name of the section like Dimensions, DimensionAssociations, etc */
	private String name;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public SectionIdentifier(String type, String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * Just the generated Eclipse hashCode implementation
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Slightly altered Eclipse equals implementation with some craptastic
	 * boilerplate removed. Well, technically it's not crappy but this method
	 * isn't likely to be gangbanged and need to have a couple of extra shortcut
	 * identity checks.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		SectionIdentifier other = (SectionIdentifier) obj;
		return (type.equals(other.type) && name.equals(other.name));
	}

}