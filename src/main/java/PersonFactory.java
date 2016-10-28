import com.base.Person;

/**
 * Created by Administrator on 2016/10/28.
 */
public interface PersonFactory<P extends Person> {
    P create(String firstName,String lastName);
}
