## ADS File Reader

ADS File Reader is a compact Java library for… you guessed it, reading ADS files. What is an ADS file? Well, it's a weird little format that you might run into if you work with Hyperion and use LCM to export dimensions out of EPMA.

### ADS File Format

An ADS file usually looks like the following (although the names in the sections and the fields/values are different, of course):

```
!FILE_FORMAT=ADS
!VERSION=1.0

!Section=Dimensions
'Name|DimensionClass|ApplySecurity|AttributeDataType
Scenario|Scenario|True|Text

!Section=DimensionAssociations
'BaseDimension|Property|TargetDimension
Scenario|Alias|Alias
Scenario|StartYear|Year
Scenario|EndYear|Year
Scenario|StartPeriod|Period
Scenario|EndPeriod|Period

!Members=Scenario
'Name|Alias|DataType|Description|EnableProcessManagement|EndPeriod
Current||Unspecified||True|Dec
Plan||Unspecified||True|Dec
Forecast||Unspecified||True|Dec
Actual||Unspecified||False|Jun
```

I don't know if there's an actual spec for the format but key elements seem to be this:

 * A couple of `PROPERTY=VALUE` fields in the header, namely `FILE_FORMAT` and `VERSION`.
 
 * An arbitrary number of sections. Each section has a header, definition, and data. The header is a bang followed by a "type" and a "name". In the above example, the first section is said to have a type of Section and a name of Dimensions. The column spec starts with an apostrophe and is delimited with pipes. The data follows and is just delimited with pipes.
 
### Example

The purpose of the library is to make getting the data out of an ADS file and into a Java program (so you can do whatever it is you need to do with it) as easy as humanly possible. To that end, reading in the data a breeze. Check this awesomeness out:

```
AdsContents scenario = AdsContents.create("scenario.ads");

for (Map<String, String> line : scenario.getSection("Section", "DimensionAssociations")) {
	System.out.println("Line: " + line);
}
```

And get this output:

```
Line: {'BaseDimension=Scenario, Property=Alias, TargetDimension=Alias}
Line: {'BaseDimension=Scenario, Property=StartYear, TargetDimension=Year}
Line: {'BaseDimension=Scenario, Property=EndYear, TargetDimension=Year}
Line: {'BaseDimension=Scenario, Property=StartPeriod, TargetDimension=Period}
Line: {'BaseDimension=Scenario, Property=EndPeriod, TargetDimension=Period}
```

Holy cow! Pure awesomeness. What is going on?

 1. AdsContents is a class that models the entire contents of the ADS file. This is not a streaming API – the entire thing goes into memory all at once. In practice this shouldn't be an issue.
 
 2. AdsContents has a static method `create()` that can build an `AdsContents` object from a filename String or an InputStream. Internally this just builds and calls the `AdsFileReader` class which is normally used for this (but the static convenience method makes our lives easier in many use cases)
 
 3. AdsContents has a `getSection(String type, String name)` method that returns a `Section` object. The type and name correspond to the values after the ! in the ADS file. An ADS file can have multiple sections with the same type but can't have multiple sections with the same type *and* name. This is why we need to specify both. There's also a convenience method for getting a set of sections with a given type.
 
 4. A Section object implements the Iterable interface for type of Map<String, String>. The Map is constructed using the field specs in the section and the data. For example, the Section/DimensionAssociations section has fields BaseDimension, Property, TargetDimension in this case. So a Map will be constructed where the key is the field name and the value is the value for a given line. 
 
 5. In the example code we are just printing the Map itself which is why we get the {… stuff …} lines since that's how Map prints out. But if we needed to access the value for the BaseDimension property we could just call line.get("BaseDimension") and we'd have it.
 
### Misc

Only reading ADS files is supported at this time – not writing them.

AdsContents has a few other convenience methods and also implements an Iterable interface for Sections, so the process of reading through a file programmatically is really simple.

### License

Licensed under Apache Software License version 2.0