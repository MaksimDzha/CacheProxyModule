package ru.sbrf.javaschool;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

//Класс реализует поиск результата вычисления в кэше.
//Если результат не найден, осуществляется кэширование.

class CacheInFiles {

    //Хранение и восстановление кэша реализовано с помощью класса HashMap
    private static Map<String, Object> cacheMethods = new HashMap<>();

    /**
     * Поиск результата в кэшированных файлах
     *
     * @param method Метод, результат работы которого необходимо найти или кэшировать
     * @param args Аргументы, переданные в method
     * @param cacheObject Объект, в котором реализован method
     * @param cacheAnn Аннотация с настройками вариантов кэширования (см. Cache.class)
     */
    static Object findInFiles(Method method, Object[] args, Object cacheObject, Cache cacheAnn) {
        String cacheArgs = CashArgs.get(args, cacheAnn); //Индекс для поиска или записи результата
        try {
            FileInputStream fis = new FileInputStream(fileName(cacheObject, method, cacheAnn));
            ObjectInputStream ois = new ObjectInputStream(fis);
            cacheMethods = (HashMap) ois.readObject();
            if (cacheMethods.containsKey(cacheArgs)) {
                System.out.println("Результат из кэша с аргументами ( " + cacheArgs + "): " + cacheMethods.get(cacheArgs));
                return cacheMethods.get(cacheArgs);
            }
        } catch (FileNotFoundException e) {
            cacheMethods.clear();
            System.out.print("Метод не кэшировался. ");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Результат не найден, запускается выполнение основного алгоритма:");
        return cacheInFiles(method, args, cacheArgs, cacheObject, cacheAnn);
    }

    //Запись результата в файл (добавление)
    private static Object cacheInFiles(Method method, Object[] args, String cacheArgs, Object cacheObject, Cache cacheAnn) {
        Object result = null;
        try {
            result = method.invoke(cacheObject, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        cacheMethods.put(cacheArgs, Trim.trim(method, result, cacheAnn.countElement()));
        try {
            FileOutputStream fos = new FileOutputStream(fileName(cacheObject, method, cacheAnn));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cacheMethods);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            System.out.println("Попытка кэширования не сериализуемого результата. " +
                    "Используйте для результата сериализуемый класс.");
//            e.printStackTrace();
        }
        return result;
    }

    //Получение имени файла, в котором должны содержаться кэшированные данные
    private static String fileName(Object cacheObject, Method method, Cache cacheAnn) {
        if (cacheAnn.fileName().isEmpty())
            return ("C:\\Users\\User\\IdeaProjects\\CacheProxyModule\\src\\cache\\" + cacheObject.hashCode() + "." + cacheObject.getClass().getName() + "." + method.getName() + ".cache");
        else return ("C:\\Users\\User\\IdeaProjects\\CacheProxyModule\\src\\cache\\" + cacheAnn.fileName() + ".cache");
    }


}
