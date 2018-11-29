package dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import dao.CheckTicketDAO;
import dao.SearchShowDAO;
import dbc.DatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import vo.Show;

public class SearchShowDAOTest extends TestCase {

	private SearchShowDAO dao;
	private DatabaseConnection dbc;

	@Before
	public void setUp() throws Exception {
		dbc = new DatabaseConnection();
		//dao.runSQL("Z:\\Software_Test_2018Fall\\TheatreTicketSystem\\resources\\sample.sql");
		dbc.runSQL("/Users/ddd/Documents/SJTU_Courses/SoftwareTest/Software_Test_2018Fall/TheatreTicketSystem/resources/sample.sql");
		dao = new SearchShowDAO();
	}
	@After
	public void tearDown() throws Exception {
		dao.close();
	}

	@Test
	public void testSearchTitlesByDayTimestamp() {
		ArrayList<String> titles_expect = new ArrayList<String>();
		ArrayList<String> titles_expect0 = new ArrayList<String>();
		
		titles_expect.add("上海交通迎新晚会");
		
//		????searchTitlesByDay????
		assertTrue(dao.searchTitlesByDay(Timestamp.valueOf("2018-10-10 18:00:00")));
		assertEquals(titles_expect0, dao.searchTitlesByDay(null));

//		?????Timestamp???????????????????????
//		assertNull(dao.searchTitlesByDay(Timestamp.valueOf("10-10-2018 00:00:00")));
//		assertNull(dao.searchTitlesByDay(Timestamp.valueOf("2018-10-10")));	}
	}

	private void assertTrue(ArrayList<String> searchTitlesByDay) {
		// TODO Auto-generated method stub
		
	}
	@Test 
	public void testSearchShows() {
		ArrayList<Show> titles_expect1 = new ArrayList<Show>(); 
		ArrayList<Show> titles_expect2 = new ArrayList<Show>(); 
		Show show = new Show();
		show.setPrice(BigDecimal.valueOf(10000,2));
		show.setScreening("A0001");
		show.setSeatNum(BigDecimal.valueOf(10000,0));
		show.setState(1);
		show.setTitle("上海交大迎新晚会");
		show.setStartTime(Timestamp.valueOf("2018-10-10 18:00:00"));
		titles_expect1.add(show);
		
		System.out.println(show.getScreening());
		System.out.println(show.getTitle());
		System.out.println(show.getPrice());
		System.out.println(show.getSeatNum());
		System.out.println(show.getStartTime());
		System.out.println(show.getState());
		
		assertEquals(titles_expect1, dao.SearchShows("上海交大迎新晚会", Timestamp.valueOf("2018-10-10 18:00:00"), 1));
		assertEquals(titles_expect2, dao.SearchShows("上海交大迎新晚会", Timestamp.valueOf("2018-10-10 18:00:00"), 5));
		assertEquals(titles_expect1, dao.SearchShows("上海交大迎新晚会", Timestamp.valueOf("2018-10-10 18:00:00"), null));
		assertEquals(titles_expect2, dao.SearchShows("上海交大迎新晚会", null, 0));
		assertEquals(titles_expect2, dao.SearchShows("上海交大迎新晚会", null, 5));
		assertEquals(titles_expect1, dao.SearchShows("上海交大迎新晚会", null, null));
		assertEquals(titles_expect2, dao.SearchShows(null, Timestamp.valueOf("2018-10-10 18:00:00"), 1));
		assertEquals(titles_expect2, dao.SearchShows(null, Timestamp.valueOf("2018-10-10 18:00:00"), 5));
		assertEquals(titles_expect2, dao.SearchShows(null, Timestamp.valueOf("2018-10-10 18:00:00"), null));
		assertEquals(titles_expect2, dao.SearchShows(null, null, 1));
		assertEquals(titles_expect2, dao.SearchShows(null, null, 5));
		assertEquals(titles_expect2, dao.SearchShows(null, null, null));
		
//		assertNull(dao.SearchShows("?????????????", Timestamp.valueOf("10-10-2018 18:00:00"), 1));
//		assertNull(dao.SearchShows("?????????????", Timestamp.valueOf("10-10-2018 18:00:00"), 5));
//		assertNull(dao.SearchShows("?????????????", Timestamp.valueOf("10-10-2018 18:00:00"), 1));
//		assertNull(dao.SearchShows("?????????????", Timestamp.valueOf("2018-10-10"), 1));
//		assertNull(dao.SearchShows("?????????????", Timestamp.valueOf("2018-10-10"), 5));
//		assertNull(dao.SearchShows("?????????????", Timestamp.valueOf("2018-10-10"), null));
//		assertNull(dao.SearchShows(null, Timestamp.valueOf("10-10-2018 18:00:00"), 1));
//		assertNull(dao.SearchShows(null, Timestamp.valueOf("10-10-2018 18:00:00"), 5));
//		assertNull(dao.SearchShows(null, Timestamp.valueOf("10-10-2018 18:00:00"), null));
//		assertNull(dao.SearchShows(null, Timestamp.valueOf("2018-10-10"), 1));
//		assertNull(dao.SearchShows(null, Timestamp.valueOf("2018-10-10"), 5));
//		assertNull(dao.SearchShows(null, Timestamp.valueOf("2018-10-10"), null));
	}

	@Test
	public void testGetShow() {
		Show show1 = new Show();
		show1.setPrice(BigDecimal.valueOf(2500,2));
		show1.setScreening("A0001");
		show1.setSeatNum(BigDecimal.valueOf(200,0));
		show1.setState(1);
		show1.setTitle("念念手记");
		show1.setStartTime(Timestamp.valueOf("2018-10-10 17:20:00"));
		
		assertEquals(show1, dao.getShow("念念手记", "A0001"));
		assertNull(dao.getShow("念念手记", "B0001"));
		assertNull(dao.getShow("念念手记", "A001"));
		assertNull(dao.getShow("念念手记", null));
		assertNull(dao.getShow(null, "A0001"));
		assertNull(dao.getShow(null, "B0001"));
		assertNull(dao.getShow(null, "A001"));
		assertNull(dao.getShow(null, "B001"));
		assertNull(dao.getShow(null, null));

	}

}
