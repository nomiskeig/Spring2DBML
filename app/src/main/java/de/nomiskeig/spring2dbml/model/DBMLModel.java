package de.nomiskeig.spring2dbml.model;

import java.util.ArrayList;
import java.util.List;

public class DBMLModel implements DBMLElement{
    List<DBMLTable> tables;

    public DBMLModel() {
        this.tables = new ArrayList<>();
    }
	public List<DBMLTable> getTables() {
		return tables;
	}
    public void addTable(DBMLTable table) {
        this.tables.add(table);
    }
	@Override
	public void accept(DBMLVisitor visitor) {
        visitor.visitDBMLModel(this);
	}

}
