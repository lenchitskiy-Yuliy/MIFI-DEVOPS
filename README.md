# Консольный калькулятор: CI/CD на GitHub Actions

Учебный проект: консольный калькулятор на Java 17 (Maven) с пайплайном GitHub Actions.

Пайплайн `.github/workflows/ci-cd.yml` запускается при каждом пуше, при Pull Request в `master` и вручную.
Он собирает проект, прогоняет unit-тесты, кэширует зависимости Maven и сохраняет JAR-файл как артефакт.

## Локальная сборка

```bash
mvn clean package
echo "2 + 3 * 4 S" | java -jar target/calculator-1.0.0.jar
```
