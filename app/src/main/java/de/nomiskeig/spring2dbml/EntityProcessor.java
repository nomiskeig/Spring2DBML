package de.nomiskeig.spring2dbml;

import java.util.List;

import de.nomiskeig.spring2dbml.model.DBMLColumn;
import de.nomiskeig.spring2dbml.model.DBMLModel;
import de.nomiskeig.spring2dbml.model.DBMLRef;
import de.nomiskeig.spring2dbml.model.DBMLRefType;
import de.nomiskeig.spring2dbml.model.DBMLTable;
import de.nomiskeig.spring2dbml.utils.FieldTypeConverter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class EntityProcessor {
    private DBMLModel model;

    public EntityProcessor(DBMLModel model) {
        this.model = model;
    }

    public void process(CtClass<?> clazz, List<String> tableNames) {
        DBMLTable table = new DBMLTable();
        String name = getTableName(clazz);
        table.setName(name);

        for (CtField<?> field : clazz.getFields()) {
            String fieldName = field.getSimpleName();
            String fieldType = field.getType().getSimpleName();

            // handle the case where the type is another entity, as this is represented by
            // keys in the db
            if ((field.getType().isClass() || field.getType().isInterface()) && !fieldType.equals("String")) {
                fieldType = "int";
                if (field.hasAnnotation(JoinColumn.class)) {
                    JoinColumn joinColumnAnn = field.getAnnotation(JoinColumn.class);
                    String otherColumnName = joinColumnAnn.name();
                    if (joinColumnAnn.referencedColumnName().length() > 0) {
                        otherColumnName = joinColumnAnn.referencedColumnName();

                    }
                    DBMLRefType refType = DBMLRefType.ONETOONE;
                    String otherTableName = getTableName((CtClass<?>) field.getType().getDeclaration());
                    if (field.hasAnnotation(OneToOne.class)) {
                        refType = DBMLRefType.ONETOONE;
                    }
                    if (field.hasAnnotation(OneToMany.class)) {
                        refType = DBMLRefType.ONETOMANY;
                    }
                    if (field.hasAnnotation(ManyToOne.class)) {
                        refType = DBMLRefType.MANYTOONE;
                    }
                    DBMLRef ref = new DBMLRef(name, field.getSimpleName(), otherTableName, otherColumnName,
                            refType);
                    this.model.addRef(ref);
                }
                if (field.hasAnnotation(ManyToMany.class) && field.hasAnnotation(JoinTable.class)) {
                    JoinTable joinTableAnn = field.getAnnotation(JoinTable.class);
                    DBMLTable m2mTable = new DBMLTable();
                    m2mTable.setName(joinTableAnn.name());
                    // TODO: there might bei multiple joinColumns
                    JoinColumn joinColumnAnn = joinTableAnn.joinColumns()[0];
                    m2mTable.addColumn(new DBMLColumn(joinColumnAnn.name(), "integer"));
                    JoinColumn inverseJoinColumnAnn = joinTableAnn.inverseJoinColumns()[0];
                    m2mTable.addColumn(new DBMLColumn(inverseJoinColumnAnn.name(), "integer"));
                    boolean add = true;
                    for (String tableName : tableNames) {
                        if (tableName.equals(joinTableAnn.name())) {
                            add = false;
                        }
                    }
                    if (add) {
                        this.model.addTable(m2mTable);
                    }
                    DBMLRef ref1 = new DBMLRef(joinColumnAnn.name(), joinColumnAnn.referencedColumnName(),
                            joinTableAnn.name(), joinColumnAnn.name(), DBMLRefType.ONETOMANY);
                    DBMLRef ref2 = new DBMLRef(inverseJoinColumnAnn.name(), inverseJoinColumnAnn.referencedColumnName(),
                            joinTableAnn.name(), inverseJoinColumnAnn.name(), DBMLRefType.ONETOMANY);
                    this.model.addRef(ref1);
                    this.model.addRef(ref2);

                }
            }

            DBMLColumn column = new DBMLColumn(fieldName, FieldTypeConverter.convertToDBML(fieldType));
            table.addColumn(column);
        }

        this.model.addTable(table);

    }

    private String getTableName(CtClass<?> clazz) {
        String name = clazz.getQualifiedName();
        if (clazz.hasAnnotation(Table.class)) {
            Table tableAnn = clazz.getAnnotation(Table.class);
            name = tableAnn.name();

        }
        return name;

    }

}
