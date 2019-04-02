package ru.sbrf;
import java.lang.reflect.Array;
import java.util.*;

//Интерфейс для подключения CacheProxy

interface Service {

    long doHardWork(long time);
    long doHardWork();
    long doVeryHardWork(long time, String message);
    ArrayList doWorkList(int count);
    HashSet doWorkSet(int count);
    Long[] doWorkArray(int count);
}
