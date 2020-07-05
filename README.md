# Adapters for Gson JSON library

![Webp net-resizeimage](https://user-images.githubusercontent.com/7099230/86540678-cf351a80-bf0f-11ea-873d-a334d006dd65.png)

![Java CI with Maven](https://github.com/iSharipov/gson-adapters/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
<a href="https://codecov.io/gh/iSharipov/gson-adapters">
  <img src="https://codecov.io/gh/iSharipov/gson-adapters/branch/master/graph/badge.svg" />
</a>
<a href="https://lgtm.com/projects/g/iSharipov/gson-adapters/alerts/">
    <img alt="Total alerts" src="https://img.shields.io/lgtm/alerts/g/iSharipov/gson-adapters.svg?logo=lgtm&logoWidth=18"/>
</a>
<a href="https://lgtm.com/projects/g/iSharipov/gson-adapters/context:java">
    <img alt="Language grade: Java" src="https://img.shields.io/lgtm/grade/java/g/iSharipov/gson-adapters.svg?logo=lgtm&logoWidth=18"/>
</a>
<br />
[![LinkedIn][linkedin-shield]][linkedin-url]

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/iSharipov

## Why use Gson adapters?

Sometimes we need to deserialize a JSON to a concrete class which inherits from an abstract class.

![Alt text](/uml.png?raw=true "UML class diagram")

`Gson + Retrofit + Wiremock example - io.github.isharipov.gson.adapters.PolymorphDeserializerTest`

Library provides a set of annotations you can apply to abstract class.
Also, library contains Polymorph Deserializer which do the trick. 

To tell the deserializer which implementation to use to deserialization, annotate abstract class with @JsonType annotation and provide information about subtipes through set of @JsonSubtype annotations.

```java
@JsonType(
        property = "type",
        subtypes = {
                @JsonSubtype(clazz = CommissionEmployee.class, name = "commission"),
                @JsonSubtype(clazz = HourlyEmployee.class, name = "hourly"),
                @JsonSubtype(clazz = SalariedEmployee.class, name = "salaried")
        }
)
public abstract class Employee {
    private final String id;
    private final String type;
...    
```

```java
Gson gson = new GsonBuilder()
                .registerTypeAdapter(Employee.class, new PolymorphDeserializer<Employee>())
                .create();
```
Pay attention, that in each implementation you should have to have a magic field, based on which, the Deserializer will determine the final implementation.

### Gradle
```groovy
dependencies {
    implementation("io.github.isharipov:gson-adapters:0.1")
}
```

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>io.github.isharipov</groupId>
        <artifactId>gson-adapters</artifactId>
        <version>0.1</version>
    </dependency>
<dependencies>
```

## Requirements
Library requires Java 1.8 or later.