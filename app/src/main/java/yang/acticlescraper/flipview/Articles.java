package yang.acticlescraper.flipview;

import java.util.ArrayList;
import java.util.List;


public class Articles {

  public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();
  public static final class Data {
    public final String title;
    public final String author;
    public final String content;
    public final String time;
    public final String city;
    public final String link;

    public Data(String title, String author, String content, String time,
                 String city, String link) {
      this.title = title;
      this.author = author;
      this.content = content;
      this.time = time;
      this.city = city;
      this.link = link;
    }
  }
}
