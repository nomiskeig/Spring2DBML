package de.nomiskeig.spring2dbml.model;

import java.util.ArrayList;
import java.util.List;

public class DBMLModel implements DBMLElement{
    List<DBMLTable> tables;
    List<DBMLRef> refs;

    public DBMLModel() {
        this.tables = new ArrayList<>();
        this.refs = new ArrayList<>();
    }
	public List<DBMLRef> getRefs() {
		return refs;
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
	public void addRef(DBMLRef ref) {
        this.refs.add(ref);
	}

}
