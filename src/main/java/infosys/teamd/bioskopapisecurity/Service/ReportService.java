package infosys.teamd.bioskopapisecurity.Service;

import net.sf.jasperreports.engine.JasperPrint;

import java.sql.Connection;

public interface ReportService {
//   Connection getConnection();
   JasperPrint generateJasperPrint() throws Exception;

}
