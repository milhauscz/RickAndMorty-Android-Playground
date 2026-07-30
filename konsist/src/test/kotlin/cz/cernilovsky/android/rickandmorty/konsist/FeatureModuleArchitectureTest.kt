package cz.cernilovsky.android.rickandmorty.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.ext.list.withPath
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class FeatureModuleArchitectureTest {
    @Test
    fun `api modules only contain domain packages`() {
        Konsist
            .scopeFromProduction()
            .files
            .withPath("/api/src/main/")
            .withPath("/feature/")
            .assertTrue { it.hasPackage("..domain..") }
    }

    @Test
    fun `api modules do not import data ui or di layers`() {
        val forbiddenLayers = listOf("data", "ui", "di")
        Konsist
            .scopeFromProduction()
            .files
            .withPath("/api/src/main/")
            .withPath("/feature/")
            .assertFalse { file ->
                file.importsAnyFeatureLayer(forbiddenLayers)
            }
    }

    @Test
    fun `impl modules do not import foreign feature data or di layers`() {
        FEATURES.forEach { owner ->
            val foreignFeatures = FEATURES.filter { it != owner }
            Konsist
                .scopeFromProduction()
                .files
                .withPath("/feature/$owner/impl/src/main/")
                .assertFalse { file ->
                    foreignFeatures.any { foreign ->
                        file.importsFeatureLayer(foreign, "data") ||
                            file.importsFeatureLayer(foreign, "di")
                    }
                }
        }
    }
}

private fun KoFileDeclaration.importsFeatureLayer(
    feature: String,
    layer: String,
): Boolean =
    imports.any { import ->
        import.hasTextContaining(".$feature.$layer.")
    }

private fun KoFileDeclaration.importsAnyFeatureLayer(layers: List<String>): Boolean =
    FEATURES.any { feature ->
        layers.any { layer -> importsFeatureLayer(feature, layer) }
    }
