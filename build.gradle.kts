plugins {
    java
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

val projectGroup: String by project
val projectVersion: String by project
val projectDescription: String by project

group = projectGroup
version = projectVersion
description = projectDescription

tasks.withType(Javadoc::class) {
    setFailOnError(false)
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes(
            "Main-Class" to "${projectGroup}.Driver",
            "Built-By" to System.getProperty("user.name"),
            "Created-By" to "Gradle ${gradle.gradleVersion}",
            "Build-Jdk" to JavaVersion.current()
        )
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar")}.map { zipTree(it)}
    })
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
