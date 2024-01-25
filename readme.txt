Решение использует три докора:
  1. Докер pg-for-test с тестовой БД Postgre, используется для запуска тестов, чтобы не портить основную БД
  2. Докер pgdocker с основной БД Postgre, испоульзутеся непосредственно исполняемым приложением
  3. Докер stage2_task5 собственно с запущенным микросервисом. Собирается на основе файлов Dockerfile и docker-compose.yml в корне проекта

В разработке микросервиса использованы следующие зависимости:
  1. json-schema-validator - для валидации входящих json на корректность, в том числе на наличие обазательных полей
     Для валидации используются json схемы хранящиеся в ресурсах src/main/resources/json-schema
  2. flyway-core - для создания на основе sql скриптов структуры базы данных и первичного наполнения её данными.
     SQL скрипты расположены в src/main/resources/db/migration
  3. lombok - для автоматической генерации стандарного кода
  4. hamcrest + mockito-core - для создания тестовых модулей

Для проверки работы микросервиса использовался SoapUI, тестовые запросы которого сохранены в корне прокта в файле soapui-create-csi-test.xml

Для модульного тестирования используются отдельные ресурсы:
    src/test/resources/json - тестовые json запросы использующиес для модульного тестирования
    src/test/resources/scripts - sql скрипты flyway для формирования тестового окружения БД

Структура бинов:
  src/main/java/vtb/courses/stage2_task5/Controller/CsiController.java
    - основной Rest Controller по обработке POST запросов
  src/main/java/vtb/courses/stage2_task5/Service/AccountNumService.java
    - сервис по выбору номера счёта из пула счетов
  src/main/java/vtb/courses/stage2_task5/Service/CsiService.java
    - сервис по созданию экземпляра продукта
  src/main/java/vtb/courses/stage2_task5/Service/CreateAccountService.java
    - сервис по созданию продуктового регистра для переданного экземпляра продукта