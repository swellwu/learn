import com.utils.PinYinUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/1/3.
 */
public class PinYin4JTest {
    @Test
    public void getFirstSpellTest(){
        String chinese="中国";
        String pin = PinYinUtil.getFirstSpell(chinese);
        assertEquals("zg",pin);
        chinese="云望";
        pin = PinYinUtil.getFirstSpell(chinese);
        assertEquals("yw",pin);
    }

    @Test
    public void test(){
        String chinese="大家好";
        String pin = PinYinUtil.getFullSpell(chinese);
        assertEquals("dajiahao",pin);
        chinese="swellwu早上好";
        pin = PinYinUtil.getFullSpell(chinese);
        assertEquals("swellwuzaoshanghao",pin);
    }
}
