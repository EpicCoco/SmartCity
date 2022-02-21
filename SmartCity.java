import org.jsoup.Jsoup;
import java.util.LinkedList;
import org.jsoup.nodes.Document;
import java.util.stream.Collectors;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import org.json.*;
import org.json.simple.*;  
import org.json.simple.parser.*; 
import java.util.Scanner;
import java.io.*; 
import java.util.*;
import org.json.simple.parser.JSONParser;
import com.google.gson.*;
import com.careerjet.webservice.api.Client;
public class SmartCity {
    public static void main(String args[]) throws Exception{
        System.out.println("Smart City");
        //Tourism module
        //get hotel
        URL url = new URL("https://api.geoapify.com/v2/places?categories=accommodation.hotel&filter=rect:-83.5565068267818,34.08940116555951,-83.18829317321723,33.79675168842013&limit=20&apiKey=a305ad8ec2e34a569c192df2bba27be2");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        StringBuilder result = new StringBuilder();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);   
            }
            String s = result.toString();
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject)jsonParser.parse(s);
            JsonArray status = jo.getAsJsonArray("features");
            for (int i = 0; i < status.size(); i++){
                JsonElement hotel = status.get(i);
                JsonObject j = hotel.getAsJsonObject();
                JsonObject properties = j.getAsJsonObject("properties");
                JsonPrimitive hotelName = properties.getAsJsonPrimitive("name");
                if (hotelName == null) {
                    hotelName = properties.getAsJsonPrimitive("address_line1");
                }
                JsonPrimitive addr = properties.getAsJsonPrimitive("address_line2");
                System.out.println(hotelName + "\n" + addr);
            }

        }
        URL url2 = new URL("https://api.opentripmap.com/0.1/en/places/radius?radius=20000&lon=-83.357604&lat=33.9519&apikey=5ae2e3f221c38a28845f05b634ee2da680014417a5342d1496cdc86a");
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        StringBuilder result2 = new StringBuilder();
        conn2.setRequestMethod("GET");
        try (BufferedReader reader2 = new BufferedReader(
            new InputStreamReader(conn2.getInputStream()))) {
                for (String line2; (line2 = reader2.readLine()) != null; ) {
                    result2.append(line2); 
                }
                String s2 = result2.toString();
                JsonParser jsonParser = new JsonParser();
                JsonObject root = (JsonObject)jsonParser.parse(s2);
                JsonArray features = root.getAsJsonArray("features");
                int count=0;
                LinkedList<String> types = new LinkedList<String>();
                
                for (int i = 0; i < features.size(); i++) {
                    JsonElement place = features.get(i);
                    JsonObject placeObject = place.getAsJsonObject();
                    JsonObject properties = placeObject.getAsJsonObject("properties");
                    System.out.println(properties.getAsJsonPrimitive("name").getAsString() + properties.getAsJsonPrimitive("kinds").getAsString());
                    
                    types.addLast(properties.getAsJsonPrimitive("kinds").getAsString());
                    
                    for (String j:types){
                        if (j.equals("cemeteries,historic,burial_places,interesting_places")){
                            count += 1;
                        }
                    }
                    
                    
                }
                List<String> Unique = types.stream().distinct().collect(Collectors.toList());
                for (String s: Unique) {
                    System.out.println(s + ": " + Collections.frequency(types, s));
                }
        }
        
    }
}