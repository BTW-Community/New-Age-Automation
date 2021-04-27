package net.minecraft.src;

import java.util.List;

public class AutoPlusBlockAestheticOpaque extends FCBlockAestheticOpaque {
	public AutoPlusBlockAestheticOpaque(int id) {
		super(id);
	}

    public int getItemIDDroppedOnStonecutter(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        return meta == this.m_iSubtypeWhiteStone ? FCBetterThanWolves.fcBlockWhiteStoneSidingAndCorner.blockID : -1;
    }

    public int getItemCountDroppedOnStonecutter(World world, int x, int y, int z)
    {
    	return 2;
    }

    public int getItemDamageDroppedOnStonecutter(World world, int x, int y, int z)
    {
        return 0;
    }

    public boolean doesBlockBreakStonecutter(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
        return meta == m_iSubtypeBarrel || meta == m_iSubtypeDung || meta == m_iSubtypePadding || meta == m_iSubtypeRope || meta == m_iSubtypeSoap || meta == m_iSubtypeWicker;
    }
}