package ramola.com.it_ebook;

public class Item_main {
    String Section,title,description,url,url_more;

    public Item_main(String section, String title, String description,String url_more) {
        Section = section;
        this.title = title;
        this.description = description;
        this.url_more=url_more;
    }

    public Item_main(String section, String title, String description,String url,String url_more) {
        Section = section;
        this.title = title;
        this.description = description;
        this.url =url;
        this.url_more=url_more;
    }
}
