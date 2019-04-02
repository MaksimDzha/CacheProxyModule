package ru.sbrf.javaschool;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

//Класс реализует поиск результата вычисления в кэше.
//Если результат не найден, осуществляется кэширование.

class CacheInMemory {

    //cacheMemori содержит все результаты кэширования
    private static Map<String, Map> cacheMemori = new HashMap<>();

    //Хранение и восстановление кэша реализовано с помощью класса HashMap
    private static Map<String, Object> cacheMethods = new HashMap<>();

    //Ключ для хранения результата
    private static String key;

    /**
     * Поиск результата в памяти
     *
     * @param method Метод, результат работы которого необходимо найти или кэшировать
     * @param args Аргументы, переданные в method
     * @param cacheObject Объект, в котором реализован method
     * @param cacheAnn Аннотация с настройками вариантов кэширования (см. Cache.class)
     */
    static Object findInMemory(Method method, Object[] args, Object cacheObject, Cache cacheAnn) {
        String cacheArgs = CashArgs.get(args, cacheAnn);
        key = cacheObject.hashCode() + ", " + cacheObject.getClass().getName() + ", " + method.toString();
        if (cacheMemori.containsKey(key))
            if (cacheMethods.containsKey(cacheArgs)) {
                System.out.println("Результат из кэша с аргументами ( " + cacheArgs + "): " + cacheMemori.get(key).get(cacheArgs));
                return cacheMemori.get(key).get(cacheArgs);
            }
        System.out.println("Результат не найден, запускается выполнение основного алгоритма:");
        return cacheInMemori(method, args, cacheArgs, cacheObject, cacheAnn);
    }

    //Запись результата в память
    private static Object cacheInMemori(Method method, Object[] args, String cacheArgs, Object cacheObject, Cache cacheAnn) {
        Object result = null;
        try {
            result = method.invoke(cacheObject, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        cacheMethods.put(cacheArgs, Trim.trim(method, result, cacheAnn.countElement()));
        cacheMemori.put(key, cacheMethods);
        return result;
    }

}
