package yang.acticlescraper.scrape;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import yang.acticlescraper.flipview.Articles;
import yang.acticlescraper.flipview.ViewApp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Created by le on 15/7/2015.
 */
public class Utils {
    private static int count = 0;
    private static int MAX_ARTICLE = 5;
    public static String getContent(String SURL) {
        //if(ViewApp.adapter!=null)
           // ViewApp.adapter.clearData();
        count = 0;
        URL url;
        try {
            url = new URL(SURL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                builder.append(line.trim());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException logOrIgnore) {
            }
        }
        //System.out.println(builder.toString());
        Document doc = Jsoup.parse(builder.toString());

        // then use something like this to get your element:
        Elements tds = doc.getElementsByTag("a");
        for (Element td : tds) {
            String linkHref = td.attr("href");
            String linkClass = td.attr("class");
            String linkText = td.text();
            if(count > MAX_ARTICLE) break;
            if(linkClass.equalsIgnoreCase("title-link") && !linkHref.contains("http")){
                String newURL = "http://www.bbc.com"+linkHref;
                getChildContent(newURL);
                count ++;
            }
        }

        /*String title_start = "<h1 class=\"story-body__h1\">";
        String title_end = "</h1>";
        String title_part = builder.substring(builder.indexOf(title_start) + title_start.length());
        String title = title_part.substring(0, title_part.indexOf(title_end));

        String content_start = "<p class=\"story-body__introduction\">";
        String content_end = "</p>";
        String content_part = builder.substring(builder.indexOf(content_start) + content_start.length());
        String content = content_part.substring(0, content_part.indexOf(content_end));
        System.out.println(content);*/
        return ("title");
    }
    public static String getChildContent(String SURL) {

        URL url;
        try {
            url = new URL(SURL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                builder.append(line.trim());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException logOrIgnore) {
            }
        }
        // System.out.println(builder.toString());

        Document doc = Jsoup.parse(builder.toString());

        // then use something like this to get your element:
        Elements h1s = doc.getElementsByTag("h1");
        String title="";
        String content = "";
        String time = "";
        String imageLink = "";
        for (Element h1 : h1s) {
            String linkHref = h1.attr("href");
            String linkClass = h1.attr("class");
            String linkText = h1.text();
            if(linkClass.equalsIgnoreCase("story-body__h1"))
                title = new String(linkText);
        }
        if(title.length() < 3) return (title);
        Elements divs = doc.getElementsByTag("div");
        for (Element td : divs) {
            String linkHref = td.attr("href");
            String linkClass = td.attr("class");
            String linkText = td.text();
            if(linkClass.equalsIgnoreCase("story-body__inner"))
                content = new String(linkText);
            if(linkClass.equalsIgnoreCase("date date--v2"))
                time = new String(linkText);
        }
        Elements imgs = doc.getElementsByTag("img");
        for (Element td : imgs) {
            String linkSrc = td.attr("src");
            String linkClass = td.attr("class");
            String linkText = td.text();
            if(linkClass.equalsIgnoreCase("js-image-replace"))
            {
                imageLink = new String(linkSrc);
                Log.e("aaa", linkSrc);
                break;
            }
        }
        Articles.Data temp = new Articles.Data(title, "BBC Com",
                content,
                time, "Singapore",
                SURL,imageLink);
        if(ViewApp.adapter!=null)
            if(ViewApp.adapter.size()>count)
                ViewApp.adapter.set(count,temp);
            else
            ViewApp.adapter.addData(temp);

        return (title);
    }

}
