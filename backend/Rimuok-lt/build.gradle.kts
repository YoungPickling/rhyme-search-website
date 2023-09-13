plugins {
	java
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	id("io.freefair.lombok") version "8.3"
}

group = "lt.rimuok"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_20
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

tasks.withType<JavaExec> {
	systemProperty("file.encoding", "UTF-8")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
//	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok:1.18.28")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	annotationProcessor("org.projectlombok:lombok:1.18.28")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito:mockito-junit-jupiter")
}

tasks.withType<Test> {
	useJUnitPlatform()
}


