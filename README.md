# Kitto Payment System

Платежі

Клієнт реєструється в системі і має одну або кілька Кредитних карт, кожна з яких відповідає певному Рахунку в системі. Клієнт за допомогою Рахунку може здійснити Платіж.
Платіж має один з двох статусів: 'підготовлений' або 'відправлений'. (За бажанням: реалізувати можливість генерації pdf-звіту про платіж).
Клієнт має особистий кабінет, в якому може переглядати інформацію про свої платежі та рахунки. Необхідно реалізувати можливість сортування:
- платежів:
1) за номером;
2) за датою (від старих до нових, від нових до старих);
- рахунків:
1) за номером;
2) за найменуванням рахунку;
3) за розміром залишку коштів на рахунку.
   Клієнт може поповнити або заблокувати один зі своїх рахунків. Для розблокування рахунку клієнт повинен зробити запит до на розблокування.
   Адміністратор системи володіє правами:
- блокування / розблокування користувача;
- блокування / розблокування одного з рахунків користувача.
---

###Технічні вимоги
####Основні

  
 Зареєстрований користувач:
 
- [x] Перегянути історію всіх транзакцій

- [x] Створити платіжну карту

- [x] Заблокувати платіжну карту

- [x] Переглянути список всіх карт

- [x] Відсортувати карти за ключами (ім'я, номер, кількість грошей на рахунку)

- [x] Перейти до особистого кабінету

- [x] Змінити дані про користувача

- [x] Подати за'яву на розблокування платіжної карти

- [x] Створити переказ грошей із карти на карту

- [x] Створити платіж за однією із категорій (поповнення мобільного, платіж за реквізитами, комуналка, благодійність)

- [x] Сортувати платежі за ключами (номер платежу, дата від нових до старих та навпаки)

- [x] Згенерувати PDF звіт про платежі

Адміністратор може:

- [x] Заблокувати та розблокувати користувача

- [x] Розблокувати карту користувача (в разі надісланого запиту)

####Додаткові

- [x] У якості СУБД використовувати MySql або PostgreSQL.
- [x] Для доступу до даних використовувати JDBC API із застосуванням готового або самостійно розробленого пулу з'єднань.
- [x] Забезпечити багатомовність:
   - [x] повинна бути можливість перемикати мову інтерфейсу;
   - [x] повинна бути підтримка введення, виведення і зберігання інформаціі записаної на різних мовах;
   - [x] в якості мов обрати мінімум дві (одна з кирилицею і одна з латиницею).
- [x] Архітектура застосунка повинна відповідати шаблону MVC.
- [x] При реалізації бізнес-логіки необхідно використовувати шаблони проектування: Команда,
Стратегія, Фабрика, Будівельник, Сінглтон, Фронт-контролер, Спостерігач, Адаптер та ін.
- [x] Використовувати сервлети та JSP.
- [x] Використовувати Apache Tomcat у якості контейнера сервлетів.
- [x] На сторінках JSP застосовувати теги з бібліотеки JSTL та розроблені власні теги (мінімум: один
      тег custom tag library і один тег tag file)...
   - [x] custom tag library.
   - [x] tag file.
 - [x] Реалізувати PRG.
 - [x] При розробці використовувати сессії, фільтри, слухачі.  
 - [x] У застосунку повинні бути реалізовані аутентифікація і авторизація, розмежування прав
 доступу користувачів системи до компонентів програми. Шифрування паролів заохочується.
    -[x] аутентифікація;
    -[x] авторизація;
    -[x] розмеження прав доступу;
    -[ ] шифрування паролів;
- [x] Впровадити у проект журнал подій із використанням бібліотеки log4j.
- [x] Код повинен містити коментарі документації (всі класи верхнього рівня, нетривіальні методи
      і конструктори).
- [ ] Застосунок має бути покритим модульними тестами (мінімальний відсоток покриття 40%).
- [x] Застосунок має коректно реагувати на помилки та виключні ситуації різного роду (кінцевий
     користувач не повинен бачити stack trace на стороні клієнта).
- [x] Використання HTML, CSS, JS фреймворків для інтерфейсу користувача.

     