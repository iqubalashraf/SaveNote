package note.save.app.savenote.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import note.save.app.savenote.R;
import note.save.app.savenote.dataModel.Note;

/**
 * Created by ashrafiqubal on 21/01/18.
 */

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    AppCompatActivity activity;
    List<Note> notes = new ArrayList<>();

    public NoteListAdapter(AppCompatActivity activity) {

        this.activity = activity;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderNoteCards(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card_layout, parent, false));
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Note note = notes.get(position);
        if(holder instanceof ViewHolderNoteCards){
            ((ViewHolderNoteCards) holder).note_title.setText(note.getTitle());
            ((ViewHolderNoteCards) holder).note_description.setText(note.getDescription());
            if(note.getIs_hearted() == 1){
                changeStatusOfView(((ViewHolderNoteCards) holder).heart_button, true);
            }else {
                changeStatusOfView(((ViewHolderNoteCards) holder).heart_button, false);
            }
            if(note.getIs_star() == 1){
                changeStatusOfView(((ViewHolderNoteCards) holder).star_button, true);
            }else {
                changeStatusOfView(((ViewHolderNoteCards) holder).star_button, false);
            }
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

    private class ViewHolderNoteCards extends RecyclerView.ViewHolder {
        View itemView, horizontal_line;
        ImageView star_button, heart_button;
        TextView note_title, note_description, last_updated_time;

        private ViewHolderNoteCards(View itemView) {
            super(itemView);
            this.itemView = itemView;
            star_button = itemView.findViewById(R.id.star_button);
            heart_button = itemView.findViewById(R.id.heart_button);
            horizontal_line = itemView.findViewById(R.id.horizontal_line);
            note_title = itemView.findViewById(R.id.note_title);
            note_description = itemView.findViewById(R.id.note_description);
            last_updated_time = itemView.findViewById(R.id.last_updated_time);

        }
    }
    private void changeStatusOfView(View view, boolean value){
        view.setSelected(value);
    }
}
