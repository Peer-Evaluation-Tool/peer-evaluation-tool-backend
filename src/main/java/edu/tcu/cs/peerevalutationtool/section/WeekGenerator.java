package edu.tcu.cs.peerevalutationtool.section;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WeekGenerator {
    public static ArrayList<LocalDate> generateWeeks(String startingDate, String endingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uu")
                .withResolverStyle(ResolverStyle.STRICT);

        LocalDate startDate = LocalDate.parse(startingDate, formatter);
        LocalDate endDate = LocalDate.parse(endingDate, formatter);

        ArrayList<LocalDate> weeks = new ArrayList<>();
        // Adjust the start date to the nearest preceding Monday
        LocalDate firstMonday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate nextWeek = firstMonday;
        while (nextWeek.isBefore(endDate)) {
            weeks.add(nextWeek);
            nextWeek = nextWeek.plusWeeks(1);
        }
        return weeks;
    }

    public static void main(String[] args) {
        Section section = new Section();
        section.setId("Section 2023-2024");
        section.setYear("2023-2024");
        section.setFirstDate("08/21/23");
        section.setLastDate("05/01/24");

        section.populateActiveWeeks();
        System.out.println(section.getActiveWeeks());

        HashSet<Integer> indicesToRemove = new HashSet<>();
        indicesToRemove.add(0);
        indicesToRemove.add(2);

        section.dropActiveWeeks(indicesToRemove);

        System.out.println("\n" + section.getActiveWeeks());
    }
}
