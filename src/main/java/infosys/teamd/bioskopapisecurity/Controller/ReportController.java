package infosys.teamd.bioskopapisecurity.Controller;

import infosys.teamd.bioskopapisecurity.Service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("teamD/v1/reports")
@SecurityRequirement(name = "bearer-key")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private HttpServletResponse response;


    @GetMapping("/schedule")
    public void getReservasiReportA() throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"schedules_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintA();
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
    
    @GetMapping("/reservasi")
    public void getReservasiReport() throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"reservasi_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrint();

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping("/films")
    public void getReservasiReportFilms() throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"films_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintFilms();

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping("/seats/Available")
    public void getReservasiReportSeatsAvailable() throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"films_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrintSeatsAvailable();

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
