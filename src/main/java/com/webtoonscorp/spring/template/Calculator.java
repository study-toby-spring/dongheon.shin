package com.webtoonscorp.spring.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String path) throws IOException {

        BufferedReaderCallback callback = new BufferedReaderCallback() {

            public Integer doSomethingWithReader(BufferedReader reader) throws IOException {

                String line;
                Integer sum = 0;

                while ((line = reader.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }

                return sum;
            }
        };

        return fileReadTemplate(path, callback);
    }


    public Integer calcMul(String path) throws IOException {

        BufferedReaderCallback callback = new BufferedReaderCallback() {

            public Integer doSomethingWithReader(BufferedReader reader) throws IOException {

                String line;
                Integer sum = 1;

                while ((line = reader.readLine()) != null) {
                    sum *= Integer.valueOf(line);
                }

                return sum;
            }
        };

        return fileReadTemplate(path, callback);
    }

    private Integer fileReadTemplate(String path, BufferedReaderCallback callback) throws IOException {

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(path));
            return callback.doSomethingWithReader(reader);
        }
        catch (IOException e) {

            System.out.println(e.getMessage());
            throw e;
        }
        finally {

            if (reader != null) {

                try {

                    reader.close();
                }
                catch (IOException e) {

                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
