sed -i '/dependencies {/a \
    include "dev.onyxstudios.cardinal-components-api:cardinal-components-base:${project.cardinal_components_version}" \
    include "dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${project.cardinal_components_version}" \
    include "io.wispforest:owo-lib:${project.owo_version}"' build.gradle
