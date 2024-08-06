package io.agamivriddhi.base;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = BaseApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    private static final String CONTROLLER = "Controller";
    private static final String SERVICE = "Service";
    private static final String CONTROLLER_PACKAGE = "..controller..";
    private static final String SERVICE_PACKAGE = "..service..";

    /**
     * Checks if every controller follows proper naming convention and has the required annotation
     */
    @ArchTest
    private ArchRule controllerNomenclature = classes().that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .or().haveSimpleNameEndingWith(CONTROLLER)
            .should().beAnnotatedWith(RestController.class)
            .andShould().haveSimpleNameEndingWith(CONTROLLER)
            .because("controller should be easy to locate");

    /**
     * Checks if every service follows proper naming convention and has the required annotation
     */
    @ArchTest
    private ArchRule serviceNomenclature = classes().that().areAnnotatedWith(Service.class)
            .or().haveSimpleNameEndingWith(SERVICE)
            .should().beAnnotatedWith(Service.class)
            .andShould().haveSimpleNameEndingWith(SERVICE)
            .because("service should be easy to locate");

    /**
     * Checks if the layered architectural rules and followed.
     * The Controller package should not be accessed by any other package and should access only Service package
     */
    @ArchTest
    private ArchRule controllerServiceLayers = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer(CONTROLLER_PACKAGE).definedBy(annotatedWith(RestController.class))
            .layer(SERVICE_PACKAGE).definedBy(annotatedWith(Service.class))
            .whereLayer(CONTROLLER_PACKAGE).mayNotBeAccessedByAnyLayer()
            .whereLayer(CONTROLLER_PACKAGE).mayOnlyAccessLayers(SERVICE_PACKAGE)
            .whereLayer(SERVICE_PACKAGE).mayOnlyBeAccessedByLayers(CONTROLLER_PACKAGE);

    /**
     * Verifies that all the layer architecture rules defined via jmolecules library are in check
     */
    @ArchTest
    private ArchRule jmoleculesLayers = JMoleculesArchitectureRules.ensureLayering();

}
