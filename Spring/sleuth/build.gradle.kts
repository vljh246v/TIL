

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

		plugins {
			id("org.springframework.boot") version "2.7.11-SNAPSHOT"
			id("io.spring.dependency-management") version "1.0.15.RELEASE"
			kotlin("jvm") version "1.6.21"
			kotlin("plugin.spring") version "1.6.21"
		}

group = "com.demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2021.0.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
//	implementation("org.springframework.cloud:spring-cloud-starter-zipkin")
	implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
//	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}