package net.bluemonster122.tutmod.worldgen;

import net.bluemonster122.tutmod.ModObjects;
import net.bluemonster122.tutmod.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenOres implements IWorldGenerator {
  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    switch (world.provider.getDimension()) {
      case 0: // The Overworld
        generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
      case 1: // The End
        // NOOP
      case -1: // The Nether
        //NOOP
    }
  }
  
  private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    generateOre(ModObjects.block_ore.getDefaultState().withProperty(BlockOre.VARIENTS, BlockOre.OreTypes.BLUE), world, random, chunkX << 4, chunkZ << 4, 0, 256, 10 + random.nextInt(15), 8);
  }
  
  private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances){
    for (int i = 0; i < chances; i++) {
      BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(maxY - minY), z + random.nextInt(16));
      WorldGenMinable generator = new WorldGenMinable(ore, size);
      generator.generate(world, random, pos);
    }
  }
  
  /*
  
  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    // This checks which dimension is being generated, and then generating the relevant things.
    switch (world.provider.getDimension()) {
      case 0:  //surface
        generateSurface(world, random, chunkX * 16, chunkZ * 16);
      case 1:  //end
        generateEnd(world, random, chunkX * 16, chunkZ * 16);
      case -1:  //nether
        generateNether(world, random, chunkX * 16, chunkZ * 16);
    }
  }
  
  private void generateNether(World world, Random random, int i, int i1) {
    
  }
  
  private void generateEnd(World world, Random random, int i, int i1) {
    
  }
  
  private void generateSurface(World world, Random random, int x, int z) {
    // common
    this.addSpawnChance(ModObjects.block_ore.getDefaultState().withProperty(BlockOre.VARIENTS, BlockOre.OreTypes.BLUE), world, random, x, z, 16, 16, 4 + random.nextInt(6), 30, 0, 68);
  }
  
  private void addSpawnChance(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, float chanceToSpawn, int minY, int maxY) {
    if (chanceToSpawn >= 1) {
      for (int i = 0; i < chanceToSpawn; i++) {
        addOreSpawn(block, world, random, blockXPos, blockZPos, maxX, maxZ, maxVeinSize, minY, maxY);
      }
    } else if (chanceToSpawn > random.nextFloat()) {
      addOreSpawn(block, world, random, blockXPos, blockZPos, maxX, maxZ, maxVeinSize, minY, maxY);
    }
  }
  
  private void addOreSpawn(IBlockState block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ, int maxVeinSize, int minY, int maxY) {
    int posX = blockXPos + random.nextInt(maxX);
    int posY = minY + random.nextInt(maxY - minY);
    int posZ = blockZPos + random.nextInt(maxZ);
    (new WorldGenMinable(block, maxVeinSize, b -> b.equals(Blocks.STONE.getDefaultState()))).generate(world, random, new BlockPos(posX, posY, posZ));
  }
  
  */
}
