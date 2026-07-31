import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import java.util.Properties

internal fun ApplicationExtension.configureReleaseSigning(project: Project) {
    val signingProperties = Properties()
    val keystorePropertiesFile = project.rootProject.file("keystore.properties")
    if (keystorePropertiesFile.exists()) {
        keystorePropertiesFile.inputStream().use { signingProperties.load(it) }
    } else {
        listOf(
            "storeFile" to System.getenv("RELEASE_STORE_FILE"),
            "storePassword" to System.getenv("RELEASE_STORE_PASSWORD"),
            "keyAlias" to System.getenv("RELEASE_KEY_ALIAS"),
            "keyPassword" to System.getenv("RELEASE_KEY_PASSWORD"),
        ).forEach { (key, value) ->
            if (!value.isNullOrBlank()) {
                signingProperties[key] = value
            }
        }
    }

    val storeFilePath = signingProperties.getProperty("storeFile") ?: return
    val storeFile = project.rootProject.file(storeFilePath)
    if (!storeFile.exists()) return

    signingConfigs {
        create("release") {
            this.storeFile = storeFile
            storePassword = signingProperties.getProperty("storePassword")
            keyAlias = signingProperties.getProperty("keyAlias")
            keyPassword = signingProperties.getProperty("keyPassword")
        }
    }
    buildTypes.getByName("release").signingConfig = signingConfigs.getByName("release")
}
