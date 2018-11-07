package com.example.nouran.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.nouran.bakingapp.Activities.MainActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

//    public static final String TOAST_ACTION = "com.example.android.bakingapp.TOAST_ACTION";
//    public static final String EXTRA_ITEM = "com.example.android.bakingapp.EXTRA_ITEM";
    private static SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private static final String MY_PREFS_NAME = "MyPrefsFile";


//    @Override
//    public void onReceive(Context context, Intent intent) {
//        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//        if (intent.getAction().equals(TOAST_ACTION)) {
//            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
//            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
//        }
//        super.onReceive(context, intent);
//    }
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // update each of the widgets with the remote adapter
//        for (int appWidgetId : appWidgetIds) {
//            // Here we setup the intent which points to the StackViewService which will
//            // provide the views for this collection.
//            Intent intent = new Intent(context, ListWidgetService.class);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//            // When intents are compared, the extras are ignored, so we need to embed the extras
//            // into the data so that the extras will not be ignored.
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
//            rv.setRemoteAdapter(R.id.widget_lst, intent);
//            // The empty view is displayed when the collection has no items. It should be a sibling
//            // of the collection view.
////            rv.setEmptyView(R.id.widget_lst, R.id.empty_view);
//            // Here we setup the a pending intent template. Individuals items of a collection
//            // cannot setup their own pending intents, instead, the collection as a whole can
//            // setup a pending intent template, and the individual items can set a fillInIntent
//            // to create unique before on an item to item basis.
//            Intent toastIntent = new Intent(context, BakingAppWidget.class);
//            toastIntent.setAction(BakingAppWidget.TOAST_ACTION);
//            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setPendingIntentTemplate(R.id.widget_lst, toastPendingIntent);
//            appWidgetManager.updateAppWidget(appWidgetId, rv);
//        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
//    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        sharedPrefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String namePref = sharedPrefs.getString("name", null);
        String ingredPref = sharedPrefs.getString("Ingredients", null);


        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(appWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        RemoteViews remoteViews;
//        if(width <300)
//            remoteViews = get


        CharSequence widgetText = namePref;
        CharSequence widgetIngredients = ingredPref;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        Intent intent = new Intent(context, MainActivity.class);



        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_txt_view, pendingIntent);

        views.setTextViewText(R.id.widget_txt_view, widgetText);
        views.setTextViewText(R.id.widget_Text_view, widgetIngredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

