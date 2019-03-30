import java.util.ArrayList;

public class Linker {
	
	private final static int machine_size = 300;
	//private static Module[] modules = new Module[machine_size];
	//private static ArrayList<Symbol> symbol_table;
	//private static ArrayList<String> warnings;

	
	//check if it's already in the symbol table
	public static Symbol isInTable(ArrayList<Symbol> symbol_table, Symbol symbol){	
		for (Symbol table_s: symbol_table) {
			if ((symbol.getSym()).equals(table_s.getSym())){
				return table_s;
			}
		}
		return null;
	}
	
	
	//set symbol_table
	//parse definitions list in modules, into symbol_table in linker
	public static void setSymbol_table(Module[] modules, ArrayList<Symbol> symbol_table) {
		for (Module m: modules) {
			if (m.getDefinitions()!= null) {
				for (Symbol module_s: m.getDefinitions()){
					
					//check if it's already in the symbol table
					if (isInTable(symbol_table, module_s) != null) {
						Symbol table_s = isInTable(symbol_table, module_s);
						//System.out.println("error: multiply defined");
						//module_s.setError("Error: This variable is multiply defined; last value used.");
						table_s.setError("Error: This variable is multiply defined; last value used.");
						
						//System.out.println(table_s.getDef_loc());
						table_s.setDef_loc(module_s.getDef_loc());
						table_s.setNumOfModule(module_s.getNumOfModule());
					}
					else {
						symbol_table.add(module_s);	
					}
				}
			}
		}
		
		for (Symbol s: symbol_table) {
			s.setAbs_loc(modules[s.getNumOfModule()].getBase_address() + s.getDef_loc());
		}
	}
	
	
	//deal with texts
	public static void dealWithTexts(ArrayList<Symbol> symbol_table, Module[] modules) {
		for (Module m: modules) {
			for (Entry e: m.getTexts()) {
				if (e.getType().equals("A")) {
					checkAbsolute(modules, e);
				}
				else if(e.getType().equals("R")) {
					relocateRelative(modules, m, e);
				}
			}
		}
		resolveExternal(symbol_table, modules);
		for (Module m: modules) {
			for (Entry e: m.getTexts()) {
				if (e.getType().equals("E")) {
					if(e.getError().contains("not defined")) {
						e.setAddress_field(111);
					}
					else {
						e.setAddress_field(e.getSymbolUsed().getAbs_loc());
					}
				}
			}
		}
	}
	
	//deal with A
	public static void checkAbsolute(Module[] modules, Entry e) {
		if (e.getAddress_field() >= Linker.machine_size) {
			e.setError("Error: A type address exceeds machine size; max legal value used. ");
			e.setAddress_field(Linker.machine_size-1);
		}
	}
	
	//deal with R
	public static void relocateRelative(Module[] modules, Module m, Entry e) {
		if (e.getAddress_field()>= m.getNT()) {
			
			//System.out.println("R exceeds");
			e.setError("Error: Type R address exceeds module size: 0 (relative). ");
			e.setAddress_field(m.getBase_address());
		}
		else {
			//System.out.println("test for entry line:" + (e.getIndex() + e.getOffset()) + ", addressfield: "+ e.getAddress_field());
			e.setAddress_field(m.getBase_address() + e.getAddress_field());
			//System.out.println("test for entry line after set:" + (e.getIndex() + e.getOffset()) + ", addressfield: "+ e.getAddress_field());
		}
	}
	
	//deal with E
	//apply uses list to text entries
	public static void resolveExternal(ArrayList<Symbol> symbol_table, Module[] modules) {
		for (Module m: modules) {
			Entry[] texts = m.getTexts();
			for (Symbol module_s: m.getUses()){
				//System.out.println(module_s.getSym() + module_s.getUse_loc());
				resolve(modules, symbol_table, texts, texts[module_s.getUse_loc()], module_s);
			}
		}
	}
	
	//a recursive function for linked list of resolveExternal until hits 777
	public static void resolve(Module[] modules, ArrayList<Symbol> symbol_table, Entry[] texts, Entry e, Symbol symbol) {
		int next = e.getAddress_field();
		
		//System.out.println("test for entry line before resolve:" + (e.getIndex() + e.getOffset()) + ", addressfield: "+ e.getAddress_field());	
		
		if (isInTable(symbol_table, symbol) == null) {
			e.setError("Error: " + symbol.getSym() + " is not defined; 111 used.");
			//System.out.println("dffd");
			//e.setAddress_field(111);
		}
		else {
			if (e.getSymbolUsed() != null)
				e.setError("Error: Multiple symbols used here; last one used. ");
			
			Symbol table_s = isInTable(symbol_table, symbol);
			table_s.setUse_loc(symbol.getUse_loc());
			e.setSymbolUsed(table_s);
			//e.setAddress_field(modules[table_s.getNumOfModule()].getBase_address() + table_s.getDef_loc());
			
		}
		
		
		//System.out.println("test for entry line after resolve:" + (e.getIndex() + e.getOffset()) + ", addressfield: "+ e.getAddress_field());
		
		if (next == 777) 
			return;
		else
			resolve(modules, symbol_table, texts, texts[next], symbol);
		
	} 


	private static void printSymbol_table(ArrayList<Symbol> symbol_table, Module[] modules) {
		System.out.println("Symbol Table");
		for (Symbol s: symbol_table) {
			//System.out.println(s.getSym()+"="+ (modules[s.getNumOfModule()].getBase_address() + s.getDef_loc()) );
			System.out.println(s.getSym()+"="+ (modules[s.getNumOfModule()].getBase_address() + s.getDef_loc()) + " " + s.getError());
		}
		System.out.println();
	}
	
	private static void printModules(Module[] modules) {
		System.out.println("Memory Map");
		for (Module m: modules) {
			if (m.getTexts()!= null) {
				for (Entry e: m.getTexts()){
				System.out.println((e.getIndex()+e.getOffset())+ ":  " + e.getOpcode() + String.format("%03d", e.getAddress_field()) + " " + e.getError());
				}
			}
		}
		System.out.println();
	}
	
	private static void printWarnings (String warnings) {
		System.out.println(warnings);
	}
	
	public static void main (String[] args) {
		
		//read everything from scanner into modules
		Module[] modules = Reader.Read();
		
		//get definitions into symbol table
		ArrayList<Symbol> symbol_table = new ArrayList<Symbol>(5);
		setSymbol_table(modules, symbol_table);
		
		//printSymbol_table(symbol_table, modules);
		//printModules(modules);
		
		//deal with texts
		dealWithTexts(symbol_table, modules);
		
		//error 3: If a symbol is defined but not used, print a warning message.
		String warnings = "";
		if (symbol_table != null) {
			for (Symbol s: symbol_table) {
				if (s.getUse_loc() == -1) {
					warnings = warnings + "Warning: " + s.getSym() + " was defined in module " + s.getNumOfModule() + " but never used.\n";
				}
			}
		}
		
		printSymbol_table(symbol_table, modules);
		printModules(modules);
		
		printWarnings(warnings);
	}
}
