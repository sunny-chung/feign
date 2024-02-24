# Feign

This Feign is forked from the official [Feign from OpenFeign](https://github.com/OpenFeign/feign) v13.2.1, with the following change:
- add customizable ContextManipulateProvider for propagating micrometer context

This is specifically made for [spring-starter-feign-coroutine](https://github.com/sunny-chung/spring-starter-feign-coroutine), a Kotlin Coroutine Feign integrated with Spring Boot. It is possible for other projects to use this Feign, because there is no other breaking change.

Only the following artifacts are published under the maven group "io.github.sunny-chung":
- parent
- bom
- core
- kotlin

For other modules, the official maven group "io.github.openfeign" can be used.

## Example Usage

In build.gradle.kts,

```kotlin
val feignCoreVersion = "13.2.1"
dependencies {
    implementation("io.github.sunny-chung:feign-core:$feignCoreVersion-patch-1")
    implementation("io.github.sunny-chung:feign-kotlin:$feignCoreVersion-patch-1") {
        exclude(group = "io.github.openfeign", module = "feign-core")
    }
    implementation("io.github.openfeign:feign-slf4j:$feignCoreVersion") {
        exclude(group = "io.github.openfeign", module = "feign-core")
    }
    implementation("org.springframework.cloud:spring-cloud-openfeign-core:4.0.4") {
        exclude(group = "io.github.openfeign", module = "feign-core")
    }
    implementation("io.github.openfeign:feign-micrometer:$feignCoreVersion") {
        exclude(group = "io.github.openfeign", module = "feign-core")
    }
    // ...
}
```

To build a CoroutineFeign with Micrometer support,

```kotlin
CoroutineFeign.builder<Unit>()
  // ...
  .contextManipulateProvider(object : ContextManipulateProvider {
    val CONTEXT_KEY = "MicrometerContext"
  
    override fun snapshot(context: MutableMap<String, Any>) {
      val snapshot: ContextSnapshot = ContextSnapshotFactory.builder().build().captureAll()
      context[CONTEXT_KEY] = snapshot
    }
  
    override fun restore(context: MutableMap<String, Any>) {
      (context[CONTEXT_KEY] as ContextSnapshot).setThreadLocals()
    }
  })
  .target(type, config.url)
```
