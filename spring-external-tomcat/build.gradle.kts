plugins {
    id("java")
    id("war")
}

group = "advanced"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.springframework:spring-webmvc:6.1.13")
}

tasks.test {
    useJUnitPlatform()
}