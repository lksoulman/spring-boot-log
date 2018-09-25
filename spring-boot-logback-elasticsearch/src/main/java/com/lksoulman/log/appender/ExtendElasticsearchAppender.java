package com.lksoulman.log.appender;

import java.io.IOException;

import com.internetitem.logback.elasticsearch.ClassicElasticsearchPublisher;
import com.internetitem.logback.elasticsearch.ElasticsearchAppender;

public class ExtendElasticsearchAppender extends ElasticsearchAppender {

	protected ClassicElasticsearchPublisher buildElasticsearchPublisher() throws IOException {
        return new ExtendClassicElasticsearchPublisher(getContext(), errorReporter, settings, elasticsearchProperties, headers);
    }
}
