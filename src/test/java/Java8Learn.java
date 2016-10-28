import com.google.common.base.Preconditions;
import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/10/28.
 */

public class Java8Learn {

    @Test
    public void streamTest(){
        List<Integer> myList=new ArrayList<>();
        myList.add(1);
        myList.add(2);
        myList.add(3);

        //一下迭代操作不会影响原序列

        //利用stream完成遍历运算，即对一个集合内的每个对象作用一个函数，得到返回值的集合
        Stream<Integer> myStream = myList.stream().map(i->i+1);
        //注意这里toArray的参数
        List<Integer> addList = Lists.newArrayList(myStream.toArray(Integer[]::new));
        Preconditions.checkArgument(Objects.equals(addList,Lists.newArrayList(2,3,4)));

        //filter完成筛选,选出所有奇数，对一个集合的每个元素作用一个函数，得到返回值为true的元素的新集合
        Integer[] arr = myList.stream().filter(i->i%2==1).toArray(Integer[]::new);
        Preconditions.checkArgument(Objects.equals(Lists.newArrayList(arr),Lists.newArrayList(1,3)));
    }
}
