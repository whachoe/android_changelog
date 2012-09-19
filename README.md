Android Changelog
=================

Show a simple changelog-screen whenever a user updates your app.   
   
   
# Usage  
* Set up this project and compile as Library Project   
* Add it to your Android Project (http://developer.android.com/tools/projects/projects-eclipse.html)   
* In the onCreate of your main-activity, add this to the bottom:   

<pre>
       String changelog = getString(R.string.your_changelog_text); // Make sure this one is in values/strings.xml
       AndroidChangelog cl = new AndroidChangelog(this, changelog);     
       // Only use next line when debugging the changelog:   
       cl.resetVersion();   
       cl.init();   
</pre>

* Or if you rather supply your own view:  
<pre>
       AndroidChangelog cl = new AndroidChangelog(this, "");

       LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
       LinearLayout clv = (LinearLayout) inflater.inflate(R.layout.your_own_changelog_view, null);
       cl.setContentView(clv);
       cl.init();
</pre>

* If you want to make a menu-item for it:
<pre>
       private static final int MENU_CHANGELOG = 1;
       public boolean onCreateOptionsMenu(Menu menu) 
       {
	 	    super.onCreateOptionsMenu(menu);
		    // Code for Changelog
		    menu.add(2, MENU_CHANGELOG, 3, "Changelog");
		
		    return true;
	    }

	    @Override
	    public boolean onMenuItemSelected(int featureId, MenuItem item) 
	    {
		    boolean result = super.onMenuItemSelected(featureId, item);

		    switch(item.getItemId()) {
		    // Code for Changelog	
		    case MENU_CHANGELOG: 
			    AndroidChangelog.getInstance().showChangelog();
			    return(true);
		    }
		
		    return result;
	    }
</pre>

# How does it do it?
It keeps the version for which you last viewed the changelog in your app's SharedPreferences. Each time the app starts, the current version gets checked against that preference and if the current version > preference-version, the changelog-screen gets shown    
The code is actually very simple.   
