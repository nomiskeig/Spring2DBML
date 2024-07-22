package de.nomiskeig.spring2dbml.model;

import java.util.ArrayList;
import java.util.List;

public class DBMLTable implements DBMLElement {
    private String name;
    private List<DBMLColumn> columns;

    public String getName() {
		return name;
	}

	public void setName(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

	@Override
	public void accept(DBMLVisitor visitor) {
        visitor.visitDBMLTable(this);
	}

	public List<DBMLColumn> getColumns() {
        return this.columns;
	}

    public void addColumn(DBMLColumn column) {
        this.columns.add(column);
    }

}
