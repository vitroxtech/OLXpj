package squaring.vitrox.olxpj.Presenter;

import android.view.View;

import squaring.vitrox.olxpj.Model.Category;

/**
 * Created by miguelgomez on 10/27/16.
 */

public interface FirstActivityPresenter {

    void checkIfFirstTime();
    void getMostClickedCat();
    void onImageClicked();
     interface view
     {
         void isfirstTime(Boolean b);
         void mostViewedCategory(Category c);
         void goToProducts(String CategoryName);
     }

}
