# Learning Spring Boot

## Auth0 For Authentication
 Application based on Auth0 API (set to Single Page Application).

Permissions for Users include:
- `<read/delete/add>:employees`
- `<read/delete/add>:places`

See `.env.example` for env requirements.

## Gradle Commands
When updating dependencies in `build.gradle`
## Rebuild
`./gradlew build`
## Rebuild, skip tests
`./gradlew build -x test`

## Intellij
Reset IDE cache at `File > Invalidate Caches > Check all > Invalidate and Restart`

## Docker Hot Reload
Using volumes and spring dev dependency made hot reloading difficult, but if running api in docker, api can be quickly reloaded with `reload fast with docker exec spb_ang_pg-api-1 ./gradlew compileJava --no-daemon`

## Docker for Testing
An integration test requires Docker to be running in the background, as it creates a temporary postgres container