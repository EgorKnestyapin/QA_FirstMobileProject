package com.remindly.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReminderTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        app.getMainScreenHelper().confirm();
    }

    @Test
    public void addReminderPositiveTest() {
        // tap on add reminder
        app.getMainScreenHelper().tapOnAddReminder();
        String title = "Holiday";
        // enter reminder title
        app.getReminderHelper().enterTitleOfReminder(title);
        // select date, month, year
        app.getReminderHelper().tapOnDateField();
        app.getReminderHelper().swipeToMonth("future", 2, "MAY");
        app.getReminderHelper().selectDate(0);
        app.getReminderHelper().selectYear("future", "2025");
        app.getReminderHelper().tapOnOk();
        // select time
        app.getReminderHelper().selectTime("AM", 540, 1200, 780, 1060);
        // select repetition interval
        app.getReminderHelper().selectRepetitionInterval("3");
        // scroll
        app.getReminderHelper().scroll(0.6, 0.4);
        // select type of repetition
        app.getReminderHelper().selectTypeOfRepetition("day");
        app.getReminderHelper().saveReminder();
        // assert reminder is added
        Assert.assertTrue(app.getReminderHelper().isReminderAdded(title));
    }
}
