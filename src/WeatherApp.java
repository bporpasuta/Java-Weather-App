import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {
    public static  JSONObject getWeatherData(String locationName){

        JSONArray locationData =  getlocationData(locationName);
        if(locationData == null || locationData.isEmpty()){
            return null;
        }

        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        String urlString = "https://api.open-meteo.com/v1/forecast?"+
                        "latitude="+latitude+"&longitude="+longitude+
                        "&hourly=temperature_2m,weather_code,wind_speed_10m,relative_humidity_2m&timezone=Asia%2FBangkok";
       try{
           HttpURLConnection conn = fetchAPIResponse(urlString);
           if(conn.getResponseCode() != 200){
               System.out.println("Error: Could not connect");
               return null;
           }
           StringBuilder resultJSon = new StringBuilder();
           Scanner scanner = new Scanner(conn.getInputStream());
           while(scanner.hasNext()){
               resultJSon.append(scanner.nextLine());
           }
           scanner.close();
           conn.disconnect();

           JSONParser parser = new JSONParser();
           JSONObject resultJsonObj = (JSONObject)  parser.parse(String.valueOf(resultJSon));
           JSONObject hourly = (JSONObject)  resultJsonObj.get("hourly");

           JSONArray time = (JSONArray)  hourly.get("time");
           int index = findIndexOfCurrentTime(time);

           JSONArray temperatureData  =  (JSONArray) hourly.get("temperature_2m");
           double temperature = (double) temperatureData.get(index);

           JSONArray weatherCode =  (JSONArray) hourly.get("weather_code");
           String weatherCondition = convertWeatherCode((long) weatherCode.get(index));

           JSONArray humidityData   = (JSONArray) hourly.get("relative_humidity_2m");
           long humidity = (long) humidityData.get(index);

           JSONArray windSpeedData  = (JSONArray) hourly.get("wind_speed_10m");
           double windspeed = (double) windSpeedData.get(index);


           //Build the weather json data obl=ject that we are going to access in our fronted

           JSONObject weatherData =  new JSONObject();
           weatherData.put("tempurature",temperature);
           weatherData.put("weather_condition",weatherCondition);
           weatherData.put("humidity",humidity);
           weatherData.put("windspeed",windspeed);

           return weatherData;






       }catch(Exception e){
           e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getlocationData(String locationName){
        locationName = locationName.replaceAll(" ","+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name="+
                locationName + "&count=100&language=en&format=json";
        try {
            HttpURLConnection  conn = fetchAPIResponse(urlString);

            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else{
                StringBuilder resultJson = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                //read and store the resulting json data into our string builder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());
                }
                scanner.close();
                conn.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                JSONArray locationData = (JSONArray) resultJsonObj.get("results");
                return  locationData;

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;


    }
    private static HttpURLConnection fetchAPIResponse(String urlString){
        try{
            disableSSLVerification();
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    conn.connect();
                    return conn;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void disableSSLVerification() {

        try {

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                X509Certificate[] certs,
                                String authType) {
                        }

                        public void checkServerTrusted(
                                X509Certificate[] certs,
                                String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");

            sc.init(null, trustAllCerts,
                    new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(
                    sc.getSocketFactory());

            HostnameVerifier allHostsValid =
                    (hostname, session) -> true;

            HttpsURLConnection.setDefaultHostnameVerifier(
                    allHostsValid);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();
        for(int i=0; i<timeList.size();i++){
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)) {
                return i;
            }

        }
        return 0 ;
    }
    private static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDate =  currentDateTime.format(formatter);

        return formattedDate;

    }
    private static  String convertWeatherCode (long weathercode){
        String weathercondition = "";
        if(weathercode == 0L){
            //clear
            weathercondition = "Clear";

        } else if (weathercode>0L && weathercode <= 3L) {
            //Cloudy
            weathercondition =  "Cloudy";

        } else if (( weathercode>= 51L && weathercode<=67L) || (weathercode >=80L && weathercode <=99L)) {
            weathercondition = "Rain";

        } else if (weathercode >= 71L &&weathercode<=77L) {
            weathercondition ="Snow";
        }
        return weathercondition;

    }

}
