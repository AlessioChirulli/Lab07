package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
public List<PowerOutage> getPowerOutage(int nerc,int oreMax){
	
	oreMax*=3600;
	
	String sql="SELECT id,customers_affected,date_event_began,date_event_finished "
			+" FROM poweroutages "
			+" WHERE nerc_id = ? "
			+"AND to_seconds(date_event_finished)-to_seconds(date_event_began) <= ? ";
	
	List<PowerOutage>powerOutage=new ArrayList<PowerOutage>();
	
	try {
		Connection conn = ConnectDB.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		
		st.setString(1, Integer.toString(nerc));
		st.setString(2, Integer.toString(oreMax));
		ResultSet res = st.executeQuery();
		
		while (res.next()) {
			LocalDateTime di=res.getTimestamp("date_event_began").toLocalDateTime();
			LocalDateTime df=res.getTimestamp("date_event_finished").toLocalDateTime();
		PowerOutage pTemp=new PowerOutage(res.getInt("id"),di,df,res.getInt("customers_affected"));
		powerOutage.add(pTemp);
		}

		conn.close();

	} catch (SQLException e) {
		throw new RuntimeException(e);
	}

	return powerOutage;
}
}
