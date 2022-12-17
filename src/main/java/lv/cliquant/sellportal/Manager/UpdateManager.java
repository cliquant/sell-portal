package lv.cliquant.sellportal.Manager;

import com.google.gson.JsonParser;
import lv.cliquant.sellportal.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateManager {
    public static void check() {
        Double currentversion = Main.getInstance().getConfig().getDouble("version");

        try {

            String a = "https://raw.githubusercontent.com/cliquant/sell-portal/main/version";
            URL url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            inputLine = br.readLine();

            if(Double.parseDouble(inputLine) != currentversion) {
                Main.getInstance().getLogger().info("Plugin is outdated. New version: " + inputLine);
                Main.getInstance().getLogger().info("Download resource: https://builtbybit.com/resources/sellportal.26261/");
            } else {
                Main.getInstance().getLogger().info("Plugin is up to date.");
            }

            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
