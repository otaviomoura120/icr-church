package com.devhouse.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.devhouse", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule core_should_not_depend_on_adapters = noClasses()
            .that().resideInAPackage("com.devhouse.core..")
            .should().dependOnClassesThat()
            .resideInAPackage("com.devhouse.adapters..")
            .because("The core should not depend on adapters.");


    @ArchTest
    static final ArchRule inbound_adapters_should_not_depend_on_outbound_adapters = noClasses()
            .that().resideInAPackage("com.devhouse.adapters.inbound..")
            .should().dependOnClassesThat()
            .resideInAPackage("com.devhouse.adapters.outbound..")
            .because("Inbound ports should not depend on outbound ports.");

    @ArchTest
    static final ArchRule core_services_should_not_be_called_directly_by_adapters = noClasses()
            .that().resideInAPackage("com.devhouse.adapters..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("com.devhouse.core.services..")
            .because("Services cannot be called directly, use a port.");

}
