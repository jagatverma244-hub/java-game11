package com.javagame.Config;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Component
    public class GameUtil {
        public int getCurrentGameNo() {
            LocalDate today = LocalDate.now();
            return today.getYear() * 1000 + today.getDayOfYear();

        }

        public int getDisawarCurrentGameno() {
            LocalDateTime now = LocalDateTime.now();
            LocalDate referenceDate;

            // Agar abhi 5:50 AM se pehle ka time hai, to aaj ka game number "kal ke date" ka maana jaaye
            if (now.toLocalTime().isBefore(LocalTime.of(5, 50))) {
                referenceDate = now.toLocalDate().minusDays(1);
            } else {
                referenceDate = now.toLocalDate();
            }

            // Game number format: year * 1000 + day of year
            return referenceDate.getYear() * 1000 + referenceDate.getDayOfYear();
        }
    public int getgaliCurrentGameno() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate referenceDate;

        // Agar abhi 5:50 AM se pehle ka time hai, to aaj ka game number "kal ke date" ka maana jaaye
        if (now.toLocalTime().isBefore(LocalTime.of(1, 30))) {
            referenceDate = now.toLocalDate().minusDays(1);
        } else {
            referenceDate = now.toLocalDate();
        }

        // Game number format: year * 1000 + day of year
        return referenceDate.getYear() * 1000 + referenceDate.getDayOfYear();
    }

    }




