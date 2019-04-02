package ru.sbrf.javaschool;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

//Реализация интерфейса Service

public class UseService implements Service, Serializable {

    @Override
    @Cache(value = StorageType.IN_FILES)
    public long doHardWork(long time) {
        System.out.print("Сервис запущен главным приложением. Время выполнения: ");
        long result;
        long t1 = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        result = (t2 - t1) / 1000;
        System.out.println(result + " сек.");

        return result;
    }

    @Override
    @Cache(value = StorageType.IN_FILES)
    public long doHardWork() {
        System.out.print("Сервис запущен главным приложением. Время выполнения: ");
        long result;
        long t1 = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        result = (t2 - t1) / 1000;
        System.out.println(result + " сек.");
        return result;
    }

    @Override
    @Cache(value = StorageType.IN_FILES)
    public long doVeryHardWork(long time, String message) {
        System.out.println("Долгий сервис запущен главным приложением. Сообщение: " + message);
        long result;
        System.out.print("Время работы: ");
        long t1 = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        result = (t2 - t1) / 1000;
        System.out.println(result + " сек.");
        return result;
    }

    @Override
    @Cache(value = StorageType.IN_FILES, countElement = 5)
    public ArrayList doWorkList(int count) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < count; i++)
            list.add(i);
        System.out.println("Результат в коллекции = " + list);
        return list;
    }

    @Override
    @Cache(value = StorageType.IN_FILES, countElement = 5)
    public HashSet doWorkSet(int count) {
        HashSet set = new HashSet();
        for (int i = 0; i < count; i++)
            set.add(i);
        System.out.println("Результат в коллекции = " + set);
        return set;
    }

    @Override
    @Cache(value = StorageType.IN_FILES, countElement = 5)
    public Long[] doWorkArray(int count) {
        Long[] list = new Long[count];
        for (int i = 0; i < count; i++)
            list[i] = (long) i;
        System.out.println("Результат в коллекции = " + list);
        return list;
    }

}

