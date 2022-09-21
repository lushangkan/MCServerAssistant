plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.12.3"

}

group = "cn.cutemc"
version = "0.2.1"

repositories {
    maven ("https://repo.opencollab.dev/maven-snapshots/")
    maven ("https://repo.opencollab.dev/maven-releases/")
    maven("https://maven.aliyun.com/repository/public")
    maven ("https://jitpack.io")
    mavenCentral()
}
dependencies {
    //lombok
    implementation ("org.projectlombok:lombok:1.18.24")
    testImplementation("junit:junit:4.13.1")

    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")

    testCompileOnly ("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.24")

    //MCProtocolLib
    implementation ("com.github.steveice10:mcprotocollib:1.18.2-1")

    //DNS
    implementation ("dnsjava:dnsjava:3.5.1")
}
