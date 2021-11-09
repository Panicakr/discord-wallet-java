import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.StringJoiner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Wallet 
{
    private static String ChangeWalletBal(String botID, String userID, String amountOfCurrency, String currencySymbol, String type) throws IOException {
        URL url = new URL("https://discord-wallet.glitch.me/r");
        
        HttpURLConnection con = (HttpURLConnection) 
        url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        Map<String,String> arguments = new HashMap<>();

        if(type == "WITHDRAWAL") {
            amountOfCurrency = "-" + amountOfCurrency;
        }
        
        arguments.put("clientID", botID);
        arguments.put("userID", userID);
        arguments.put("amountOfCurrency", amountOfCurrency);
        arguments.put("type", type);
        arguments.put("symbol", currencySymbol);

        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
                + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        con.setFixedLengthStreamingMode(length);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.connect();
        try(OutputStream os = con.getOutputStream()) {
            os.write(out);
        }

         BufferedReader in = new BufferedReader(new InputStreamReader(
            con.getInputStream())); 
        String inputLine; 
        while ((inputLine = in.readLine()) != null) { 
            return inputLine;
        }

        in.close(); 
        
        throw new IOException();
    }

    public static void addToWallet(String botID, String userID, String amountOfCurrency, String currencySymbol) {
        try {
            Wallet.ChangeWalletBal(botID, userID, amountOfCurrency, currencySymbol, "DEPOSIT");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void removeFromWallet(String botID, String userID, String amountOfCurrency, String currencySymbol) {
        try {
            Wallet.ChangeWalletBal(botID, userID, amountOfCurrency, currencySymbol, "WITHDRAWAL");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static String checkBalance(String botID, String userID, String currencySymbol) {
        try {
            String bal = Wallet.ChangeWalletBal(botID, userID, "null", currencySymbol, "INQUIRY");
            String[] response = bal.split(":");
            String res = (String)Array.get(response, 1);
            String r = (String)Array.get(res.split("}"), 0);

            return r;
        } catch (IOException error) {
            error.printStackTrace();
            return "Error";
        }
    }
}
