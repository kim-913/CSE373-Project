package problems;


import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Parts b.i and b.ii should go here.
 *
 * (Implement `contains3` and `intersect` as described by the spec.
 *  See the spec on the website for examples and more explanation!)
 */
public class MapProblems {

    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> map = new HashMap<>();
        for (String name : m1.keySet()) {
            if (m1.get(name).equals(m2.get(name))) {
                if (m2.containsKey(name)) {
                    map.put(name, m1.get(name));
                }
            }
        }
        return map;
    }

    public static boolean contains3(List<String> input) {
        boolean bool = false;
        Map<String, Integer> map = new HashMap<>();
        for (String word: input) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
                if (map.get(word)>= 3) {
                    bool = true;
                }
            } else {
                map.put(word, 1);
            }
        }
        return bool;
    }
}
