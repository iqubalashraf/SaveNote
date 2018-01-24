package note.save.app.savenote.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import note.save.app.savenote.R;

/**
 * Created by ashrafiqubal on 24/01/18.
 */

public class RightMenuFragment extends Fragment {

    OnClickActions onClickActions;

    public void setOnClickActions(OnClickActions onClickActions) {
        this.onClickActions = onClickActions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_right_menu, container, false);
    }

    public interface OnClickActions{
        void onCloseButtonClick();
        void onApplyButtonClick();
    }
}
