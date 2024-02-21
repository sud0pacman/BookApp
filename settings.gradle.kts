pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = "BookApp"
include(":app")
