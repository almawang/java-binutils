/**
 * Represents a Relocation table entry in an ELF file
 * 
 * Written by Alma - almawang96@gmail.com
 */
package nl.lxtreme.binutils.elf;

import java.nio.ByteBuffer;

public class RelocationEntry {
	private String symbolName;
	
	private long offset;
	private long info;
	private long addend = -1;
	private boolean is32;
	
	public RelocationEntry( ByteBuffer b, boolean is32, boolean useAddend)
	{	this.is32 = is32;
		this.addend = useAddend ? 0 : -1;
		if(is32 && useAddend)
		{
			this.offset = b.getInt();
			this.info = b.getInt();
			this.addend = b.getInt();
		} else if(is32 && !useAddend){
			this.offset = b.getInt();
			this.info = b.getInt();
		} else if(!is32 && useAddend){
			this.offset = b.getLong();
			this.info = b.getLong();
			this.addend = b.getLong();
		} else if(!is32 && !useAddend){
			this.offset = b.getLong();
			this.info = b.getLong();
		}
	}
	
	public RelocationEntry( long offset, long info )
	{
		this.offset = offset;
		this.info = info;
	}
	
	public RelocationEntry( long offset, long info, long addend )
	{
		this.offset = offset;
		this.info = info;
		this.addend = addend;
	}
	
	public long getOffset(){ return offset; }
	public long getInfo(){ return info; }
	public boolean hasAddend(){ return addend >= 0 ? true : false; }
	public long getAddend(){ return addend; }
	
	public long getSym()
	{
		return is32 ? info >> 8 : info >> 32;
	}
	
	public long getType()
	{
		return is32 ? (byte)info : info & 0xffffffffL;
	}
	
	public void setSymbolName(String sym){ this.symbolName = sym; }
	
	@Override 
	public String toString()
	{
		StringBuilder sb = new StringBuilder( String.format("Offset: 0x%s \tInfo: 0x%s \tAddend: %s \tSymbol Index: %s \tType: %s \t", Long.toHexString(offset), Long.toHexString(info), hasAddend() ? addend : " ", getSym(), getType()));
		if(symbolName != null){
			sb.append("Symbol Name: ");
			sb.append(symbolName);
		}
		return sb.toString();
	}
}
