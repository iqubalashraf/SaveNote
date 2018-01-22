package note.save.app.savenote.adapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.List;

import note.save.app.savenote.R;
import note.save.app.savenote.dataModel.Note;
import note.save.app.savenote.sql.DatabaseHandler;
import note.save.app.savenote.utils.GeneralUtil;

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    AppCompatActivity activity;
    List<Note> notes = new ArrayList<>();
    DatabaseHandler db;

    public NoteListAdapter(AppCompatActivity activity, DatabaseHandler db) {
        this.activity = activity;
        this.db = db;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderNoteCards(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card_layout_with_delete, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Note note = notes.get(position);

        if (holder instanceof ViewHolderNoteCards) {
            binderHelper.bind(((ViewHolderNoteCards) holder).swipeLayout, note.getId() + "");
            ((ViewHolderNoteCards) holder).bind(note);
        }

    }

    @Override
    public int getItemCount() {
        if (notes != null) {
            return notes.size();
        } else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void changeStatusOfView(View view, int value) {
        if (value == 1)
            view.setSelected(true);
        else
            view.setSelected(false);
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolderNoteCards extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView, horizontal_line;
        ImageView star_button, heart_button;
        TextView note_title, note_description, last_updated_time;
        private SwipeRevealLayout swipeLayout;
        private View deleteLayout;
        FrameLayout main_layout;

        private ViewHolderNoteCards(View itemView) {
            super(itemView);
            this.itemView = itemView;
            star_button = itemView.findViewById(R.id.star_button);
            star_button.setOnClickListener(this);
            heart_button = itemView.findViewById(R.id.heart_button);
            heart_button.setOnClickListener(this);
            horizontal_line = itemView.findViewById(R.id.horizontal_line);
            note_title = itemView.findViewById(R.id.note_title);
            note_description = itemView.findViewById(R.id.note_description);
            last_updated_time = itemView.findViewById(R.id.last_updated_time);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            deleteLayout = itemView.findViewById(R.id.delete_layout);
            main_layout = itemView.findViewById(R.id.main_layout);
            main_layout.setOnClickListener(this);
        }

        public void bind(final Note note) {
            deleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (db.deleteNote(note.getId())) {
                        notes.remove(position);  // perform delete operation
                        notifyItemRemoved(position);
                    } else {
                        GeneralUtil.showMessage(activity.getString(R.string.text_unable_to_perform_this_opeartion_right_now));
                    }
                }
            });
            note_title.setText(note.getTitle());
            note_description.setText(note.getDescription());
            last_updated_time.setText(GeneralUtil.getFormattedTime(note.getLast_updated_time()));
            changeStatusOfView(heart_button, note.getIs_hearted());
            changeStatusOfView(star_button, note.getIs_star());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Note note = notes.get(position);
            switch (view.getId()) {
                case R.id.star_button:
                    if (db.updateStarStatus(note.getId(), view.isSelected() ? 0 : 1)) {
                        notes.get(position).setIs_star(view.isSelected() ? 0 : 1);
                        changeStatusOfView(view, view.isSelected() ? 0 : 1);
                    } else {
                        GeneralUtil.showMessage(activity.getString(R.string.text_unable_to_perform_this_opeartion_right_now));
                    }
                    break;
                case R.id.heart_button:
                    if (db.updateHeartStatus(note.getId(), view.isSelected() ? 0 : 1)) {
                        notes.get(position).setIs_hearted(view.isSelected() ? 0 : 1);
                        changeStatusOfView(view, view.isSelected() ? 0 : 1);
                    } else {
                        GeneralUtil.showMessage(activity.getString(R.string.text_unable_to_perform_this_opeartion_right_now));
                    }
                    break;
            }
            notifyDataSetChanged();
        }
    }
}
