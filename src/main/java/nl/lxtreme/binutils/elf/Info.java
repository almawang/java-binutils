/**
 * Info for Symbol Table Entries
 * 
 * Written by Alma - almawang96@gmail.com
 */
package nl.lxtreme.binutils.elf;

public class Info {
	public static class Type {
		public static final Type NOTYPE = new Type (0, "Type not specified");
		public static final Type OBJECT = new Type (1, "Associated with a data object");
		public static final Type FUNC = new Type(2, "Associated with a function or executable code");
		public static final Type SECTION = new Type(3,"Associated with a section, typically for relocation");
		public static final Type FILE = new Type(4, "Gives the source file of the object file");
		public static final Type LOPROC = new Type(13, "Processor specific");
		public static final Type HIPROC = new Type(15, "Processor specific"); 
		
		private static final Type[] TYPES = { NOTYPE, OBJECT, FUNC, SECTION, FILE };

		private int no;
		private String desc;
		
		private Type(int no, String desc){
			this.no = no;
			this.desc = desc;
		}
		
		public static Type valueOf(int value){
			for (Type t : TYPES){
				if (t.no == value)
					return t;
				if(value >= LOPROC.ordinal() && value <= HIPROC.ordinal())
					return LOPROC;
			}
			return null;
		}
		
		public int ordinal() {
			return no;
		}
		
		String getTypeString()
		{
			switch(no) {
				case 0:
					return "NOTYPE";
				case 1:
					return "OBJECT";
				case 2: 
					return "FUNC";
				case 3:
					return "SECTION";
				case 4:
					return "FILE";
				default:
					return "ERROR";
			}
		}
		
		@Override 
		public boolean equals(Object b){
			return (((Type)b).no == this.no);
		}
		
		@Override
		public String toString()
		{
			return desc;
		}
		
	}
	
	public static class Bind {
		public static final Bind LOCAL = new Bind(0, "Not visible outside object file where defined. May exist in multiple files without interference");
		public static final Bind GLOBAL = new Bind(1, "Visible to all object files combined. Will satisfy another file's undefined global symbol");
		public static final Bind WEAK = new Bind(2, "Similar to global symbols, but lower precedence");
		public static final Bind LOPROC = new Bind(13, "Processor Specific");
		public static final Bind HIPROC = new Bind(15, "Processor Specific");
		
		public static final Bind[] BINDS = { LOCAL, GLOBAL, WEAK };
		
				
		private int no;
		private String desc;
		
		private Bind(int no, String desc){
			this.no = no;
			this.desc = desc;
		}
		
		public static Bind valueOf(int value){
			for (Bind b : BINDS){
				if (b.no == value)
					return b;
				if(value >= LOPROC.ordinal() && value <= HIPROC.ordinal())
					return LOPROC;
			}
			return null;
		}
		
		int ordinal() {
			return no;
		}
		
		String getBindString()
		{
			switch(no) {
				case 0:
					return "LOCAL";
				case 1:
					return "GLOBAL";
				case 2: 
					return "WEAK";
				default:
					return "ERROR";
			}
		}
		
		@Override 
		public boolean equals(Object b){
			return (((Bind)b).no == this.no);
		}
		
		@Override
		public String toString(){
			return desc;
		}
	}

		
	private int info;
	private Type type;
	private Bind bind;
	
	private Info(int info){
		this.info = info;
		this.type = Type.valueOf(info & 0xf);
		this.bind = Bind.valueOf(info >> 4);	
	}
	
	public static Info valueOf (int info){
		Info i = new Info(info);
		return(i.type == null || i.bind == null ? null : i);
	}
	
	public int ordinal() {
		return info;
	}

	public String getBindString() {
		return bind.getBindString();
	}

	public String getTypeString() {
		return type.getTypeString();
	}
	
	@Override
	public String toString() {
		return (ordinal() + " " + "Bind: " + getBindString() + "Type: " + getTypeString());
	}
		
}
