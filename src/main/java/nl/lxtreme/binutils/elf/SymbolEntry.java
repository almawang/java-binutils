/* Represents a symbol table entry in an ELF file
 * 
 * Written by Alma - almawang96@gmail.com
 * 
 */
package nl.lxtreme.binutils.elf;

import java.nio.ByteBuffer;

public class SymbolEntry {
	
	private String stringName;

	private int name;
	private long value, size;
	private Info info;
	private byte other;
	private char shndx;

	public SymbolEntry(ByteBuffer b, boolean is32)
	{
		  if(is32){
			  name = b.getInt();
			  value = b.getInt();
			  size = b.getInt();
			  info = Info.valueOf(b.get());
			  other = b.get();
			  shndx = b.getChar();
		  } else {
			  name = b.getInt();
			  info = Info.valueOf(b.get());
			  other = b.get();
			  shndx = b.getChar();
			  value = b.getLong();
			  size = b.getLong();
		  }
	}
	public SymbolEntry(int name, long value, long size, Info info, byte other, char shndx){
		this.name = name;
		this.value = value;
		this.size = size;
		this.info = info;
		this.other = other;
		this.shndx = shndx;
	}
	
	public int getName(){
		return name;
	}
	public long getValue(){
		return value;
	}
	public long getSize(){
		return size;
	}
	public Info getInfo(){
		return info;
	}
	public byte getOther(){
		return other;
	}
	public char getShndx(){
		return shndx;
	}
	
	public static String getShndxString(char shndx)
	{
		switch(shndx)
		{
			case 0:
				return "UNDEF";
			case 0xff00:
				return "LOPROC";
			case 0xff01:
				return "AFTER";
			case 0xff02:
				return "AMD64_LCOMMON";
			case 0xff1f:
				return "HIPROC";
			case 0xff20:
				return "LOOS";
			case 0xff3f:
				return "SUNW";
			case 0xfff1:
				return "ABS";
			case 0xfff2:
				return "COMMON";
			case 0xffff:
				return "XINDEX";
			default:
				return Integer.toString((int)shndx); // from hex to int string
		}
	}
	
	public void setName(String name){
		this.stringName = name;
	}
	
	public String getStringName(){ return stringName; }
	
	@Override
	public String toString(){
		String bind = info.getBindString();
		String type = info.getTypeString();
		String pattern = "Value: 0x%s \t Size: 0x%s \t Bind: %s \t Type: %s \t Other: %s \t Shndx: %s\t Name: %s \n";
		return(String.format(pattern, Long.toHexString(value), Long.toHexString(size), bind, type, 
				other, getShndxString(shndx),stringName == null ? name : stringName));
	}
	

}
