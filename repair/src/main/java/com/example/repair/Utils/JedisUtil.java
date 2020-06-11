package com.example.repair.Utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

//jedis连接池
public class JedisUtil {
    private static JedisPool jp=null;
    private static String host=null;
    private static int port;
    private static int maxIdle;
    private static int maxTotal;

    static {
        ResourceBundle rb=ResourceBundle.getBundle("redis");
        host=rb.getString("redis.host");
        port= Integer.parseInt(rb.getString("redis.port"));
        maxIdle= Integer.parseInt(rb.getString("redis.maxIdle"));
        maxTotal= Integer.parseInt(rb.getString("redis.maxTotal"));
        JedisPoolConfig jpc=new JedisPoolConfig();
        jpc.setMaxIdle(maxIdle);  //最大活动连接数
        jpc.setMaxTotal(maxTotal);  //最大连接数
        jp=new JedisPool(jpc,host,port);
    }

    public static Jedis getJedis(){
        return jp.getResource();
    }

    public static void main(String[] args) {
        JedisUtil.getJedis();
    }
}
