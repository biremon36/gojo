sed -i '/repositories {/a \
    maven { url "https://maven.ladysnake.org/releases" } \
    maven { url "https://maven.wispforest.io" } \
    maven { url "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/" }' build.gradle
sed -i '/dependencies {/a \
    modImplementation "dev.onyxstudios.cardinal-components-api:cardinal-components-base:${project.cardinal_components_version}" \
    modImplementation "dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${project.cardinal_components_version}" \
    modImplementation "io.wispforest:owo-lib:${project.owo_version}" \
    annotationProcessor "io.wispforest:owo-lib:${project.owo_version}" \
    modImplementation "software.bernie.geckolib:geckolib-fabric-1.20.1:${project.geckolib_version}"' build.gradle
