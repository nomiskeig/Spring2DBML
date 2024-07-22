package de.nomiskeig.spring2dbml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.nomiskeig.spring2dbml.model.DBMLColumn;
import de.nomiskeig.spring2dbml.model.DBMLModel;
import de.nomiskeig.spring2dbml.model.DBMLRef;
import de.nomiskeig.spring2dbml.model.DBMLTable;
import de.nomiskeig.spring2dbml.model.DBMLVisitor;

public class DBMLFilePrinter implements DBMLVisitor {
    private File file;
    private DBMLModel model;
    private StringBuilder res;

    public DBMLFilePrinter(File file, DBMLModel model) {
        this.file = file;
        this.model = model;
        res = new StringBuilder();

    }

    public void print() throws IOException {
        Path path = file.toPath();

        this.model.accept(this);

        Files.write(path, res.toString().getBytes());

    }


    @Override
    public void visitDBMLColumn(DBMLColumn column) {
        res.append("    ");
        res.append(column.getName());
        res.append(" ");
        res.append(column.getType());
        res.append("\n");
    }

    @Override
    public void visitDBMLModel(DBMLModel model) {
        for (DBMLTable table : model.getTables()) {
            table.accept(this);
        }
        // TODO: visit ref
    }

    @Override
    public void visitDBMLTable(DBMLTable table) {
        res.append("Table " + table.getName() + " {\n");
        for (DBMLColumn column : table.getColumns()) {
            column.accept(this);
        }
        res.append("}\n");


    }

    @Override
    public void visitDBMLRef(DBMLRef ref) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitDBMLRef'");
    }

}
