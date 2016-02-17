package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import barqsoft.footballscores.service.ScoreService;

/**
 * Created by Pablo on 9/02/16.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "toastAction";
    public static final String EXTRA_ITEM = "extraItemAction";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context, MyAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            // Sets up the intent that points to the StackViewService that will
            // provide the views for this collection.
            Intent intent = new Intent(context, ScoreService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            // When intents are compared, the extras are ignored, so we need to embed the extras
            // into the data so that the extras will not be ignored.
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            remoteViews.setRemoteAdapter(widgetId, R.id.scoreListView, intent);
            remoteViews.setEmptyView(R.id.emptyView, R.id.scoreListView);

            Intent toastIntent = new Intent(context, MyAppWidgetProvider.class);
            toastIntent.setAction(MyAppWidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.scoreListView, toastPendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex + " : " + appWidgetId, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
