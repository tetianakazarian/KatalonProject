package utils

import java.time.LocalDate
import java.time.Period
import java.util.concurrent.ThreadLocalRandom

//custom manilupations with date and time
class DateCustomUtils {

    static Map<String, String> randomDOB(int minAge = 18, int maxAge = 60) {

        LocalDate today = LocalDate.now()

        // Oldest and youngest allowed dates
        LocalDate maxDate = today.minusYears(minAge)   // youngest (18)
        LocalDate minDate = today.minusYears(maxAge)   // oldest (60)

        long minDay = minDate.toEpochDay()
        long maxDay = maxDate.toEpochDay()

        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)

        LocalDate dob = LocalDate.ofEpochDay(randomDay)

        return [
            day  : dob.getDayOfMonth().toString(),
            month: dob.getMonthValue().toString(),
            year : dob.getYear().toString()
        ]
    }
}