package ru.sbrf;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
//        Method[] methods = LinkedList.class.getMethods();
//        int i = 0;
//        for(Method method: methods)
//            System.out.println(method.getName() + " [" + i++ + "]");
//        Class[] parameterTypes = methods[43].getParameterTypes();
//
//        for(Class parameterType: parameterTypes)
//            System.out.println(parameterType.getClass());
//
//
        Service testService = new UseService();
        Service cacheTestService = (Service) Proxy.newProxyInstance(
                UseService.class.getClassLoader(),
                UseService.class.getInterfaces(),
                new CacheProxy(testService));
        cacheTestService.doHardWork(4L);
        cacheTestService.doHardWork(4L);
        cacheTestService.doHardWork(6L);
        cacheTestService.doHardWork(6L);
        cacheTestService.doHardWork((long) 4);
        cacheTestService.doHardWork(8L);
        cacheTestService.doHardWork();
        cacheTestService.doHardWork(8L);
        cacheTestService.doHardWork();
        cacheTestService.doHardWork(6L);
        cacheTestService.doVeryHardWork(5, "Зря...");
        cacheTestService.doVeryHardWork(5, "Зря...");
        cacheTestService.doVeryHardWork(5, "Что-то изменилось?");
        cacheTestService.doVeryHardWork(5, "Что-то изменилось?");
        cacheTestService.doVeryHardWork(7, "Что-то изменилось?");
        cacheTestService.doVeryHardWork(7, "Что-то изменилось?");
        cacheTestService.doWorkList(9);
        cacheTestService.doWorkList(9);
        cacheTestService.doWorkSet(9);
        cacheTestService.doWorkSet(9);
        Object[] objects1;
        Object[] objects2;
        objects1 = cacheTestService.doWorkArray(9);
        objects2 = cacheTestService.doWorkArray(9);
        System.out.println("Основной алгоритм: " + Arrays.toString(objects1));
        System.out.println("Кэш: " + Arrays.toString(objects2));

//        String basePath = new File("").getAbsolutePath();
//        System.out.println(basePath);
//
//        String path = new File("src/main/resources/conf.properties")
//                .getAbsolutePath();
//        System.out.println(path);
//        CacheDir.create();
//        System.out.println(CacheDir.getCachePath());
//        CacheDir.delete();
    }
}
