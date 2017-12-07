package co.com.millennialapps.utils.tools;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import java.util.LinkedList;

import co.com.millennialapps.utils.R;
import co.com.millennialapps.utils.common.Pair;

/**
 * Created by erick on 7/9/2017.
 */

public class SearchBarHandler implements SearchView.OnSuggestionListener {

    private static final String COLUMN_TITLE = "TitleSuggestion";
    private static final String COLUMN_SUBTITLE = "SubtitleSuggestion";
    private final int LIMIT_RECENT_SEARCHES = 2;
    private final int LIMIT_ADDITIONAL_SUGGESTIONS = 10;
    private final SimpleCursorAdapter ADAPTER;
    private final SearchView SEARCH_VIEW;
    private final MenuItem SEARCH_ITEM;
    private Activity activity;
    private LinkedList<Pair<String, String>> suggestionList;
    private boolean useRecentSearches = false;
    private int idDrawer;
    private int colorPrimaryDark;

    public SearchBarHandler(Activity activity, Menu menu, int idAction, int idHint, LinkedList<Pair<String, String>> list, int idDrawer, int colorPrimaryDark) {
        this.activity = activity;
        this.idDrawer = idDrawer;
        this.colorPrimaryDark = colorPrimaryDark;
        if (list == null) {
            suggestionList = new LinkedList<>();
        } else {
            suggestionList = list;
        }
        ADAPTER = new SimpleCursorAdapter(activity,
                R.layout.search_view_suggestion_row,
                null, new String[]{COLUMN_TITLE, COLUMN_SUBTITLE}, new int[]{R.id.txtTitle, R.id.txtSubtitle},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        SEARCH_ITEM = menu.findItem(idAction);
        SEARCH_VIEW = (SearchView) SEARCH_ITEM.getActionView();

        SEARCH_VIEW.setSuggestionsAdapter(ADAPTER);
        SEARCH_VIEW.onActionViewExpanded();
        ((AutoCompleteTextView) SEARCH_VIEW.findViewById(R.id.search_src_text)).setThreshold(1);
        setHint(idHint);
        SEARCH_VIEW.setOnSuggestionListener(this);
        animationChange(activity);
    }

    @Override
    public boolean onSuggestionSelect(int index) {
        Cursor cursor = ADAPTER.getCursor();
        String selectedString = "";
        if (cursor.moveToFirst()) {
            int i = -1;
            do {
                i++;
            } while (i != index && cursor.moveToNext());
            selectedString = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        }
        cursor.close();
        SEARCH_VIEW.setQuery(selectedString, true);
        return true;
    }

    @Override
    public boolean onSuggestionClick(int index) {
        onSuggestionSelect(index);
        return false;
    }

    public void populateAdapter(String query) {
        final MatrixCursor mCursor = new MatrixCursor(new String[]{BaseColumns._ID, COLUMN_TITLE, COLUMN_SUBTITLE});
        int a = Preferences.getSuggestions(activity).size();
        int b = suggestionList.size();
        int c = a + b;
        if (useRecentSearches) {
            int recentSearches = 0;
            for (int i = 0; i < a; i++) {
                if (Preferences.getSuggestions(activity).get(i).toLowerCase().contains(query.toLowerCase())) {
                    mCursor.addRow(new Object[]{i, Preferences.getSuggestions(activity).get(i)});
                    recentSearches++;
                    if (recentSearches == LIMIT_RECENT_SEARCHES) {
                        break;
                    }
                }
            }
        }
        int additionalSuggestions = 0;
        for (int i = a; i < c; i++) {
            if (suggestionList.get(i - a).getFirst().toLowerCase().contains(query.toLowerCase())) {
                mCursor.addRow(new Object[]{i - a, suggestionList.get(i - a).getFirst(),
                        suggestionList.get(i - a).getSecond()});
                additionalSuggestions++;
                if (additionalSuggestions == LIMIT_ADDITIONAL_SUGGESTIONS) {
                    break;
                }
            }
        }
        ADAPTER.changeCursor(mCursor);
    }

    public void addChangeTextListener(SearchView.OnQueryTextListener listener) {
        SEARCH_VIEW.setOnQueryTextListener(listener);
    }

    private void animationChange(final Activity activity) {
        MenuItemCompat.setOnActionExpandListener(SEARCH_ITEM,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        Animations.circleReveal(activity, 3, false, true, colorPrimaryDark, idDrawer);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        Animations.circleReveal(activity, 3, false, false, colorPrimaryDark, idDrawer);
                        return true;
                    }
                });
    }

    public void enableRecentSearches() {
        useRecentSearches = true;
    }

    public void open() {
        if (!SEARCH_ITEM.isActionViewExpanded()) {
            SEARCH_ITEM.expandActionView();
        }
    }

    public void close() {
        if (SEARCH_ITEM.isActionViewExpanded()) {
            SEARCH_ITEM.collapseActionView();
        }
    }

    public MenuItem getSEARCH_ITEM() {
        return SEARCH_ITEM;
    }

    public SearchView getSEARCH_VIEW() {
        return SEARCH_VIEW;
    }

    public void setSuggestionList(LinkedList<Pair<String, String>> list) {
        this.suggestionList = list;
    }

    public void setHint(int idHint) {
        ((AutoCompleteTextView) SEARCH_VIEW.findViewById(R.id.search_src_text)).setHint(idHint);
    }

    public void setIdDrawer(int idDrawer) {
        this.idDrawer = idDrawer;
    }

    public void setColorPrimaryDark(int colorPrimaryDark) {
        this.colorPrimaryDark = colorPrimaryDark;
    }
}