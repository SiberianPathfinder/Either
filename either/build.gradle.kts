import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    id("org.jetbrains.dokka") version "1.4.20"
    `maven-publish`
}

group = "com.sibpf.utils"
version = "0.1"

publishing {
    publications {
        create<MavenPublication>("either") {
            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.4.20")
    dokkaJavadocPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.4.20")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}