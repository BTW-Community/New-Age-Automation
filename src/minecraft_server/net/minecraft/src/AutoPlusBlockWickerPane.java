package net.minecraft.src;

public class AutoPlusBlockWickerPane extends FCBlockWickerPane {
	public AutoPlusBlockWickerPane(int id) {
		super(id);
	}

    public boolean CanTransformItemIfFilter(ItemStack var1)
    {
        return var1.itemID == Block.gravel.blockID || var1.itemID == FCBetterThanWolves.fcItemWheat.itemID;
    }
}