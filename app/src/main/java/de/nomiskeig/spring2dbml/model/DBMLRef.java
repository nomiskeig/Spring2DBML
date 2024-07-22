package de.nomiskeig.spring2dbml.model;

public class DBMLRef implements DBMLElement{

    private String originTable;
    private String originColumn;
    private String targetTable;
    private String targetColumn;
	private DBMLRefType type;
	public DBMLRef(String originTable, String originColumn, String targetTable, String targetColumn, DBMLRefType type) {
        this.originTable = originTable;
        this.originColumn = originColumn;
        this.targetTable = targetTable;
        this.targetColumn = targetColumn;
        this.type = type;
    }
	public String getOriginColumn() {
		return originColumn;
	}
	public String getTargetColumn() {
		return targetColumn;
	}
	public String getOriginTable() {
		return originTable;
	}
    public String getTargetTable() {
		return targetTable;
	}

    public DBMLRefType getType() {
		return type;
	}
	@Override
	public void accept(DBMLVisitor visitor) {
        visitor.visitDBMLRef(this);
	}

}
