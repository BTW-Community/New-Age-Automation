package net.minecraft.src;

public class AutoPlusRecipes {
	public static void addRecipes() {
		addAllRecipes();
		addStonecutterRecipesToVanillaBlocks();
	}
	
	private static void addAllRecipes() {
		FCRecipes.AddMillStoneRecipe(new ItemStack(Item.blazePowder, 2), new ItemStack(Item.blazeRod));
		FCRecipes.AddStokedCauldronRecipe(new ItemStack(AutoPlusDefs.beltLubricated), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcItemBelt), new ItemStack(FCBetterThanWolves.fcItemTallow)});
		FCRecipes.AddRecipe(new ItemStack(AutoPlusDefs.stonecutter), new Object[] {"YYY", "XZX", "#X#", '#', FCBetterThanWolves.fcItemStoneBrick, 'X', FCBetterThanWolves.fcItemGear, 'Y', FCBetterThanWolves.fcItemIngotDiamond, 'Z', AutoPlusDefs.beltLubricated});
        FCRecipes.AddRecipe(new ItemStack(Item.comparator, 1), new Object[] {" # ", "#X#", "III", '#', Block.torchRedstoneActive, 'X', FCBetterThanWolves.fcItemPolishedLapis, 'I', FCBetterThanWolves.fcItemStoneBrick});
        FCRecipes.AddRecipe(new ItemStack(AutoPlusDefs.loom), new Object[] {"fSf", "gBg", "pgp", 'f', FCBetterThanWolves.fcItemHempFibers, 'S', FCBetterThanWolves.fcBlockSlats, 'g', FCBetterThanWolves.fcItemGear, 'B', FCBetterThanWolves.fcItemBelt, 'p', new ItemStack(FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, 32767)});
        
        FCRecipes.RemoveAnvilRecipe(new ItemStack(FCBetterThanWolves.fcItemPolishedLapis, 2), new Object[] {"###", "###", "GGG", " R ", '#', new ItemStack(Item.dyePowder, 1, 4), 'G', Item.goldNugget, 'R', Item.redstone});
        FCRecipes.AddRecipe(new ItemStack(FCBetterThanWolves.fcItemPolishedLapis, 2), new Object[] {"###", "GGG", " R ", '#', new ItemStack(Item.dyePowder, 1, 4), 'G', Item.goldNugget, 'R', Item.redstone});
	}
	
	private static void addStonecutterRecipesToVanillaBlocks() {
		Block.stone.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSmoothStoneSidingAndCorner.blockID);
		Block.stone.setItemCountDroppedOnStonecutter(2);
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockSmoothStoneSidingAndCorner).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSmoothStoneMouldingAndDecorative.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockSmoothStoneMouldingAndDecorative).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSmoothStoneSidingAndCorner.blockID);

		if (!AutoPlusDecoIntegration.isDecoInstalled()) {
			Block.stoneBrick.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner.blockID);
			Block.stoneBrick.setItemCountDroppedOnStonecutter(2);
		}
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockStoneBrickMouldingAndDecorative.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockStoneBrickMouldingAndDecorative).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockStoneBrickSidingAndCorner.blockID);
		
		Block.blockNetherQuartz.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSidingAndCornerBlackStone.blockID);
		Block.blockNetherQuartz.setItemCountDroppedOnStonecutter(2);
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockSidingAndCornerBlackStone).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockMouldingAndDecorativeBlackStone.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockMouldingAndDecorativeBlackStone).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSidingAndCornerBlackStone.blockID);
		
		if (!AutoPlusDecoIntegration.isDecoInstalled()) {
			Block.sandStone.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSandstoneSidingAndCorner.blockID);
			Block.sandStone.setItemCountDroppedOnStonecutter(2);
		}
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockSandstoneSidingAndCorner).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSandstoneMouldingAndDecorative.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockSandstoneMouldingAndDecorative).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockSandstoneSidingAndCorner.blockID);

		Block.brick.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockBrickSidingAndCorner.blockID);
		Block.brick.setItemCountDroppedOnStonecutter(2);
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockBrickSidingAndCorner).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockBrickMouldingAndDecorative.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockBrickMouldingAndDecorative).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockBrickSidingAndCorner.blockID);

		Block.netherBrick.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockNetherBrickSidingAndCorner.blockID);
		Block.netherBrick.setItemCountDroppedOnStonecutter(2);
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockNetherBrickSidingAndCorner).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockNetherBrickMouldingAndDecorative.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockNetherBrickMouldingAndDecorative).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockNetherBrickSidingAndCorner.blockID);
		Block.netherFence.setItemIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockNetherBrickSidingAndCorner.blockID);
		Block.netherFence.setItemCountDroppedOnStonecutter(2);
		Block.netherFence.setItemDamageDroppedOnStonecutter(1);
		
		((FCBlockSidingAndCornerAndDecorative) FCBetterThanWolves.fcBlockWhiteStoneSidingAndCorner).setMouldingIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockWhiteStoneMouldingAndDecorative.blockID);
		((FCBlockMouldingAndDecorative) FCBetterThanWolves.fcBlockWhiteStoneMouldingAndDecorative).setCornerIDDroppedOnStonecutter(FCBetterThanWolves.fcBlockWhiteStoneSidingAndCorner.blockID);
	}
}