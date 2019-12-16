plugins {
    java
    checkstyle
}

java {
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

checkstyle {
    maxWarnings = 0
}

tasks.withType<JavaCompile> {
    options.isWarnings = true
}
