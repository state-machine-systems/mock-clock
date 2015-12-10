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
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Mutable implementation of java.time.Clock for testing.
 */
public class MockClock extends Clock {

    public static MockClock now(ZoneId zone) {
        return at(Clock.system(zone));
    }

    public static MockClock at(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return new MockClock(clock.instant(), clock.getZone());
    }

    public static MockClock at(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        return new MockClock(instant, zone);
    }

    public static MockClock at(ZonedDateTime zonedDateTime) {
        Objects.requireNonNull(zonedDateTime, "zonedDateTime");
        return at(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    public static MockClock at(LocalDateTime localDateTime, ZoneId zone) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zone, "zone");
        return at(localDateTime.atZone(zone));
    }

    public static MockClock at(LocalDate localDate, ZoneId zone) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(zone, "zone");
        return at(localDate.atStartOfDay(zone));
    }

    public static MockClock at(LocalDate localDate, LocalTime localTime, ZoneId zone) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(localTime, "localTime");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDateTime.of(localDate, localTime), zone);
    }

    public static MockClock at(int year, Month month, int dayOfMonth, int hour, int minute, int second,
            int nanoOfSecond, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second, nanoOfSecond), zone);
    }

    public static MockClock at(int year, Month month, int dayOfMonth, int hour, int minute, int second, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second), zone);
    }

    public static MockClock at(int year, Month month, int dayOfMonth, int hour, int minute, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute), zone);
    }

    public static MockClock at(int year, Month month, int dayOfMonth, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), zone);
    }

    public static MockClock at(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
            ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second, nanoOfSecond), zone);
    }

    public static MockClock at(int year, int month, int dayOfMonth, int hour, int minute, int second, ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second), zone);
    }

    public static MockClock at(int year, int month, int dayOfMonth, int hour, int minute, ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute), zone);
    }

    public static MockClock at(int year, int month, int dayOfMonth, ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), zone);
    }

    private Instant instant;
    private final ZoneId zone;

    private MockClock(Instant instant, ZoneId zone) {
        this.instant = instant;
        this.zone = zone;
    }

    public MockClock set(Instant instant) {
        Objects.requireNonNull(instant, "instant");
        this.instant = instant;
        return this;
    }

    public MockClock set(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        this.instant = localDateTime.atZone(zone).toInstant();
        return this;
    }

    public MockClock set(LocalDate localDate, LocalTime localTime) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(localTime, "localTime");
        this.instant = ZonedDateTime.of(localDate, localTime, zone).toInstant();
        return this;
    }

    /**
     * Set the clock's date using the given LocalDate, retaining the current time.
     *
     * @param localDate the LocalDate to use
     * @return this MockClock instance
     */
    public MockClock set(LocalDate localDate) {
        Objects.requireNonNull(localDate, "localDate");
        LocalTime localTime = toZonedDateTime().toLocalTime();
        this.instant = ZonedDateTime.of(localDate, localTime, zone).toInstant();
        return this;
    }

    /**
     * Set the clock's time using the given LocalTime, retaining the current date.
     *
     * @param localTime the LocalTime to use
     * @return this MockClock instance
     */
    public MockClock set(LocalTime localTime) {
        Objects.requireNonNull(localTime, "localTime");
        LocalDate localDate = toZonedDateTime().toLocalDate();
        this.instant = ZonedDateTime.of(localDate, localTime, zone).toInstant();
        return this;
    }

    /**
     * Set the clock's date and time using the given year, month, day of month, hour, minute, second and nanosecond.
     *
     * @param year         the year to use
     * @param month        the month to use
     * @param dayOfMonth   the day of month to use
     * @param hour         the hour to use
     * @param minute       the minute to use
     * @param second       the second to use
     * @param nanoOfSecond the nanosecond to use
     * @return this MockClock instance
     */
    public MockClock set(int year, Month month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        Objects.requireNonNull(month, "month");
        ZonedDateTime zonedDateTime = ZonedDateTime
                .of(year, month.getValue(), dayOfMonth, hour, minute, second, nanoOfSecond, zone);
        this.instant = zonedDateTime.toInstant();
        return this;
    }

    /**
     * Set the clock's date and time using the given year, month, day of month, hour, minute and second, retaining the
     * current nanosecond.
     *
     * @param year       the year to use
     * @param month      the month to use
     * @param dayOfMonth the day of month to use
     * @param hour       the hour to use
     * @param minute     the minute to use
     * @param second     the second to use
     * @return this MockClock instance
     */
    public MockClock set(int year, Month month, int dayOfMonth, int hour, int minute, int second) {
        int nanoOfSecond = toZonedDateTime().getNano();
        return set(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    /**
     * Set the clock's date and time using the given year, month, day of month, hour and minute, retaining the
     * current second and nanosecond.
     *
     * @param year       the year to use
     * @param month      the month to use
     * @param dayOfMonth the day of month to use
     * @param hour       the hour to use
     * @param minute     the minute to use
     * @return this MockClock instance
     */
    public MockClock set(int year, Month month, int dayOfMonth, int hour, int minute) {
        ZonedDateTime zonedDateTime = toZonedDateTime();
        int second = zonedDateTime.getSecond();
        int nanoOfSecond = zonedDateTime.getNano();
        return set(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    /**
     * Set the clock's date using the given year, month and day of month, retaining the current time.
     *
     * @param year       the year to use
     * @param month      the month to use
     * @param dayOfMonth the day of month to use
     * @return this MockClock instance
     */
    public MockClock set(int year, Month month, int dayOfMonth) {
        ZonedDateTime zonedDateTime = toZonedDateTime();
        int hour = zonedDateTime.getHour();
        int minute = zonedDateTime.getMinute();
        int second = zonedDateTime.getSecond();
        int nanoOfSecond = zonedDateTime.getNano();
        return set(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    /**
     * Set the clock's date and time using the given year, month, day of month, hour, minute, second and nanosecond.
     *
     * @param year         the year to use
     * @param month        the month to use
     * @param dayOfMonth   the day of month to use
     * @param hour         the hour to use
     * @param minute       the minute to use
     * @param second       the second to use
     * @param nanoOfSecond the nanosecond to use
     * @return this MockClock instance
     */
    public MockClock set(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        return set(year, Month.of(month), dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    /**
     * Set the clock's date and time using the given year, month, day of month, hour, minute and second, retaining the
     * current nanosecond.
     *
     * @param year       the year to use
     * @param month      the month to use
     * @param dayOfMonth the day of month to use
     * @param hour       the hour to use
     * @param minute     the minute to use
     * @param second     the second to use
     * @return this MockClock instance
     */
    public MockClock set(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        return set(year, Month.of(month), dayOfMonth, hour, minute, second);
    }

    /**
     * Set the clock's date and time using the given year, month, day of month, hour and minute, retaining the
     * current second and nanosecond.
     *
     * @param year       the year to use
     * @param month      the month to use
     * @param dayOfMonth the day of month to use
     * @param hour       the hour to use
     * @param minute     the minute to use
     * @return this MockClock instance
     */
    public MockClock set(int year, int month, int dayOfMonth, int hour, int minute) {
        return set(year, Month.of(month), dayOfMonth, hour, minute);
    }

    /**
     * Set the clock's date using the given year, month and day of month, retaining the current time.
     *
     * @param year       the year to use
     * @param month      the month to use
     * @param dayOfMonth the day of month to use
     * @return this MockClock instance
     */
    public MockClock set(int year, int month, int dayOfMonth) {
        return set(year, Month.of(month), dayOfMonth);
    }

    public MockClock setYear(int year) {
        this.instant = toZonedDateTime().withYear(year).toInstant();
        return this;
    }

    public MockClock setMonth(Month month) {
        return setMonth(month.getValue());
    }

    public MockClock setMonth(int month) {
        this.instant = toZonedDateTime().withMonth(month).toInstant();
        return this;
    }

    public MockClock setDayOfMonth(int dayOfMonth) {
        this.instant = toZonedDateTime().withDayOfMonth(dayOfMonth).toInstant();
        return this;
    }

    public MockClock setHour(int hour) {
        this.instant = toZonedDateTime().withHour(hour).toInstant();
        return this;
    }

    public MockClock setMinute(int minute) {
        this.instant = toZonedDateTime().withMinute(minute).toInstant();
        return this;
    }

    public MockClock setSecond(int second) {
        this.instant = toZonedDateTime().withSecond(second).toInstant();
        return this;
    }

    public MockClock setMilli(int milliOfSecond) {
        int nanoOfSecond = (int) TimeUnit.MILLISECONDS.toNanos(milliOfSecond);
        return setNano(nanoOfSecond);
    }

    public MockClock setNano(int nanoOfSecond) {
        this.instant = toZonedDateTime().withNano(nanoOfSecond).toInstant();
        return this;
    }

    public MockClock advanceBy(Duration duration) {
        this.instant = toZonedDateTime().plus(duration).toInstant();
        return this;
    }

    public MockClock advanceByDays(int days) {
        return advanceBy(Duration.ofDays(days));
    }

    public MockClock advanceByHours(int hours) {
        return advanceBy(Duration.ofHours(hours));
    }

    public MockClock advanceByMinutes(int minutes) {
        return advanceBy(Duration.ofMinutes(minutes));
    }

    public MockClock advanceBySeconds(int seconds) {
        return advanceBy(Duration.ofSeconds(seconds));
    }

    public MockClock advanceByMillis(int millis) {
        return advanceBy(Duration.ofMillis(millis));
    }

    public MockClock advanceByNanos(int nanos) {
        return advanceBy(Duration.ofNanos(nanos));
    }

    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.ofInstant(instant, zone);
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public MockClock withZone(ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return new MockClock(instant, zone);
    }

    @Override
    public Instant instant() {
        return instant;
    }

    @Override
    public String toString() {
        return "MockClock{" +
                "instant=" + instant +
                ", zone=" + zone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MockClock mockClock = (MockClock) o;
        return Objects.equals(instant, mockClock.instant) && Objects.equals(zone, mockClock.zone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instant, zone);
    }
}
