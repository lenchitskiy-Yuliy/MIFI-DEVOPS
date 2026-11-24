# Консольный калькулятор

Учебный проект на Java 17 с Maven и GitHub Actions.

## Локальный запуск

```bash
mvn clean package
printf '%s\n' '2 + 3 * 4 S' | java -jar target/calculator-1.0.0.jar
```

Сборка и запуск в Docker:

```bash
docker build -t calculator:local .
printf '%s\n' '2 + 3 * 4 S' | docker run --rm -i calculator:local
```

Обе команды запуска выводят `5.0` и `20.0`. Калькулятор выполняет операции
по порядку ввода. Команда `S` завершает программу. Для интерактивного режима
используйте `docker run --rm -it calculator:local`.

## GitHub Actions и GHCR

[Пайплайн](.github/workflows/ci-cd.yml) запускается при push в любую ветку,
при pull request в `master` и вручную из вкладки Actions.

1. `build` собирает образ по многоэтапному Dockerfile и сохраняет его вместе с JAR как артефакты.
2. `test` запускает JUnit-тесты и проверяет калькулятор в собранном контейнере.
3. `publish` загружает проверенный образ в GitHub Container Registry. Тег образа равен полному SHA коммита.
4. `verify-published` скачивает образ из GHCR на отдельном раннере и проверяет его запуск.

На pull request выполняются сборка и тестирование. При push и ручном запуске
публикация начинается после успешных тестов. Для GHCR используется встроенный
`GITHUB_TOKEN`.

## Запуск опубликованного образа

Из лога успешной джобы `Pull and run image from GHCR` возьмите полный SHA коммита из тега образа.
Если пакет приватный, сначала выполните `docker login ghcr.io` с учётной записью
GitHub и токеном classic PAT с правом `read:packages` и доступом к пакету.

```bash
read -r -p 'SHA коммита: ' SHA
IMAGE="ghcr.io/lenchitskiy-yuliy/mifi-devops:$SHA"
docker pull "$IMAGE"
printf '%s\n' '2 + 3 * 4 S' | docker run --rm -i "$IMAGE"
```

Ожидаемый вывод:

```text
5.0
20.0
```
