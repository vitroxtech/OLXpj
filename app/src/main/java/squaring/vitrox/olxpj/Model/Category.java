package squaring.vitrox.olxpj.Model;

/**
 * Created by miguelgomez on 10/25/16.
 */

public class Category {

    private String id;

    private String categoryname;

    private String clicks;

    private String Link;

    public Category() {

    }

    public Category(String id, String categoryname, String clicks) {
        this.id = id;
        this.categoryname = categoryname;
        this.clicks = clicks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getClicks() {
        return clicks;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
