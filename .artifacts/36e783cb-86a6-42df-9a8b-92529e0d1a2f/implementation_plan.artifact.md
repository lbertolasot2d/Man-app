# Fix Kotlin Version Mismatch and Room Compilation Errors

This plan addresses the `Unresolved reference` errors in generated Room code and the Kotlin metadata version mismatch by aligning the project's Kotlin and KSP versions with the latest stable releases compatible with the dependencies.

## User Review Required

> [!IMPORTANT]
> The project currently uses Kotlin 2.2.10, but dependencies (like the standard library or Room runtime) appear to be pulling in or requiring Kotlin 2.4.x metadata. I am upgrading the Kotlin version to **2.4.10** and KSP to **2.3.11** to resolve this mismatch.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/lbert/AndroidStudioProjects/Manapp/gradle/libs.versions.toml)
- Update `kotlin` version from `2.2.10` to `2.4.10`.
- Update `ksp` version from `2.2.10-2.0.2` to `2.3.11`.
- Update `serialization` version from `1.7.3` to `1.11.0` for compatibility with Kotlin 2.4.

---

## Verification Plan

### Automated Tests
1. **Gradle Sync**: Run `gradle_sync` to ensure all dependencies are resolved.
2. **Clean Build**: Run `./gradlew clean :app:assembleDebug` to verify that the Room generated code compiles without "Unresolved reference" errors.
3. **KSP Verification**: Confirm that `AppDatabase_Impl.kt` is correctly generated and imports `mutableListOf`, `lazy`, etc., correctly.

### Manual Verification
1. Inspect the generated `AppDatabase_Impl.kt` file after the build to ensure the imports are resolved.
