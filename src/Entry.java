
public class Entry {
	int numOfModule;
	int index;
	int offset;
	
	String type;
	int opcode;
	int address_field;
	String error = "";
	
	Symbol symbolUsed;

	
	
	public Entry() {
		super();
	}

	public Entry(int numOfModule, int index, int offset, String type, int opcode, int address_field) {
		super();
		this.numOfModule = numOfModule;
		this.index = index;
		this.offset = offset;
		this.type = type;
		this.opcode = opcode;
		this.address_field = address_field;
	}

	
	public int getNumOfModule() {
		return numOfModule;
	}

	public void setNumOfModule(int numOfModule) {
		this.numOfModule = numOfModule;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getAddress_field() {
		return address_field;
	}

	public void setAddress_field(int address_field) {
		this.address_field = address_field;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = this.error + error;
	}

	public Symbol getSymbolUsed() {
		return symbolUsed;
	}

	public void setSymbolUsed(Symbol symbolUsed) {
		this.symbolUsed = symbolUsed;
	}
	
	



	
}
