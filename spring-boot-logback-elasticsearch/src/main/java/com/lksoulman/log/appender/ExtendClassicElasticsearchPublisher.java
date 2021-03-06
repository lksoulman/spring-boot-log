package com.lksoulman.log.appender;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.internetitem.logback.elasticsearch.ClassicElasticsearchPublisher;
import com.internetitem.logback.elasticsearch.config.ElasticsearchProperties;
import com.internetitem.logback.elasticsearch.config.HttpRequestHeaders;
import com.internetitem.logback.elasticsearch.config.Settings;
import com.internetitem.logback.elasticsearch.util.ErrorReporter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;

public class ExtendClassicElasticsearchPublisher extends ClassicElasticsearchPublisher {

	public ExtendClassicElasticsearchPublisher(Context context, ErrorReporter errorReporter, Settings settings,
			ElasticsearchProperties properties, HttpRequestHeaders headers) throws IOException {
		super(context, errorReporter, settings, properties, headers);
	}

	@Override
    protected void serializeCommonFields(JsonGenerator gen, ILoggingEvent event) throws IOException {
        gen.writeObjectField("@timestamp", getTimestamp(event.getTimeStamp()));

        if (settings.isRawJsonMessage()) {
        	String formattedMessage = event.getFormattedMessage();
        	gen.writeObjectField("message", formattedMessage);
        } else {
            String formattedMessage = event.getFormattedMessage();
            if (settings.getMaxMessageSize() > 0 && formattedMessage.length() > settings.getMaxMessageSize()) {
                formattedMessage = formattedMessage.substring(0, settings.getMaxMessageSize()) + "..";
            }
            gen.writeObjectField("message", formattedMessage);
        }

        if(settings.isIncludeMdc()) {
            for (Map.Entry<String, String> entry : event.getMDCPropertyMap().entrySet()) {
                gen.writeObjectField(entry.getKey(), entry.getValue());
            }
        }
    }
}
