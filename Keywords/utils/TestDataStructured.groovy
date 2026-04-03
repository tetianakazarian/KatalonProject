package utils

// class which incapsulates the test data in structure
class TestDataStructured {

	String name
	String firstName
	String lastName
    String email
    String password
    String day
    String month
    String year
    String address
    String country
    String state
    String city
    String zipcode
    String mobile

    @Override
    String toString() {
        return """
        TestDataStructured(
            name='${firstName} ${lastName}',
            firstName='${firstName}',
            lastName='${lastName}',
            email='${email}',
            password='${password}',
            day='${day}',
            month='${month}',
            year='${year}',
            address='${address}',
            country='${country}',
            state='${state}',
            city='${city}',
            zipcode='${zipcode}',
            mobile='${mobile}'
        )
        """.stripIndent().trim()
    }
}