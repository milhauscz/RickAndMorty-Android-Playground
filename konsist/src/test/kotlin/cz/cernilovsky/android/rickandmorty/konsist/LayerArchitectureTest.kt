package cz.cernilovsky.android.rickandmorty.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class LayerArchitectureTest {
    @Test
    fun `clean architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                val domain = Layer("Domain", "$ROOT_PACKAGE..domain..")
                val data = Layer("Data", "$ROOT_PACKAGE..data..")
                val ui = Layer("UI", "$ROOT_PACKAGE..ui..")
                val di = Layer("DI", "$ROOT_PACKAGE..di..")

                domain.dependsOnNothing()
                data.dependsOn(domain)
                ui.dependsOn(domain)
                di.dependsOn(domain)
                di.dependsOn(data)
            }
    }
}
