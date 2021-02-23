package com.example.ServletTest.command.getcommands;

import com.example.ServletTest.command.ServletCommand;
import com.example.ServletTest.command.postcommands.LoginCommand;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.exception.DatabaseException;
import com.example.ServletTest.model.generetingpdf.GeneratePdfFile;
import com.example.ServletTest.model.payment.Payment;
import com.example.ServletTest.service.payment.PaymentService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PreparePdfFile implements ServletCommand {
    private static final Logger logger = Logger.getLogger(PreparePdfFile.class);
    private final PaymentService paymentService;
    private final String mainPage;
    private String errorFailedToGeneratePdf;
    private final String errorPage;

    public PreparePdfFile() {
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();

        mainPage = properties.getProperty("mainPage");
        errorFailedToGeneratePdf = properties.getProperty("errorFailedToGeneratePdf");
        errorPage = properties.getProperty("errorPageDatabase");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing generating pdf file command");

        long cardId = 1;
        List<Payment> payments;

        try {
            payments = paymentService.getListOfPaymentsThatBelongToCreditCard(cardId);
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        GeneratePdfFile generatePdfFile = new GeneratePdfFile();
        String fileName = "file" + cardId + ".pdf";

        try {
            generatePdfFile.createPdfFile(fileName, LoginCommand.wrapPaymentList(payments));
        } catch (DatabaseException e) {
            request.setAttribute("errorCause", e.getMessage());
            return errorPage;
        }

        request.setAttribute("prepared", true);
        request.setAttribute("fileName", fileName);
        return mainPage;
    }
}
