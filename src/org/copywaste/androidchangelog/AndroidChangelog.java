package org.copywaste.androidchangelog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AndroidChangelog {
	private Activity activity ;
	private String versionPrefName = "AndroidChangelogVersionCode";
	private String title = "Changelog";
	private String content = "";
	private int ourAnimation =  android.R.anim.fade_in;
	private ViewGroup contentView;
	
	public AndroidChangelog(Activity activity, String changelogContent) {
		this.activity = activity;
		this.setContent(changelogContent);
	}
	
	public void setVersionPrefName(String name) {
		this.versionPrefName = name;
	}
	
	public String getVersionPrefName() {
		return versionPrefName;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContentView(ViewGroup view) {
		this.contentView = view;
	}
	
	public ViewGroup getContentView() {
		return contentView;
	}
	
	/**
	 * Reset the stored version to 0. Handy to put in when debugging to always see this screen first
	 */
	public void resetVersion() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		prefs.edit().putInt(versionPrefName, 0).commit();
	}
	
	public void init() {
		PackageInfo pInfo;
		try {
			// Get the version of the app
			pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
			int version = pInfo.versionCode;
			
			// Get the stored version of the app
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
			
			// If the current version > stored version: Show the screen
			if (version > prefs.getInt(versionPrefName, 0)) {
				// Save the current version so we won't show the screen again next time
				prefs.edit().putInt(versionPrefName, version).commit();
				
				// Create the screen
				ViewGroup rootview = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
				if (contentView != null) {
					rootview.addView(contentView);
				} else { // Reasonable default view
					LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
					LinearLayout sv = (LinearLayout) inflater.inflate(org.copywaste.androidchangelog.R.layout.changelog_view, null);
					TextView titleview = (TextView) sv.findViewById(org.copywaste.androidchangelog.R.id.changelog_title);
					TextView contentview = (TextView) sv.findViewById(org.copywaste.androidchangelog.R.id.changelog_content);
					titleview.setText(getTitle());
					contentview.setText(Html.fromHtml(getContent()));
					
					Button quitbutton = (Button) sv.findViewById(org.copywaste.androidchangelog.R.id.changelog_quitbutton);
					quitbutton.setTag(sv);
					quitbutton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							LinearLayout sv = (LinearLayout) v.getTag();
							sv.setVisibility(View.GONE);
						}
					});
					
					// Setting a fade-in on our scrollview
					sv.setAnimation(AnimationUtils.loadAnimation(activity, ourAnimation));
					
					// Add the whole bunch to the root-View
					rootview.addView(sv);
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
