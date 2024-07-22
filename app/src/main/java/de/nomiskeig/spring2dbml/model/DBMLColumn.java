package de.nomiskeig.spring2dbml.model;


public class DBMLColumn implements DBMLElement {
    private String name;

	private String type;

	public DBMLColumn(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

    @Override
	public void accept(DBMLVisitor visitor) {
        visitor.visitDBMLColumn(this);
	}

}
