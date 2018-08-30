package codeformas.com.miranda_wear.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codeformas.com.miranda_wear.R;
import codeformas.com.miranda_wear.model.Group;
import codeformas.com.miranda_wear.view.group.IGroupsView;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.CardViewHolder> {
    private final Context mainContext;
    private final List<Group> items;
    private IGroupsView iGroupsView;

    private int sw = 0;

    public GroupsAdapter(Context mainContext, List<Group> items, IGroupsView iGroupsView) {
        this.mainContext = mainContext;
        this.items = items;
        this.iGroupsView = iGroupsView;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row_groups, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Group item = items.get(position); //get item from my List<Place>
        holder.itemView.setTag(item); //save items in Tag

        holder.txtTitle.setText(item.getName());
        holder.txtDescription.setText(item.getDescription());
        holder.btnEdit.setTag(item);
        holder.btnDelete.setTag(item);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(items != null){
            size = items.size();
        }
        return size;
    }

    //class static
    public class CardViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_image)
        TextView txtImage;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_description)
        TextView txtDescription;
        @BindView(R.id.btn_edit)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;

        public CardViewHolder(View v){
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
