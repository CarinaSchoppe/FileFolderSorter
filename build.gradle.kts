import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "+"
    //javafx plugin
    id("application")
    id("com.github.johnrengelman.shadow") version "+"
    id("org.openjfx.javafxplugin") version "+"
}

group = "de.carina"
version = "1.0"
description = "Carinas Picture sorter"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-imaging:+")
    testImplementation(kotlin("test"))
}
javafx {
    version = "+"
    modules("javafx.controls", "javafx.fxml")

}

tasks {

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(8)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    withType<KotlinCompile>{
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xuse-k2",
                "-Xjdk-release=1.8"
            )
            jvmTarget = "1.8"
            languageVersion = "1.7"
        }
    }
    test {
        useJUnitPlatform()
    }
}



application{
    mainClass.set("de.carina.filesorter.FileSorterKt")
}