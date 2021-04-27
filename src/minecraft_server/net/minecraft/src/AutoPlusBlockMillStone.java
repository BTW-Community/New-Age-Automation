package net.minecraft.src;

public class AutoPlusBlockMillStone extends FCBlockMillStone {
	public AutoPlusBlockMillStone(int id) {
		super(id);
	}

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return ((FCTileEntityMillStone) par1World.getBlockTileEntity(par2, par3, par4)).m_stackMilling == null ? 0 : 15;
    }
}