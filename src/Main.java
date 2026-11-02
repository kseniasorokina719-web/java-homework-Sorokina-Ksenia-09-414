public class Main {
    public static void main(String[] args) {

        System.out.println("========== БАНКОВСКАЯ СИСТЕМА ==========\n");

        // Создаём балансы
        Balance balance1 = new Balance(500_000, Currency.RUB);
        Balance balance2 = new Balance(10_000, Currency.USD);

        System.out.println("Баланс 1: " + balance1);
        System.out.println("Баланс 2: " + balance2);

        // Создаём банковские аккаунты

        BankAccount account1 = new BankAccount("Иван Иванов", "RU001", balance1, 100_000);
        BankAccount account2 = new BankAccount("Пётр Петров", "RU002", balance2, 50_000);

        System.out.println("\n--- Аккаунты ---");
        System.out.println(account1);
        System.out.println(account2);

        // Вносим деньги
        System.out.println("\n--- Внесение денег ---");
        account1.deposit(50_000); // вносим 500 рублей
        System.out.println(account1);

        // Снимаем деньги
        System.out.println("\n--- Снятие денег ---");
        account1.withdraw(30_000); // снимаем 300 рублей
        System.out.println(account1);

        // Перевод между аккаунтами
        System.out.println("\n--- Перевод ---");
        account1.transferTo(account2, 100_000); // переводим 1000 рублей
        System.out.println(account1);
        System.out.println(account2);

        // Конвертация валюты
        System.out.println("\n--- Конвертация валюты ---");
        Balance rubBalance = new Balance(960_000, Currency.RUB); // 9600 рублей
        System.out.println("Исходный баланс: " + rubBalance);
        Balance usdBalance = rubBalance.convertTo(Currency.USD);
        System.out.println("После конвертации в USD: " + usdBalance);

        Balance eurBalance = rubBalance.convertTo(Currency.EUR);
        System.out.println("После конвертации в EUR: " + eurBalance);

        // Корнер кейс: попытка снять больше лимита
        System.out.println("\n--- Проверка лимита ---");
        try {
            account2.withdraw(60_000); // лимит 50000, пытаемся снять 60000
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Корнер кейс: отрицательная сумма
        System.out.println("\n--- Проверка отрицательной суммы ---");
        try {
            account1.deposit(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Корнер кейс: недостаточно средств
        System.out.println("\n--- Проверка недостатка средств ---");
        try {
            Balance smallBalance = new Balance(100, Currency.RUB); // 1 рубль
            smallBalance.subtract(200); // пытаемся снять 2 рубля
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // Сброс лимита
        System.out.println("\n--- Сброс лимита ---");
        account2.resetDailyLimit();
        account2.withdraw(40_000); // теперь можно, лимит сброшен
        System.out.println(account2);

        System.out.println("\n========== ВСЁ РАБОТАЕТ! ==========");
    }
}