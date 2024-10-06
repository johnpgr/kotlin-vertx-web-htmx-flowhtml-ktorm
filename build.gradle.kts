import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("com.google.devtools.ksp") version "1.9.20-1.0.14"
  kotlin("jvm") version "1.9.20"
  application
}

group = "github.johnpgr"

version = "1.0.0-SNAPSHOT"

repositories { mavenCentral() }

val vertxVersion = "4.5.10"
val htmlFlowVersion = "4.6"
val komapperVersion = "3.1.0"
val junitJupiterVersion = "5.9.1"

val mainVerticleName = "experiment.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application { mainClass.set(launcherClassName) }

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  platform("org.komapper:komapper-platform:$komapperVersion").let {
    implementation(it)
    ksp(it)
  }
  implementation("org.komapper:komapper-starter-r2dbc:$komapperVersion")
  implementation("org.komapper:komapper-dialect-postgresql-r2dbc:$komapperVersion")
  ksp("org.komapper:komapper-processor")
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
  implementation("com.github.xmlet:htmlflow:$htmlFlowVersion")
  implementation("org.mindrot:jbcrypt:0.4")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions.jvmTarget = "21"

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest { attributes(mapOf("Main-Verticle" to mainVerticleName)) }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging { events = setOf(PASSED, SKIPPED, FAILED) }
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += listOf("-opt-in=org.komapper.annotation.KomapperExperimentalAssociation")
  }
}

tasks.withType<JavaExec> {
  args =
          listOf(
                  "run",
                  mainVerticleName,
                  "--redeploy=$watchForChange",
                  "--launcher-class=$launcherClassName",
                  "--on-redeploy=$doOnChange"
          )
}
