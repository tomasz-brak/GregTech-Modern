package com.gregtechceu.gtceu.api.data.worldgen.ores;

import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGeneratorUtils;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Holds a vein's {@link OreBlockPlacer}s for each of its blocks, grouped by chunk.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GeneratedVein {
    @Getter
    private final ChunkPos origin;

    @Getter
    private final IWorldGenLayer layer;

    private final Map<ChunkPos, Map<BlockPos, OreBlockPlacer>> generatedOres;

    @Getter
    public List<GeneratedVeinMetadata> metadata;

    /**
     * @param origin              The vein's origin chunk (NOT its actual center, which may be outside the origin chunk)
     * @param oresByPosition      The ore placers for each ore block position.<br>
     *                            Doesn't need to be ordered, grouping by chunks is done internally.
     */
    public GeneratedVein(ChunkPos origin, IWorldGenLayer layer, Map<BlockPos, OreBlockPlacer> oresByPosition, List<GeneratedVeinMetadata> metadata) {
        this.origin = origin;
        this.layer = layer;

        this.generatedOres = WorldGeneratorUtils.groupByChunks(oresByPosition);
        this.metadata = metadata;
    }

    /**
     * Retrieve the ore placers for all blocks inside the specified chunk.
     */
    public Map<BlockPos, OreBlockPlacer> consumeOres(ChunkPos chunk) {
        return this.generatedOres.getOrDefault(chunk, Map.of());
    }

    @Override
    public String toString() {
        return "GeneratedVein[origin=" + origin + ", chunks={" + generatedOres.keySet().stream().map(ChunkPos::toString).collect(Collectors.joining(", ")) + "}]";
    }

    public ChunkPos getOrigin() {
        return this.origin;
    }

    public String getLocalizedWaypointName(){
        return "test waypoint name";
    }
}
