package ru.sbrf;
import java.lang.reflect.Method;
import java.util.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

class Trim {

    /**
     * Метод проверяет, является ли передаваемый объект коллекцией, множеством или массивом.
     * Если является, то уменьшает количество элементов до указанного значения.
     *
     * @param method Метод, возвращамый класс которого проверяется
     * @param result Результат работы method
     * @param count  Ограничение, наложенное на количество элементов
     */
    static Object trim(Method method, Object result, int count) {
        if (method.getReturnType().getName().equals("java.util.List"))
            return trimList(result, count);
        if (method.getReturnType().getName().equals("java.util.Set"))
            return trimSet(result, count);
        if (method.getReturnType().isArray())
            return trimArray(result, count);
        try {
            if (method.getReturnType().newInstance() instanceof Set)
                return trimSet(result, count);
            if (method.getReturnType().newInstance() instanceof Collection)
                return trimList(result, count);

        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Результат не является коллекцией, множеством или массивом.");
        }
        return result;
    }

    //Удаление "лишних" элементов из коллекции, использующей интерфейс List
    private static Object trimList(Object result, int count) {
        List newResult = (List) result;
        if (newResult.size() < count) count = newResult.size();
        return newResult.stream().limit(count).collect(toList());
    }

    //Удаление "лишних" элементов из множества, использующего интерфейс Set
    private static Object trimSet(Object result, int count) {
        System.out.println("Мы в trimSet");
        Set newResult = (Set) result;
        if (newResult.size() < count) count = newResult.size();
        return newResult.stream().limit(count).collect(toSet());
    }

    //Удаление "лишних" элементов из массива
    private static Object trimArray(Object result, int count) {
        if (((Object[]) result).length < count) count = ((Object[]) result).length;
        return Arrays.copyOfRange((Object[]) result, 0, count);
    }
}
