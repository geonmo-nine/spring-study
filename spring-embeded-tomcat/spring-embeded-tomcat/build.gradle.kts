plugins {
    id("java")
}

group = "hello"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 스프링 mvc 추가
    implementation("org.springframework:spring-webmvc:6.0.4")
    // 내장 톰캣 추가
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.5")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("buildJar") {
    manifest {
        attributes(
            "Main-Class" to "hello.embeded.EmbededTomcatSpringMain" // Replace with your main class
        )
    }
}