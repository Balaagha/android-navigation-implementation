package com.example.androidnavigationimplementation

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.example.androidnavigationimplementation.welcome.UserInfoModel


class AuthorizeMeWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.authorizeme_appwidget
        )

        /*val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)*/

        val args = Bundle()
        args.putString("param1", "Test User")
        args.putString("param2", "Handled Link From Widget")
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.welcomeFragment)
            .setArguments(args)
            .createPendingIntent()

        remoteViews.setOnClickPendingIntent(R.id.authorizeme_button, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}