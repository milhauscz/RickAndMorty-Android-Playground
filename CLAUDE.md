# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Native Android (Jetpack Compose) app browsing the [Rick and Morty API](https://rickandmortyapi.com/).
Ported from a sibling Kotlin Multiplatform project (`cz.cernilovsky.kmp.rickandmorty`) to serve as a
playground for Android-only APIs the multiplatform stack doesn't expose — starting with
`NavigableListDetailPaneScaffold`. Modularized, offline-first (Room is the single source of truth),
Now-in-Android-style architecture. See `README.md` for the feature overview and module graph.

## Commands

Run Gradle with a plain `./gradlew` (on Windows `.\gradlew`) invocation — no `cd`, redirects, or pipes.

```bash
# Tests — unit + Robolectric/Compose UI, all on the JVM host, across every module
./gradlew test
./gradlew :feature:characters:impl:test                       # single module
./gradlew :feature:characters:impl:test --tests "*CharactersViewModelTest*"   # single test

# Code quality
./gradlew formatKotlin          # auto-fix formatting
./gradlew lintKotlin detekt     # verify style (kotlinter + detekt)
./gradlew :konsist:test         # architecture / layer checks
./gradlew konsistCheck          # alias for :konsist:test

# App
./gradlew :app:installDebug
```

Requirements: JDK 17+, Android SDK (compileSdk 37).

### Verifying a change

A full `./gradlew build` fails on pre-existing detekt/kotlinter debt, not just your change. To verify
your own work, run the relevant module's `test` (add `-x lintKotlin -x detekt` if you need `build` but
want to skip the style gates).

## Architecture

### Modules

Every module's build script is minimal because shared setup lives in the `build-logic` included build
as precompiled convention plugins. To change how modules are built (Android target config, Compose,
Koin, Room, lint), edit the plugin in `build-logic/convention/src/main/kotlin/*.gradle.kts`, not the
individual modules.

- `rickandmorty.android.library` — Android library target, namespace, unit tests, lint.
- `rickandmorty.android.feature` — the above + Compose, Koin, lifecycle (for UI **impl** modules).
- `rickandmorty.android.compose` — Compose (BOM + core artifacts). Applied *after* the base Android
  plugin (`rickandmorty.android.library` or `rickandmorty.android.application`) in every consumer —
  it configures `android { }` via AGP's `CommonExtension` rather than declaring its own
  `com.android.library`/`com.android.application`, so it doesn't conflict with either.
- `rickandmorty.android.application` — for `:app`: application id, versioning defaults, packaging.
- `rickandmorty.room` — Room + KSP wiring. **KSP/Room runs only in `:core:database`.**
- `rickandmorty.lint` — kotlinter + detekt (detekt config at `config/detekt/detekt.yml`).

Android namespaces are derived from the module path (see `ProjectExtensions.kt`), e.g.
`:core:database` → `cz.cernilovsky.android.rickandmorty.core.database`. All code lives under the
`cz.cernilovsky.android.rickandmorty` package base.

`:app` is the umbrella module: `App` composable, type-safe navigation `Routes`, `MainActivity`, and the
`initKoin` aggregation that wires every module's Koin module together (`app/.../di/Module.kt`).

Each feature is split into `:feature:<name>:api` and `:feature:<name>:impl`:

| Submodule | Contents |
| --- | --- |
| **api** | Domain models and repository interfaces (`rickandmorty.android.library`) |
| **impl** | Data layer, Koin module, and — for characters — use cases + Compose UI (`rickandmorty.android.feature`) |

`:feature:characters:impl` depends only on **api** modules of episode and location (never their impl).
`:app` wires all **impl** modules in `initKoin`.

### Layering inside a feature

Each feature is split into `data / domain / ui` packages with unidirectional MVVM:

```
ui (Compose screen) → ViewModel (StateFlow / Paging flow) → UseCase
  → Repository (interface in domain, impl in data)
    → Remote data source (Ktor) → API
    └ Local data source (Room DAO) → SQLite   ◄── single source of truth
```

- Interfaces live in `domain` (e.g. `CharactersRepository`); implementations live in `data`, suffixed
  `Impl` (e.g. `CharactersRepositoryImpl`) or `KtorImpl` for the Ktor-backed remote data sources (e.g.
  `CharactersDataSourceKtorImpl`). Koin binds impl to interface (`... bind CharactersRepository::class`).
- The character list uses a Paging 3 `RemoteMediator`: the UI observes a `PagingSource` over Room while
  the mediator fetches from the network and writes into the database on demand.
- Each feature owns a `di/<Feature>Module.kt` Koin module; add it to `initKoin` in `:app` when creating
  a new feature.

### Resource ownership

With `android.nonTransitiveRClass=true`, each module's `R` class only sees its own resources — cross-
module string lookups need an explicit import (and an alias when both modules' `R` classes are in
scope), e.g. `cz.cernilovsky.android.rickandmorty.core.designsystem.R`. `:core:designsystem` owns the
error strings + fonts; `:feature:characters:impl` owns everything else it renders.

### Tests

Unit tests (pure JVM) and Robolectric/Compose UI tests both live under `src/test/`, run entirely on the
JVM host via `./gradlew test`. Fakes/fixtures for shared use live alongside the tests that need them
(e.g. `CharacterFixtures.kt`, `FakeRepositories.kt` in `:feature:characters:impl`).

### Known deviations from the KMP source project

- `data class BuildConfig(isDebug)` was renamed to `AppBuildConfig` in `:core:common` to avoid reading
  as the AGP-generated `BuildConfig` class.
- Material3's `HorizontalUncontainedCarousel` (episode carousel) is replaced with a plain `LazyRow`:
  its opt-in marker (`ExperimentalMaterial3ExpressiveApi`) compiles as Kotlin-internal in the resolved
  material3 artifact and isn't usable from outside the module.
- The `headlineSmallEmphasized`/`labelMediumEmphasized` typography variants used in the KMP UI aren't
  part of this Compose BOM's public `Typography` surface; the character list card uses the plain
  `headlineSmall`/`labelMedium` styles instead.
