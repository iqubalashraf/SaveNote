package note.save.app.savenote.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import note.save.app.savenote.R;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 24/01/18.
 */

public class RightMenuFragment extends Fragment implements View.OnClickListener{

    OnClickActions onClickActions;
    Activity activity;
    TextView button_filter, button_heart, button_favorite, button_apply;
    View mainView;

    public void setOnClickActions(OnClickActions onClickActions) {
        this.onClickActions = onClickActions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_right_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        mainView = getView();
        initialize();
        initializeOnClickListerner();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_apply:
                if(onClickActions != null){
                    onClickActions.onApplyButtonClick();
                }
                break;
            case R.id.button_filter:
                if(onClickActions != null){
                    onClickActions.onCloseButtonClick();
                }
                break;
            case R.id.button_heart:
                GeneralUtil.isHearted(!GeneralUtil.isHearted());
                changeView(view, GeneralUtil.isHearted());
                break;
            case R.id.button_favorite:
                GeneralUtil.isStar(!GeneralUtil.isStar());
                changeView(view, GeneralUtil.isStar());
                break;
        }
    }

    public interface OnClickActions{
        void onCloseButtonClick();
        void onApplyButtonClick();
    }

    private void initialize(){
        button_filter = mainView.findViewById(R.id.button_filter);
        button_heart = mainView.findViewById(R.id.button_heart);
        button_favorite = mainView.findViewById(R.id.button_favorite);
        button_apply = mainView.findViewById(R.id.button_apply);
        changeView(button_heart, GeneralUtil.isHearted());
        changeView(button_favorite, GeneralUtil.isStar());
    }
    private void initializeOnClickListerner(){
        button_filter.setOnClickListener(this);
        button_heart.setOnClickListener(this);
        button_favorite.setOnClickListener(this);
        button_apply.setOnClickListener(this);
    }

    private void changeView(View view, boolean value){
        view.setSelected(value);
    }
}
