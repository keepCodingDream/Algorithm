package rpn;


import rpn.core.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Just for test
 *
 * @author tracy.
 * @create 2018-02-06 20:48
 **/
public class Main {
    public static void main(String[] args) throws IOException {
        Processor processor = new Processor();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = in.readLine()) != null) {
            try {
                processor.processInput(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
