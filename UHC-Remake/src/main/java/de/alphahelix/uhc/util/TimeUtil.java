package de.alphahelix.uhc.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String getRemainingTimeTillNextReward(Date current) {

        long millis = TimeUnit.SECONDS.toMillis(getDateDiff(current));

        long days = TimeUnit.MILLISECONDS.toDays(millis);

        long hours = TimeUnit.MILLISECONDS.toHours(millis)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));

        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));

        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        String time = String.format("§6%02d §7%s, §6%02d §7%s, §6%02d §7%s und §6%02d §7%s",

                days,
                "Tage",

                hours,
                "Stunden",

                minutes,
                "Minuten",

                seconds,
                "Sekunden");

        time = time.replace("§", "&");

        if (days <= 0) {
            time = time.replace("00 &7Tage, ", "");
        } else if (days < 10) {
            time = time.replaceFirst("0", "");
            if (days == 1) {
                time = time.replace("Tage", "Tag");
            }
        }
        if (hours <= 0) {
            time = time.replace("00 &7Stunden, ", "");
        } else if (hours < 10) {
            time = time.replaceFirst("0", "");
            if (hours == 1) {
                time = time.replace("Stunden", "Stunde");
            }
        }
        if (minutes <= 0) {
            time = time.replace("00 &7Minuten ", "");
        } else if (minutes < 10) {
            time = time.replaceFirst("0", "");
            if (minutes == 1) {
                time = time.replace("Minuten", "Minute");
            }
        }
        if (seconds <= 0) {
            time = time.replace("und &600 &7Sekunden", "");
        } else if (seconds < 10) {
            time = time.replaceFirst("0", "");
            if (seconds == 1) {
                time = time.replace("Sekunden", "Sekunde");
            }
        }

        return time.replace("&", "§");
    }

    public static long getDateDiff(Date date) {
        long diffInMillies = date.getTime() - new Date().getTime();
        return TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static boolean canClaimReward(Date current) {
        return getDateDiff(current) <= 0;
    }

    public static Date increaseDate(int dura) {
        Date current = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.DAY_OF_MONTH, dura);

        return cal.getTime();
    }
}
