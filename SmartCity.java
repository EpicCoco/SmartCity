import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class SmartCity {
    public static void main(String args[]) throws Exception{
        System.out.println("Smart City");
        //Tourism module
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
                JsonPrimitive addr = properties.getAsJsonPrimitive("address_line2");
                System.out.println(addr);
            }

        }
    }
}