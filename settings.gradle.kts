import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        google() // 필터 없이 전체 허용
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BeingGood"
include(":app")
 