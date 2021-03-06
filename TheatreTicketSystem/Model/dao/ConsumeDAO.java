package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Member;
import vo.Show;

public class ConsumeDAO extends baseDAO {
	
	public Member getMember(String memberId) {
		String sql = "select * "				/*FAULT## FAILURE INDUCING CODE */
				+ "from theatre_ticket.member "
				+ "where member_id = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Member member = new Member();
				member.setAll(rs);
				return member;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean recharge(String memberId, BigDecimal amount) {
		if(memberId == null) {
			return false;
		}
		if(amount == null) {
			return false;
		}
		if(amount.compareTo(BigDecimal.valueOf(0)) < 1) {
			return false;
		}
		Member member = getMember(memberId);
		if(member == null) {
			return false;
		}
		String sql = "update theatre_ticket.member "
				+ "set account = account + ? "
				+ "where member_id = ? ";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setBigDecimal(1, amount);
			pstmt.setString(2, memberId);
			int rows_count = pstmt.executeUpdate();
			if(rows_count > 0) { /*FAULT## FAILURE INDUCING CODE */
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean purchaseByMember(String title, String screening, Integer seat, String memberId) {
		SearchShowDAO searchShowDao = new SearchShowDAO();
		Show show = searchShowDao.getShow(title, screening);
		if(show == null) {//check show exist
			return false;
		}
		Member member = getMember(memberId);
		if(member == null) {//check member exist
			return false;
		}
		if(seat < 1 || seat > show.getSeatNum().intValue()) { /*FAULT## FAILURE INDUCING CODE */ //check seat in [1,2,3,...,seat_num] 
			return false;
		}
		BigDecimal price = show.getPrice();
		String sqlQueryTicket = "select * from theatre_ticket.ticket where title = ? and screening = ? and seat = ? and (state = 0 or state = 1)";
		String sqlInsertTicket = "insert into theatre_ticket.ticket(title,screening,seat,price,member_id,state) values(?,?,?,?,?,1) ";
		String sqlUpdateMember = "update theatre_ticket.member set account = account - ?, consumption = consumption + ? where member_id = ? ";
		String sqlQueryMember = "select * from theatre_ticket.member where member_id = ? ";
		boolean isSuccess = false;
		try {
			conn.setAutoCommit(false);

			PreparedStatement pstmtQueryTicket = conn.prepareStatement(sqlQueryTicket);
			pstmtQueryTicket.setString(1, title);
			pstmtQueryTicket.setString(2, screening);
			pstmtQueryTicket.setInt(3, seat);
			PreparedStatement pstmtInsertTicket = conn.prepareStatement(sqlInsertTicket);
			pstmtInsertTicket.setString(1, title);
			pstmtInsertTicket.setString(2, screening);
			pstmtInsertTicket.setInt(3, seat);
			pstmtInsertTicket.setBigDecimal(4, price);
			pstmtInsertTicket.setString(5, memberId);
			PreparedStatement pstmtUpdateMember = conn.prepareStatement(sqlUpdateMember);
			pstmtUpdateMember.setBigDecimal(1, price);
			pstmtUpdateMember.setBigDecimal(2, price);
			pstmtUpdateMember.setString(3, memberId);
			PreparedStatement pstmtQueryMember = conn.prepareStatement(sqlQueryMember);
			pstmtQueryMember.setString(1, memberId);
			
			//pstmtQueryTicket
			ResultSet rsQueryTicket = pstmtQueryTicket.executeQuery();
			if(!rsQueryTicket.next()) {
				isSuccess = true;
			}
			
			//pstmtInsertTicket
			if(isSuccess) {
				isSuccess = false;
				int countInsertTicket = pstmtInsertTicket.executeUpdate();
				if(countInsertTicket == 1) {
					isSuccess = true;
				}
			}
			
			//pstmtUpdateMember
			if(isSuccess) {
				isSuccess = false;
				int countUpdateMember = pstmtUpdateMember.executeUpdate();
				if(countUpdateMember == 1) {
					isSuccess = true;
				}
			}
			
			//pstmtQueryMember
			if(isSuccess) {
				isSuccess = false;
				ResultSet rsQueryMember = pstmtQueryMember.executeQuery();
				if(rsQueryMember.next()) {
					Member memberQuery = new Member();
					memberQuery.setAll(rsQueryMember);
					if(memberQuery.getAccount().compareTo(BigDecimal.valueOf(0)) >= 0) {
						isSuccess = true;
					}
				}
			}
			
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				if(isSuccess) {
					conn.commit();
				}
				else {
					conn.rollback();
				}
				conn.setAutoCommit(true);
			} catch (Exception e) {
				isSuccess = false;
				e.printStackTrace();
			}
		}
		return isSuccess;
	}
	
}
