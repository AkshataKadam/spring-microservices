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
            .layer(CONTROLLER).definedBy(annotatedWith(RestController.class))
            .layer(SERVICE).definedBy(annotatedWith(Service.class))
            .whereLayer(CONTROLLER).mayNotBeAccessedByAnyLayer()
            .whereLayer(CONTROLLER).mayOnlyAccessLayers(SERVICE)
            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(CONTROLLER);

    /**
     * Verifies that all the layer architecture rules defined via jmolecules library are in check
     */
    @ArchTest
    private ArchRule jmoleculesLayers = JMoleculesArchitectureRules.ensureLayering();

}
