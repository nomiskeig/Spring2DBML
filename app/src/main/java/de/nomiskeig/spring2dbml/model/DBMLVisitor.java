package de.nomiskeig.spring2dbml.model;  

public interface DBMLVisitor {
    void visitDBMLColumn(DBMLColumn column);
    void visitDBMLModel(DBMLModel model);
    void visitDBMLTable(DBMLTable table);
    void visitDBMLRef(DBMLRef ref);
}
