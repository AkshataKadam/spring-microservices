package io.agamivriddhi.base;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = BaseApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {
    private JavaClasses javaClasses = new ClassFileImporter().importPackages("io.agamivriddhi.base.controller");

    private String CONTROLLER = "Controller";
    private String SERVICE = "Service";
    private String DOMAIN = "Domain";

    // UNCOMMENT WHEN THE ACTUAL CODING STARTS
//    @DisplayName("Package classes interdependency check")
//    @Test
//    public void noClassesShouldDependOnExternalPackage() {
//        ArchRule rule = noClasses().should().dependOnClassesThat().resideInAnyPackage("io.agamivriddhi.base.service");
//        rule.check(javaClasses);
//    }

    @ArchTest
    private ArchRule controller_naming = classes().that().areAnnotatedWith(Controller.class)
            .or().areAnnotatedWith(RestController.class)
            .or().haveSimpleNameEndingWith(CONTROLLER)
            .should().beAnnotatedWith(RestController.class)
            .andShould().haveSimpleNameEndingWith(CONTROLLER)
            .because("controller should be easy to locate");

    @ArchTest
    private ArchRule service_naming = classes().that().areAnnotatedWith(Service.class)
            .or().haveSimpleNameEndingWith(SERVICE)
            .should().beAnnotatedWith(Service.class)
            .andShould().haveSimpleNameEndingWith(SERVICE)
            .because("service should be easy to locate");

    // UNCOMMENT WHEN THE ACTUAL CODING STARTS
//    @ArchTest
//    private ArchRule layers = Architectures.layeredArchitecture()
//            .consideringOnlyDependenciesInLayers()
//            .layer(CONTROLLER).definedBy(annotatedWith(Controller.class))
//            .layer(SERVICE).definedBy(annotatedWith(Service.class))
//            .layer(DOMAIN).definedBy(assignableTo(Repository.class))
//            .whereLayer(CONTROLLER).mayNotAccessAnyLayer()
//            .whereLayer(CONTROLLER).mayOnlyAccessLayers(SERVICE)
//            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(CONTROLLER)
//            .whereLayer(SERVICE).mayOnlyAccessLayers(DOMAIN)
//            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(SERVICE)
//            .whereLayer(DOMAIN).mayNotAccessAnyLayer();

}
