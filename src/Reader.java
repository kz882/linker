import java.util.Scanner;

public class Reader {
	
	static Module[] Read() {
		//parse every input into modules
		Scanner sc = new Scanner(System.in);
		
		int num_modules = sc.nextInt();
		
		Module[] modules = new Module[num_modules];
		
		for (int i=0; i<num_modules; i++) {
			//create a temporary module
			Module m = new Module();
			
			m.setNumOfModule(i);
			
			//parse module base address into module's data field
			if (i!=0) {
				m.setBase_address(modules[i-1].getBase_address() + modules[i-1].getNT());
			}
			
			
			//parse definitions to module's data field
			m.setND(sc.nextInt());
			Symbol[] definitions = new Symbol[m.getND()];
			
			for (int j=0; j<m.getND(); j++) {
				String sym = sc.next();
				int def_loc = sc.nextInt();
				
				Symbol s = new Symbol(sym, def_loc, m.getNumOfModule());

				definitions[j] = s;
			}
			
			m.setDefinitions(definitions);
			
			//parse uses to module's data field
			m.setNU(sc.nextInt());
			Symbol[] uses = new Symbol[m.getNU()];
			
			for (int j=0; j<m.getNU(); j++) {

				String sym = sc.next();
				int use_loc = sc.nextInt();
				
				Symbol s = new Symbol(sym, m.getNumOfModule());
				s.setUse_loc(use_loc);
				
				uses[j] = s;
			}
			
			m.setUses(uses);
			
			//parse text entries into module's data field
			m.setNT(sc.nextInt());
			
			Entry[] texts = new Entry[m.getNT()];
			
			for (int j=0; j<m.getNT(); j++) {

				String type = sc.next();
				int word = sc.nextInt();
				int opcode = word/1000;
				int address_field = word - opcode*1000;
				
				Entry e = new Entry(m.getNumOfModule(), m.getBase_address(), j, type, opcode, address_field);
				
				texts[j] = e;
			}
			
			m.setTexts(texts);
			
			//now we have all info for m, add it to modules list
			modules[i] = m;
		}
		
		sc.close();
		
		return modules;
		
	}
}
