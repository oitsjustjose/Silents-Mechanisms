package net.silentchaos512.mechanisms.init;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.mechanisms.SilentMechanisms;
import net.silentchaos512.mechanisms.block.alloysmelter.AlloySmelterBlock;
import net.silentchaos512.mechanisms.block.batterybox.BatteryBoxBlock;
import net.silentchaos512.mechanisms.block.compressor.CompressorBlock;
import net.silentchaos512.mechanisms.block.crusher.CrusherBlock;
import net.silentchaos512.mechanisms.block.dryingrack.DryingRackBlock;
import net.silentchaos512.mechanisms.block.electricfurnace.ElectricFurnaceBlock;
import net.silentchaos512.mechanisms.block.generator.coal.CoalGeneratorBlock;
import net.silentchaos512.mechanisms.block.generator.lava.LavaGeneratorBlock;
import net.silentchaos512.mechanisms.block.mixer.MixerBlock;
import net.silentchaos512.mechanisms.block.pump.PumpBlock;
import net.silentchaos512.mechanisms.block.refinery.RefineryBlock;
import net.silentchaos512.mechanisms.block.wire.WireBlock;
import net.silentchaos512.mechanisms.util.MachineTier;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class ModBlocks {
    public static final List<DryingRackBlock> DRYING_RACKS = new ArrayList<>();
    public static AlloySmelterBlock basicAlloySmelter;
    public static AlloySmelterBlock alloySmelter;
    public static CrusherBlock basicCrusher;
    public static CrusherBlock crusher;
    public static CompressorBlock compressor;
    public static ElectricFurnaceBlock electricFurnace;
    public static RefineryBlock refinery;
    public static MixerBlock mixer;
    public static Block pump;
    public static CoalGeneratorBlock coalGenerator;
    public static LavaGeneratorBlock lavaGenerator;
    public static BatteryBoxBlock batteryBox;
    public static WireBlock wire;
    public static FlowingFluidBlock oil;
    public static FlowingFluidBlock diesel;

    private ModBlocks() {}

    public static void registerAll(RegistryEvent.Register<Block> event) {
        Arrays.stream(Ores.values()).forEach(ore -> register(ore.getName() + "_ore", ore.getBlock()));
        Arrays.stream(Metals.values()).forEach(metal -> register(metal.getName() + "_block", metal.asBlock()));

        DRYING_RACKS.add(register("oak_drying_rack", new DryingRackBlock()));
        DRYING_RACKS.add(register("birch_drying_rack", new DryingRackBlock()));
        DRYING_RACKS.add(register("spruce_drying_rack", new DryingRackBlock()));
        DRYING_RACKS.add(register("jungle_drying_rack", new DryingRackBlock()));
        DRYING_RACKS.add(register("dark_oak_drying_rack", new DryingRackBlock()));
        DRYING_RACKS.add(register("acacia_drying_rack", new DryingRackBlock()));

        register("stone_machine_frame", new GlassBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3, 10).sound(SoundType.STONE)));
        register("alloy_machine_frame", new GlassBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3, 10).sound(SoundType.METAL)));

        basicAlloySmelter = register("basic_alloy_smelter", new AlloySmelterBlock(MachineTier.BASIC));
        alloySmelter = register("alloy_smelter", new AlloySmelterBlock(MachineTier.STANDARD));
        basicCrusher = register("basic_crusher", new CrusherBlock(MachineTier.BASIC));
        crusher = register("crusher", new CrusherBlock(MachineTier.STANDARD));
        compressor = register("compressor", new CompressorBlock());
        electricFurnace = register("electric_furnace", new ElectricFurnaceBlock());
        refinery = register("refinery", new RefineryBlock());
        mixer = register("mixer", new MixerBlock());
        pump = register("pump", new PumpBlock());
        coalGenerator = register("coal_generator", new CoalGeneratorBlock());
        lavaGenerator = register("lava_generator", new LavaGeneratorBlock());
        batteryBox = register("battery_box", new BatteryBoxBlock());
        wire = register("wire", new WireBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1, 5)));

        // Fluids (no items)
        oil = register("oil", createFluidBlock(() -> ModFluids.OIL), null);
        diesel = register("diesel", createFluidBlock(() -> ModFluids.DIESEL), null);
    }

    private static <T extends Block> T register(String name, T block) {
        BlockItem item = new BlockItem(block, new Item.Properties().group(SilentMechanisms.ITEM_GROUP));
        return register(name, block, item);
    }

    private static <T extends Block> T register(String name, T block, @Nullable BlockItem item) {
        ResourceLocation id = SilentMechanisms.getId(name);
        block.setRegistryName(id);
        ForgeRegistries.BLOCKS.register(block);

        if (item != null) {
            ModItems.BLOCKS_TO_REGISTER.put(name, item);
        }

        return block;
    }

    private static FlowingFluidBlock createFluidBlock(Supplier<FlowingFluid> fluid) {
        return new FlowingFluidBlock(fluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
    }
}
