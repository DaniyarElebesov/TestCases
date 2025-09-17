# README — Единый стиль кода (Java)
> Цель: обеспечить **лаконичный, читаемый и единообразный** код в проектах компании. Документ охватывает правила именования, форматирования, оформления классов/методов, комментариев и Javadoc, а также содержит примеры.
---
## 1) Базовые принципы
* **Читаемость важнее компактности.** Пишите так, чтобы код легко читался через 6 месяцев.
* **Последовательность > индивидуальный вкус.** Если правило принято — соблюдаем везде.
* **Простота.** Предпочитайте простые решения сложным, избегайте преждевременной оптимизации.
* **Явность.** Меньше магии, больше явных зависимостей и контрактов.
---
## 2) Именование
### 2.1 Пакеты
* `lowercase`, без подчёркиваний: `com.company.billing.payment`.
### 2.2 Классы/Интерфейсы/Записи/Перечисления/Аннотации
* `UpperCamelCase`: `UserService`, `PaymentController`, `PriceCalculator`, `UserDto`, `UserRecord`.
* Интерфейсы — без префикса `I`. Вместо `IUserService` → `UserService`.
* Enum — существительное в единственном числе: `Status`, а константы в нём `UP`, `DOWN`.
* Аннотации: `@Audit`, `@Secured`.
### 2.3 Методы
* `lowerCamelCase`, глагол + объект: `findById`, `calculateTotal`, `toDto`.
* Boolean-методы: `isActive`, `hasDiscount`, `shouldRetry`.
* Тест-методы (JUnit): `shouldReturn404_whenUserNotFound()` (Given/When/Then).
### 2.4 Поля и переменные
* `lowerCamelCase`: `totalPrice`, `userRepository`.
* Константы: `UPPER_SNAKE_CASE`: `DEFAULT_PAGE_SIZE`.
* Коллекции — имя во множественном числе: `users`, `orderItems`.
* Параметры — самодокументируемые имена: `timeoutMillis`, `maxRetries`.
### 2.5 Префиксы/Суффиксы
* DTO/VO/Request/Response допускаются: `UserDto`, `CreateUserRequest`, `UserResponse`.
* Реализации интерфейсов: `UserServiceImpl` (когда реально нужно различать), иначе лучше конкретное имя: `DefaultUserService`.
---
## 3) Структура файлов и классов
* Один public-класс на файл. Имя файла = имя класса.
* Порядок членов класса:
    1. Константы
    2. Поля
    3. Конструкторы
    4. Статические фабрики/билдеры
    5. Public методы
    6. Protected методы
    7. Package-private методы
    8. Private методы
    9. `equals`/`hashCode`/`toString`
       Пример:
```java
public class InvoiceService {
    // 1) Константы
    private static final int DEFAULT_PAGE_SIZE = 50;
    // 2) Поля
    private final InvoiceRepository invoiceRepository;
    private final Clock clock;
    // 3) Конструкторы
    public InvoiceService(InvoiceRepository invoiceRepository, Clock clock) {
        this.invoiceRepository = requireNonNull(invoiceRepository);
        this.clock = requireNonNull(clock);
    }
    // 4) Статические фабрики (при необходимости)
    public static InvoiceService withSystemClock(InvoiceRepository repo) {
        return new InvoiceService(repo, Clock.systemUTC());
    }
    // 5) Public методы
    public Page<Invoice> findInvoices(InvoiceFilter filter, int page, int size) { /* ... */ }
    // 8) Private методы
    private void validateFilter(InvoiceFilter filter) { /* ... */ }
    // 9) equals/hashCode/toString
    @Override public String toString() { /* ... */ }
}
```
---
## 4) Форматирование
### 4.1 Отступы и длина строки
* Отступы: **4 пробела** (без табов).
* Длина строки: до **120 символов** (желательно). Переносите длинные выражения.
### 4.2 Скобки и переносы
* Стиль скобок K\&R (открывающая на той же строке):
```java
if (isValid) {
    doWork();
} else {
    log.warn("Invalid state");
}
```
* Переносы параметров по одному на строку при длинных сигнатурах:
```java
public User createUser(
        String login,
        String email,
        Role role,
        Duration timeout) { /* ... */ }
```
### 4.3 Пробелы
* После запятых, вокруг бинарных операторов: `a + b`, `map.put(key, value)`.
* Не ставим пробелы перед `;`, не ставим дополнительные между методом и скобками: `doWork()`.
### 4.4 Пустые строки
* Отделяйте смысловые блоки одной пустой строкой.
* Не более одной пустой строки подряд.
### 4.5 Импорты
* Без `*`-импортов. Сортировка: `java.*`, `javax.*`, `org.*`, `com.*`, затем проектные.
* Статические импорты — после обычных.
---
## 5) Методы: лаконичность и читаемость
* **Одна ответственность** на метод. Размер до \~30–40 строк.
* **Ранний выход** вместо глубокой вложенности:
```java
if (!isValid(request)) {
    return Optional.empty();
}
// основная ветка
```
* Не бойтесь выделять приватные методы — это повышает читаемость.
* Явные имена переменных, избегайте `tmp`, `obj`.
* Параметров ≤ 4; больше — выносите в объект-параметр.
---
## 6) Исключения и логирование
* Используйте проверяемые исключения для ожидаемых ошибок ввода/вывода; `RuntimeException` — для ошибок программной логики.
* Не глушите исключения:
```java
try {
    process();
} catch (IOException e) {
    log.error("Failed to process file {}", fileName, e);
    throw e;
}
```
* Логируйте **контекст** и **идемпотентные** сообщения. Уровни: `error` (ошибка), `warn` (аномалия), `info` (бизнес-событие), `debug` (технические детали).
---
## 7) Nullability, Optional, коллекции
* На входе методов — **не принимайте** `Optional`, используйте аннотации (`@NotNull`, `@Nullable`) или валидацию.
* На выходе — можно `Optional<T>` для «может не быть».
* Никогда не возвращайте `null`-коллекции; используйте `List.of()`, `Collections.emptyList()`.
* Предпочитайте **иммутабельные** коллекции для DTO/ответов.
---
## 8) Лямбды/Streams
* Используйте, когда повышают читаемость. Не усложняйте «стрим-спагетти».
* Правило: если выражение не влезает в 1–2 понятные операции — вынесите в именованный метод.
  Плохо:
```java
var result = items.stream().filter(i -> i.isActive() && i.price() > min && i.tags().contains(tag))
    .sorted(comparing(Item::price).reversed()).limit(limit).map(Item::id).toList();
```
Хорошо:
```java
var result = items.stream()
    .filter(this::isEligible)
    .sorted(byPriceDesc())
    .limit(limit)
    .map(Item::id)
    .toList();
```
---
## 9) Комментарии и TODO
* Комментарии — для **почему**, а не **что**. Код должен объяснять «что» сам.
* `// TODO(username): описание` — только с контекстом и ссылкой на задачу.
* Запрещены устаревшие/вводящие в заблуждение комментарии.
---
## 10) Javadoc: когда и как
### 10.1 Когда оставлять Javadoc
* Публичные API (библиотеки, модули общего пользования).
* Сложные алгоритмы и неочевидные решения.
* Переиспользуемые интерфейсы/абстракции.
* Публичные DTO/записи, если есть бизнес-контекст.
* Внутренние детали — комментарии в коде предпочтительнее Javadoc.
### 10.2 Стиль Javadoc
* Краткое описание в 1–2 предложения в **повелительном наклонении**.
* Теги по необходимости: `@param`, `@return`, `@throws`, `@since`, `@implNote`, `@implSpec`, `@see`.
* Указывайте **единицы измерения**, временные зоны, форматы данных.
  Пример (интерфейс):
```java
/**
 * Calculates total price with taxes and discounts.
 * <p>All monetary values are treated as immutable in currency {@code USD}.
 * Thread-safe.
 *
 * @param items immutable list of items; must not be null
 * @return total price, never null
 * @throws IllegalArgumentException if items contain negative price
 * @see TaxPolicy
 */
Money calculateTotal(List<Item> items);
```
Пример (класс):
```java
/**
 * Repository for accessing invoices.
 *
 * @implSpec Implementations must be thread-safe.
 */
public interface InvoiceRepository { /* ... */ }
```
Пример (метод со временем):
```java
/**
 * Returns due invoices created after the given moment.
 *
 * @param since inclusive lower bound in UTC
 * @return immutable page of invoices
 */
public Page<Invoice> findDueSince(Instant since) { /* ... */ }
```
---
## 11) Аннотации и порядок
* Сначала аннотации фреймворков (Spring/Jakarta), затем валидации, затем кастомные.
* Несколько аннотаций — каждая на новой строке.
  Пример:
```java
@Service
@Transactional(readOnly = true)
public class UserQueryService { /* ... */ }
```
---
## 12) Искусство «красивого» класса
* **Ясный публичный контракт** и минимально необходимое API.
* Инкапсуляция: поля `private final` где это возможно.
* Конструктор/билдер вместо сеттеров для обязательных полей.
* Не перегружайте класс — делите по ответственности (SRP).
* Отдельные типы вместо «магических» строк и чисел (`Money`, `Percentage`, `UserId`).
  Шаблон класса:
```java
public final class Money {
    private final BigDecimal amount;
    private final Currency currency;
    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }
    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }
    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
    private void requireSameCurrency(Money other) { /* ... */ }
}
```
---
## 13) Тесты
* Имена по шаблону: `should<Ожидание>_when<Условие>`.
* Структура теста: **Given–When–Then** с пустыми строками между блоками.
* Одно логическое утверждение на тест (или несколько аспектов одной идеи).
* Параметризованные тесты — для типовых наборов.
  Пример:
```java
@ParameterizedTest
@CsvSource({"10,USD, 12.00", "5,USD, 6.00"})
void shouldApplyVat_whenUsTax(String amount, String currency, String expected) {
    // Given
    Money input = Money.of(new BigDecimal(amount), Currency.getInstance(currency));
    // When
    Money actual = tax.apply(input);
    // Then
    assertThat(actual).isEqualTo(Money.parse(expected + " " + currency));
}
```
---
## 14) Git и формат в PR (кратко)
* Коммиты по правилу: `type(scope): краткое описание` — `feat(core): add money converter`.
* В PR: описание изменения, скриншоты/логика миграций, чек-лист проверки.
---
## 15) Чек-лист перед ревью
* [ ] Имена отражают назначение
* [ ] Нет «мертвого» кода/комментированных блоков
* [ ] Покрыты крайние случаи и ошибки
* [ ] Логи информативны, без шума
* [ ] Нет дублирования, разумная декомпозиция
* [ ] Javadoc там, где нужен
* [ ] Форматирование и импорты по правилам
---
## 16) Мини-«словарик» плохого/хорошего кода
Плохо:
```java
int f(List<String> a){for(String s:a){if(s!=null&&s.length()>3){System.out.println(s);}}return 0;}
```
Хорошо:
```java
int printLongerThan3(List<String> strings) {
    for (String s : strings) {
        if (s == null) continue;
        if (s.length() > 3) {
            System.out.println(s);
        }
    }
    return 0;
}
```
---
## 17) Инструменты
* Подключите автоформатер (Spotless/Google Java Format) в CI.
* В IDE включите: удаление хвостовых пробелов, вставку final где возможно, оптимизацию импортов.
---
## 18) Примеры «до/после» (коротко)
До:
```java
public BigDecimal c(List<Item> items){BigDecimal t=ZERO;for(Item i:items){if(i!=null){t=t.add(i.getPrice());}}return t;}
```
После:
```java
/** Calculates total price of non-null items. */
public BigDecimal calculateTotal(List<Item> items) {
    BigDecimal total = BigDecimal.ZERO;
    for (Item item : items) {
        if (item == null) continue;
        total = total.add(item.getPrice());
    }
    return total;
}
```
---
## 19) TL;DR
* 4 пробела, 120 символов, K\&R скобки.
* Имена по назначению; boolean: `is*/has*/should*`.
* Методы короткие, одна ответственность, ранние выходы.
* Коллекции не `null`, лучше иммутабельные.
* Javadoc — для публичного API и сложной логики, описываем контракты и единицы измерения.
> Любые вопросы/предложения по стилю — через обсуждение в командах, итоговые изменения фиксируем в этом README и применяем ко всем репозиториям.