package ru.sbrf;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Аннотация позволяет настроить варианты поиска и кэширования возвращаемого методом результата.
 * Применяется к методам, которые возвращают значения.
 * <p> StorageType value() - настройка поиска и записи кэшированных данных: в ОЗУ или на диск в файлы.
 * По умолчанию - в ОЗУ.
 * <p> int[] args - массив с номерами аргументов, по которым определяется уникальность результата.
 * Порядок перечисления аргументов не имеет значения. По умолчанию используются все аргументы.
 * <p> String fileName() - имя файла, в который будет осуществляться кэширование.
 * По умолчанию - null (присвоение имен осуществляет алгоритм).
 * <p> int countElement() - максимальное количество элементов коллекции, множества или массива, которое надо хранить.
 * По умолчанию - 100_000 элементов.
 */
public @interface Cache {

    StorageType value() default StorageType.IN_MEMORY;

    int[] args() default {0};

    String fileName() default "";

    int countElement() default 100_000;
}
