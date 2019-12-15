plugins {
    id("java")
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("http://repo.maven.apache.org/maven2")
    }
}

dependencies {
    implementation("commons-cli:commons-cli:1.4")
    implementation("jline:jline:2.11")
    implementation("org.json:json:20180813")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
}

group = "com.tigergraph.client"
version = "2.5.0"
description = "Provides a command line interface for interacting with the Tigergraph server."

java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    publications.withType<MavenPublication> {
    //    artifact(sourcesJar.get())

        pom {
            name.set(project.name)
            description.set(project.description)
        }

    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
