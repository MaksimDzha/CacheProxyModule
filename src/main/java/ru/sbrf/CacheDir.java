package ru.sbrf;

import java.io.File;

//Создание и удаление директории с кэш-файлами

public class CacheDir {

    private static File cacheDir = new File("cache");

    //Создание директории cache
    public void create() {
        if (!cacheDir.exists()) {
            System.out.println("Создание директории для хранения кэш-файлов: " + cacheDir.getAbsolutePath());
            boolean dirCacheExist = false;
            try {
                cacheDir.mkdir();
                dirCacheExist = true;
            } catch (SecurityException e) {
                System.out.println("Ошибка создания каталога");
//                e.printStackTrace();
            }
            if (dirCacheExist)
                System.out.println("Директория создана успешно");
        }
    }

    //Путь к директории
    public String getCachePath(){
        if (cacheDir.exists())
            return (cacheDir.getAbsolutePath() + "\\");
        return "";
    }

    //Удаление директории "cache" по умолчанию
    public static void delete() {
        delete(cacheDir);
    }

    //Удаление указанной директории
    public static void delete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                delete(f);
            file.delete();
        } else {
            file.delete();
        }
    }
}
