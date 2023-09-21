package testCases;

import java.util.Date;


public class Get_Date_Time {

    public static void main(String[] args) throws InterruptedException {


        System.out.println(System.getProperty("user.dir"));

        Date date_format = new Date(System.currentTimeMillis());

        String s = String.valueOf(date_format);

        String [] date_Time = s.split(" ");

        String day = date_Time[0];
        String month = date_Time[1];
        String date = date_Time[2];
        String time = date_Time[3];
        String time_format = date_Time[4];
        String year = date_Time[5];

        System.out.println(month);
        System.out.println(year);

    }
}
