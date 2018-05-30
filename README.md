# Проектирование архитектур программного обеспечения

Олейник Максим

гр. 13541/3

### Предметная область:
Библиотека, выдача книг на время, обмен книгами

[Wiki по проекту](https://github.com/MaxCiv/LibraryArchitecture/wiki)

### Как запустить:
1. Запустите проект в IDEA, при импорте Gradle настроек выберите `"Use gradle wrapper task configuration"`.
2. Используйте 
> Gradle Tasks -> javafx -> jfxRun

для запуска программы из среды разработки или
> Gradle Tasks -> shadow -> shadowJar

для создания jar файла, он попадёт в папку build\libs (запускается из консоли `java -jar maxciv-1.0-all.jar`).

3. При входе введите логин `root` и пароль `1111`, однако, чтобы это сработало, нужно настроить MySQL.

### Как настроить MySQL:
1. Установите и настройте все основные элементы MySQL (можно найти на офиц. сайте).
2. Запустите в MySQL Workbench [этот скрипт](https://github.com/MaxCiv/LibraryArchitecture/blob/master/LibraryApp/src/main/resources/createDB.sql), это создаст начальную базу данных.
3. Настройте в [этом классе](https://github.com/MaxCiv/LibraryArchitecture/blob/master/LibraryApp/src/main/java/com/maxciv/storage/DataGateway.java) поля URL, USER, PASSWORD под вашу конфигурацию.