package neresources.registry;

import neresources.api.utils.restrictions.BlockRestriction;

import java.util.*;

public class DimensionRegistry
{
    private static Map<BlockRestriction,Set<Integer>> registry = new HashMap<BlockRestriction, Set<Integer>>();
    private static Set<Integer> altDimensions = new TreeSet<Integer>();

    public static void registerDimension(BlockRestriction block, int dim)
    {
        Set<Integer> saved = registry.get(block);
        if (saved == null)
            saved = new TreeSet<Integer>();
        saved.add(dim);
        altDimensions.add(dim);
        registry.put(block,saved);
    }

    public static Set<Integer> getDimensions(BlockRestriction block)
    {
        if (registry.containsKey(block)) return registry.get(block);
        return null;
    }

    public static Set<Integer> getAltDimensions()
    {
        return altDimensions;
    }
}
