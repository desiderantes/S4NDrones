plugins {
    kotlin("jvm") version "1.3.72"
    application
}

group = "com.desiderantes"
version = "1.0-RELEASE"

val spekVersion = "2.0.9"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt:clikt:2.6.0")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testImplementation(kotlin("test"))
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly(kotlin("reflect"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClass.set("com.desiderantes.s4ndrones.MainKt")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}