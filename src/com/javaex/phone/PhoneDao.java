package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {
	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 생성자
	public PhoneDao() {
	}

	// g/s

	// 일반메소드
	private void getConnet() {
		try {// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	private void close() {
		// 5. 자원정리
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 정보 추가
	public void personInsert(PersonVo vo) {
		getConnet();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO person VALUES (seq_person_id.nextval, ?, ?,?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getHp());
			pstmt.setString(3, vo.getCompany());

			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	// 정보 수정
	public void personUpdate(PersonVo vo) {
		getConnet();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update person";
			query += " set name = ?,";
			query += "     hp = ?,";
			query += "     company = ?";
			query += " where person_id = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getHp());
			pstmt.setString(3, vo.getCompany());
			pstmt.setInt(4, vo.getPersonId());

			pstmt.executeUpdate();

			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	// 삭제
	public void personDelete(int personId) {
		getConnet();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from person";
			query += " where person_id =?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, personId);
			pstmt.executeUpdate();

			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	// 리스트
	public List<PersonVo> getPhoneList() {
		List<PersonVo> phoneList  = new ArrayList<PersonVo>();

		getConnet();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT person_id,";
			query += "        name,";
			query += "        hp,";
			query += "        company";
			query += " FROM person";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
			
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				phoneList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return phoneList;
	}
	
	//검색 기능
	// 정보 수정
		public List<PersonVo> personSh(String keyword) {
			List<PersonVo> keyList = new ArrayList<PersonVo>();
			getConnet();
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " SELECT person_id,";
				query += "        name,";
				query += "        hp,";
				query += "        company";
				query += " FROM person";
				query += " WHERE name like ?";
				query += " OR hp like ?";
				query += " OR company like ?";
				pstmt = conn.prepareStatement(query);
				
				String ky = '%'+keyword+'%';

				pstmt.setString(1,ky );
				pstmt.setString(2,ky);
				pstmt.setString(3,ky );
				
				
				rs = pstmt.executeQuery();

				// 4.결과처리
				while (rs.next()) {
					int personId = rs.getInt("person_id");
					String name = rs.getString("name");
					String hp = rs.getString("hp");
					String company = rs.getString("company");
				
					PersonVo vo = new PersonVo (personId,name,hp,company);
					keyList.add(vo);
				}
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			close();
			return keyList;
		}

}
