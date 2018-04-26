package Models.Other;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeFlow {
        private int next = 0;
        private LocalDate today;
        private Date date = new Date();
        private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    /**
     * Gets the date from system
     */
    public TimeFlow(){
            today = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String toString(){
        return (today.format(format));
    }

    public LocalDate getDate(){
        return today;
    }

    /**
     * Sets the date one day forward
     */
    public void next(){
            next++;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,next);
            setDate(calendar.getTime());
    }

    public void setDate(Date date){
        today = dateToLocalDate(date);
    }

    public void setDate(LocalDate date){
        next = date.getDayOfYear() - (dateToLocalDate(this.date)).getDayOfYear();
        today = date;
    }

    private LocalDate dateToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date localDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
