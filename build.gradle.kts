plugins {
    java
    `maven-publish`
    checkstyle
}

repositories {
    mavenLocal()
    maven {
        url = uri("http://repo.maven.apache.org/maven2")
    }
}

dependencies {
    implementation("commons-cli:commons-cli:${Versions.commonsCli}")
    implementation("jline:jline:${Versions.jline}")
    implementation("org.json:json:${Versions.json}")
    implementation("javax.xml.bind:jaxb-api:${Versions.jaxbApi}")
}

java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

checkstyle {
    // maxWarnings = 0
}

val projectGroup: String by project
val projectVersion: String by project
val projectDescription: String by project

group = projectGroup
version = projectVersion
description = projectDescription

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-noframes-severity-sorted.xsl")
    }
}

tasks.withType(Javadoc::class) {
    setFailOnError(false)
}

tasks.withType<Jar> {
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

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    // options.isWarnings = true
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
