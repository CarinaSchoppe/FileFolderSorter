import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "+"
    //javafx plugin
    id("application")
    id("com.github.johnrengelman.shadow") version "+"
    id("org.openjfx.javafxplugin") version "+"
}

group = "de.carina"
version = "1.0.2"
description = "Carinas Picture sorter"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}
javafx {
    version = "+"
    modules("javafx.controls", "javafx.fxml")

}

tasks {

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(11)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    withType<KotlinCompile>{
        kotlinOptions {
            jvmTarget = "11"
            languageVersion = "2.0"
        }
    }
    test {
        useJUnitPlatform()
    }
}



application{
    mainClass.set("de.carina.filesorter.FileSorterKt")
}
