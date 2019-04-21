package ru.sbrf;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//Класс реализует перехват методов объекта и кэширование возвращаемых значений.
//Метод не должен быть void и иметь аннотацию @Cache для запуска алгоритма кэширования.

public class CacheProxy implements InvocationHandler {

    private Object cacheObject; //Объект, методы которого подвергаются кэшированию

    public CacheProxy(Object cacheObject) {
        this.cacheObject = cacheObject;
    }

    private CacheInMemory cacheInMemory = new CacheInMemory();
    private CacheInFiles cacheInFiles = new CacheInFiles();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Работает прокси");
        System.out.println("Проверяем аннотацию @Cache");
        Cache cacheAnn = cacheObject.getClass()
                .getMethod(method.getName(), method.getParameterTypes())
                .getAnnotation(Cache.class);
        if (cacheAnn != null) {
            System.out.print("Проверяем кэш...");
            switch (cacheAnn.value()) {
                case IN_MEMORY:
                    System.out.println(StorageType.IN_MEMORY);
                    return cacheInMemory.findInMemory(method, args, cacheObject, cacheAnn);
                case IN_FILES:
                    System.out.println(StorageType.IN_FILES);
                    return cacheInFiles.findInFiles(method, args, cacheObject, cacheAnn);

            }
        }
        System.out.println("Метод без аннотации @Cache, выполняем без кеширования: ");
        return method.invoke(cacheObject, args);
    }
}
