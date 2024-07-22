package de.nomiskeig.spring2dbml;

import de.nomiskeig.spring2dbml.model.DBMLColumn;
import de.nomiskeig.spring2dbml.model.DBMLModel;
import de.nomiskeig.spring2dbml.model.DBMLTable;
import jakarta.persistence.Table;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

public class EntityProcessor {
    private DBMLModel model;
    public EntityProcessor(DBMLModel model) {
        this.model = model;
    }
    public void process(CtClass<?> clazz) {
        DBMLTable table = new DBMLTable();
        String name = clazz.getQualifiedName();
        if (clazz.hasAnnotation(Table.class)) {
            Table tableAnn = clazz.getAnnotation(Table.class);
            name = tableAnn.name();

        }
        table.setName(name);

        for (CtField<?> field : clazz.getFields()) {
            String fieldName = field.getSimpleName();
            String fieldType = field.getType().getSimpleName();
            DBMLColumn column = new DBMLColumn(fieldName, fieldType);
            table.addColumn(column);
        }

        this.model.addTable(table);



    }

}
