package com.githug.rok.celebrity.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RokLogger implements PluginLogger {
    private final Logger logger;

    public RokLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String s) {
        this.logger.info(s);
    }

    @Override
    public void debug(String s) {
        // TODO: Add debug mode
        this.logger.info("[DEBUG] " + s);
    }

    @Override
    public void warn(String s) {
        this.logger.warning(s);
    }

    @Override
    public void warn(String s, Throwable t) {
        this.logger.log(Level.WARNING, s, t);
    }

    @Override
    public void severe(String s) {
        this.logger.severe(s);
    }

    @Override
    public void severe(String s, Throwable t) {
        this.logger.log(Level.SEVERE, s, t);
    }
}
