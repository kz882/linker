public class Symbol {
	String sym;
	int def_loc = -1;
	int use_loc = -1;
	int abs_loc = -1;
	
	int numOfModule = -1;
	
	String error = "";
	
	static final int arbitraryLimits = 8;
	
	public Symbol(String sym, int numOfModule) {
		this.sym = sym;
		if (sym.length() > arbitraryLimits) {
			this.setError("Error: Symbol name exceeds arbitrary limits 8 characters.");
		}
		this.numOfModule = numOfModule;
	}
	
	public Symbol(String sym, int loc, int numOfModule) {
		this.sym = sym;
		if (sym.length() > arbitraryLimits) {
			this.setError("Error: Symbol name exceeds arbitrary limits 8 characters.");
		}
		this.def_loc = loc;
		this.numOfModule = numOfModule;
	}
	
	public int getNumOfModule() {
		return numOfModule;
	}

	public void setNumOfModule(int numOfModule) {
		this.numOfModule = numOfModule;
	}

	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	
	public int getDef_loc() {
		return def_loc;
	}

	public void setDef_loc(int def_loc) {
		this.def_loc = def_loc;
	}

	public int getUse_loc() {
		return use_loc;
	}

	public void setUse_loc(int use_loc) {
		this.use_loc = use_loc;
	}

	public int getAbs_loc() {
		return abs_loc;
	}

	public void setAbs_loc(int abs_loc) {
		this.abs_loc = abs_loc;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = this.error + error;
	}
	
}
