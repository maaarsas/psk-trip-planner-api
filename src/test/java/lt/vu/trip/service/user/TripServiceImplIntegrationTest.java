package lt.vu.trip.service.user;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import lt.vu.trip.entity.Trip;
import lt.vu.trip.entity.User;
import lt.vu.trip.service.trip.TripSearchCriteria;
import lt.vu.trip.service.trip.TripService;
import lt.vu.trip.service.trip.TripServiceImpl;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class TripServiceImplIntegrationTest {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired
	private TripService tripService;

	@MockBean
	private UserService userService;

	@Test
	@UseDataProvider(value = "getAllParamsData", location = TripServiceImplDataProvider.class)
	public void testGetAll(int page, int resultsPerPage, List<LocalDate> filterDates,
			int expectedElementCount, int expectedPageCount, List<Long> expectedIds) {
		Page<Trip> trips = tripService.getAll(page, resultsPerPage, buildSearchCriteria(filterDates));
		assertTripPage(trips, expectedElementCount, expectedPageCount, expectedIds);
	}

	@Test
	@UseDataProvider(value = "getOrganizedByCurrentUserData", location = TripServiceImplDataProvider.class)
	public void testGetOrganizedByCurrentUser(int page, int resultsPerPage, List<LocalDate> filterDates, int currentUserId,
			int expectedElementCount, int expectedPageCount, List<Long> expectedIds) {
		Mockito.when(userService.getCurrentUser()).thenReturn(User.builder().id((long) currentUserId).build());
		Page<Trip> trips = tripService.getOrganizedByCurrentUser(page, resultsPerPage, buildSearchCriteria(filterDates));
		assertTripPage(trips, expectedElementCount, expectedPageCount, expectedIds);
	}

	@Test
	@UseDataProvider(value = "getCurrentUserParticipatingInData", location = TripServiceImplDataProvider.class)
	public void testGetCurrentUserParticipatingIn(int page, int resultsPerPage, List<LocalDate> filterDates, int currentUserId,
											  int expectedElementCount, int expectedPageCount, List<Long> expectedIds) {
		Mockito.when(userService.getCurrentUser()).thenReturn(User.builder().id((long) currentUserId).build());
		Page<Trip> trips = tripService.getCurrentUserParticipatingIn(page, resultsPerPage, buildSearchCriteria(filterDates));
		assertTripPage(trips, expectedElementCount, expectedPageCount, expectedIds);
	}

	@Test
	@UseDataProvider(value = "getCurrentUserInvitedInData", location = TripServiceImplDataProvider.class)
	public void testGetCurrentUserInvitedIn(int page, int resultsPerPage, List<LocalDate> filterDates, int currentUserId,
												  int expectedElementCount, int expectedPageCount, List<Long> expectedIds) {
		Mockito.when(userService.getCurrentUser()).thenReturn(User.builder().id((long) currentUserId).build());
		Page<Trip> trips = tripService.getCurrentUserInvitedIn(page, resultsPerPage, buildSearchCriteria(filterDates));
		assertTripPage(trips, expectedElementCount, expectedPageCount, expectedIds);
	}

	private TripSearchCriteria buildSearchCriteria(List<LocalDate> filterDates) {
		return TripSearchCriteria.builder()
				.startDateFrom(filterDates.get(0))
				.startDateTo(filterDates.get(1))
				.endDateFrom(filterDates.get(2))
				.endDateTo(filterDates.get(3))
				.build();
	}

	private List<Long> extractIdsFromTrips(List<Trip> trips) {
		return trips.stream().map(Trip::getId).collect(Collectors.toList());
	}

	private void assertTripPage(Page<Trip> tripPage, int expectedElementCount, int expectedPageCount, List<Long> expectedIds) {
		Assert.assertEquals(expectedElementCount, tripPage.getTotalElements());
		Assert.assertEquals(expectedPageCount, tripPage.getTotalPages());
		Assert.assertThat(extractIdsFromTrips(tripPage.getContent()), IsIterableContainingInAnyOrder.containsInAnyOrder(
				expectedIds.stream().map(Matchers::equalTo).collect(Collectors.toList())
		));
	}

	@TestConfiguration
	static class TripServiceImplTestContextConfiguration {
		@Bean
		public TripService tripService() {
			return new TripServiceImpl();
		}
	}
}
