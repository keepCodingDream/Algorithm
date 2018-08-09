package leetCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 从正整数 N 开始，我们按任何顺序（包括原始顺序）将数字重新排序，注意其前导数字不能为零。
 * <p>
 * 如果我们可以通过上述方式得到 2 的幂，返回 true；否则，返回 false。
 *
 * @author tracy.
 * @create 2018-07-18 16:42
 **/
public class ReorderedPowerOf2 {
    public static void main(String[] args) {
        System.out.println(new ReorderedPowerOf2Solution().reorderedPowerOf2(268341));
    }
}

class ReorderedPowerOf2Solution {
    public boolean reorderedPowerOf2(int N) {
        List<char[]> array = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            array.add(String.valueOf(1 << i).toCharArray());
        }
        char[] item = String.valueOf(N).toCharArray();
        for (int i = 0; i < 30; i++) {
            char[] camp = array.get(i);
            int matchCount = 0;
            if (camp.length == item.length) {
                Set<Integer> contains = new HashSet<>(camp.length);
                for (char charItem : item) {
                    for (int k = 0; k < camp.length; k++) {
                        if (charItem == camp[k] && !contains.contains(k)) {
                            matchCount++;
                            contains.add(k);
                            break;
                        }
                    }
                }
                if (matchCount == item.length) {
                    return true;
                }
            }
        }
        return false;
    }
}
