package de.nomiskeig.spring2dbml.model;

public class DBMLRef implements DBMLElement{

	@Override
	public void accept(DBMLVisitor visitor) {
        visitor.visitDBMLRef(this);
	}

}
