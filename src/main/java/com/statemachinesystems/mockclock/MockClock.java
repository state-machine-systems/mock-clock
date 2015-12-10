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
 * Mutable implementation of {@link java.time.Clock} for testing.
 */
public class MockClock extends Clock {

    /**
     * Construct a {@link MockClock} using the current time in the given zone.
     *
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the current time in the given zone
     */
    public static MockClock now(ZoneId zone) {
        return at(Clock.system(zone));
    }

    /**
     * Construct a {@link MockClock} using the current time according to the given clock.
     *
     * @param clock  the clock to use
     * @return a {@link MockClock} instance initially set to the current time according to the given clock
     */
    public static MockClock at(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return new MockClock(clock.instant(), clock.getZone());
    }

    /**
     * Construct a {@link MockClock} using the given {@link Instant} and zone.
     *
     * @param instant  the {@link Instant} to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given {@link Instant} and zone
     */
    public static MockClock at(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        return new MockClock(instant, zone);
    }

    /**
     * Construct a {@link MockClock} using the given {@link ZonedDateTime}.
     *
     * @param zonedDateTime  the {@link ZonedDateTime} to use
     * @return a {@link MockClock} instance initially set to the given {@link ZonedDateTime}
     */
    public static MockClock at(ZonedDateTime zonedDateTime) {
        Objects.requireNonNull(zonedDateTime, "zonedDateTime");
        return at(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    /**
     * Construct a {@link MockClock} using the given {@link LocalDateTime} and zone.
     *
     * @param localDateTime  the {@link LocalDateTime} to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given {@link LocalDateTime} and zone
     */
    public static MockClock at(LocalDateTime localDateTime, ZoneId zone) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zone, "zone");
        return at(localDateTime.atZone(zone));
    }

    /**
     * Construct a {@link MockClock} using the given date and zone.
     *
     * @param localDate  the date to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given date and zone
     */
    public static MockClock at(LocalDate localDate, ZoneId zone) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(zone, "zone");
        return at(localDate.atStartOfDay(zone));
    }

    /**
     * Construct a {@link MockClock} using the given date, time and zone.
     *
     * @param localDate  the date to use
     * @param localTime  the time to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given date, time and zone
     */
    public static MockClock at(LocalDate localDate, LocalTime localTime, ZoneId zone) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(localTime, "localTime");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDateTime.of(localDate, localTime), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month, hour, minute, second, nanosecond and
     * zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param hour  the hour to use
     * @param minute  the minute to use
     * @param second  the second to use
     * @param nanoOfSecond  the nanosecond to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month, hour, minute, second,
     * nanosecond and zone
     */
    public static MockClock at(int year, Month month, int dayOfMonth, int hour, int minute, int second,
            int nanoOfSecond, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second, nanoOfSecond), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month, hour, minute, second and zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param hour  the hour to use
     * @param minute  the minute to use
     * @param second  the second to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month, hour, minute, second
     * and zone
     */
    public static MockClock at(int year, Month month, int dayOfMonth, int hour, int minute, int second, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month, hour, minute and zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param hour  the hour to use
     * @param minute  the minute to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month, hour, minute and zone
     */
    public static MockClock at(int year, Month month, int dayOfMonth, int hour, int minute, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month and zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month and zone
     */
    public static MockClock at(int year, Month month, int dayOfMonth, ZoneId zone) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month, hour, minute, second, nanosecond and
     * zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param hour  the hour to use
     * @param minute  the minute to use
     * @param second  the second to use
     * @param nanoOfSecond  the nanosecond to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month, hour, minute, second,
     * nanosecond and zone
     */
    public static MockClock at(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
            ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second, nanoOfSecond), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month, hour, minute, second and zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param hour  the hour to use
     * @param minute  the minute to use
     * @param second  the second to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month, hour, minute, second
     * and zone
     */
    public static MockClock at(int year, int month, int dayOfMonth, int hour, int minute, int second, ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute, second), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month, hour, minute and zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param hour  the hour to use
     * @param minute  the minute to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month, hour, minute and zone
     */
    public static MockClock at(int year, int month, int dayOfMonth, int hour, int minute, ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return at(LocalDate.of(year, month, dayOfMonth), LocalTime.of(hour, minute), zone);
    }

    /**
     * Construct a {@link MockClock} using the given year, month, day of month and zone.
     *
     * @param year  the year to use
     * @param month  the month to use
     * @param dayOfMonth  the day of month to use
     * @param zone  the zone to use
     * @return a {@link MockClock} instance initially set to the given year, month, day of month and zone
     */
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

    /**
     * Set the clock's date and time to the given {@link Instant}.
     *
     * @param instant  the {@link Instant} to use
     * @return this {@link MockClock} instance
     */
    public MockClock set(Instant instant) {
        Objects.requireNonNull(instant, "instant");
        this.instant = instant;
        return this;
    }

    /**
     * Set the clock's date and time using the given {@link LocalDateTime}.
     *
     * @param localDateTime  the {@link LocalDateTime} to use
     * @return this {@link MockClock} instance
     */
    public MockClock set(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        this.instant = localDateTime.atZone(zone).toInstant();
        return this;
    }

    /**
     * Set the clock's date and time using the given {@link LocalDate} and {@link LocalTime}.
     *
     * @param localDate  the {@link LocalDate} to use
     * @param localTime  the {@link LocalTime} to use
     * @return this {@link MockClock} instance
     */
    public MockClock set(LocalDate localDate, LocalTime localTime) {
        Objects.requireNonNull(localDate, "localDate");
        Objects.requireNonNull(localTime, "localTime");
        this.instant = ZonedDateTime.of(localDate, localTime, zone).toInstant();
        return this;
    }

    /**
     * Set the clock's date using the given {@link LocalDate}, retaining the current time.
     *
     * @param localDate the {@link LocalDate} to use
     * @return this {@link MockClock} instance
     */
    public MockClock set(LocalDate localDate) {
        Objects.requireNonNull(localDate, "localDate");
        LocalTime localTime = toZonedDateTime().toLocalTime();
        this.instant = ZonedDateTime.of(localDate, localTime, zone).toInstant();
        return this;
    }

    /**
     * Set the clock's time using the given {@link LocalTime}, retaining the current date.
     *
     * @param localTime the {@link LocalTime} to use
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
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
     * @return this {@link MockClock} instance
     */
    public MockClock set(int year, int month, int dayOfMonth) {
        return set(year, Month.of(month), dayOfMonth);
    }

    /**
     * Set the clock's year only, retaining all other date and time components.
     *
     * @param year  the year to use
     * @return this {@link MockClock} instance
     */
    public MockClock setYear(int year) {
        this.instant = toZonedDateTime().withYear(year).toInstant();
        return this;
    }

    /**
     * Set the clock's month only, retaining all other date and time components.
     *
     * @param month  the month to use
     * @return this {@link MockClock} instance
     */
    public MockClock setMonth(Month month) {
        return setMonth(month.getValue());
    }

    /**
     * Set the clock's month only, retaining all other date and time components.
     *
     * @param month  the month to use
     * @return this {@link MockClock} instance
     */
    public MockClock setMonth(int month) {
        this.instant = toZonedDateTime().withMonth(month).toInstant();
        return this;
    }

    /**
     * Set the clock's day of month only, retaining all other date and time components.
     *
     * @param dayOfMonth  the day of month to use
     * @return this {@link MockClock} instance
     */
    public MockClock setDayOfMonth(int dayOfMonth) {
        this.instant = toZonedDateTime().withDayOfMonth(dayOfMonth).toInstant();
        return this;
    }

    /**
     * Set the clock's hour only, retaining all other date and time components.
     *
     * @param hour  the hour to use
     * @return this {@link MockClock} instance
     */
    public MockClock setHour(int hour) {
        this.instant = toZonedDateTime().withHour(hour).toInstant();
        return this;
    }

    /**
     * Set the clock's minute only, retaining all other date and time components.
     *
     * @param minute  the minute to use
     * @return this {@link MockClock} instance
     */
    public MockClock setMinute(int minute) {
        this.instant = toZonedDateTime().withMinute(minute).toInstant();
        return this;
    }

    /**
     * Set the clock's second only, retaining all other date and time components.
     *
     * @param second  the second to use
     * @return this {@link MockClock} instance
     */
    public MockClock setSecond(int second) {
        this.instant = toZonedDateTime().withSecond(second).toInstant();
        return this;
    }

    /**
     * Set the clock's millisecond only, retaining all other date and time components.
     *
     * @param milliOfSecond  the millisecond to use
     * @return this {@link MockClock} instance
     */
    public MockClock setMilli(int milliOfSecond) {
        int nanoOfSecond = (int) TimeUnit.MILLISECONDS.toNanos(milliOfSecond);
        return setNano(nanoOfSecond);
    }

    /**
     * Set the clock's nanosecond only, retaining all other date and time components.
     *
     * @param nanoOfSecond  the nanosecond to use
     * @return this {@link MockClock} instance
     */
    public MockClock setNano(int nanoOfSecond) {
        this.instant = toZonedDateTime().withNano(nanoOfSecond).toInstant();
        return this;
    }

    /**
     * Advance the clock by the given {@link Duration}.
     *
     * @param duration  the duration by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceBy(Duration duration) {
        this.instant = toZonedDateTime().plus(duration).toInstant();
        return this;
    }

    /**
     * Advance the clock by the given number of days (represented as 24 hours.)
     *
     * @param days  the number of days by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceByDays(int days) {
        return advanceBy(Duration.ofDays(days));
    }

    /**
     * Advance the clock by the given number of hours.
     *
     * @param hours  the number of hours by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceByHours(int hours) {
        return advanceBy(Duration.ofHours(hours));
    }

    /**
     * Advance the clock by the given number of minutes.
     *
     * @param minutes  the number of minutes by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceByMinutes(int minutes) {
        return advanceBy(Duration.ofMinutes(minutes));
    }

    /**
     * Advance the clock by the given number of seconds.
     *
     * @param seconds  the number of seconds by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceBySeconds(int seconds) {
        return advanceBy(Duration.ofSeconds(seconds));
    }

    /**
     * Advance the clock by the given number of milliseconds.
     *
     * @param millis  the number of milliseconds by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceByMillis(int millis) {
        return advanceBy(Duration.ofMillis(millis));
    }

    /**
     * Advance the clock by the given number of nanoseconds.
     *
     * @param nanos  the number of nanoseconds by which to advance
     * @return this {@link MockClock} instance
     */
    public MockClock advanceByNanos(int nanos) {
        return advanceBy(Duration.ofNanos(nanos));
    }

    /**
     * Retrieve the clock's date and time as a {@link ZonedDateTime}.
     *
     * @return a {@link ZonedDateTime} representing the clock's current state
     */
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
        return "MockClock[" + instant + "," + zone + "]";
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
