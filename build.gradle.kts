plugins {
    kotlin("jvm") version "1.6.20"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
}

group = "kr.blugon"
version = "1.0.0"


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.20")
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }

    jar {
        archiveVersion.set(project.version.toString())
        archiveBaseName.set(project.name)
//        archiveFileName.set("${project.name}-${project.version}-all.jar")
        archiveFileName.set("${project.name}-${project.version}.jar")
        from(sourceSets["main"].output)
    }

//    create<Jar>("sourcesJar") {
//        archiveClassifier.set("sources")
//        from(sourceSets["main"].allSource)
//    }
//
//    create<Jar>("javadocJar") {
//        archiveClassifier.set("javadoc")
//        dependsOn("dokkaHtml")
//        from("$buildDir/dokka/html")
//    }
}