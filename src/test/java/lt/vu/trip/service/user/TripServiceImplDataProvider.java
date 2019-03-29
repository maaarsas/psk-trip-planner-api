package lt.vu.trip.service.user;

import com.tngtech.java.junit.dataprovider.DataProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripServiceImplDataProvider {

	@DataProvider()
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
			List<LocalDate> dateList = new ArrayList<>();
			for (String date : (String[])line[2]) {
				if (date == null) {
					dateList.add(null);
				} else {
					dateList.add(LocalDate.parse(date));
				}
			}
			line[2] = dateList;
			line[5] = new ArrayList<Long>(Arrays.asList((Long[])line[5]));
		}
		return data;
	}

}
