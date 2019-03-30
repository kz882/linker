
public class Module {
	
	private int base_address;
	private int numOfModule;
	private int ND;
	private int NU;
	private int NT;
	private Symbol[] definitions;
	private Symbol[] uses;
	private Entry[] texts;
 
	public Module (int ND, int NU, int NT) {
		this.base_address = 0;
		this.ND = ND;
		this.NU = NU;
		this.NT = NT;

		this.definitions = new Symbol[ND];
		this.uses = new Symbol[NU];
		this.texts = new Entry[NT];
	}
	
	public Module() {
		// TODO Auto-generated constructor stub
	}
	
	public int getNumOfModule() {
		return numOfModule;
	}

	public void setNumOfModule(int numOfModule) {
		this.numOfModule = numOfModule;
	}

	public Symbol[] getDefinitions() {
		return definitions;
	}

	public void setDefinitions(Symbol[] definitions) {
		this.definitions = definitions;
	}

	public int getBase_address() {
		return base_address;
	}

	public void setBase_address(int base_address) {
		this.base_address = base_address;
	}
	

	public int getND() {
		return ND;
	}

	public void setND(int nD) {
		ND = nD;
	}

	public int getNU() {
		return NU;
	}

	public void setNU(int nU) {
		NU = nU;
	}

	public int getNT() {
		return NT;
	}

	public void setNT(int nT) {
		NT = nT;
	}

	public Symbol[] getUses() {
		return uses;
	}

	public void setUses(Symbol[] uses) {
		this.uses = uses;
	}

	public Entry[] getTexts() {
		return texts;
	}

	public void setTexts(Entry[] texts) {
		this.texts = texts;
	}
	

}
