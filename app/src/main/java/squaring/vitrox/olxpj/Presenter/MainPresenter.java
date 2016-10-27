package squaring.vitrox.olxpj.Presenter;

import squaring.vitrox.olxpj.Adapters.CategoryAdapter;
import squaring.vitrox.olxpj.Model.Category;

/**
 * Created by miguelgomez on 10/24/16.
 */

public interface MainPresenter {

    void onload();

    void onItemClick(Category item);

    interface View {
        void clickSaved(String categoryName);

        void refresh(Category photos);

    }


}
