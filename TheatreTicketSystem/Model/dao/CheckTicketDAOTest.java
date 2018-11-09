package dao;

import java.util.ArrayList;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import vo.Ticket;

import dbc.DatabaseConnection;
import junit.framework.TestCase;

public class CheckTicketDAOTest extends TestCase {
	private DatabaseConnection dbc;
	private CheckTicketDAO dao;
	private ArrayList<Ticket> title_expect2;
	private Ticket title_expect1;

	@Before
	public void setUp() throws Exception {
		dbc = new DatabaseConnection();
		dao = new CheckTicketDAO();
		dao.runSQL("Z:\\Software_Test_2018Fall\\TheatreTicketSystem\\resources\\sample.sql");

		title_expect1 = new Ticket();
		title_expect2 = new ArrayList<Ticket>();
		title_expect1.setScreening("A0001");
		title_expect1.setTicketId(2L);
		title_expect1.setMemberId("A0002");
		title_expect1.setSeat(2);
		title_expect1.setState(1);
		title_expect1.setTitle("上海交大迎新晚会");
		title_expect1.setPrice(BigDecimal.valueOf(10000, 2));
		title_expect2.add(title_expect1);
	}

	@After
	public void tearDown() throws Exception {
		dbc.close();
		System.out.println("checkticket conn release");
	}

	@Test
	public void testGetTicket() {
		assertEquals(title_expect1, dao.getTicket(2L));
		assertNull(dao.getTicket(11L));
		assertNull(dao.getTicket(-1L));
		assertNull(dao.getTicket(0L));
		assertNull(dao.getTicket(null));
	}

	@Test
	public void testSearchTicketsByMemberId() {
		ArrayList<Ticket> title_expect3 = new ArrayList<Ticket>();
		assertEquals(title_expect2, dao.searchTicketsByMemberId("A0002"));
		assertEquals(title_expect3, dao.searchTicketsByMemberId("B0001"));
		assertEquals(title_expect3, dao.searchTicketsByMemberId("A001"));
		assertEquals(title_expect3, dao.searchTicketsByMemberId(null));
	}

	@Test
	public void testCheckTicket() {
		assertTrue(dao.checkTicket(1L));
		assertFalse(dao.checkTicket(11L));
		assertFalse(dao.checkTicket(-1L));
		assertFalse(dao.checkTicket(0L));
		assertFalse(dao.checkTicket(null));
	}

}
