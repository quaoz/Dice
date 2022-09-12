plugins {
    id("java")
}

group = "com.github.quaoz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:23.0.0")
}

tasks.withType<Jar> {
    manifest {
        attributes("Main-Class" to "com.github.quaoz.Main")
    }

    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
