package de.nomiskeig.spring2dbml.model;


public interface DBMLElement {
    void accept(DBMLVisitor visitor);
}
