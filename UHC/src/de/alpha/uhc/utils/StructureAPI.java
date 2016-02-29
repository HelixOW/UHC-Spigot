package de.alpha.uhc.utils;

public class StructureAPI {
	 
	private byte[] blocks;
    private byte[] data;
    private short width;
    private short lenght;
    private short height;
 
    public StructureAPI(byte[] blocks, byte[] data, short width, short lenght, short height)
    {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
    }
 
    /**
    * @return the blocks
    */
    public byte[] getBlocks()
    {
        return blocks;
    }
 
    /**
    * @return the data
    */
    public byte[] getData()
    {
        return data;
    }
 
    /**
    * @return the width
    */
    public short getWidth()
    {
        return width;
    }
 
    /**
    * @return the lenght
    */
    public short getLenght()
    {
        return lenght;
    }
 
    /**
    * @return the height
    */
    public short getHeight()
    {
        return height;
    }
}
