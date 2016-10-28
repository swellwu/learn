/**
 * Created by Administrator on 2016/10/28.
 */

/**
 * java8接口可以有默认实现
 */
public interface Formula {
    double caculate(int a);

    default double sqrt(int a){
        return Math.sqrt(a);
    }
}
