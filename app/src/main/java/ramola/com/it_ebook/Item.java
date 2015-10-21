package ramola.com.it_ebook;

import android.graphics.Bitmap;

/**
 * Created by lenovo on 10/18/2015.
 */
public class Item {
    String Title,Author,Description,id;
    Bitmap bm;

    public Item(String title, String author, String description,String Id, Bitmap bm) {
        Title = title;
        Author = author;
        Description = description;
        id=Id;
        this.bm = bm;
    }
}
