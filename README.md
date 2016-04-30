# Java to TypeScript (Maven plugin)

Generate your TypeScript from Java DTO classes.

## Example

Java DTO class: `src/main/java/fr/pinguet62/java2typescript/sample/dto/MyDto.java`
```java
package fr.pinguet62.java2typescript.sample.dto;

import java.util.List;
import fr.pinguet62.java2typescript.sample.dto.sub.OtherDto;

public class MyDto {
	private int id;
	private List<String> codes;
	private OtherDto other;
	// getter & setter
}
```

Maven plugin:
```xml
<plugin>
	<groupId>fr.pinguet62</groupId>
	<artifactId>java2typescript-maven-plugin</artifactId>
	<version>...</version>
	<configuration>
		<srcDir>src/main/java/fr/pinguet62/java2typescript/sample/dto/</srcDir>
		<tgtDir>src/main/webapp/app/dto/</tgtDir>
	</configuration>
</plugin>
```

Output: `src/main/webapp/app/dto/MyDto.ts`
```typescript
import {OtherDto} from './sub/OtherDto';

export class MyDto {
	id: number;
	codes: Array<string>;
	other: OtherDto;
}
```

## Configuration

* `srcDir`  
	The Java DTO source folder
	* Required: true
	* Command line argument: `java2typescript.srcDir`  
	Example: `mvn ... -Djava2typescript.srcDir=...`
* `tgtDir`  
	The TypeScript DTO target folder (created if doesn't exist)
	* Required: true
	* Command line argument: `java2typescript.tgtDir`  
		Example: `mvn ... -Djava2typescript.tgtDir=...`

## Limitations

### Supported

* Generic types
* Dependency (but not checked)

### Not supported

* Merge: generated file replace old
* Methods: only attributes are parsed
* Included classes & multi-classes: only 1 `public` class per file
* Attribute initialization: convert only declarations
* Advanced primitive types: `Map` is not supported
* JSON field name using @annotation

## Next version...

* Multi-configuration: if you have several DTO packages
* File path/name pattern: actually `{srcDir}/**Dto.java`

___

1. Convert Java file from source folder (not recursive) to target folder
2. Recursive iteration
3. Option: File **filter**  
	Ex: srcDir = `/src/main/java/**.Dto.java`
4. Option: Folder hierarchy  
	srcDir = **`/path/to/src/`**  
	Source file path: **`/path/to/src/`**`sub/folder/file.java`  
	tgtDir = **`/other/path/to/tgt/`**  
	Target file path: **`/other/path/to/tgt/`**`sub/folder/file.java`