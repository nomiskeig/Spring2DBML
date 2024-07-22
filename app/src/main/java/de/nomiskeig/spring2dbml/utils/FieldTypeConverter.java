package de.nomiskeig.spring2dbml.utils;

public  class FieldTypeConverter {
    public static String convertToDBML(String javaType) {
        if (javaType.equals("String")) {
            return "varchar";
        }
        if (javaType.equals("int")) {
            return "integer";
        }
        return javaType;

    }

}
