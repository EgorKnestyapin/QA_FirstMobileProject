package com.remindly.fw;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ReminderHelper extends BaseHelper {
    public ReminderHelper(AppiumDriver driver) {
        super(driver);
    }

    public void enterTitleOfReminder(String title) {
        type(By.id("reminder_title"), title);
    }

    public void tapOnDateField() {
        tap(By.id("date"));
    }

    public void swipeToMonth(String period, int number, String month) {
        pause(500);
        if (!getSelectedMonth().equals(month)) {
            for (int i = 0; i < number; i++) {
                if (period.equals("future")) {
                    swipe(0.8, 0.4);
                } else if (period.equals("past")) {
                    swipe(0.5, 0.8);
                }
            }
        }
    }

    public void swipe(double start, double stop) {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * start);
        int stopY = (int) (size.height * stop);
        new TouchAction((PerformsTouchActions) driver).longPress(PointOption.point(x, startY))
                .moveTo(PointOption.point(x, stopY))
                .release().perform();
    }

    public String getSelectedMonth() {
        return driver.findElement(By.id("date_picker_month")).getText();
    }

    public void selectDate(int index) {
        List<WebElement> days = driver.findElements(By.className("android.view.View"));
        days.get(index).click();
    }

    public void selectYear(String period2, String year) {
        tap(By.id("date_picker_year"));

        pause(500);

        if (!getSelectedYear().equals(year)) {
            if (period2.equals("future")) {
                swipeUntilNeededYear(year, 0.6, 0.5);
            } else if (period2.equals("past")) {
                swipeUntilNeededYear(year, 0.5, 0.6);
            }
        }
    }

    private void swipeUntilNeededYear(String year, double startPoint, double stopPoint) {
        while (!getYear().equals(year)) {
            swipeInElement(By.className("android.widget.ListView"), startPoint, stopPoint);
        }
    }

    private void swipeInElement(By locator, double startPoint, double stopPoint) {
        Dimension size = driver.manage().window().getSize();
        // get activity point
        int y = (int) (size.height * startPoint);
        int y2 = (int) (size.height * stopPoint);
        // get Locator's point
        WebElement element = driver.findElement(locator);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int middleX = (leftX + rightX) / 2;

        new TouchAction((PerformsTouchActions) driver).longPress(PointOption.point(middleX, y))
                .moveTo(PointOption.point(middleX, y2))
                .release().perform();
    }

    private String getYear() {
        return driver.findElement(By.id("month_text_view")).getText();
    }

    public String getSelectedYear() {
        return driver.findElement(By.id("date_picker_year")).getText();
    }

    public void tapOnOk() {
        tap(By.id("ok"));
    }

    public void selectTime(String timeOfDay, int hourX, int hourY, int minuteX, int minuteY) {
        tap(By.id("time"));
        pause(500);
        if (timeOfDay.equalsIgnoreCase("AM")) {
            tapWithCoordinates(290, 1330);
        } else if (!timeOfDay.equalsIgnoreCase("PM")) {
            throw new RuntimeException("Incorrect timeOfDay");
        }
        tapWithCoordinates(hourX, hourY);
        tapWithCoordinates(minuteX, minuteY);
        tap(By.id("ok"));
    }

    public void tapWithCoordinates(int x, int y) {
        new TouchAction((PerformsTouchActions) driver)
                .tap(PointOption.point(x, y))
                .release().perform();
    }

    public void selectRepetitionInterval(String hourInterval) {
        tap(By.id("repeat_no_text"));
        type(By.className("android.widget.EditText"), hourInterval);
        tap(By.id("android:id/button1"));
    }

    public void scroll(double startPoint, double stopPoint) {
        Dimension size = driver.manage().window().getSize();
        int x = (size.width / 2);
        int y = (int) (size.height * startPoint);
        int y2 = (int) (size.height * stopPoint);

        new TouchAction((PerformsTouchActions) driver).longPress(PointOption.point(x, y))
                .moveTo(PointOption.point(x, y2))
                .release().perform();
    }

    public void selectTypeOfRepetition(String type) {
        type = type.toLowerCase();
        tap(By.id("repeat_type_text"));
        switch (type) {
            case "minute":
                tap(By.xpath("//*[@text='Minute']"));
                break;
            case "hour":
                tap(By.xpath("//*[@text='Hour']"));
                break;
            case "day":
                tap(By.xpath("//*[@text='Day']"));
                break;
            case "week":
                tap(By.xpath("//*[@text='Week']"));
                break;
            case "month":
                tap(By.xpath("//*[@text='Month']"));
                break;
            default:
                throw new RuntimeException("Invalid type");
        }
    }

    public void saveReminder() {
        tap(By.id("save_reminder"));
    }

    public boolean isReminderAdded(String title) {
        return isElementPresent(By.xpath(String.format("//*[@text='%s']", title)));
    }
}
