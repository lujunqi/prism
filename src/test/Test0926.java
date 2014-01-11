package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.prism.dbutil.VMPreparedStatement;

public class Test0926 {
	public static void main(String[] args) {
		Test0926 t = new Test0926();
		t.card();
		t.groupCard();
	}

	public void groupCard() {
		try {
			Date s = new Date();
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:e:/sqlite0926.db");
			String sql0 = "SELECT  C.GROUP_ID, C.GC_ID, C.CARD_ID, C.GC_ORDER, C.DUTY, C.ENT_ID"
					+ "  FROM TB_ENT_GROUP_CARD C, TABLE(FUNC_ENT_CARD(3, 187)) T\n"
					+ " WHERE C.ENT_ID = 187\n"
					+ "   AND T.CARD_ID = C.CARD_ID";
			String sql = "INSERT INTO TB_ENT_GROUP_CARD (GROUP_ID,GC_ID,CARD_ID,GC_ORDER,DUTY,ENT_ID) VALUES (?,?,?,?,?,?)";
			PreparedStatement prep = conn.prepareStatement(sql);
			List<Map<String, Object>> l = server(sql0);
			for (Map<String, Object> m : l) {
				prep.setObject(1, m.get("GROUP_ID"), Types.NUMERIC);
				prep.setObject(2, m.get("GC_ID"), Types.NUMERIC);
				prep.setObject(3, m.get("CARD_ID"), Types.NUMERIC);
				prep.setObject(4, m.get("GC_ORDER"), Types.NUMERIC);
				prep.setObject(5, m.get("DUTY"), Types.VARCHAR);
				prep.setObject(6, m.get("ENT_ID"), Types.NUMERIC);
				prep.addBatch();
			}
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			Date e = new Date();
			System.out.println(e.getTime() - s.getTime());
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void card() {
		try {
			Date s = new Date();
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:e:/sqlite0926.db");

			String sql0 = "SELECT   CARD_ID,"
					+ "       ENT_ID,    CARD_NAME,     MOBILE,"
					+ "       SHORT_CODE,  DEPART_NAME," + " CARD_EMAIL,"
					+ "       CARD_LEVEL,   CARD_NAME_PY_J,"
					+ "       CARD_NAME_PY_Q,   IS_SHOW,"
					+ "       VIEW_LEVEL FROM TB_ENT_CARD C"
					+ " WHERE C.ENT_ID = 187";
			String sql = "INSERT INTO TB_ENT_CARD(CARD_ID, ENT_ID,CARD_NAME,MOBILE,"
					+ "       SHORT_CODE,  DEPART_NAME," + " CARD_EMAIL,"
					+ "       CARD_LEVEL,   CARD_NAME_PY_J,"
					+ "       CARD_NAME_PY_Q,   IS_SHOW,"
					+ "       VIEW_LEVEL )VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement prep = conn.prepareStatement(sql);
			List<Map<String, Object>> l = server(sql0);
			for (Map<String, Object> m : l) {
				prep.setObject(1, m.get("CARD_ID"), Types.NUMERIC);
				prep.setObject(2, m.get("ENT_ID"), Types.NUMERIC);
				prep.setObject(3, m.get("CARD_NAME"), Types.VARCHAR);
				prep.setObject(4, m.get("MOBILE"), Types.VARCHAR);
				prep.setObject(5, m.get("SHORT_CODE"), Types.VARCHAR);
				prep.setObject(6, m.get("DEPART_NAME"), Types.VARCHAR);
				prep.setObject(7, m.get("CARD_EMAIL"), Types.VARCHAR);
				prep.setObject(8, m.get("CARD_LEVEL"), Types.NUMERIC);
				prep.setObject(9, m.get("CARD_NAME_PY_J"), Types.VARCHAR);
				prep.setObject(10, m.get("CARD_NAME_PY_Q"), Types.VARCHAR);
				prep.setObject(11, m.get("IS_SHOW"), Types.NUMERIC);
				prep.setObject(12, m.get("VIEW_LEVEL"), Types.NUMERIC);
				prep.addBatch();
			}
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			Date e = new Date();
			System.out.println(e.getTime() - s.getTime());
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> server(String sql) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.1.14:1521:qqdb", "txl123",
					"txl123");
			VMPreparedStatement cmd = new VMPreparedStatement(conn);

			l = cmd.getListColValue(sql);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
}
