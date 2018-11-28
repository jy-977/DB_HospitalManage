import java.sql.ResultSet;

public interface TableCTRL {
	
	abstract ResultSet CheckTableExist(String tableName);
	abstract void CreateTable();
	abstract void CloseConn();
}
