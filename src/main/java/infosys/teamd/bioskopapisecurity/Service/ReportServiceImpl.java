package infosys.teamd.bioskopapisecurity.Service;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private DataSource dataSource;

    private Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public JasperPrint generateJasperPrintA() throws Exception {
        InputStream fileReport = new ClassPathResource("reports/Schedules.jasper").
        getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
                getConnection());
        return jasperPrint;
    }
    
    @Override
    public JasperPrint generateJasperPrint() throws Exception{
        InputStream fileReport = new ClassPathResource("reports/reservasi.jasper").
                getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
                getConnection());
        return jasperPrint;
    }
    @Override
    public JasperPrint generateJasperPrintFilms() throws Exception {
        InputStream fileReport = new ClassPathResource("reports/Films.jasper").
                getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
                getConnection());
        return jasperPrint;
    }
    @Override
    public JasperPrint generateJasperPrintSeatsAvailable() throws Exception {
        InputStream fileReport = new ClassPathResource("reports/Seats_Available.jasper").
                getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
                getConnection());
        return jasperPrint;
    }
}
