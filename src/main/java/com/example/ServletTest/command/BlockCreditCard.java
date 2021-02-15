package com.example.ServletTest.command;

import com.example.ServletTest.dao.creditcard.CreditCardDaoImpl;
import com.example.ServletTest.service.creditcard.CreditCardService;
import com.example.ServletTest.util.MappingProperties;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockCreditCard implements ServletCommand {
    private static final Logger logger = Logger.getLogger(BlockCreditCard.class);
    private CreditCardService creditCardService;
    private String mainPage;

    public BlockCreditCard() {
        creditCardService = new CreditCardService(CreditCardDaoImpl.getInstance());
        MappingProperties properties = MappingProperties.getInstance();
        mainPage = properties.getProperty("mainPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Executing blocking credit card command");
        long cardId = Long.parseLong(request.getParameter("cardId"));
        creditCardService.blockCreditCardById(cardId);

        return mainPage;
    }
}
