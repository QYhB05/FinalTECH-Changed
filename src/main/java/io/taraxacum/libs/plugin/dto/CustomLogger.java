package io.taraxacum.libs.plugin.dto;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class CustomLogger extends Logger {
    private String banner = "";
    private Logger logger;

    protected CustomLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static CustomLogger newInstance(@Nonnull Logger logger) {
        CustomLogger customLogger = new CustomLogger(logger.getName(), logger.getResourceBundleName());
        customLogger.logger = logger;

        return customLogger;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public void info(String msg) {
        this.logger.info(this.banner + msg);
    }

    @Override
    public void warning(String msg) {
        this.logger.warning(this.banner + msg);
    }

    @Override
    public void severe(Supplier<String> msgSupplier) {
        this.logger.severe(this.banner + msgSupplier);
    }
}
