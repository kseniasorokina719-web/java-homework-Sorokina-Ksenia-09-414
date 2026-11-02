public final class BankAccount {

    private final String ownerName;       // имя владельца
    private final String accountNumber;   // номер счёта
    private Balance balance;              // баланс
    private final long dailyWithdrawLimit; // лимит на вывод в день (в копейках)
    private long dailyWithdrawnAmount;     // сколько уже выведено сегодня (в копейках)

    // Конструктор
    public BankAccount(String ownerName, String accountNumber, Balance balance, long dailyWithdrawLimit) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Имя владельца не может быть пустым");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Номер счёта не может быть пустым");
        }
        if (balance == null) {
            throw new IllegalArgumentException("Баланс не может быть null");
        }
        if (dailyWithdrawLimit < 0) {
            throw new IllegalArgumentException("Лимит не может быть отрицательным");
        }
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dailyWithdrawLimit = dailyWithdrawLimit;
        this.dailyWithdrawnAmount = 0;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Balance getBalance() {
        return balance;
    }

    public long getDailyWithdrawLimit() {
        return dailyWithdrawLimit;
    }

    public long getDailyWithdrawnAmount() {
        return dailyWithdrawnAmount;
    }

    // Внести деньги
    public void deposit(long kopecks) {
        if (kopecks < 0) {
            throw new IllegalArgumentException("Нельзя внести отрицательную сумму");
        }
        this.balance = this.balance.add(kopecks);
        System.out.println("✅ Внесено " + formatKopecks(kopecks) + " на счёт " + accountNumber);
    }

    // Снять деньги
    public void withdraw(long kopecks) {
        if (kopecks < 0) {
            throw new IllegalArgumentException("Нельзя снять отрицательную сумму");
        }
        // Проверяем лимит
        if (dailyWithdrawnAmount + kopecks > dailyWithdrawLimit) {
            throw new IllegalArgumentException("Превышен дневной лимит вывода! Лимит: "
                    + formatKopecks(dailyWithdrawLimit) + ", уже выведено: "
                    + formatKopecks(dailyWithdrawnAmount) + ", хотите: " + formatKopecks(kopecks));
        }
        this.balance = this.balance.subtract(kopecks);
        this.dailyWithdrawnAmount += kopecks;
        System.out.println("✅ Снято " + formatKopecks(kopecks) + " со счёта " + accountNumber);
    }

    // Перевод денег между аккаунтами
    public void transferTo(BankAccount recipient, long kopecks) {
        if (recipient == null) {
            throw new IllegalArgumentException("Получатель не может быть null");
        }
        if (kopecks < 0) {
            throw new IllegalArgumentException("Нельзя перевести отрицательную сумму");
        }
        if (this.balance.getAmountInKopecks() < kopecks) {
            throw new IllegalArgumentException("Недостаточно средств для перевода");
        }
        // Снимаем у отправителя
        this.balance = this.balance.subtract(kopecks);
        // Начисляем получателю
        recipient.balance = recipient.balance.add(kopecks);
        System.out.println("✅ Переведено " + formatKopecks(kopecks)
                + " со счёта " + this.accountNumber + " на счёт " + recipient.accountNumber);
    }

    // Сброс дневного лимита
    public void resetDailyLimit() {
        this.dailyWithdrawnAmount = 0;
        System.out.println("🔄 Дневной лимит сброшен для счёта " + accountNumber);
    }

    // Вспомогательный метод для форматирования
    private String formatKopecks(long kopecks) {
        long rubles = kopecks / 100;
        long k = kopecks % 100;
        return String.format("%d.%02d руб.", rubles, k);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "владелец='" + ownerName + '\'' +
                ", счёт='" + accountNumber + '\'' +
                ", баланс=" + balance +
                ", дневной лимит=" + formatKopecks(dailyWithdrawLimit) +
                ", выведено сегодня=" + formatKopecks(dailyWithdrawnAmount) +
                '}';
    }
}