package infosys.teamd.bioskopapisecurity.Controller;

import infosys.teamd.bioskopapisecurity.Service.ReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/teamD/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/rservasi")
    public void getReservasiReport() throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-Dispositin", "attachment; filname=\"reservasi_list.pdf\"");
        JasperPrint jasperPrint = reportService.generateJasperPrint();
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
