package com.csv.writer;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.opencsv.CSVWriter;


public class WritingToCSV {
	
	 public static final String ETC_GMT = "Etc/GMT";
	 
	 public static final String  ZULU = "yyyy-MM-dd'T'HH:mm:ssXXX";
	
	public static final String COMPANY_KEY = "076a14b0-8f48-409f-950a-08d30e2de6bb";
	
	public static final String CALENDAR = "\"076a14b0-8f48-409f-950a-08d30e2de6bb\"";
	
	public static String PROVIDER = "\"r3b3a1590816830146\"";
	
	public static final String TIMEZONE = "\"Asia/Kolkata\"";
	
	public static final String TYPE = "\"EVENT\"";
	
	public static final String ACTION = "EVENT_CREATE";
	
	public static final String BRAND = "SetMore";
	
	
   public static void main(String args[]) throws Exception {
      //Instantiating the CSVWriter class

	   String[] staffdata = {
			   "r44b91590853587739","r46d11590853371865","r6b2e1590853530753",
			   "r85a61590853444649","r8b221590853456822","r8f721590853515451",
			   "radc41590853407770","rb1fc1590853450959","rb7f41590853363654","rd3851590853574718",
			   "re7cd1590853476714","recc41590853430490","recd81590853541965","red611590853499438",
			   "rf37a1590853400800","rfaeb1590853552848","rfef01590853438047","ra32d1590759494144",
			   "r0e131590759509672","r79771590853381383","r089d1590853671102",
			   "r14801590853683473","re7ca1590853695123","r1e8d1590853705028","r90bd1590853719160",
			   "r31731590853724819","r492d1590853731877","r8db71590853739500","r1c2d1590853756246",
			   "rdb891590853763139","r21f51590853769323","r0bc41590853776246","rcec51590853783163",
			   "rc2621590853790171","r81441590853800212","r798b1590853807093","rf6a71590853815550",
			   "r09501590853824300",
			   "rb86e1590856895211",
			   "r81151590856924324",
			   "red8e1590856944032",
			   "r233a1590856971409",
			   "rb8061590857056618"};
	   
	   
      List<ZonedDateTime> zonedDateTimes = getDates("2020-06-01T08:00:00+05:30","2022-01-01T08:00:00+05:30");
      for(String str : staffdata) {
    	  CSVWriter writer = new CSVWriter(new FileWriter(str+".csv"));
          //Writing data to a csv file
          String line1[] = {"brand","merchant","action", "calendar", "provider", "startDateTime", "endDateTime","timezone","type"};
          writer.writeNext(line1);
    	  PROVIDER = "\""+str+"\"";
        int count = getRandomCount();
        for(ZonedDateTime day : zonedDateTimes) {
      	  int i=0;
      	  while(i<=count) {
      		  writer.writeNext(getData(day));  
      		  i++;
      	  }
        }
        writer.flush();
    }
  
      //Flushing data from writer to file
     
      System.out.println("Data entered");
   }
   
   public static int getRandomCount() {
	   Random random = new Random();
	   int value = random.nextInt(80)+20;
	   return value;  
   }
   
   public static String[] getData(ZonedDateTime date) {
	 
	   ZonedDateTime startTime = date.plusMinutes(getRandomStartTime());
	   ZonedDateTime endTime = startTime.plusMinutes(getDuration());
	   
	   String[] stringArray3 = new String[9];
	   stringArray3[0] = BRAND;
	   stringArray3[1] = COMPANY_KEY;
	   stringArray3[2] = ACTION;
	   stringArray3[3] = CALENDAR;
	   stringArray3[4] = PROVIDER;
	   stringArray3[5] = "\""+startTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern(ZULU))+"\"";
	   stringArray3[6] = "\""+endTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern(ZULU))+"\"";
	   stringArray3[7] = TIMEZONE;
	   stringArray3[8] = TYPE;
	   
	   
	   return stringArray3;
   }
   
   
   
   private static long getDuration() {
	   int[] durationArr = {15,30};
	   Random random = new Random();
	   int value = random.nextInt(2);
	   return Long.valueOf(durationArr[value]);
   }
   
   private static long getRandomDuration() {
	   Random random = new Random();
	   int value = random.nextInt(23)+1;
	   return Long.valueOf(value)*5;
   }
   
   private static long getRandomStartTime() {
	   Random random = new Random();
	   int value = random.nextInt(95)+1;
	   return Long.valueOf(value)*15;
   }
   
   public static Set<Long> getEventDaysFalls(long start_time, long end_time) {

       Set<Long> multiEventDays = new HashSet<Long>();
       start_time = getStartOfDay(start_time);
       while (start_time <= end_time) {
           multiEventDays.add(start_time);
           start_time += 24 * 60 * 60 * 1000;
       }
       return multiEventDays;
   }
   
   public static Long getStartOfDay(final long date) {
       final Calendar calendar = Calendar.getInstance();
       calendar.setTimeInMillis(date);
       calendar.set(Calendar.HOUR_OF_DAY, 0);
       calendar.set(Calendar.MINUTE, 0);
       calendar.set(Calendar.SECOND, 0);
       calendar.set(Calendar.MILLISECOND, 0);
       return calendar.getTimeInMillis();
   }
   
   public static List<ZonedDateTime> getDates(String startDate, String endDate) {
       ZonedDateTime startInGmt = getTimeWithOffset("Asia/Kolkata", ZonedDateTime.parse(startDate, DateTimeFormatter.ofPattern(ZULU)));
       ZonedDateTime endInGmt = getTimeWithOffset("Asia/Kolkata", ZonedDateTime.parse(endDate, DateTimeFormatter.ofPattern(ZULU)));
       return getDates(startInGmt, endInGmt);
   }
   
   public static List<ZonedDateTime> getDates(ZonedDateTime startDate, ZonedDateTime endDate) {

       LocalDateTime startDateLocal = startDate.toLocalDate().atStartOfDay();
       ZonedDateTime startDateStartOfTheDay  = startDateLocal.atZone(startDate.getZone());
       long numOfDaysBetween = ChronoUnit.DAYS.between(startDateLocal, endDate.toLocalDate().atTime(LocalTime.MAX).plusNanos(1));

       return IntStream.iterate(0, i -> i + 1)
               .limit(numOfDaysBetween)
               .mapToObj(i -> startDateStartOfTheDay.plusDays(i))
               .collect(Collectors.toList());
   }

   
   public static ZonedDateTime getTimeWithOffset(String timezoneId, ZonedDateTime zonedateTime){

       return zonedateTime.withZoneSameInstant(ZoneId.of(timezoneId));

    }
   
}
