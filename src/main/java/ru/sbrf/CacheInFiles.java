package ru.sbrf;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

//Класс реализует поиск результата вычисления в кэше.
//Если результат не найден, осуществляется кэширование.

class CacheInFiles {

    //Хранение и восстановление кэша реализовано с помощью класса HashMap
    private static Map<String, Object> cacheMethods = new HashMap<>();

    //Класс для создания директории кэширования
    private CacheDir cacheDir = new CacheDir();

    //Класс для создания одной из частей "ключа" из массива аргументов
    private CacheArgs cArgs = new CacheArgs();

    //Класс для "обрезания" слишком больших контейнеров и массивов
    private Trim trim = new Trim();

    /**
     * Поиск результата в кэшированных файлах
     *
     * @param method      Метод, результат работы которого необходимо найти или кэшировать
     * @param args        Аргументы, переданные в method
     * @param cacheObject Объект, в котором реализован method
     * @param cacheAnn    Аннотация с настройками вариантов кэширования (см. Cache.class)
     */
    Object findInFiles(Method method, Object[] args, Object cacheObject, Cache cacheAnn) throws IOException {
        String cacheArgs = cArgs.get(args, cacheAnn); //Индекс для поиска или записи результата
        try {
            FileInputStream fis = new FileInputStream(fileName(cacheObject, method, cacheAnn));
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                cacheMethods = (HashMap) ois.readObject();
            }
            if (cacheMethods.containsKey(cacheArgs)) {
                System.out.println("Результат из кэша с аргументами ( " + cacheArgs + "): " + cacheMethods.get(cacheArgs));
                return cacheMethods.get(cacheArgs);
            }
        } catch (FileNotFoundException e) {
            cacheMethods.clear();
            System.out.print("Метод не кэшировался. ");
        } catch (ClassNotFoundException e) {
             e.printStackTrace();
        }
        System.out.println("Результат не найден, запускается выполнение основного алгоритма:");
        return cacheInFiles(method, args, cacheArgs, cacheObject, cacheAnn);
    }

    //Запись результата в файл (добавление)
    private Object cacheInFiles(Method method, Object[] args, String cacheArgs, Object cacheObject, Cache cacheAnn) throws IOException {
        Object result = null;
        try {
            result = method.invoke(cacheObject, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        cacheMethods.put(cacheArgs, trim.cut(method, result, cacheAnn.countElement()));
        cacheDir.create();
        try {
            FileOutputStream fos = new FileOutputStream(fileName(cacheObject, method, cacheAnn));
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(cacheMethods);
                oos.flush();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
//            e.printStackTrace();
        }
        return result;
    }

    //Получение имени файла, в котором должны содержаться кэшированные данные
    private String fileName(Object cacheObject, Method method, Cache cacheAnn) {
        if (cacheAnn.fileName().isEmpty())
            return (cacheDir.getCachePath() + cacheObject.hashCode() + "." + cacheObject.getClass().getName() + "." + method.getName() + ".cache");
        else return (cacheDir.getCachePath() + cacheAnn.fileName() + ".cache");
    }


}
