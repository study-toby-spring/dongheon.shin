package com.webtoonscorp.spring.template;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {

    Integer doSomethingWithReader(BufferedReader reader) throws IOException;
}
