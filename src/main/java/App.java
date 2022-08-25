import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class App {

    public static void main(String[] args)
            throws IOException {

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Please Enter Country Name: ");
        String name = reader.nextLine();
        reader.close();

        URL url = new URL("https://restcountries.com/v3.1/name/" + name);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();
        System.out.println("Response code is " + responsecode);

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            scanner.close();

            inline = inline.substring(1);
            inline = inline.substring(0,inline.length()-1);

            JSONObject ob = new JSONObject(inline);
            JSONArray capital_raw = ob.getJSONArray("capital");
            String capital = capital_raw.get(0).toString();
            System.out.println("Capital of " + name + " is " + capital);

        }
    }

    public static String getCapital(String country)
            throws IOException {

        URL url = new URL("https://restcountries.com/v3.1/name/" + country);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            scanner.close();

            inline = inline.substring(1);
            inline = inline.substring(0,inline.length()-1);

            JSONObject ob = new JSONObject(inline);
            JSONArray capital_raw = ob.getJSONArray("capital");
            String capital = capital_raw.get(0).toString();
            System.out.println("Capital of " + country + " is " + capital);
            return capital;

        }
    }

    @Test
    public void testCapital() throws IOException {
        Assert.assertEquals(getCapital("Uzbekistan"), "Tashkent");
        Assert.assertEquals(getCapital("Peru"), "Lima");
    }
}
