package com.statemachinesystems.mockclock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Test;

import static java.time.Month.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MockClockTest {

    private static final ZoneId UTC = ZoneId.of("UTC");

    private static final int YEAR = 2015;
    private static final Month MONTH = DECEMBER;
    private static final int DAY_OF_MONTH = 9;
    private static final int HOUR = 12;
    private static final int MINUTE = 25;
    private static final int SECOND = 38;
    private static final int NANO_OF_SECOND = 111;
    private static final LocalDate LOCAL_DATE = LocalDate.of(YEAR, MONTH, DAY_OF_MONTH);
    private static final LocalTime LOCAL_TIME = LocalTime.of(HOUR, MINUTE, SECOND, NANO_OF_SECOND);
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(LOCAL_DATE, LOCAL_TIME);
    private static final Instant DATE_TIME_INSTANT = LOCAL_DATE_TIME.atZone(UTC).toInstant();
    private static final Instant DATE_ONLY_INSTANT = LOCAL_DATE.atStartOfDay(UTC).toInstant();

    @Test
    public void constructUsingSystemClock() {
        Clock systemClock = Clock.system(UTC);
        Instant now = systemClock.instant();

        MockClock mockClock = MockClock.now(UTC).setNano(0);

        Instant roundedInstant = ZonedDateTime.ofInstant(now, UTC).withNano(0).toInstant();
        assertClockInstant(mockClock, roundedInstant);
    }

    @Test
    public void constructFromClock() {
        Clock fixedClock = Clock.fixed(DATE_TIME_INSTANT, UTC);
        MockClock mockClock = MockClock.at(fixedClock);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromInstant() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromZonedDateTime() {
        ZonedDateTime zonedDateTime = LOCAL_DATE_TIME.atZone(UTC);
        MockClock mockClock = MockClock.at(zonedDateTime);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromLocalDateTime() {
        MockClock mockClock = MockClock.at(LOCAL_DATE_TIME, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromLocalDate() {
        MockClock mockClock = MockClock.at(LOCAL_DATE, UTC);

        assertClockInstant(mockClock, DATE_ONLY_INSTANT);
    }

    @Test
    public void constructFromLocalDateAndLocalTime() {
        MockClock mockClock = MockClock.at(LOCAL_DATE, LOCAL_TIME, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromFullDateAndTimeWithNanoResolution() {
        MockClock mockClock = MockClock.at(YEAR, DECEMBER, DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromFullDateAndTimeWithSecondResolution() {
        MockClock mockClock = MockClock.at(YEAR, DECEMBER, DAY_OF_MONTH, HOUR, MINUTE, SECOND, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT.minusNanos(NANO_OF_SECOND));
    }

    @Test
    public void constructFromFullDateAndTimeWithMinuteResolution() {
        MockClock mockClock = MockClock.at(YEAR, DECEMBER, DAY_OF_MONTH, HOUR, MINUTE, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT.minusNanos(NANO_OF_SECOND).minusSeconds(SECOND));
    }

    @Test
    public void constructFromFullDate() {
        MockClock mockClock = MockClock.at(YEAR, DECEMBER, DAY_OF_MONTH, UTC);

        assertClockInstant(mockClock, DATE_ONLY_INSTANT);
    }

    @Test
    public void constructFromFullDateAndTimeWithIntegerMonthAndNanoResolution() {
        MockClock mockClock = MockClock.at(YEAR, 12, DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT);
    }

    @Test
    public void constructFromFullDateAndTimeWithIntegerMonthAndSecondResolution() {
        MockClock mockClock = MockClock.at(YEAR, 12, DAY_OF_MONTH, HOUR, MINUTE, SECOND, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT.minusNanos(NANO_OF_SECOND));
    }

    @Test
    public void constructFromFullDateAndTimeWithIntegerMonthAndMinuteResolution() {
        MockClock mockClock = MockClock.at(YEAR, 12, DAY_OF_MONTH, HOUR, MINUTE, UTC);

        assertClockInstant(mockClock, DATE_TIME_INSTANT.minusNanos(NANO_OF_SECOND).minusSeconds(SECOND));
    }

    @Test
    public void constructFromFullDateWithIntegerMonth() {
        MockClock mockClock = MockClock.at(YEAR, 12, DAY_OF_MONTH, UTC);

        assertClockInstant(mockClock, DATE_ONLY_INSTANT);
    }

    @Test
    public void adjustWithInstant() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, 13, 212, UTC).toInstant();

        mockClock.set(adjustedInstant);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithLocalDateTime() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        LocalDateTime adjustedLocalDateTime = LocalDateTime.of(2016, 1, 4, 9, 30, 13, 212);
        Instant adjustedInstant = ZonedDateTime.of(adjustedLocalDateTime, UTC).toInstant();

        mockClock.set(adjustedLocalDateTime);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithLocalDateAndLocalTime() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        LocalDate adjustedLocalDate = LocalDate.of(2016, 1, 4);
        LocalTime adjustedLocalTime = LocalTime.of(9, 30, 13, 212);
        Instant adjustedInstant = ZonedDateTime.of(adjustedLocalDate, adjustedLocalTime, UTC).toInstant();

        mockClock.set(adjustedLocalDate, adjustedLocalTime);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithLocalDateLeavingTimeUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        LocalDate adjustedLocalDate = LocalDate.of(2016, 1, 4);
        Instant adjustedInstant = ZonedDateTime.of(adjustedLocalDate, LOCAL_TIME, UTC).toInstant();

        mockClock.set(adjustedLocalDate);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithLocalTimeLeavingDateUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        LocalTime adjustedLocalTime = LocalTime.of(9, 30, 13, 212);
        Instant adjustedInstant = ZonedDateTime.of(LOCAL_DATE, adjustedLocalTime, UTC).toInstant();

        mockClock.set(adjustedLocalTime);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndTimeWithNanoResolution() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, 13, 212, UTC).toInstant();

        mockClock.set(2016, JANUARY, 4, 9, 30, 13, 212);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndTimeWithSecondResolutionLeavingNanoUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, 13, NANO_OF_SECOND, UTC).toInstant();

        mockClock.set(2016, JANUARY, 4, 9, 30, 13);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndTimeWithMinuteResolutionLeavingSecondAndNanoUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.set(2016, JANUARY, 4, 9, 30);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateLeavingTimeUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.set(2016, JANUARY, 4);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndTimeWithIntegerMonthAndNanoResolution() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, 13, 212, UTC).toInstant();

        mockClock.set(2016, 1, 4, 9, 30, 13, 212);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndTimeWithIntegerMonthAndSecondResolutionLeavingNanoUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, 13, NANO_OF_SECOND, UTC).toInstant();

        mockClock.set(2016, 1, 4, 9, 30, 13);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndTimeWithIntegerMonthAndMinuteResolutionLeavingSecondAndNanoUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, 9, 30, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.set(2016, 1, 4, 9, 30);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithFullDateAndIntegerMonthLeavingTimeUnchanged() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(2016, 1, 4, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.set(2016, 1, 4);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithYear() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(2017, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setYear(2017);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithMonth() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, JANUARY.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setMonth(JANUARY);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithIntegerMonth() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, JANUARY.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setMonth(1);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithDayOfMonth() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), 17, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setDayOfMonth(17);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithHour() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, 9, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setHour(9);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithMinute() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, 30, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setMinute(30);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithSecond() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, 13, NANO_OF_SECOND, UTC).toInstant();

        mockClock.setSecond(13);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithMilli() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        int nanoOfSecond = 400_000_000;
        Instant adjustedInstant = ZonedDateTime.of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND,
                nanoOfSecond, UTC).toInstant();

        mockClock.setMilli(400);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void adjustWithNano() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime.of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND, 212, UTC)
                .toInstant();

        mockClock.setNano(212);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceByDuration() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND + 10, NANO_OF_SECOND, UTC).toInstant();

        mockClock.advanceBy(Duration.ofSeconds(10));

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceByDays() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH + 3, HOUR, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.advanceByDays(3);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceByHours() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR + 2, MINUTE, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.advanceByHours(2);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceByMinutes() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE + 4, SECOND, NANO_OF_SECOND, UTC).toInstant();

        mockClock.advanceByMinutes(4);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceBySeconds() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND + 5, NANO_OF_SECOND, UTC).toInstant();

        mockClock.advanceBySeconds(5);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceByMillis() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        int nanoOfSecond = NANO_OF_SECOND + 400_000_000;
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND, nanoOfSecond, UTC).toInstant();

        mockClock.advanceByMillis(400);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void advanceByNanos() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        Instant adjustedInstant = ZonedDateTime
                .of(YEAR, MONTH.getValue(), DAY_OF_MONTH, HOUR, MINUTE, SECOND, NANO_OF_SECOND + 1000, UTC).toInstant();

        mockClock.advanceByNanos(1000);

        assertClockInstant(mockClock, adjustedInstant);
    }

    @Test
    public void withZoneCopyHasSameInstantButDifferentZone() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);
        ZoneId cet = ZoneId.of("CET");

        MockClock copied = mockClock.withZone(cet);

        assertClockInstant(copied, DATE_TIME_INSTANT, cet);
    }

    @Test
    public void sameConstructorParametersYieldEqualInstances() {
        MockClock mockClock1 = MockClock.at(DATE_TIME_INSTANT, UTC);
        MockClock mockClock2 = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertThat(mockClock1.equals(mockClock2), is(true));
    }

    @Test
    public void sameInstancesAreEqual() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertThat(mockClock.equals(mockClock), is(true));
    }

    @Test
    public void nullInstanceIsNotEqual() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertThat(mockClock.equals(null), is(false));
    }

    @Test
    public void instanceOfDifferentClassIsNotEqual() {
        MockClock mockClock = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertThat(mockClock.equals(Clock.systemUTC()), is(false));
    }

    @Test
    public void clockWithDifferentInstantIsNotEqual() {
        MockClock mockClock1 = MockClock.at(DATE_TIME_INSTANT, UTC);
        MockClock mockClock2 = MockClock.at(DATE_ONLY_INSTANT, UTC);

        assertThat(mockClock1.equals(mockClock2), is(false));
    }

    @Test
    public void clockWithDifferentZoneIsNotEqual() {
        MockClock mockClock1 = MockClock.at(DATE_TIME_INSTANT, UTC);
        MockClock mockClock2 = MockClock.at(DATE_TIME_INSTANT, ZoneId.of("CET"));

        assertThat(mockClock1.equals(mockClock2), is(false));
    }

    @Test
    public void sameConstructorParametersYieldEqualHashCodes() {
        MockClock mockClock1 = MockClock.at(DATE_TIME_INSTANT, UTC);
        MockClock mockClock2 = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertThat(mockClock1.hashCode(), is(mockClock2.hashCode()));
    }

    @Test
    public void sameConstructorParametersYieldEqualStringRepresentations() {
        MockClock mockClock1 = MockClock.at(DATE_TIME_INSTANT, UTC);
        MockClock mockClock2 = MockClock.at(DATE_TIME_INSTANT, UTC);

        assertThat(mockClock1.toString(), is(mockClock2.toString()));
    }

    private void assertClockInstant(MockClock mockClock, Instant instant) {
        assertClockInstant(mockClock, instant, UTC);
    }

    private void assertClockInstant(MockClock mockClock, Instant instant, ZoneId zone) {
        assertThat(mockClock.instant(), is(instant));
        assertThat(mockClock.getZone(), is(zone));
    }
}
