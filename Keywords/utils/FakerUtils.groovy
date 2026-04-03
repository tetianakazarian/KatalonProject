package utils

import com.kms.katalon.core.annotation.Keyword
import config.Env
import utils.DateCustomUtils

// to generate fake test data
class FakerUtils {

    private static final List<String> FIRST_NAMES = ['Anna', 'Kate', 'Olivia', 'Emma', 'Lina', 'Mia', 'Sara', 'Nina']
    private static final List<String> LAST_NAMES = ['Brown', 'Smith', 'Johnson', 'Taylor', 'Miller', 'Davis', 'Wilson', 'Clark']
    private static final List<String> CITIES = ['Nicosia', 'Larnaca', 'Limassol', 'Paphos']
    private static final List<String> STATES = ['Nicosia', 'Larnaca', 'Limassol', 'Paphos']

    @Keyword
    Map generateUser() {
        long stamp = System.currentTimeMillis()
        String firstName = randomItem(FIRST_NAMES)
        String lastName = randomItem(LAST_NAMES)
        String name = "${firstName} ${lastName}"
        String city = randomItem(CITIES)
        String state = randomItem(STATES)
        String email = "katalon_${stamp}@mailinator.com"
		
		def dob = DateCustomUtils.randomDOB()
		
		return [
			name	  : name,
            firstName : firstName,
            lastName  : lastName,
            email     : email,
            password  : Env.defaultPassword(),
            day       : dob.day,
            month     : dob.month,
            year      : dob.year,
            address   : "${100 + (stamp % 900)} Demo Street",
            country   : 'Canada',
            state     : state,
            city      : city,
            zipcode   : "${10000 + (stamp % 89999)}",
            mobile    : "99${stamp.toString()[-8..-1]}"
        ]
    }

    private static String randomItem(List<String> values) {
        int index = new Random().nextInt(values.size())
        return values[index]
    }
}
