import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTestTwo {

    private String generateData(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldBeCompleted() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item__control").findBy(text("Москва")).click();
        String date = generateData(7, "dd.MM.yyyy");
        $("[class=input__icon]").click();
        if (generateData(0, "MM").equals(generateData(7, "MM"))) {
        } else {
            $("[data-step='1'][data-disabled=false]").click();
        }
        String day = generateData(7, "d");
        $(byText(day)).click();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldBeCompletedNextMonth() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item__control").findBy(text("Москва")).click();
        String date = generateData(27, "dd.MM.yyyy");
        $("[class=input__icon]").click();
        if (generateData(0, "MM").equals(generateData(27, "MM"))) {
        } else {
            $("[data-step='1'][data-disabled=false]").click();
        }
        String day = generateData(27, "d");
        $(byText(day)).click();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
