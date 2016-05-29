package com.rip.roomies.activities.duties;

import android.os.Bundle;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.ListMyDutiesFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.views.DutyContainer;

/**
 * The activity of when the user wishes to view his or her duties.
 */
public class ListMyDuties extends GenericActivity implements ListMyDutiesFunction {
	DutyContainer dc;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_list_my_duties);

		dc = (DutyContainer) findViewById(R.id.duty_list);

		DutyController.getController().listMyDuties(this);
	}

	@Override
	public void listMyDutiesFail() {
		Toast.makeText(this, DisplayStrings.LIST_MY_DUTIES_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void listMyDutiesSuccess(Duty[] duties) {
		for (Duty d : duties) {
			dc.addDuty(d);
		}
	}
}
