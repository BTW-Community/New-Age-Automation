package net.minecraft.src;

public class AutoPlusBlockLoom extends Block {
	protected AutoPlusBlockLoom(int id) {
		super(id, FCBetterThanWolves.fcMaterialPlanks);
		this.SetAxesEffectiveOn(true);
		this.setHardness(1.0F);
		this.setResistance(5.0F);
		this.SetFireProperties(FCEnumFlammability.PLANKS);
		this.SetBuoyant();
		this.setStepSound(soundWoodFootstep);
		this.setUnlocalizedName("autoPlusBlockLoom");
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	public int onBlockPlaced(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var9)
	{
		return this.SetFacing(var9, var5);
	}

	public int PreBlockPlacedBy(World var1, int var2, int var3, int var4, int var5, EntityLiving var6)
	{
		int var7 = FCUtilsMisc.ConvertOrientationToFlatBlockFacing(var6);
		return this.SetFacing(var5, var7);
	}

	public int GetFacing(int var1)
	{
		return (var1 & 3) + 2;
	}

	public int SetFacing(int var1, int var2)
	{
		var1 &= -4;
		var1 |= MathHelper.clamp_int(var2, 2, 5) - 2;
		return var1;
	}
}