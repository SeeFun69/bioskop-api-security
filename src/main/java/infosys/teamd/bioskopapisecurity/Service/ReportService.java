package infosys.teamd.bioskopapisecurity.Service;

import net.sf.jasperreports.engine.JasperPrint;


public interface ReportService {
    JasperPrint generateJasperPrintA() throws Exception;
    JasperPrint generateJasperPrint() throws Exception;
    JasperPrint generateJasperPrintFilms() throws Exception;
    JasperPrint generateJasperPrintSeatsAvailable() throws Exception;

}
