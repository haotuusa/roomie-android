package com.rip.roomies.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.duties.CreateDuty;
import com.rip.roomies.activities.duties.ListAllDuties;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Home extends GenericActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TextView dutiesScreen;

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		dutiesScreen = (TextView) findViewById(R.id.home_overallduties);

		final Activity self = this;

		dutiesScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, ListAllDuties.class));
			}
		});
	}

	@Override
	public void onBackPressed() {
		// This does nothing
	}
}
