package com.githug.rok.celebrity.utils;

public interface PluginLogger {
    void info(String s);

    void debug(String s);

    void warn(String s);

    void warn(String s, Throwable t);

    void severe(String s);

    void severe(String s, Throwable t);
}
