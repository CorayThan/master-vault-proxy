import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
}

group = "coraythan"
version = "7"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.register("copyVersion") {
	doLast {
		File("$projectDir", "version.txt").writeText("$version")
		val dockerrunContents = File("$projectDir/docker", "Dockerrun.aws.json.template")
			.readText()
			.replace("\$version", "$version")
		File("$projectDir/docker", "Dockerrun.aws.json")
			.writeText(dockerrunContents)
	}
}

tasks.register<Copy>("dockerCopyJar") {
	from(File("build/libs/master-vault-proxy-$version.jar"))
	rename("master-vault-proxy-$version.jar", "mvproxy.jar")
	into(File("./docker"))
	include("*.jar")
}
