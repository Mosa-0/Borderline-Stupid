package com.gmail.pokepuff37.bls.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import static com.gmail.pokepuff37.bls.events.EventPickerClass.EventPickerMethod;

public class Timer {
    public static boolean isRunning = true;
    public static long timerTicks = -1L;
    public static int timerSeconds = 0;
    public static int timerMinutes = 0;

    public static boolean newMax = false;
    public static long timerTicksMax = 30000L;
    public static int timerSecondsMax = 25;
    public static int timerMinutesMax = 0;

    public static void init() {
        timerCount();
    }

    public static void timerCount() {
        ServerTickEvents.START_SERVER_TICK.register(s -> {
            if (isRunning && timerTicks != -1) {
                if (timerMinutes < 10 && timerSeconds < 10) {
                    System.out.println("0" + timerMinutes + ":0" + timerSeconds);
                } else if (timerMinutes < 10) {
                    System.out.println("0" + timerMinutes + ":" + timerSeconds);
                } else if (timerSeconds < 10) {
                    System.out.println(timerMinutes + ":0" + timerSeconds);
                } else {
                    System.out.println(timerMinutes + ":" + timerSeconds);
                }
                System.out.println(timerTicks);
                if (timerTicks == 0) {
                    EventPickerMethod();
                }
                timerTicks--;
                if (timerTicks != -1) {
                    if (timerTicks % 20 == 0) {
                        timerSeconds--;
                        if (timerSeconds < 0) {
                            timerMinutes--;
                            timerSeconds = 59;
                        }
                    }
                }
            }
        });
    }
}