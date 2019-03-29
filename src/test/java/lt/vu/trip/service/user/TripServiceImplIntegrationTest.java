package lt.vu.trip.service.user;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import lt.vu.trip.entity.Trip;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
	@UseDataProvider("getAllParamsData")
	public void testGetAll(int page, int resultsPerPage, LocalDate startDate, LocalDate endDate,
						int expectedElementCount, int expectedPageCount, List<Long> expectedIds) {
		TripSearchCriteria criteria = TripSearchCriteria.builder().startDate(startDate).endDate(endDate).build();

		Page<Trip> trips = tripService.getAll(page, resultsPerPage, criteria);

		Assert.assertEquals(expectedElementCount, trips.getTotalElements());
		Assert.assertEquals(expectedPageCount, trips.getTotalPages());
		Assert.assertThat(extractIdsFromTrips(trips.getContent()), IsIterableContainingInAnyOrder.containsInAnyOrder(
				expectedIds.stream().map(Matchers::equalTo).collect(Collectors.toList())
		));
	}

	@DataProvider
	public static Object[][] getAllParamsData() {
		return new Object[][]{
			// page, results per page, start date, end date, expected element count,
			// expected page count, expected ids of trips
			{1, 100, null, null, 4, 1, new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L))}, // get all
			{1, 2, null, null, 4, 2, new ArrayList<>(Arrays.asList(2L, 3L))}, // first page, but not all elements
			{2, 3, null, null, 4, 2, new ArrayList<>(Arrays.asList(4L))}, // second page
			{1, 100, LocalDate.parse("2019-04-20"), null, 2, 1, new ArrayList<>(Arrays.asList(1L, 4L))}, // get all, start date limited
			{1, 100, LocalDate.parse("2020-04-20"), null, 0, 0, new ArrayList<>(Arrays.asList())}, // get all, no results
			{2, 2, LocalDate.parse("2019-04-19"), null, 3, 2, new ArrayList<>(Arrays.asList(4L))}, // start date limited, second page
			{1, 10, LocalDate.parse("2019-04-19"), LocalDate.parse("2019-04-26"), 1, 1, new ArrayList<>(Arrays.asList(1L))}, // both dates limited
		};
	}

	private List<Long> extractIdsFromTrips(List<Trip> trips) {
		return trips.stream().map(Trip::getId).collect(Collectors.toList());
	}

	@TestConfiguration
	static class TripServiceImplTestContextConfiguration {
		@Bean
		public TripService tripService() {
			return new TripServiceImpl();
		}
	}
}
