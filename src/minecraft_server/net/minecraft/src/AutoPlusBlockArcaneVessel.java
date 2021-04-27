package net.minecraft.src;

public class AutoPlusBlockArcaneVessel extends FCBlockArcaneVessel {
	public AutoPlusBlockArcaneVessel(int var1) {
		super(var1);
	}

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote && var5 instanceof EntityXPOrb)
        {
            super.onEntityCollidedWithBlock(var1, var2, var3, var4, (EntityXPOrb)var5);
            //Updates comparator state
            var1.func_96440_m(var2, var3, var4, this.blockID);
        }
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