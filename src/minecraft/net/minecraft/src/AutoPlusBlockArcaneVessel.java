package net.minecraft.src;

public class AutoPlusBlockArcaneVessel extends FCBlockArcaneVessel {
	public AutoPlusBlockArcaneVessel(int var1) {
		super(var1);
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
    	FCTileEntityArcaneVessel tileEntity = (FCTileEntityArcaneVessel) par1World.getBlockTileEntity(par2, par3, par4);
    	int currentXP = tileEntity.GetContainedTotalExperience();
    	
    	float xpPercent = currentXP / (float) tileEntity.m_iMaxContainedExperience;

        return MathHelper.floor_float(xpPercent * 14.0F) + (currentXP > 0 ? 1 : 0);
    }
}