Android Changelog
=================

Show a simple changelog-screen whenever a user updates your app. 


# Usage
. Set up this project and compile as Library Project
. Add it to your Android Project (http://developer.android.com/tools/projects/projects-eclipse.html)
. In the onCreate of your main-activity, add this to the bottom:
      `AndroidChangelog cl = new AndroidChangelog(this, "The content you want to show");
       // Only use next line when debugging the changelog:
       cl.resetVersion();
       cl.init();`

