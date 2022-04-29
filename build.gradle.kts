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


//publishing {
//    publications {
//        create<MavenPublication>(rootProject.name) {
//            artifact(tasks["jar"])
//            artifact(tasks["sourcesJar"])
//            artifact(tasks["javadocJar"])
//
//            repositories {
//                maven {
//                    val releasesRepoUrl = "https://repo.projecttl.net/repository/maven-releases/"
//                    val snapshotsRepoUrl = "https://repo.projecttl.net/repository/maven-snapshots/"
//                    url = uri(
//                        if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl
//                        else releasesRepoUrl
//                    )
//
//                    credentials.runCatching {
//                        username = project.properties["username"] as String?
//                        password = project.properties["password"] as String?
//                    }
//                }
//            }
//
//
//            pom {
//                name.set(rootProject.name)
//                description.set("")
//                url.set("https://github.com/blugon09/${rootProject.name}")
//                developers {
//                    developer {
//                        id.set("blugon09")
//                        name.set("Blugon")
//                        email.set("blugon0921@gmail.com")
//                    }
//                }
//                scm {
//                    connection.set("scm:git:https://github.com/blugon09/${rootProject.name}.git")
//                    developerConnection.set("scm:git:https://github.com/blugon09/${rootProject.name}.git")
//                    url.set("https://github.com/blugon09/${rootProject.name}.git")
//                }
//            }
//        }
//    }
//}