package com.example.paymentsystem.command.getcommands;

import com.example.paymentsystem.command.ServletCommand;
import com.example.paymentsystem.command.postcommands.LoginCommand;
import com.example.paymentsystem.dao.payment.PaymentDaoImpl;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.generetingpdf.GeneratePdfFile;
import com.example.paymentsystem.model.payment.Payment;
import com.example.paymentsystem.service.payment.PaymentService;
import com.example.paymentsystem.util.MappingProperties;
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
