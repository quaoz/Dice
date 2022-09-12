plugins {
    id("java")
}

group = "com.github.quaoz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.jetbrains:annotations:20.1.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}