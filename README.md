# Rediser 介绍
Rediser是一个缓存工具，是在Jedis之上封装的一个工具，为的是让业务方使用更简单。目前还在开发中，敬请关注

### 快速使用
使用很简单，不需要close进行释放，资源会在系统正常退出前关闭
```java
@Test
@SneakyThrows
public void testCreate1() {
    Rediser rediser = new Rediser();
    rediser.bind("localhost", 6379);
    rediser.start();

    rediser.set("rediser", "testCreate");
    Assert.assertEquals(rediser.get("rediser"), "testCreate");
}

@Test
public void testCreate2() {
    Rediser rediser = new Rediser();
    rediser.bind("localhost:6379");
    
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(1000);
    rediser.setPoolConfig(poolConfig);

    rediser.start();

    rediser.set("rediser", "testCreate");
    Assert.assertEquals(rediser.get("rediser"), "testCreate");
}
```

### 过期时间配置
在业务代码中经常需要配置过期时间，很多业务没有办法统一进行配置，或者说不好整理，我们这里提供一个好的方式，如下使用
```java
@Test
public void testEnumSetNx() {
    Rediser rediser = new Rediser();
    rediser.bind("localhost:6379");
    rediser.start();

    rediser.del(LK_U, 12);
    Assert.assertEquals(rediser.setNx(LK_U, "value", 12), new Integer(1));
    show(rediser.get(LK_U, 12));
}
```
其中LK_U是我们的业务枚举，其中包含版本号和过期时间还有key，比如如下，这里有个注解 @RediserKeyEnum，其中有三个属性，主要是用来配置和解析使用，其中key通过占位符方式，在使用的时候拼接key，版本号也是用来拼接key，而过期时间是redis的过期时间。配置时候这样配置，使用时候就很简单，如上所示
```java
@RediserKeyEnum(keyField = "key", expireTimeField = "expiredTime", versionField = "version")
public enum BusinessCacheEnum1 {

    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     */
    LK_U_I("LK_U_I_{0}", TimeUnit.DAYS, 1.1),
    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     * 过期：3秒钟过期
     */
    LK_U("LK_U_I_{0}", 3, TimeUnit.SECONDS, 1.0),
    /**
     * 结构：ValueRedisTemplate
     * key:用户对象的unionId
     * value：用户对象的id
     */
    LK_PER("LK_PER_{0}", 0, TimeUnit.SECONDS, 1.3);

    private String key;
    /**
     * 过期时间，单位：seconds。如果不设置则默认为5秒
     */
    private long expiredTime = 5 * 1000;
    /**
     * 版本号
     */
    private double version = 1.0;

    BusinessCacheEnum1(String key, TimeUnit timeUnite, double version) {
        this.key = key;
        this.expiredTime = timeUnite.toMillis(1);
        this.version = version;
    }

    BusinessCacheEnum1(String key, int expiredTime, TimeUnit timeUnite, double version) {
        this.key = key;
        this.expiredTime = timeUnite.toMillis(expiredTime);
        this.version = version;
    }
}
```

### 存放实体数据
我们这里也增加了一些api，用于存放实体数据，实体数据序列化目前采用FastJson，后续替换为Gson
```java
@Test
public void testEnumSetObject() {
    Rediser rediser = new Rediser();
    rediser.bind("localhost:6379");
    rediser.start();

    rediser.del(LK_U, 12);

    TestEntity entity = new TestEntity();
    entity.setAge(12);
    entity.setName("nihao");

    show(rediser.setObject(LK_U, entity, 12));
    show(rediser.getObject(TestEntity.class, LK_U, 1));
    show(rediser.getObject(TestEntity.class, LK_U, 12));
}
```
