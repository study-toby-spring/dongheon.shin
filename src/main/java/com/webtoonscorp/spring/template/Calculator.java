package com.webtoonscorp.spring.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String path) throws IOException {

        LineCallback callback = new LineCallback() {

            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) + value;
            }
        };

        return lineReadTemplate(path, callback, 0);
    }


    public Integer calcMul(String path) throws IOException {

        LineCallback callback = new LineCallback() {

            public Integer doSomethingWithLine(String line, Integer value) {
                return Integer.valueOf(line) * value;
            }
        };

        return lineReadTemplate(path, callback, 1);
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

    private Integer lineReadTemplate(String path, LineCallback callback, int initValue) throws IOException {

        BufferedReader reader = null;
        String line;

        Integer value = initValue;

        try {

            reader = new BufferedReader(new FileReader(path));

            while ((line = reader.readLine()) != null) {
                value = callback.doSomethingWithLine(line, value);
            }

            return value;
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
