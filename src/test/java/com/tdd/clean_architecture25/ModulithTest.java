package com.tdd.clean_architecture25;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;


class ModulithTest {

    // Analyze the module structure based on the application root package
    ApplicationModules modules = ApplicationModules.of(CleanArchitecture25Application.class);

    @Test
    void verifyModulithStructure() {
        // Verify rule:
        // 1. There are no cycles between modules (Circular Dependencies)
        // 2. Modules must not access "internal" packages belonging to other modules

        modules.verify();
    }

    @Test
    void writeDocumentation() {
        // Create an architecture diagram in the target/modulith-docs folder
        // Very useful for technical documentation

        Assertions.assertDoesNotThrow(() ->
                new Documenter(modules).writeModulesAsPlantUml()
        );
    }
}
