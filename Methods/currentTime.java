import java.time.LocalDateTime;
public class currentTime {
    
    public static void main(String[] args) {
        

        System.out.println(getCurrentTimeAndDate());
    }

    public static String getCurrentTimeAndDate()
    {
        LocalDateTime now = LocalDateTime.now();
        
        int day = now.getDayOfMonth();     // 11
        int month = now.getMonthValue();   // 12
        int year = now.getYear();          // 2024
        int hour = now.getHour();          // 14
        int minute = now.getMinute();      // 30
        int second = now.getSecond();      // 45
        
        String finalTimeAndDate = hour + ":" + minute + ":"+ second + " ("+day+"/" + month+ "/"+ year + ") ";

        return finalTimeAndDate;
    }
}
