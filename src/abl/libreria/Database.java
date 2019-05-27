package abl.libreria;
import java.sql.*;

public class Database {

	private Connection conexion = null;
	// 0 mysql , 
	private final String[] CONTROLADORES = {
			                                 "com.mysql.cj.jdbc.Driver",
			                                 "oracle.jdbc.driver.OracleDriver",
			                                 "com.microsoft.sqlserver.jdbc.SQLServerDriver"
			                               };
	
	public static void main(String[] args) {
		
	}
	
	
	public Database(int controlador, String urlDatabase ,String nombreBBDD ,String user, String password ) 
			        throws ClassNotFoundException, SQLException,Exception {
		
		if(controlador < 0 || controlador >= CONTROLADORES.length ) {
			// escribimos mensaje que saldre en el debugger;
		    throw new Exception("Error en el numero del controlador");
		    }
			Class.forName(CONTROLADORES[controlador]);
			// linea de conexion a la base de datos Mysql.
			conexion = DriverManager.getConnection(urlDatabase + nombreBBDD +"?serverTimezone=UTC",user,password);
	}
	
	public void ejecutarSQL(String sql) throws SQLException {

		Statement sentenciaSQL = conexion.createStatement();
		sentenciaSQL.executeUpdate(sql);	
		
	}
	
	public void cerrarDatabase() throws SQLException {
		conexion.close();
	}
	
	public ResultSet consultarSQL(String sql) throws SQLException{
		Statement sentenciaSQL = conexion.createStatement();
		return sentenciaSQL.executeQuery(sql);
		
	}
	 
	
	public int getMaxId(String sql)  throws SQLException{
		// select id_marca , marca from marcas order by id_marca desc;
		Statement sentenciaSQL = conexion.createStatement();
		ResultSet consulta = sentenciaSQL.executeQuery(sql);
		int idmax=0;
		if(consulta.next()) idmax = consulta.getInt(1); // el primer campo de la SELECT está en pos 1.
		return idmax + 1; // 
	}
	
	
	

	
	

}
