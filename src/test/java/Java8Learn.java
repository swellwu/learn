import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/10/28.
 */

public class Java8Learn {

    @Test
    public void streamTest(){
        List<Integer> myList=new ArrayList<Integer>();
        myList.add(1);
        myList.add(2);
        myList.add(3);

        Stream<Integer> myStream = myList.stream();
    }
}
