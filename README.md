# Java to TypeScript (Maven plugin)

![Not maintained](https://img.shields.io/badge/maintained%3F-no-red.svg?style=flat)
[![Dependency Status](https://www.versioneye.com/user/projects/5940500d368b0800700df470/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5940500d368b0800700df470)
[![Known Vulnerabilities](https://snyk.io/test/github/pinguet62/java2typescript-maven-plugin/badge.svg)](https://snyk.io/test/github/pinguet62/java2typescript-maven-plugin)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/63e8913fa0654b1f9c3bcd9ac4f3abba)](https://www.codacy.com/app/pinguet62/java2typescript-maven-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pinguet62/java2typescript-maven-plugin&amp;utm_campaign=Badge_Grade)
[![GitHub Actions](https://github.com/pinguet62/java2typescript-maven-plugin/workflows/CI/badge.svg?branch=master)](https://github.com/pinguet62/java2typescript-maven-plugin/actions?query=workflow%3ACI+branch%3Amaster)
[![codecov.io](https://codecov.io/github/pinguet62/java2typescript-maven-plugin/coverage.svg?branch=master)](https://codecov.io/github/pinguet62/java2typescript-maven-plugin?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/pinguet62/java2typescript-maven-plugin/badge.svg?branch=master)](https://coveralls.io/github/pinguet62/java2typescript-maven-plugin?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pinguet62/java2typescript-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pinguet62/java2typescript-maven-plugin)
[![Javadocs](https://www.javadoc.io/badge/com.github.pinguet62/java2typescript-maven-plugin.svg)](https://www.javadoc.io/doc/com.github.pinguet62/java2typescript-maven-plugin)

Generate your TypeScript DTO classes from Java DTO classes.

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
    <groupId>com.github.pinguet62</groupId>
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
    * Command line argument: `java2typescript.srcDir` (ex: `mvn ... -Djava2typescript.srcDir=...`)
* `tgtDir`  
    The TypeScript DTO target folder (created if doesn't exist)
    * Required: true
    * Command line argument: `java2typescript.tgtDir` (ex: `mvn ... -Djava2typescript.tgtDir=...`)

## Limitations

### Supported

* Generic types
* Dependency (but not checked)

### Not (yet) supported

* Merge: generated file replace old
* Methods: only attributes are parsed
* Included classes & multi-classes: only 1 `public` class per file
* Attribute initialization: convert only declarations
* Advanced primitive types: `Map` is not supported
* JSON field name using @annotation
* Multi-configuration: if you have several DTO packages
* File path/name pattern: actually `{srcDir}/**Dto.java`
