package com.itheima.demo01;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Auther:bingli
 * ClassName:MyProxy
 * Package:com.itheima.demo01
 * Description:
 *
 * @date:2023/3/26-03-26 23:28
 * @author:2550976002@qq.com
 * @version:1.0
 */
//JDK动态代理
public class MyProxy implements InvocationHandler {

    //声明目标类接口
    private UserDao userDao;

    //创建代理方法
    public Object createProxy(UserDao userDao) {
        this.userDao = userDao;
        // 1.类加载器
        ClassLoader classLoader = MyProxy.class.getClassLoader();
        // 2.被代理对象实现的所有接口
        Class[] classes = userDao.getClass().getInterfaces();
        // 3.使用代理类，进行增强，返回的是代理对象
        return Proxy.newProxyInstance(classLoader, classes, this);
    }

    /**
     * 所有动态代理类的方法调用，都会交由invoke()方法去处理
     * proxy 被代理的对象
     * method 将要被执行的方法信息（反射）
     * args 执行方法时需要的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 创建切面对象
        MyAspect myAspect = new MyAspect();
        // 前增强
        myAspect.check_Permissions();
        // 在目标类上调用方法，并传入参数
        Object obj = method.invoke(userDao, args);
        // 后增强
        myAspect.log();
        return obj;
    }
}
