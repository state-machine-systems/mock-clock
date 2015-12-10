Java 8's `Clock` class is a big step in the right direction for testability, but it's still a pain. You can
test using `Clock.fixed()` but that's immutable so you can't test how your code responds to the passage of time.
You can use a mocking library to stub the `instant()` and `getZone()` methods, but that's still awkward and indirect.

This library provides a mutable `MockClock` class that can be constructed as of a given date and time, then adjusted as
needed. Here's an example of how to use it:


    import com.statemachinesystems.mockclock.MockClock;
    import java.time.ZoneId;

    MockClock clock = MockClock.at(2015, 12, 10, 11, 16, ZoneId.of("UTC"));
    ClassUnderTest testSubject = new ClassUnderTest(clock);

    assertThat(testSubject.someMethod(), is(expectedValueAtStartTime));

    clock.advanceBySeconds(30);

    assertThat(testSubject.someMethod(), is(expectedValueAfter30Seconds));

&copy; 2015 State Machine Systems Ltd. [Apache Licence, Version 2.0]( http://www.apache.org/licenses/LICENSE-2.0)