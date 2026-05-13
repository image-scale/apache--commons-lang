package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class StopWatchTest {

    @Test
    void testInitialState() {
        final StopWatch sw = new StopWatch();
        assertEquals(StopWatch.State.UNSTARTED, sw.getState());
        assertFalse(sw.isStarted());
        assertFalse(sw.isRunning());
        assertFalse(sw.isStopped());
        assertFalse(sw.isSuspended());
        assertEquals(0, sw.getNanoTime());
        assertEquals(0, sw.getTime());
    }

    @Test
    void testStart() {
        final StopWatch sw = new StopWatch();
        sw.start();
        assertTrue(sw.isStarted());
        assertTrue(sw.isRunning());
        assertEquals(StopWatch.State.RUNNING, sw.getState());
    }

    @Test
    void testStartAlreadyStartedThrows() {
        final StopWatch sw = new StopWatch();
        sw.start();
        assertThrows(IllegalStateException.class, sw::start);
    }

    @Test
    void testStop() {
        final StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();
        assertTrue(sw.isStopped());
        assertEquals(StopWatch.State.STOPPED, sw.getState());
        assertTrue(sw.getNanoTime() >= 0);
    }

    @Test
    void testStopWithoutStartThrows() {
        final StopWatch sw = new StopWatch();
        assertThrows(IllegalStateException.class, sw::stop);
    }

    @Test
    void testStopAlreadyStoppedThrows() {
        final StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();
        assertThrows(IllegalStateException.class, sw::stop);
    }

    @Test
    void testReset() {
        final StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();
        sw.reset();
        assertEquals(StopWatch.State.UNSTARTED, sw.getState());
        assertEquals(0, sw.getNanoTime());
    }

    @Test
    void testResetAndRestart() {
        final StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();
        sw.reset();
        sw.start();
        assertTrue(sw.isRunning());
    }

    @Test
    void testSuspendAndResume() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(20);
        sw.suspend();
        assertTrue(sw.isSuspended());
        assertEquals(StopWatch.State.SUSPENDED, sw.getState());

        final long suspendedTime = sw.getNanoTime();
        Thread.sleep(50);
        assertEquals(suspendedTime, sw.getNanoTime());

        sw.resume();
        assertTrue(sw.isRunning());
    }

    @Test
    void testSuspendWhenNotRunningThrows() {
        final StopWatch sw = new StopWatch();
        assertThrows(IllegalStateException.class, sw::suspend);
    }

    @Test
    void testResumeWhenNotSuspendedThrows() {
        final StopWatch sw = new StopWatch();
        sw.start();
        assertThrows(IllegalStateException.class, sw::resume);
    }

    @Test
    void testSuspendedTimeExcluded() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(20);
        sw.suspend();
        final long beforePause = sw.getNanoTime();
        Thread.sleep(50);
        sw.resume();
        Thread.sleep(20);
        sw.stop();
        final long total = sw.getNanoTime();
        assertTrue(total < beforePause + TimeUnit.MILLISECONDS.toNanos(80),
                "Suspended time should be excluded: total=" + total);
    }

    @Test
    void testStopWhileSuspended() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(10);
        sw.suspend();
        final long atSuspend = sw.getNanoTime();
        Thread.sleep(30);
        sw.stop();
        assertEquals(atSuspend, sw.getNanoTime());
    }

    @Test
    void testSplit() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(20);
        sw.split();
        final long splitTime = sw.getSplitNanoTime();
        assertTrue(splitTime > 0);
        assertTrue(sw.getSplitTime() >= 0);
        Thread.sleep(20);
        sw.stop();
        assertTrue(sw.getNanoTime() > splitTime);
    }

    @Test
    void testSplitWhenNotRunningThrows() {
        final StopWatch sw = new StopWatch();
        assertThrows(IllegalStateException.class, sw::split);
    }

    @Test
    void testGetSplitTimeWhenNotSplitThrows() {
        final StopWatch sw = new StopWatch();
        sw.start();
        assertThrows(IllegalStateException.class, sw::getSplitNanoTime);
    }

    @Test
    void testUnsplit() {
        final StopWatch sw = new StopWatch();
        sw.start();
        sw.split();
        sw.unsplit();
        assertThrows(IllegalStateException.class, sw::getSplitNanoTime);
    }

    @Test
    void testUnsplitWhenNotSplitThrows() {
        final StopWatch sw = new StopWatch();
        sw.start();
        assertThrows(IllegalStateException.class, sw::unsplit);
    }

    @Test
    void testGetTimeWithUnit() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(20);
        sw.stop();
        assertTrue(sw.getTime(TimeUnit.MICROSECONDS) > 0);
        assertTrue(sw.getTime(TimeUnit.NANOSECONDS) > 0);
    }

    @Test
    void testGetStartTimeWhenUnstartedThrows() {
        final StopWatch sw = new StopWatch();
        assertThrows(IllegalStateException.class, sw::getStartTime);
    }

    @Test
    void testGetStartTime() {
        final StopWatch sw = new StopWatch();
        sw.start();
        assertTrue(sw.getStartTime() > 0);
    }

    @Test
    void testCreateStarted() {
        final StopWatch sw = StopWatch.createStarted();
        assertTrue(sw.isRunning());
        assertTrue(sw.isStarted());
    }

    @Test
    void testToString() {
        final StopWatch sw = new StopWatch();
        assertEquals("00:00:00.000", sw.toString());
    }

    @Test
    void testToStringAfterRunning() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(10);
        sw.stop();
        final String str = sw.toString();
        assertTrue(str.matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}"));
    }

    @Test
    void testRunningTimeIncreases() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(10);
        final long t1 = sw.getNanoTime();
        Thread.sleep(10);
        final long t2 = sw.getNanoTime();
        assertTrue(t2 > t1);
    }

    @Test
    void testMultipleSuspendResumeCycles() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(10);
        sw.suspend();
        Thread.sleep(30);
        sw.resume();
        Thread.sleep(10);
        sw.suspend();
        Thread.sleep(30);
        sw.resume();
        Thread.sleep(10);
        sw.stop();
        final long totalMs = sw.getTime();
        assertTrue(totalMs < 90,
                "Suspended periods should be excluded: totalMs=" + totalMs);
    }

    @Test
    void testResetFromSuspended() {
        final StopWatch sw = new StopWatch();
        sw.start();
        sw.suspend();
        sw.reset();
        assertEquals(StopWatch.State.UNSTARTED, sw.getState());
    }

    @Test
    void testMultipleSplits() throws Exception {
        final StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(10);
        sw.split();
        final long split1 = sw.getSplitNanoTime();
        sw.unsplit();
        Thread.sleep(10);
        sw.split();
        final long split2 = sw.getSplitNanoTime();
        assertTrue(split2 > split1);
    }
}
