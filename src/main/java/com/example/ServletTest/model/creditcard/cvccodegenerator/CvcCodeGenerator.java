package com.example.ServletTest.model.creditcard.cvccodegenerator;

import java.security.SecureRandom;

public class CvcCodeGenerator {
    private static final int MAX_VALUE_OF_CVC_CODE = 999;
    private final int cvcCode;

    public CvcCodeGenerator(SecureRandom random) {
        this.cvcCode = random.nextInt(MAX_VALUE_OF_CVC_CODE);
    }

    public int getCvcCode() {
        return cvcCode;
    }
}
