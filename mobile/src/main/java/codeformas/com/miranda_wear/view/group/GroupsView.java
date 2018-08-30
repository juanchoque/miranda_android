package codeformas.com.miranda_wear.view.group;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codeformas.com.miranda_wear.R;
import codeformas.com.miranda_wear.model.Group;
import codeformas.com.miranda_wear.presenter.GroupsPresenter;
import codeformas.com.miranda_wear.presenter.IGroupsPresenter;
import codeformas.com.miranda_wear.view.adapter.GroupsAdapter;

public class GroupsView extends Fragment implements IGroupsView {
    private OnFragmentInteractionListener mListener;
    private View view;

    private GroupsAdapter groupsAdapter = null;
    private IGroupsPresenter iGroupsPresenter;

    @BindView(R.id.list_groups)
    RecyclerView recycler;

    public GroupsView() {
        // Required empty public constructor
    }

    public static GroupsView newInstance(String param1, String param2) {
        GroupsView fragment = new GroupsView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_groups, container, false);

        ButterKnife.bind(this, this.view);

        //create intance of presenter
        this.iGroupsPresenter = new GroupsPresenter(getActivity(), this);
        this.iGroupsPresenter.loadGroups(null);

        return this.view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void loadGroups(List<Group> listGroups) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.groupsAdapter = new GroupsAdapter(getActivity(), listGroups, this);
        this.recycler.setLayoutManager(linearLayoutManager);
        this.recycler.setAdapter(this.groupsAdapter);
        this.recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
