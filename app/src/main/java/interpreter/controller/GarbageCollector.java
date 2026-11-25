package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;
import java.util.*;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}

    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    /** The roots plus every address reachable by following RefValues stored in the heap. */
    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        Deque<Integer> toVisit = new ArrayDeque<>(roots);
        while (!toVisit.isEmpty()) {
            int address = toVisit.pop();
            if (heap.get(address) instanceof RefValue ref && reachable.add(ref.address()))
                toVisit.push(ref.address());   // new address found, explore it too
        }
        return reachable;
    }

    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> keep = reachableAddresses(roots, heap);
        return heap.entrySet().stream()
                .filter(e -> keep.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
