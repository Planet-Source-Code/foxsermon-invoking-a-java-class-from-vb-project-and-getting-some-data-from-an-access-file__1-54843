import java.sql.*;

public class CAccess {
   private Connection con = null;
   private Statement stmt = null;
   private ResultSet rs = null;
   private boolean bConnect = false;
   private String sRows[] = new String[100];
   public void connectMe() throws Throwable {
     try {
       Class.forName("com.ms.jdbc.odbc.JdbcOdbcDriver");
       con = DriverManager.getConnection("jdbc:odbc:JavaTest");
       bConnect = true;
     } catch(SQLException ex) {
	throw new SQLException("SQLException: " + ex.getMessage());
     } 
   }
   public void disconnectMe() throws Throwable {
     try {
        if (rs == null)  rs.close();
        if (stmt == null)  stmt.close();
        con.close();
        con = null;        
     } catch(SQLException ex) {
	throw new SQLException("SQLException: " + ex.getMessage());
     } finally { bConnect = false; }
   }
   public boolean isConnected() {
      return bConnect;
   }
   public String[] getRows(String sSQL) throws SQLException {
      int i=0;
      try {
	stmt = con.createStatement();
	rs = stmt.executeQuery(sSQL);
        while (rs.next()) {
          sRows[i++] = rs.getString(1) + "  ->  " + rs.getString(2);
	  //System.out.println(rs.getString(1) + "   " + rs.getString(2));
        }
        rs.close();
        stmt.close();
	return sRows;
      } catch(SQLException ex) {
	throw new SQLException("SQLException: " + ex.getMessage());
      }
   }
   public final static void main(String args[]) throws Throwable {
	CAccess acc = new CAccess();
	acc.connectMe();
        acc.getRows("SELECT * FROM tableEins");
        acc.disconnectMe();
   }
}