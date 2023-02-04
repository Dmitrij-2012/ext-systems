package edu.javacourse.city.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exeption.PersonCheckException;

public class PersonCheckDao {

	private static final String SQL_REQUEST = 
			"select temporal from cr_address_person ap \r\n" + 
			"inner join cr_person p on p.person_id = ap.person_id \r\n" + 
			"inner join cr_address a on a.address_id = ap.address_id \r\n" + 
			"where\r\n" + 
			"upper(p.sur_name) = upper(?) \r\n" + 
			"and upper(p.given_name) = upper(?) \r\n" + 
			"and upper(patronymic) = upper(?) \r\n" + 
			"and p.date_of_birth = ? \r\n" + 
			"and a.street_code = ? \r\n" + 
			"and upper(a.building) = upper(?) \r\n" + 
			"and upper(extension)  = upper(?) \r\n" + 
			"and upper(a.apartment) = upper(?)";

	public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
		PersonResponse response = new PersonResponse();
		
		try (Connection con = getConnection();
				PreparedStatement stmt = con.prepareStatement(SQL_REQUEST)) {
			
			 ResultSet rs = stmt.executeQuery();
			 if (rs.next()) {
	                response.setRegistered(true);
	                response.setTemporal(rs.getBoolean("temporal"));
	            }
			
		} catch (SQLException ex) {
			throw new PersonCheckException(ex);
		}

		return response;

	}

	private Connection getConnection() throws SQLException {
		return null;
	}

}
