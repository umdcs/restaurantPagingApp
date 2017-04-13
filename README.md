# restaurantPagingApp

Testing
"ReservationTest" - Tests the Reservation class's methods.
	"toString_isCorrect" - Test the toString method by comparing the result of toString to an expected string.
	"getName_works" - Tests the getName method by comparing the result of getName to an expected string.
	"setName_works" - Tests the setName method by calling setName and comparing the result of getName to an expected string.
	"getPartySize_works" - Tests the getPartySize method by comparing the result of getPartySize to an expected integer.
	"setPartySize_works" - Tests the setPartySize method by calling setPartySize and comparing the result of getPartySize to an expected integer.
	"getPhoneNumber_works" - Tests the getPhoneNumber method by comparing the result of getPhoneNumber to an expected string.
	"setPhoneNumber_works" - Tests the setPhoneNumber method by calling setPhoneNumber and comparing the result of getPhoneNumber to an expected string.
	"getTime_works" - Tests the getTime method by comparing the result of getTime to an expected string.


"ListTest" - This class aims to ensure that the back end of the list views are maintained properly.

	create_reservation - Creates a reservation and makes sure that the same reservation is returned

	same_edit - Checks to see that an edit with the same values does not change the reservation's data

	different_edit - Checks to see that editing a reservation does change the values

	length_test - Makes sure that the lists returned when getting all reservations are of the right length

	delete_move - Makes sure that the lists remove elements for deleting and moving cases

	testReservationsEqual - Private function that is used to make sure that all data is equal. If you just use assertEquals(res1,res2) it falsely raises an error for some reason.

