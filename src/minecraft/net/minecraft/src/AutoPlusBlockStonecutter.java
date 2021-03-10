package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class AutoPlusBlockStonecutter extends Block implements FCIBlockMechanical
{
    private static final int powerChangeTickRate = 10;
    private static final int sawTimeBaseTickRate = 20;
    private static final int sawTimeTickRateVariance = 4;
    public static final float baseHeight = 0.75F;
    public static final float bladeLength = 0.625F;
    public static final float bladeHalfLength = 0.3125F;
    public static final float bladeWidth = 0.015625F;
    public static final float bladeHalfWidth = 0.0078125F;
    public static final float bladeHeight = 0.25F;

    protected AutoPlusBlockStonecutter(int var1)
    {
        super(var1, Material.rock);
        this.setHardness(2.0F);
        this.SetPicksEffectiveOn(true);
        this.InitBlockBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("autoPlusBlockStonecutter");
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World var1)
    {
        return 10;
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var9)
    {
        return this.SetFacing(var9, Block.GetOppositeFacing(var5));
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5, ItemStack var6)
    {
        int var7 = FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacingReversed(var5);
        this.SetFacing(var1, var2, var3, var4, var7);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        super.onBlockAdded(var1, var2, var3, var4);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 10);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        float var5 = 0.71875F;
        return this.GetBlockBoundsFromPoolForBaseHeight(var1, var2, var3, var4, var5).offset((double)var2, (double)var3, (double)var4);
    }

    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        return this.GetBlockBoundsFromPoolForBaseHeight(var1, var2, var3, var4, 0.75F);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (!var1.IsUpdatePendingThisTickForBlock(var2, var3, var4, this.blockID))
        {
            this.ScheduleUpdateIfRequired(var1, var2, var3, var4);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        boolean var6 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var7 = this.IsBlockOn(var1, var2, var3, var4);

        if (var7 != var6)
        {
            this.EmitSawParticles(var1, var2, var3, var4, var5);
            this.SetBlockOn(var1, var2, var3, var4, var6);

            if (var6)
            {
                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "minecart.base", 1.0F + var5.nextFloat() * 0.1F, 1.5F + var5.nextFloat() * 0.1F);
                this.ScheduleUpdateIfRequired(var1, var2, var3, var4);
            }
            else
            {
                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "minecart.base", 1.0F + var5.nextFloat() * 0.1F, 0.75F + var5.nextFloat() * 0.1F);
            }
        }
        else if (var7)
        {
            this.SawBlockToFront(var1, var2, var3, var4, var5);
        }
    }

    public void RandomUpdateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!var1.IsUpdateScheduledForBlock(var2, var3, var4, this.blockID))
        {
            this.ScheduleUpdateIfRequired(var1, var2, var3, var4);
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var1.isRemote)
        {
            if (this.IsBlockOn(var1, var2, var3, var4) && var5 instanceof EntityLiving)
            {
                int var6 = this.GetFacing(var1, var2, var3, var4);
                float var7 = 0.3125F;
                float var8 = 0.0078125F;
                float var9 = 0.25F;
                AxisAlignedBB var10;

                switch (var6)
                {
                    case 0:
                        var10 = AxisAlignedBB.getAABBPool().getAABB((double)(0.5F - var7), 0.0D, (double)(0.5F - var8), (double)(0.5F + var7), (double)var9, (double)(0.5F + var8));
                        break;

                    case 1:
                        var10 = AxisAlignedBB.getAABBPool().getAABB((double)(0.5F - var7), (double)(1.0F - var9), (double)(0.5F - var8), (double)(0.5F + var7), 1.0D, (double)(0.5F + var8));
                        break;

                    case 2:
                        var10 = AxisAlignedBB.getAABBPool().getAABB((double)(0.5F - var7), (double)(0.5F - var8), 0.0D, (double)(0.5F + var7), (double)(0.5F + var8), (double)var9);
                        break;

                    case 3:
                        var10 = AxisAlignedBB.getAABBPool().getAABB((double)(0.5F - var7), (double)(0.5F - var8), (double)(1.0F - var9), (double)(0.5F + var7), (double)(0.5F + var8), 1.0D);
                        break;

                    case 4:
                        var10 = AxisAlignedBB.getAABBPool().getAABB(0.0D, (double)(0.5F - var8), (double)(0.5F - var7), (double)var9, (double)(0.5F + var8), (double)(0.5F + var7));
                        break;

                    default:
                        var10 = AxisAlignedBB.getAABBPool().getAABB((double)(1.0F - var9), (double)(0.5F - var8), (double)(0.5F - var7), 1.0D, (double)(0.5F + var8), (double)(0.5F + var7));
                }

                var10 = var10.getOffsetBoundingBox((double)var2, (double)var3, (double)var4);
                List var11 = null;
                var11 = var1.getEntitiesWithinAABB(EntityLiving.class, var10);

                if (var11 != null && var11.size() > 0)
                {
                    DamageSource var12 = FCDamageSourceCustom.m_DamageSourceSaw;
                    int var13 = 4;
                    FCUtilsBlockPos var14 = new FCUtilsBlockPos(var2, var3, var4);
                    var14.AddFacingAsOffset(var6);
                    int var15 = var1.getBlockId(var14.i, var14.j, var14.k);
                    int var16 = var1.getBlockMetadata(var14.i, var14.j, var14.k);

                    if (var15 == FCBetterThanWolves.fcAestheticOpaque.blockID && (var16 == 13 || var16 == 12))
                    {
                        var12 = FCDamageSourceCustom.m_DamageSourceChoppingBlock;
                        var13 *= 3;

                        if (var16 == 13)
                        {
                            var1.setBlockMetadataWithNotify(var14.i, var14.j, var14.k, 12);
                        }
                    }

                    for (int var17 = 0; var17 < var11.size(); ++var17)
                    {
                        EntityLiving var18 = (EntityLiving)var11.get(var17);

                        if (var18.attackEntityFrom(var12, var13))
                        {
                            var1.playAuxSFX(2223, var2, var3, var4, var6);
                        }
                    }
                }
            }
        }
    }

    public boolean HasCenterHardPointToFacing(IBlockAccess var1, int var2, int var3, int var4, int var5, boolean var6)
    {
        return var5 != this.GetFacing(var1, var2, var3, var4);
    }

    public boolean HasLargeCenterHardPointToFacing(IBlockAccess var1, int var2, int var3, int var4, int var5, boolean var6)
    {
        return Block.GetOppositeFacing(var5) == this.GetFacing(var1, var2, var3, var4);
    }

    public int GetHarvestToolLevel(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 2;
    }

    public boolean DropComponentItemsOnBadBreak(World var1, int var2, int var3, int var4, int var5, float var6)
    {
        this.DropItemsIndividualy(var1, var2, var3, var4, FCBetterThanWolves.fcItemGear.itemID, 1, 0, var6);
        this.DropItemsIndividualy(var1, var2, var3, var4, Item.stick.itemID, 2, 0, var6);
        this.DropItemsIndividualy(var1, var2, var3, var4, FCBetterThanWolves.fcItemSawDust.itemID, 3, 0, var6);
        this.DropItemsIndividualy(var1, var2, var3, var4, Item.ingotIron.itemID, 2, 0, var6);
        this.DropItemsIndividualy(var1, var2, var3, var4, FCBetterThanWolves.fcItemNuggetIron.itemID, 4, 0, var6);
        this.DropItemsIndividualy(var1, var2, var3, var4, FCBetterThanWolves.fcItemStrap.itemID, 3, 0, var6);
        return true;
    }

    public int GetFacing(int var1)
    {
        return var1 & 7;
    }

    public int SetFacing(int var1, int var2)
    {
        var1 &= -8;
        var1 |= var2;
        return var1;
    }

    public boolean CanRotateOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 0;
    }

    public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = this.GetFacing(var1, var2, var3, var4);
        return var5 != 0 && var5 != 1;
    }

    public boolean RotateAroundJAxis(World var1, int var2, int var3, int var4, boolean var5)
    {
        if (super.RotateAroundJAxis(var1, var2, var3, var4, var5))
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate(var1));
            FCUtilsMechPower.DestroyHorizontallyAttachedAxles(var1, var2, var3, var4);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean ToggleFacing(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        var6 = Block.CycleFacing(var6, var5);
        this.SetFacing(var1, var2, var3, var4, var6);
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate(var1));
        var1.notifyBlockChange(var2, var3, var4, this.blockID);
        return true;
    }

    protected boolean IsCurrentPowerStateValid(World var1, int var2, int var3, int var4)
    {
        boolean var5 = this.IsInputtingMechanicalPower(var1, var2, var3, var4);
        boolean var6 = this.IsBlockOn(var1, var2, var3, var4);
        return var6 == var5;
    }

    public boolean IsBlockOn(IBlockAccess var1, int var2, int var3, int var4)
    {
        return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
    }

    public void SetBlockOn(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4) & 7;

        if (var5)
        {
            var6 |= 8;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);
    }

    protected void ScheduleUpdateIfRequired(World var1, int var2, int var3, int var4)
    {
        if (!this.IsCurrentPowerStateValid(var1, var2, var3, var4))
        {
            var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 10);
        }
        else if (this.IsBlockOn(var1, var2, var3, var4))
        {
            int var5 = this.GetFacing(var1, var2, var3, var4);
            FCUtilsBlockPos var6 = new FCUtilsBlockPos(var2, var3, var4, var5);
            Block var7 = Block.blocksList[var1.getBlockId(var6.i, var6.j, var6.k)];

            if (var7 != null && (var7.blockMaterial.isSolid() || var7.GetItemIDDroppedOnSaw(var1, var6.i, var6.j, var6.k) > 0 || var7.DoesBlockDropAsItemOnSaw(var1, var6.i, var6.j, var6.k)))
            {
                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "minecart.base", 1.5F + var1.rand.nextFloat() * 0.1F, 1.9F + var1.rand.nextFloat() * 0.1F);
                var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, 20 + var1.rand.nextInt(4));
            }
        }
    }

    public AxisAlignedBB GetBlockBoundsFromPoolForBaseHeight(IBlockAccess var1, int var2, int var3, int var4, float var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);

        switch (var6)
        {
            case 0:
                return AxisAlignedBB.getAABBPool().getAABB(0.0D, (double)(1.0F - var5), 0.0D, 1.0D, 1.0D, 1.0D);

            case 1:
                return AxisAlignedBB.getAABBPool().getAABB(0.0D, 0.0D, 0.0D, 1.0D, (double)var5, 1.0D);

            case 2:
                return AxisAlignedBB.getAABBPool().getAABB(0.0D, 0.0D, (double)(1.0F - var5), 1.0D, 1.0D, 1.0D);

            case 3:
                return AxisAlignedBB.getAABBPool().getAABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, (double)var5);

            case 4:
                return AxisAlignedBB.getAABBPool().getAABB((double)(1.0F - var5), 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

            default:
                return AxisAlignedBB.getAABBPool().getAABB(0.0D, 0.0D, 0.0D, (double)var5, 1.0D, 1.0D);
        }
    }

    void EmitSawParticles(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        float var7 = (float)var2;
        float var8 = (float)var3;
        float var9 = (float)var4;
        float var10 = 0.0F;
        float var11 = 0.0F;

        switch (var6)
        {
            case 0:
                var7 += 0.5F;
                var9 += 0.5F;
                var10 = 1.0F;
                break;

            case 1:
                var7 += 0.5F;
                var9 += 0.5F;
                ++var8;
                var10 = 1.0F;
                break;

            case 2:
                var7 += 0.5F;
                var8 += 0.5F;
                var10 = 1.0F;
                break;

            case 3:
                var7 += 0.5F;
                var8 += 0.5F;
                ++var9;
                var10 = 1.0F;
                break;

            case 4:
                var8 += 0.5F;
                var9 += 0.5F;
                var11 = 1.0F;
                break;

            default:
                var8 += 0.5F;
                var9 += 0.5F;
                ++var7;
                var11 = 1.0F;
        }

        for (int var12 = 0; var12 < 5; ++var12)
        {
            float var13 = var7 + (var5.nextFloat() - 0.5F) * var10;
            float var14 = var8 + var5.nextFloat() * 0.1F;
            float var15 = var9 + (var5.nextFloat() - 0.5F) * var11;
            var1.spawnParticle("smoke", (double)var13, (double)var14, (double)var15, 0.0D, 0.0D, 0.0D);
        }
    }

    protected void SawBlockToFront(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        FCUtilsBlockPos var7 = new FCUtilsBlockPos(var2, var3, var4, var6);

        if (!var1.isAirBlock(var7.i, var7.j, var7.k))
        {
            Block var8 = Block.blocksList[var1.getBlockId(var7.i, var7.j, var7.k)];

            if (var8 != null)
            {
                if (var8.doesBlockBreakStonecutter(var1, var7.i, var7.j, var7.k))
                {
                    this.BreakSaw(var1, var2, var3, var4);
                }
                else if (var8.onBlockStonecut(var1, var7.i, var7.j, var7.k, var2, var3, var4))
                {
                    this.EmitSawParticles(var1, var7.i, var7.j, var7.k, var5);
                }
            }
        }
    }

    public void BreakSaw(World var1, int var2, int var3, int var4)
    {
        this.DropComponentItemsOnBadBreak(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 1.0F);
        var1.playAuxSFX(2235, var2, var3, var4, 0);
        var1.setBlockWithNotify(var2, var3, var4, 0);
    }

    public boolean CanOutputMechanicalPower()
    {
        return false;
    }

    public boolean CanInputMechanicalPower()
    {
        return true;
    }

    public boolean IsInputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return FCUtilsMechPower.IsBlockPoweredByAxle(var1, var2, var3, var4, this);
    }

    public boolean CanInputAxlePowerToFacing(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        return var5 != var6;
    }

    public boolean IsOutputtingMechanicalPower(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public void Overpower(World var1, int var2, int var3, int var4)
    {
        this.BreakSaw(var1, var2, var3, var4);
    }
    
    //CLIENT ONLY
    private Icon iconFront;
    private Icon iconSide;
    private Icon iconBladeOff;
    private Icon iconBladeOn;

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        super.registerIcons(var1);
        this.iconFront = var1.registerIcon("autoPlusBlockStonecutter_front");
        this.iconSide = var1.registerIcon("autoPlusBlockStonecutter_side");
        this.iconBladeOff = var1.registerIcon("autoPlusBlockStonecutterBlade_off");
        this.iconBladeOn = var1.registerIcon("autoPlusBlockStonecutterBlade_on");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return var1 == 1 ? this.iconFront : (var1 == 0 ? this.blockIcon : this.iconSide);
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public Icon getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        int var6 = this.GetFacing(var1, var2, var3, var4);
        return var5 == var6 ? this.iconFront : (var5 == Facing.oppositeSide[var6] ? this.blockIcon : this.iconSide);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (this.IsBlockOn(var1, var2, var3, var4))
        {
            this.EmitSawParticles(var1, var2, var3, var4, var5);
        }
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.m_currentBlockRenderer.ShouldSideBeRenderedBasedOnCurrentBounds(var2, var3, var4, var5);
    }

    public boolean RenderBlock(RenderBlocks var1, int var2, int var3, int var4)
    {
    	var1.renderPistonBase(this, var2, var3, var4, true);
    	
        IBlockAccess var5 = var1.blockAccess;
        float var6 = 0.5F;
        float var7 = 0.5F;
        float var8 = 0.75F;
        int var9 = this.GetFacing(var5, var2, var3, var4);
        //var1.setRenderBounds(this.GetBlockBoundsFromPoolBasedOnState(var1.blockAccess, var2, var3, var4));
        //var1.renderStandardBlock(this, var2, var3, var4);
        var6 = 0.3125F;
        var7 = 0.0078125F;
        var8 = 0.25F;

        switch (var9)
        {
            case 0:
                var1.setRenderBounds((double)(0.5F - var6), 0.0D, (double)(0.5F - var7), (double)(0.5F + var6), 0.9990000128746033D, (double)(0.5F + var7));
                var1.SetUvRotateEast(3);
                var1.SetUvRotateWest(3);
                var1.SetUvRotateSouth(1);
                var1.SetUvRotateNorth(2);
                var1.SetUvRotateBottom(3);
                break;

            case 1:
                var1.setRenderBounds((double)(0.5F - var6), 0.0010000000474974513D, (double)(0.5F - var7), (double)(0.5F + var6), 1.0D, (double)(0.5F + var7));
                var1.SetUvRotateSouth(2);
                var1.SetUvRotateNorth(1);
                break;

            case 2:
                var1.setRenderBounds((double)(0.5F - var6), (double)(0.5F - var7), 0.0D, (double)(0.5F + var6), (double)(0.5F + var7), (double)var8);
                var1.SetUvRotateSouth(3);
                var1.SetUvRotateNorth(3);
                var1.SetUvRotateEast(4);
                var1.SetUvRotateWest(3);
                break;

            case 3:
                var1.setRenderBounds((double)(0.5F - var6), (double)(0.5F - var7), (double)(1.0F - var8), (double)(0.5F + var6), (double)(0.5F + var7), 1.0D);
                var1.SetUvRotateSouth(4);
                var1.SetUvRotateNorth(3);
                var1.SetUvRotateTop(3);
                var1.SetUvRotateBottom(3);
                break;

            case 4:
                var1.setRenderBounds(0.0D, (double)(0.5F - var7), (double)(0.5F - var6), (double)var8, (double)(0.5F + var7), (double)(0.5F + var6));
                var1.SetUvRotateEast(4);
                var1.SetUvRotateWest(3);
                var1.SetUvRotateTop(2);
                var1.SetUvRotateBottom(1);
                var1.SetUvRotateNorth(3);
                var1.SetUvRotateSouth(4);
                break;

            default:
                var1.setRenderBounds((double)(1.0F - var8), (double)(0.5F - var7), (double)(0.5F - var6), 1.0D, (double)(0.5F + var7), (double)(0.5F + var6));
                var1.SetUvRotateEast(3);
                var1.SetUvRotateWest(4);
                var1.SetUvRotateTop(1);
                var1.SetUvRotateBottom(2);
                var1.SetUvRotateSouth(4);
                var1.SetUvRotateNorth(3);
        }

        var1.SetRenderAllFaces(true);
        Icon var10 = this.iconBladeOff;

        if (this.IsBlockOn(var5, var2, var3, var4))
        {
            var10 = this.iconBladeOn;
        }

        FCClientUtilsRender.RenderStandardBlockWithTexture(var1, this, var2, var3, var4, var10);
        var1.SetRenderAllFaces(false);
        var1.ClearUvRotation();
        return true;
    }

    public void RenderBlockAsItem(RenderBlocks var1, int var2, float var3)
    {
        var1.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
        FCClientUtilsRender.RenderInvBlockWithMetadata(var1, this, -0.5F, -0.5F, -0.5F, 1);
        var1.setRenderBounds(0.1875D, 0.0010000000474974513D, 0.4921875D, 0.8125D, 1.0D, 0.5078125D);
        FCClientUtilsRender.RenderInvBlockWithTexture(var1, this, -0.5F, -0.5F, -0.5F, this.iconBladeOff);
    }
}
