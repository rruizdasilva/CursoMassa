package dbunit;

import br.ce.wcaquino.dao.utils.ConnectionFactory;
import org.apache.log4j.BasicConfigurator;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.*;
import java.sql.SQLException;

public class ImportExport {
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        exportarBanco("saldo.xml");
//        importarBanco("saida.xml");
        return;
    }


    public static void importarBanco(String massa) throws DatabaseUnitException, SQLException, ClassNotFoundException, FileNotFoundException {
        DatabaseConnection dbConn = new DatabaseConnection((ConnectionFactory.getConnection()));
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        FlatXmlDataSet dataSet = builder.build(new FileInputStream("massas" + File.separator + massa));
        DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
    }

    public static void exportarBanco(String massa) throws Exception {
        DatabaseConnection dbConn = new DatabaseConnection((ConnectionFactory.getConnection()));
        IDataSet dataSet = dbConn.createDataSet();
        DatabaseSequenceFilter databaseSequenceFilter = new DatabaseSequenceFilter(dbConn);
        FilteredDataSet filteredDataSet = new FilteredDataSet(databaseSequenceFilter, dataSet);
        FileOutputStream fos = new FileOutputStream(("massas" + File.separator + massa));
        //FlatXmlDataSet.write(dataSet, fos);
        FlatXmlDataSet.write(filteredDataSet, fos);
    }
}
