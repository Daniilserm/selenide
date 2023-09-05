import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryOrderTest {

    private String generateData(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBeCompleted() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText(date)).shouldBe(visible);

    }

    @Test
    void shouldBeCompletedWithDash() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов-Смирнов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText(date)).shouldBe(visible);
    }

    @Test
    void shouldNotBeFilledLatinCity() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Moscow");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldNotBeFilledLatinName() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void shouldNotBeFilledInvalidCity() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Лобня");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldNotBeEmptyCityFields() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldNotBeEmptyDateFields() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue("");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void shouldNotBeEmptyNameFields() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldNotBeEmptyPhoneFields() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void shouldNotBeInvalidPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("89990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldNotBeSelectedCheckbox() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String date = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);

    }

}
