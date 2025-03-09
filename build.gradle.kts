plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ktor)
  alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.csgames"

version = "0.0.1"

val ktorVersion = "3.1.1"

application {
  mainClass = "io.ktor.server.netty.EngineMain"

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories { mavenCentral() }

dependencies {
  implementation(libs.ktor.server.default.headers)
  implementation(libs.ktor.server.cors)
  implementation(libs.ktor.server.core)
  implementation(libs.ktor.server.host.common)
  implementation(libs.ktor.error.mapper)
  implementation(libs.ktor.server.resources)
  implementation(libs.ktor.server.netty)
  implementation(libs.logback.classic)
  implementation(libs.ktor.server.config.yaml)
  testImplementation(libs.ktor.server.test.host)
  testImplementation(libs.kotlin.test.junit)
  implementation(libs.exposed.core)
  implementation(libs.exposed.jdbc)
  implementation(libs.h2)
  implementation(libs.ktor.server.content.negotiation)
  implementation(libs.ktor.serialization.kotlinx.json)
  implementation(libs.ktor.server.html.builder)
  implementation(libs.kotlinx.html)
  implementation(libs.ktor.server.webjars)
  implementation(libs.ktor.server.compression)
  implementation(libs.ktor.server.ratelimit)

  implementation("org.webjars.npm:htmx.org:2.0.4")
  implementation("org.jetbrains.kotlin-wrappers:kotlin-css:2025.3.4")
}
