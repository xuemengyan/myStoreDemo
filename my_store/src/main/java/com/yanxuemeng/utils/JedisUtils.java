package com.yanxuemeng.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

public class JedisUtils {
    private static JedisPool jedisPool = null;
      //读取配置文件的代码，只需要执行一次，则存放在静态代码块
     static {
//解析配置文件:jedis.properties
          //以往我们解析properties使用的是：Properties类,今天介绍一个新的工具
          //1:根据properties配置文件获取ResourceBundle对象
          ResourceBundle bundle = ResourceBundle.getBundle("jedis");//这里不用写后缀名
          //2:从bundle中根据键获取值
          int maxTotal = Integer.parseInt( bundle.getString("maxTotal"));
          int maxIdel = Integer.parseInt( bundle.getString("maxIdel"));
          String url = bundle.getString("url");
          int port = Integer.parseInt( bundle.getString("port"));


          //3:创建JedisPoolConfig对象
          JedisPoolConfig poolConfig = new JedisPoolConfig();
          //设置参数
          poolConfig.setMaxTotal(maxTotal);
          poolConfig.setMaxIdle(maxIdel);
         //4:创建连接池对象JedisPool
          jedisPool = new JedisPool(poolConfig, url, port);


      }
      //写一个方法，别人可以从连接池中获取一个连接
    public static Jedis getJedis(){
         return jedisPool.getResource();
    }
    //释放资源
    public static void close(Jedis jedis){
         if(jedis != null) {
             jedis.close();
         }
    }
}
