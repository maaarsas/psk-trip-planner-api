package lt.vu.trip.service.user;

import com.tngtech.java.junit.dataprovider.DataProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripServiceImplDataProvider {

	@DataProvider
	public static Object[][] getAllParamsData() {
		Object[][] data = new Object[][] {
			// page, results per page, start date from, start date to, end date from, end date to,
			// expected element count, expected page count, expected ids of trips
			{1, 100, new String[]{null, null, null, null}, 4, 1, new Long[]{1L, 2L, 3L, 4L}}, // get all
			{1, 2, new String[]{null, null, null, null}, 4, 2, new Long[]{2L, 3L}}, // first page, but not all elements
			{2, 3, new String[]{null, null, null, null}, 4, 2, new Long[]{4L}}, // second page
			{1, 100, new String[]{"2019-04-20", null, null, null}, 2, 1, new Long[]{1L, 4L}}, // get all, start date limited
			{1, 100, new String[]{"2020-04-20", null, null, null}, 0, 0, new Long[]{}}, // get all, no results
			{2, 2, new String[]{"2019-04-19", null, null, null}, 3, 2, new Long[]{4L}}, // start date limited, second page
			{1, 10, new String[]{"2019-04-19", "2019-04-22", null, null}, 2, 1, new Long[]{1L, 2L}}, // start date limited
			{1, 10, new String[]{null, null, "2019-04-26", "2019-10-25"}, 3, 1, new Long[]{1L, 2L, 3L}}, // end date limited
			{1, 10, new String[]{"2019-04-19", "2019-12-01", "2019-05-27", "2019-12-02"}, 2, 1, new Long[]{2L, 4L}}, // both dates limited
		};
		for (Object[] line : data) {
			line[2] = buildLocalDateList((String[]) line[2]);
			line[5] = buildLongIdsList((Long[]) line[5]);
		}
		return data;
	}

	@DataProvider
	public static Object[][] getOrganizedByCurrentUserData() {
		Object[][] data = new Object[][] {
				// page, results per page, start date from, start date to, end date from, end date to, current user id
				// expected element count, expected page count, expected ids of trips
				{1, 100, new String[]{null, null, null, null}, 1, 3, 1, new Long[]{1L, 2L, 4L}}, // get all
				{1, 100, new String[]{null, null, null, null}, 3, 0, 0, new Long[]{}}, // get all
				{1, 100, new String[]{"2020-04-20", null, null, null}, 1, 0, 0, new Long[]{}}, // get all, no results
				{1, 2, new String[]{"2019-02-19", null, null, null}, 2, 1, 1, new Long[]{3L}}, // start date limited,
				{1, 10, new String[]{"2019-04-19", "2019-12-01", "2019-05-27", "2019-12-02"}, 1, 2, 1, new Long[]{2L, 4L}}, // both dates limited
		};
		for (Object[] line : data) {
			line[2] = buildLocalDateList((String[]) line[2]);
			line[6] = buildLongIdsList((Long[]) line[6]);
		}
		return data;
	}

	@DataProvider
	public static Object[][] getCurrentUserParticipatingInData() {
		Object[][] data = new Object[][] {
				// page, results per page, start date from, start date to, end date from, end date to, current user id
				// expected element count, expected page count, expected ids of trips
				{1, 100, new String[]{null, "2020-04-22", null, null}, 1, 1, 1, new Long[]{3L}}, // get all
				{1, 100, new String[]{null, null, null, null}, 4, 0, 0, new Long[]{}}, // get all
				{1, 100, new String[]{"2020-04-20", null, null, null}, 2, 0, 0, new Long[]{}}, // get all, no results
				{2, 1, new String[]{"2019-02-19", null, null, null}, 2, 2, 2, new Long[]{1L}}, // start date limited
		};
		for (Object[] line : data) {
			line[2] = buildLocalDateList((String[]) line[2]);
			line[6] = buildLongIdsList((Long[]) line[6]);
		}
		return data;
	}

	@DataProvider
	public static Object[][] getCurrentUserInvitedInData() {
		Object[][] data = new Object[][] {
				// page, results per page, start date from, start date to, end date from, end date to, current user id
				// expected element count, expected page count, expected ids of trips
				{1, 100, new String[]{null, "2020-04-22", null, null}, 1, 0, 0, new Long[]{}}, // get all
				{1, 100, new String[]{null, null, null, null}, 4, 0, 0, new Long[]{}}, // get all
				{1, 100, new String[]{"2019-04-20", null, null, null}, 2, 1, 1, new Long[]{4L}}, // get all, no results
				{2, 1, new String[]{"2019-02-19", null, null, null}, 3, 1, 1, new Long[]{}}, // start date limited
		};
		for (Object[] line : data) {
			line[2] = buildLocalDateList((String[]) line[2]);
			line[6] = buildLongIdsList((Long[]) line[6]);
		}
		return data;
	}

	private static List<LocalDate> buildLocalDateList(String[] dates) {
		List<LocalDate> dateList = new ArrayList<>();
		for (String date : dates) {
			if (date == null) {
				dateList.add(null);
			} else {
				dateList.add(LocalDate.parse(date));
			}
		}
		return dateList;
	}

	private static List<Long> buildLongIdsList(Long[] ids) {
		return new ArrayList<Long>(Arrays.asList(ids));
	}

}
