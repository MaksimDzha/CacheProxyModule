package ru.sbrf.javaschool;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class CacheInFilesTest {
//Требуется очистка папки cache перед выполнением теста
    @Test
    @Cache(countElement = 7)
    public void findInFiles() throws NoSuchMethodException {

        for (int j = 0; j < 3; j++) {

            List<Integer> testObject1 = new LinkedList<>();
            for (int i = 0; i < j + 10; i++) testObject1.add(i);

            Method[] methods = LinkedList.class.getMethods();
            Class[] parameterTypes = methods[43].getParameterTypes();
            Method method = testObject1.getClass().getMethod("subList", parameterTypes);
            Cache cache = CacheInFilesTest.class.getMethod("findInFiles", null).getAnnotation(Cache.class);

            Integer[] args1 = {0, 10};
            Integer[] args2 = {0, 3};

            assertEquals(testObject1.subList(0, 10), CacheInFiles.findInFiles(method, args1, testObject1, cache));
            assertEquals(testObject1.subList(0, 7), CacheInFiles.findInFiles(method, args1, testObject1, cache));

            assertEquals(testObject1.subList(0, 3), CacheInFiles.findInFiles(method, args2, testObject1, cache));
            assertEquals(testObject1.subList(0, 3), CacheInFiles.findInFiles(method, args2, testObject1, cache));
        }
    }
}