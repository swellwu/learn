/**
 * Created by Administrator on 2016/10/28.
 */

//函数式接口
@FunctionalInterface
public interface Converter<F,T> {
    T convert(F from);
}
