import org.gradle.api.tasks.Copy

plugins {
    application
}

val unzipJinput by tasks.registering(Copy::class) {
    from(zipTree(configurations.runtimeClasspath.get().first { it.name.contains("jinput-2.0.9-natives-all") }))
    into(layout.buildDirectory.file("native_libs"))
}

application {
    mainClass = "org.brentwardindustries.main.Main"
    applicationDefaultJvmArgs = listOf("-Djava.library.path=${layout.buildDirectory.dir("native_libs").get().asFile.absolutePath}")
}

group = "org.brentwardindustries"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.java.jinput:jinput:2.0.9")
    implementation("net.java.jinput:jinput:2.0.9:natives-all")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.named<JavaExec>("run") {
    dependsOn(unzipJinput)
    systemProperty("java.library.path", layout.buildDirectory.dir("native_libs").get().asFile.absolutePath)
}

tasks.named("assembleDist") {
    dependsOn(unzipJinput)
}

tasks.named<CreateStartScripts>("startScripts") {
    dependsOn(unzipJinput)
    classpath = files(layout.buildDirectory.dir("native_libs").get().asFile) + classpath as FileCollection
    defaultJvmOpts = listOf("-Djava.library.path=\${APP_HOME}/lib/native_libs")
    doLast {
        // Access Unix start script
        val unixScriptFile = file("$projectDir/build/scripts/${project.name}")
        if (unixScriptFile.exists()) {
            unixScriptFile.writeText(
                unixScriptFile.readText().replace(
                    "-Djava.library.path=\\\${APP_HOME}/lib/native_libs",
                    "-Djava.library.path=\'\${APP_HOME}\'/lib/native_libs"
                )
            )
        }

        // Access Windows start script
        val windowsScriptFile = file("$projectDir/build/scripts/${project.name}.bat")
        if (windowsScriptFile.exists()) {
            windowsScriptFile.writeText(
                windowsScriptFile.readText().replace(
                    "-Djava.library.path=\${APP_HOME}/lib/native_libs",
                    "-Djava.library.path=\"%APP_HOME%\\lib\\native_libs\""
                )
            )
        }
    }
}

tasks.named<Zip>("distZip") {
    dependsOn(unzipJinput)
    from(layout.buildDirectory.dir("native_libs").get().asFile) {
        into("${project.name}-${project.version}/lib/native_libs")
    }
}

tasks.named<Tar>("distTar") {
    dependsOn(unzipJinput)
    from(layout.buildDirectory.dir("native_libs").get().asFile) {
        into("${project.name}-${project.version}/lib/native_libs")
    }
}

tasks.test {
    useJUnitPlatform()
}
