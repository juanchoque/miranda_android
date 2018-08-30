package codeformas.com.miranda_wear.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import codeformas.com.miranda_wear.model.Account;
import codeformas.com.miranda_wear.model.Group;
import codeformas.com.miranda_wear.view.group.IGroupsView;

public class GroupsPresenter implements IGroupsPresenter {
    private Context context;
    private IGroupsView iGroupsView;

    public GroupsPresenter(Context context, IGroupsView iGroupsView) {
        this.context = context;
        this.iGroupsView = iGroupsView;
    }

    @Override
    public void loadGroups(Account account) {
        List<Group> listGroups = new ArrayList<Group>();
        Group group = new Group();
        group.setName("Nombre 1");
        group.setDescription("Descripcion 1");
        listGroups.add(group);

        Group group1 = new Group();
        group1.setName("Nombre 2");
        group1.setDescription("Descripcion 2");
        listGroups.add(group1);

        Group group2 = new Group();
        group2.setName("Nombre 3");
        group2.setDescription("Descripcion 3");
        listGroups.add(group2);

        this.iGroupsView.loadGroups(listGroups);
    }
}
