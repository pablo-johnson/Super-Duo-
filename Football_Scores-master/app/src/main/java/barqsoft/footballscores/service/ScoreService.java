package barqsoft.footballscores.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Score;

/**
 * Created by Pablo on 10/02/16.
 */
public class ScoreService extends RemoteViewsService {

    /**
     * To be implemented by the derived service to generate appropriate factories for
     * the data.
     *
     * @param intent
     */
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScoreRemoteViewsFactory(this.getApplicationContext());
    }

    class ScoreRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private List<Score> scoreItems = new ArrayList<>();
        private Context mContext;

        public ScoreRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        /**
         * Called when your factory is first constructed. The same factory may be shared across
         * multiple RemoteViewAdapters depending on the intent passed.
         */
        @Override
        public void onCreate() {
            updateElements();
        }

        /**
         * Called when notifyDataSetChanged() is triggered on the remote adapter. This allows a
         * RemoteViewsFactory to respond to data changes by updating any internal references.
         * <p/>
         * Note: expensive tasks can be safely performed synchronously within this method. In the
         * interim, the old data will be displayed within the widget.
         *
         * @see AppWidgetManager#notifyAppWidgetViewDataChanged(int[], int)
         */
        @Override
        public void onDataSetChanged() {
            updateElements();
        }

        /**
         * Called when the last RemoteViewsAdapter that is associated with this factory is
         * unbound.
         */
        @Override
        public void onDestroy() {
            scoreItems.clear();
        }

        @Override
        public int getCount() {
            return scoreItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Score score = scoreItems.get(position);
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.scores_list_item);
            rv.setTextViewText(R.id.home_name, score.getHomeName());
            rv.setTextViewText(R.id.away_name, score.getAwayName());
            rv.setTextViewText(R.id.data_textview, score.getDate());
            rv.setTextViewText(R.id.score_textview, score.getScore());
            rv.setImageViewResource(R.id.home_crest, score.getHomeCrestImageResource());
            rv.setImageViewResource(R.id.away_crest, score.getAwayCrestImageResource());
            rv.setContentDescription(R.id.home_crest, score.getHomeName());
            rv.setContentDescription(R.id.away_crest, score.getAwayName());


            Bundle extras = new Bundle();
            //extras.putInt(ScoresWidgetProvider.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.container, fillInIntent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        private void updateElements() {
            MyFetchService myFetchService = new MyFetchService();
            myFetchService.getData();
            Cursor cursor = mContext.getContentResolver().query(DatabaseContract.BASE_CONTENT_URI,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                scoreItems.add(new Score(cursor));
            }
            cursor.close();
        }
    }
}
