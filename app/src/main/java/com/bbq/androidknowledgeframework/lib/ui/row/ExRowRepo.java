package com.bbq.androidknowledgeframework.lib.ui.row;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 说明
 * Created by bangbang.qiu on 2021/1/27.
 */
public final class ExRowRepo {
    private final List<ExRowBaseView> mExRows = new ArrayList();
    private final ConcurrentHashMap<String, List<ExRowBaseView>> mRowsMaps = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, ExRowBaseView> mRowsViewHolderMaps = new ConcurrentHashMap();

    public ExRowRepo() {
    }

    public ExRowRepo clone() {
        ExRowRepo newRepo = new ExRowRepo();
        newRepo.mExRows.addAll(this.mExRows);

        Iterator var2;
        String key;
        ArrayList views;
        for (var2 = this.mRowsMaps.keySet().iterator(); var2.hasNext(); newRepo.mRowsMaps.put(key, views)) {
            key = (String) var2.next();
            List<ExRowBaseView> list = (List) this.mRowsMaps.get(key);
            views = new ArrayList();
            if (list != null) {
                views.addAll(list);
            }
        }

        var2 = this.mRowsViewHolderMaps.keySet().iterator();

        while (var2.hasNext()) {
            Integer key1 = (Integer) var2.next();
            ExRowBaseView view = this.mRowsViewHolderMaps.get(key1);
            newRepo.mRowsViewHolderMaps.put(key1, view);
        }

        return newRepo;
    }

    public int getCount() {
        return this.mExRows.size();
    }

    public boolean isEmpty() {
        return this.mExRows.isEmpty();
    }

    public ExRowBaseView getRow(int index) {
        return 0 <= index && index < this.getCount() ? this.mExRows.get(index) : null;
    }

    public ExRowBaseView getLastRow() {
        int index = this.getCount() - 1;
        return index >= 0 ? this.getRow(index) : null;
    }

    public ExRowBaseView getLastRow(int indexToLastOne) {
        int index = this.getCount() - 1 - indexToLastOne;
        return index >= 0 ? this.getRow(index) : null;
    }

    public List<ExRowBaseView> getRowList(String id) {
        return this.mRowsMaps.containsKey(id) ? (List) this.mRowsMaps.get(id) : null;
    }

    public ExRowBaseView getViewHolder(int viewType) {
        return this.mRowsViewHolderMaps.get(viewType);
    }

    public int add(int location, ExRowBaseView row) {
        return this.add((String) null, location, row);
    }

    public int add(String id, int location, ExRowBaseView row) {
        this.putRowMap(id, row);
        this.putRowViewHolderMap(row);
        if (row != null) {
            this.mExRows.add(location, row);
        }

        return location;
    }

    public void addAll(int location, List<ExRowBaseView> rows) {
        this.addAll((String) null, location, rows);
    }

    public void addAll(String id, int location, List<ExRowBaseView> rows) {
        if (rows != null) {
            int size = rows.size();

            for (int i = size - 1; i >= 0; --i) {
                this.add(id, location, (ExRowBaseView) rows.get(i));
            }

        }
    }

    public void addFirst(ExRowBaseView row) {
        this.addFirst((String) null, row);
    }

    public void addFirst(String id, ExRowBaseView row) {
        this.add(id, 0, row);
    }

    public void addAllFirst(List<ExRowBaseView> rows) {
        this.addAllFirst((String) null, rows);
    }

    public void addAllFirst(String id, List<ExRowBaseView> rows) {
        if (rows != null) {
            int size = rows.size();

            for (int i = size - 1; i >= 0; --i) {
                this.addFirst(id, (ExRowBaseView) rows.get(i));
            }

        }
    }

    public int addLast(ExRowBaseView row) {
        return this.addLast((String) null, row);
    }

    public int addLast(String id, ExRowBaseView row) {
        this.putRowMap(id, row);
        this.putRowViewHolderMap(row);
        int position = -1;
        if (row != null) {
            this.mExRows.add(row);
            position = this.mExRows.size() - 1;
        }

        return position;
    }

    public void addAllLast(List<ExRowBaseView> rows) {
        this.addAllLast((String) null, rows);
    }

    public void addAllLast(String id, List<ExRowBaseView> rows) {
        if (rows != null) {
            int size = rows.size();

            for (int i = 0; i < size; ++i) {
                this.addLast(id, (ExRowBaseView) rows.get(i));
            }

        }
    }

    public int removeRowAt(int index) {
        ExRowBaseView exRow = (ExRowBaseView) this.mExRows.get(index);
        this.remove(exRow);
        return index;
    }

    public int removeLastRow() {
        int index = this.getCount() - 1;
        if (index >= 0) {
            this.removeRowAt(index);
        }

        return index;
    }

    public int remove(ExRowBaseView row) {
        if (row == null) {
            return -1;
        } else {
            int index = this.mExRows.indexOf(row);
            this.removeRowMap(row);
            this.mExRows.remove(row);
            return index;
        }
    }

    public int remove(String id) {
        if (id != null && this.mRowsMaps.containsKey(id)) {
            List<ExRowBaseView> list = (List) this.mRowsMaps.get(id);
            int index = this.mExRows.size();

            ExRowBaseView exRow;
            for (Iterator var4 = list.iterator(); var4.hasNext(); this.mExRows.remove(exRow)) {
                exRow = (ExRowBaseView) var4.next();
                int delIndex = this.mExRows.indexOf(exRow);
                if (index > delIndex) {
                    index = delIndex;
                }
            }

            this.mRowsMaps.remove(id);
            return index;
        } else {
            return -1;
        }
    }

    public void clear() {
        this.mExRows.clear();
        this.mRowsMaps.clear();
        this.mRowsViewHolderMaps.clear();
    }

    private void putRowMap(String id, ExRowBaseView row) {
        if (id != null && row != null) {
            List<ExRowBaseView> list = new ArrayList();
            if (this.mRowsMaps.containsKey(id)) {
                list = this.mRowsMaps.get(id);
            }

            list.add(row);
            this.mRowsMaps.put(id, list);
        }
    }

    private void removeRowMap(ExRowBaseView row) {
        if (row != null) {
            Iterator var2 = this.mRowsMaps.values().iterator();

            while (var2.hasNext()) {
                List<ExRowBaseView> list = (List) var2.next();
                list.remove(row);
            }

        }
    }

    private void putRowViewHolderMap(ExRowBaseView row) {
        if (row != null && row instanceof ExRowBaseView) {
            ExRowBaseView recyclerViewRow = row;
            this.mRowsViewHolderMaps.putIfAbsent(recyclerViewRow.getViewType(), recyclerViewRow);
        }
    }

    public boolean checkViewType(int viewType) {
        return this.mRowsViewHolderMaps.containsKey(viewType);
    }

    public int indexOfRow(ExRowBaseView row) {
        return row == null ? -1 : this.mExRows.indexOf(row);
    }
}