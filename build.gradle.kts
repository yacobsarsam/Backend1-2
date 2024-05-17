plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.rabbitmq:amqp-client:5.14.0")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")

    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.0")
// https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient

    implementation ("org.apache.httpcomponents:httpclient:4.5.14")

    //testImplementation ("org.mockito:mockito-core:3.+")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
val integrationTestTask = tasks.register<Test>("integrationTest") {
    group = "verification"
    filter {
        includeTestsMatching("*IT")
    }
}

tasks.test{
    filter{
        includeTestsMatching("*Tests")

    }
}

