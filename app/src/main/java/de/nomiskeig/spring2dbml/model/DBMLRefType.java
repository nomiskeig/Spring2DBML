
package de.nomiskeig.spring2dbml.model;

public  enum DBMLRefType {
    
    ONETOONE("-"), ONETOMANY("<"), MANYTOONE(">"), MANYTOMANY("<>");

    private final String repr;

    private DBMLRefType(String repr) {
        this.repr = repr;
    }
    @Override
    public String toString() {
        return repr;

    }

    
}

