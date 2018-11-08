package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dbc.DatabaseConnection;
import junit.framework.TestCase;

import vo.Member;

public class ConsumeDAOTest extends TestCase {
	private DatabaseConnection dbc;
	private Member member_expect;
	private Member member_receive;
	private ConsumeDAO consumeDAO;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		dbc = new DatabaseConnection();
		//dbc.runSQL("/Users/ddd/Documents/SJTU_Courses/SoftwareTest/Software_Test_2018Fall/TheatreTicketSystem/resources/sample.sql");
		consumeDAO = new ConsumeDAO();
		consumeDAO.runSQL("/Users/ddd/Documents/SJTU_Courses/SoftwareTest/Software_Test_2018Fall/TheatreTicketSystem/resources/sample.txt");
		Connection conn = dbc.getConnection();
    	System.out.println("consume conn success");
    	member_expect = new Member();
    	member_expect.setMemberId("A0001");
    	member_expect.setAccount(BigDecimal.valueOf(100000,2));
    	member_expect.setConsumption(BigDecimal.valueOf(500000,2));
	}

	@After
	public void tearDown() throws Exception {
		dbc.close();
		super.tearDown();
		System.out.println("consume conn release");
	}

	@Test
	public void testGetMember() {
		//failNotSame("not same", member_expect, consumeDAO.getMember("A0001"));
		assertEquals(member_expect,consumeDAO.getMember("A0001"));
		assertNull(consumeDAO.getMember(null));
		assertNull(consumeDAO.getMember("A0002"));
	}

	@Test
	public void testRecharge() {
		assertTrue(consumeDAO.recharge("A0001", BigDecimal.valueOf(2000000,2)));
		assertFalse(consumeDAO.recharge("A0001", null));
		assertFalse(consumeDAO.recharge("A0001", BigDecimal.valueOf(-200000,2)));
		assertFalse(consumeDAO.recharge("A0002", BigDecimal.valueOf(400000,2)));
		assertFalse(consumeDAO.recharge("A0002",null));
		assertFalse(consumeDAO.recharge("A0002",BigDecimal.valueOf(-10000.2)));
		assertFalse(consumeDAO.recharge(null,BigDecimal.valueOf(20000,2)));
		assertFalse(consumeDAO.recharge(null,null));
		assertFalse(consumeDAO.recharge(null,BigDecimal.valueOf(-10000.2)));	
	}

	@Test
	public void testPurchaseByMember() {
		assertTrue(consumeDAO.purchaseByMember("念念手记", "A0001", 3, "A0001"));
		assertFalse(consumeDAO.purchaseByMember(null, "A0001", 3, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", null, 4, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", -1, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", 201, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", 5, null));
		assertFalse(consumeDAO.purchaseByMember(null, null, 6, "A0001"));
		assertFalse(consumeDAO.purchaseByMember(null, "A0001", -1, "A0001"));
		assertFalse(consumeDAO.purchaseByMember(null, "A0001", 201, "A0001"));
		assertFalse(consumeDAO.purchaseByMember(null, "A0001", 7, null));
		assertFalse(consumeDAO.purchaseByMember("念念手记", null, -1, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", 201, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", 8, null));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", -1, null));
		assertFalse(consumeDAO.purchaseByMember("念念手记", null, 201, null));
		assertFalse(consumeDAO.purchaseByMember(null, null, -1, "A0001"));
		assertFalse(consumeDAO.purchaseByMember(null, null, 201, "A0001"));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", -1, null));
		assertFalse(consumeDAO.purchaseByMember("念念手记", "A0001", 201, null));
		assertFalse(consumeDAO.purchaseByMember(null, "A0001", -1, null));
		assertFalse(consumeDAO.purchaseByMember(null, "A0001", 201, null));
		assertFalse(consumeDAO.purchaseByMember(null, null, 9, null));
		assertFalse(consumeDAO.purchaseByMember(null, null, -1, null));
		assertFalse(consumeDAO.purchaseByMember(null, null, 201, null));
	}

}
