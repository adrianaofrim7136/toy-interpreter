package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GarbageCollectorTest {
    // Ex9 after new(v, 30): v -> (3, int), a -> (2, Ref(int))
    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()), new RefValue(2, new RefType(new IntType())));
    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20), 2, new RefValue(1, new IntType()),
            3, new IntValue(30), 4, new IntValue(7));

    @Test
    void keepsTheLocationsReachableThroughTheHeap() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        assertEquals(Set.of(1, 2, 3), GarbageCollector.safeGarbageCollector(roots, heap).keySet());
    }

    @Test
    void removesUnreachableLocations() {
        var kept = GarbageCollector.safeGarbageCollector(Set.of(3), heap);
        assertEquals(Set.of(3), kept.keySet());           // 1, 2 and 4 are garbage
    }

    @Test
    void followsChainsOfReferences() {
        Map<Integer, IValue> chain = Map.of(
                1, new RefValue(2, new IntType()), 2, new RefValue(3, new IntType()), 3, new IntValue(9));
        assertEquals(Set.of(1, 2, 3), GarbageCollector.reachableAddresses(Set.of(1), chain));
    }
}

