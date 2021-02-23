package com.example.paymentsystem.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface handles all commands
 */

public interface ServletCommand {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
