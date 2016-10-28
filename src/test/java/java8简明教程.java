import com.base.Person;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/10/28.
 */
public class java8简明教程 {

    private Logger log = LoggerFactory.getLogger(getClass());
    private Gson gson = new Gson();

    @Test
    public void 接口中可以有方法的默认实现() {
        Formula formula = a -> a * 10;

        Preconditions.checkArgument(formula.caculate(10) == 100);
        //接口默认实现方法
        Preconditions.checkArgument(formula.sqrt(16) == 4.0);
    }

    @Test
    public void 对list排序() {
        List<String> names = Lists.newArrayList("aa", "cc", "bb", "abc");
        //lambda实现排序
        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
        System.out.println(gson.toJson(names));
    }

    @Test
    public void 函数式接口() {
        Converter<String, Integer> converter = from -> Integer.valueOf(from);
        System.out.println(converter.convert("123"));
    }

    @Test
    public void 方法和构造函数引用() {
        //类似于c++中的bind方法绑定
        Converter<String, Integer> converter = Integer::valueOf;
        System.out.println(converter.convert("123"));

        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("张", "三丰");
        System.out.println(gson.toJson(person));
    }

    @Test
    public void predicate接口学习() {
        //就是一个函数接口，test，和取反函数negate
        Predicate<String> predicate = s -> s.length() > 0;

        predicate.test("foo");
        //返回！test()
        predicate.negate().test("foo");

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }

    /**
     * 有很多接口是guava库中广泛使用后移植到标准库中的
     */
    @Test
    public void 内置函数式接口学习() {
        //Function接受一个参数，返回一个结果
        Function<String, Integer> toInteger = Integer::valueOf;
        //利用andThen串联,可以形成链式调用
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        System.out.println(backToString.apply("123"));

        //Supplier没有输入参数
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();

        //Consumer代表了在一个输入参数上需要进行的操作。
        Consumer<Person> greeter = (p) -> System.out.println("Hello, " +
                p.getFirstName());
        greeter.accept(new Person("Luke", "Skywalker"));

        //比较函数接口
        Comparator<Person> comparator = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        comparator.compare(p1, p2); // > 0
        comparator.reversed().compare(p1, p2); // < 0

        //optional
        Optional<String> optional = Optional.of("bam");
        optional.isPresent(); // true
        optional.get(); // "bam"
        optional.orElse("fallback"); // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));
    }

    /**
     * 流的使用包括三部分，生成流-》中间操作-》聚合操作
     * 中间操作返回流，可以链式调用，聚合操作表示操作的完成，不返回流
     * 无法记录链式操作
     */
    @Test
    public void streams操作学习() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        //filter
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

        //sorted
        stringCollection
                .stream()
                .sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

        //map
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        //match操作，返回boolean
        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA); // true
        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA); // false
        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ); // true

        //count
        long startsWithB =
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b"))
                        .count();
        System.out.println(startsWithB); // 3

        //reduce
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
    }

    /**
     * 并行流可以充分利用多线程的优势
     */
    @Test
    public void parallelStream并行流学习() {

        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        //顺序排序时间
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms",
                millis));

        //并行排序时间，快了将近50%
        long t01 = System.nanoTime();
        long count1 = values.parallelStream().sorted().count();
        System.out.println(count);
        long t11 = System.nanoTime();
        long millis1 = TimeUnit.NANOSECONDS.toMillis(t11 - t01);
        System.out.println(String.format("parallel sort took: %d ms", millis1));

        //集合Map不支持流操作，但也有很多使用方法
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(val));
        //如果存在key，并且value不为null，则将key和value作为参数计算新的value，如果新value为null，则删除key-value对，否则更新value
        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3); // val33
        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9); // false
        //不存在并且新的value不为null则添加
        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23); // true
        map.computeIfAbsent(3, num -> "bam");
        System.out.println(map.get(3));  // val33

        //非常实用的方法
        map.getOrDefault(42, "not found"); // not found

        //合并操作先看map中是否没有特定的key/value存在，如果是，则把key/value存入map，否则merging函数就会被调用，对现有的数值进行修改。
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue)
        );
        map.get(9); // val9
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);
    }

    @Test
    public void 时间api学习() {
        //clock
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
        log.info("millis={},legacyDate={}", millis, legacyDate);

        //详见p17
        System.out.println(ZoneId.getAvailableZoneIds());

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());


        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        System.out.println(now1.isBefore(now2)); // false
        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
        System.out.println(hoursBetween); // -3
        System.out.println(minutesBetween); // -239

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late); // 23:59:59
        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMAN);
        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime); // 13:37

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);

        DateTimeFormatter germanFormatter1 =
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);
        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter1);
        System.out.println(xmas); // 2014-12-24

        //详见p19。。。
    }

    @Test
    public void 数据流的学习1() {

        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println); // a1
        Stream.of("a1", "a2", "a3")

                .findFirst()
                .ifPresent(System.out::println); // a1
        //基本数据类型和对应的流
        IntStream.range(1, 4)
                .forEach(System.out::println);

        Arrays.stream(new int[]{1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println); // 5.0

        //mapToInt，对象流转换成基本流
        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println); // 3
        //基本流转换成对象流
        IntStream.range(1, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
    }

    @Test
    public void 数据流处理顺序() {
        //衔接操作只在终止操作调用时才执行
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                });

        //每一个元素，执行完流水线在执行下个元素
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));

        //将filter放在最前面可以减少调用次数
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    @Test
    public void 复用数据流() {
        //数据流每次调用终止操作就会关闭，利用Supplier来提供新的数据流，每次调用get都会提供一个新的数据流
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));
        streamSupplier.get().anyMatch(s -> true); // ok
        streamSupplier.get().noneMatch(s -> true); // ok

    }

    @Test
    public void collect操作() {
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", "18"),
                        new Person("Peter", "23"),
                        new Person("Pamela", "23"),
                        new Person("David", "12"));
        //collect用于将流放入集合返回
        List<Person> filtered = persons
                .stream()
                .filter(p -> p.getFirstName().startsWith("P"))
                .collect(Collectors.toList());
        System.out.println(filtered); // [Peter, Pamela]

        //也可以放到map中
        Map<String, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.getFirstName()));
        personsByAge.forEach((age, p) -> System.out.format("age %s: %s\n", age,
                gson.toJson(p)));
        //还有许多collect的高级操作详见P32
    }

    @Test
    public void flatMap操作() {
        //详见P32.
    }

    @Test
    public void reduce操作() {
        //详见37
    }

    @Test
    public void javaExecutor() {
        //线程池的创建
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });

        //关闭线程池，首先温柔的关闭，如果超出时间还没关闭，则暴力关闭
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }

    }

    /**
     * callable和runnable类似，但是返回一个值
     */
    @Test
    public void callable和Future() throws ExecutionException, InterruptedException {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        //callable的结果通过Future对象获取，future.get()会阻塞知道callable返回结果
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);
        System.out.println("future done? " + future.isDone());
        Integer result = null;
        //future可以设置超时时间
        try {
            result = future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
    }

    /**
     * 批量提交操作
     *
     * @throws InterruptedException
     */
    @Test
    public void invokeAll学习() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");
        //执行所有的callable
        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }

    Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    /**
     * 获得第一个完成的callable
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void invokeAny学习() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3));
        String result = executor.invokeAny(callables);
        System.out.println(result);
        // => task2
    }

    @Test
    public void 调度线程池学习() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Scheduling: " + System
                .nanoTime());
        //延迟调用
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.
                SECONDS);
        TimeUnit.MILLISECONDS.sleep(2337);
        //获取剩余的延时,如果为正表示还未执行，为负表示已经执行了。
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms", remainingDelay);

    }

    @Test
    public void 线程池以固定间隔调用() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            System.out.println("Scheduling: " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        int initialDelay = 0;
        int period = 1;
        //每隔period间隔调用一次任务task，如果执行任务的时间比period长，那么会延时到上一次执行完成
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
        executor.shutdown();

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        Runnable task2 = () -> {
            System.out.println("Scheduling: " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()));
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };
        //已固定间隔调用任务，这个固定间隔是指上一次完成到下一次开始的时间
        executor2.scheduleWithFixedDelay(task2, 0, 1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
    }
}
