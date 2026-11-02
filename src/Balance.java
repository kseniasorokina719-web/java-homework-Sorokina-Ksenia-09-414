public final class Balance {

    private final long amountInKopecks; // сумма в копейках
    private final Currency currency;    // валюта

    // Конструктор
    public Balance(long amountInKopecks, Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Валюта не может быть null");
        }
        if (amountInKopecks < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной: " + amountInKopecks);
        }
        this.amountInKopecks = amountInKopecks;
        this.currency = currency;
    }

    // Получить сумму в копейках
    public long getAmountInKopecks() {
        return amountInKopecks;
    }

    // Получить валюту
    public Currency getCurrency() {
        return currency;
    }

    // Получить сумму в рублях
    public double getAmountInRubles() {
        return amountInKopecks / 100.0;
    }

    // Прибавить сумму
    public Balance add(long kopecksToAdd) {
        if (kopecksToAdd < 0) {
            throw new IllegalArgumentException("Нельзя прибавить отрицательную сумму: " + kopecksToAdd);
        }
        return new Balance(this.amountInKopecks + kopecksToAdd, this.currency);
    }

    // Вычесть сумму
    public Balance subtract(long kopecksToSubtract) {
        if (kopecksToSubtract < 0) {
            throw new IllegalArgumentException("Нельзя вычесть отрицательную сумму: " + kopecksToSubtract);
        }
        if (kopecksToSubtract > this.amountInKopecks) {
            throw new IllegalArgumentException("Недостаточно средств. Есть: "
                    + this.amountInKopecks + " коп., нужно: " + kopecksToSubtract + " коп.");
        }
        return new Balance(this.amountInKopecks - kopecksToSubtract, this.currency);
    }

    // Конвертировать в другую валюту
    public Balance convertTo(Currency targetCurrency) {
        if (targetCurrency == null) {
            throw new IllegalArgumentException("Целевая валюта не может быть null");
        }
        // Переводим наши копейки в рубли-копейки, потом в целевую валюту
        // amountInKopecks - копейки текущей валюты

        long inRublesKopecks = this.amountInKopecks * this.currency.getPriceInRublesKopecks() / 100;

        long targetKopecks = inRublesKopecks * 100 / targetCurrency.getPriceInRublesKopecks();
        return new Balance(targetKopecks, targetCurrency);
    }

    @Override
    public String toString() {

        long rubles = amountInKopecks / 100;
        long kopecks = amountInKopecks % 100;
        return String.format("%d.%02d %s", rubles, kopecks, currency.getSymbol());
    }
}