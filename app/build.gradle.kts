import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application

    id("net.ltgt.errorprone") version "4.0.1"
}

tasks.withType(JavaCompile::class) {
    options.errorprone {
        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "org.example")
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
// This dependency is used by the application.
    implementation(libs.guava)

    // findbugs / errorprone / nullaway
    annotationProcessor("com.uber.nullaway:nullaway:0.11.0")
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")
    errorprone("com.google.errorprone:error_prone_core:2.28.0")
    errorproneJavac("com.google.errorprone:javac:9+181-r4173-1")

    // +++++++++ tests ++++++++
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    // Define the main class for the application.
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
