package com.example.studioghibliapi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;

public class Geolocalizacao extends AppWidgetProvider{
    private Button btnwidget;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId: appWidgetIds){
            RemoteViews view = new RemoteViews(context.getPackageName(), R.id.btnwidget);
            /*Intent intent = new Intent(context.getApplicationContext(), Local.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.geolocation);
            views.setOnClickPendingIntent(R.id.btnwidget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);*/

        }
    }
}
