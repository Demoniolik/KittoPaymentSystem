package com.example.ServletTest.command;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class CommandManager {
    private static final Logger logger = Logger.getLogger(CommandManager.class);
    private HashMap<String, ServletCommand> getCommands;
    private HashMap<String, ServletCommand> postCommands;

}
