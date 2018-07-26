package malkov.name.gygtest.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import malkov.name.gygtest.R;
import malkov.name.gygtest.db.model.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Review> list = Collections.emptyList();
    final Context context;

    public ReviewsAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void setData(List<Review> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView message;
        private final TextView date;
        private final TextView author;
        private final RatingBar rating;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            author = itemView.findViewById(R.id.author);
            rating = itemView.findViewById(R.id.rating);
        }

        private void bindTextField(final TextView t, final String text) {
            if (TextUtils.isEmpty(text)) {
                t.setVisibility(View.GONE);
            } else {
                t.setVisibility(View.VISIBLE);
                t.setText(text);
            }
        }

        void bind(Review r) {
            bindTextField(title, r.getTitle());
            bindTextField(message, r.getMessage());
            final String authorStr = r.getAuthor() == null ? null : itemView
                    .getContext().getString(R.string.fmt_reviewed_by, r.getAuthor());
            bindTextField(author, authorStr);
            bindTextField(date, r.getPrettyDate());
            rating.setRating(r.getRating());
        }
    }
}
