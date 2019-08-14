package dbunit;

import br.ce.wcaquino.dao.utils.ConnectionFactory;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ImportExport {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, DatabaseUnitException, IOException {
        DatabaseConnection dbConn = new DatabaseConnection((ConnectionFactory.getConnection()));
        IDataSet dataSet = dbConn.createDataSet();
        FileOutputStream fos = new FileOutputStream(("massas" + File.separator + "saida.xml"));
        FlatXmlDataSet.write(dataSet, fos);
    }
}
