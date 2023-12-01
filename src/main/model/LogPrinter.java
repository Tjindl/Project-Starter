package model;

public class LogPrinter {

    public static void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
