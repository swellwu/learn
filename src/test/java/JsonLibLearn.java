import com.base.Person;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class JsonLibLearn {

    private Gson gson=new Gson();

    @Test
    public void baseTest(){
        //基本对象和json转换
        //@SerializedName("first_name")指定序列化后的字段名
        Person person=new Person("firstName","lastName");
        person.setFriendsList(Arrays.asList("1111","2222"));
        String json = gson.toJson(person);
        Person person1 = gson.fromJson(json,Person.class);
        Preconditions.checkArgument(person.equals(person1));
    }


    //泛型擦除问题
    @Test
    public void genericTest(){
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
        Preconditions.checkArgument(Arrays.asList("Android","Java","PHP").equals(stringList));
    }

//    gson泛型使用
//    public class Result<T> {
//        public int code;
//        public String message;
//        public T data;
//    }
//
//    //不再重复定义Result类
//    Type userType = new TypeToken<Result<User>>(){}.getType();
//    Result<User> userResult = gson.fromJson(json,userType);
//    User user = userResult.data;
//
//    Type userListType = new TypeToken<Result<List<User>>>(){}.getType();
//    Result<List<User>> userListResult = gson.fromJson(json,userListType);
//    List<User> users = userListResult.data;
}
