import com.google.common.base.Preconditions;
import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/10/28.
 */

public class Java8Learn {

    /**
     * 简单的stream学习，包括map、filter、reduce
     */
    @Test
    public void streamTest1(){
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

        //reduce 学习
        List<Integer> list=Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        int total = list.stream().reduce((sum,one)->
            sum+one).get();
        System.out.println(total);

        //利用collect转换成常用集合
        list.stream().collect(Collectors.toList());
    }

    @Test
    public void lambdaTest1(){
        List<Integer> list= Lists.newArrayList(1,2,3,4,5);
        //利用lambda快速迭代
        list.forEach(i-> System.out.println(i));
    }

    /**
     * Predicate就是lambda表达式的声明式。
     * 这个函数是将list中的所有满足func的元素打印出来
     * @param names
     * @param func
     */
    private void myFilter(List names, Predicate func){
        names.stream().filter(name->func.test(name)).forEach(i-> System.out.println(i));
    }

    /**
     * 简单的lambda学习，自定义lambda和lambda的链式连接
     */
    @Test
    public void myLambda(){
        List<Integer> list=Lists.newArrayList(1,3,5,76,23,553,2,4,7);
        myFilter(list,i->(Integer)i<10);

        //利用Predicate的and、or等关系操作可以方便的实现集合操作
        Predicate odd=i->(Integer)i%2==1;
        Predicate gt10=i->(Integer)i>10;
        list.stream().filter(odd.and(gt10)).forEach(i-> System.out.println(i));
    }
}
