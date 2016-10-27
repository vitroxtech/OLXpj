package squaring.vitrox.olxpj.Helper;

import squaring.vitrox.olxpj.Model.Photo;

/**
 * Created by miguelgomez on 10/25/16.
 */

public class Config {

    public static String[] CATEGORIES = new String[]{"motors","properties","jobs","electronics","classifieds","tyranosaurus"};
    public static String API_KEY = "d4f93851e125c78bf011705a2607d88a";
    public static String SELECTED_CATEGORY="Scategory";
    public static String SELECTED_IMAGE="SimageUrl";
    static String FIRST_TIME="firstTime";
    static String SHARED_PREFERENCES_AREA="first_time";

    public static String getUrl(Photo photo) {

        String farmId = photo.getFarm();
        String serverId = photo.getServer();
        String id = photo.getId();
        String secret = photo.getSecret();
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", farmId, serverId, id, secret);
    }

}
