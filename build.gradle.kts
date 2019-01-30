plugins {
    java
    application
    groovy
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:26.0-jre")
    testImplementation("org.codehaus.groovy:groovy-all:2.5.4")
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "javaExamples.Application"
}
