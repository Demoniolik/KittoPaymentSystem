package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.dao.payment.PaymentDaoImpl;
import com.example.ServletTest.model.user.User;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.service.payment.PaymentService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PaymentPageCommand implements ServletCommand {
    private static final Logger logger = Logger.getLogger(PaymentPageCommand.class);
    private CreditCardService creditCardService;
    private PaymentService paymentService;
    private String errorPage;
    private String paymentsPage;
    private String loginPage;

    public PaymentPageCommand() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        paymentService = new PaymentService(PaymentDaoImpl.getInstance());
        // TODO: Here you need to load files from properties file
        paymentsPage = "WEB-INF/payments.jsp";
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing payment page command");
        String resultPage = loginPage;
        HttpSession session = request.getSession();
        Boolean authorized = (Boolean)session.getAttribute("authorized");

        if (authorized != null && authorized.equals(true)) {
            session.setAttribute("categories", paymentService.getAllCategories());
            session.setAttribute("userCreditCards",
                    creditCardService.getAllCreditCards(((User)session.getAttribute("user")).getId()));
            resultPage = paymentsPage;
        }

        return resultPage;
    }
}
