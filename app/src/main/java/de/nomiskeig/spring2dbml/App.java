/*
 * This source file was generated by the Gradle 'init' task
 */
package de.nomiskeig.spring2dbml;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.nomiskeig.spring2dbml.model.DBMLModel;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtAnnotationType;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.AnnotationFactory;
import spoon.reflect.visitor.filter.AnnotationFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class App {
    public static void main(String[] args) {

        Launcher launcher = new Launcher();
        DBMLModel dbmlModel = new DBMLModel();
        EntityProcessor ep = new EntityProcessor(dbmlModel);
        launcher.addInputResource(args[0]);
        launcher.getEnvironment().setComplianceLevel(21);
        launcher.buildModel();
        CtModel model = launcher.getModel();
        Collection<CtType<?>> types = model.getAllTypes();
        List<String> tableNames = types.stream().filter(t -> t.hasAnnotation(Entity.class))
                .map(t -> t.getAnnotation(Table.class).name()).collect(Collectors.toList());
        for (CtType<?> type : types) {
            if (type.hasAnnotation(Entity.class)) {
                ep.process((CtClass<?>) type, tableNames);
            }

        }
        File file = new File(args[1]);
        DBMLFilePrinter printer = new DBMLFilePrinter(file, dbmlModel);
        try {

            printer.print();
        } catch (IOException e) {
            System.out.println("Could not write to file.\nError: " + e.getMessage());
        }

    }
}
