package com.rip.roomies.activities.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.DutyController;
import com.rip.roomies.functions.ListMyTasksFunction;
import com.rip.roomies.models.Duty;
import com.rip.roomies.models.Task;
import com.rip.roomies.util.DisplayStrings;
import com.rip.roomies.views.DutyContainer;
import com.rip.roomies.views.DutyView;
import com.rip.roomies.views.TaskContainer;

/**
 * The activity of when the user wishes to view his or her duties.
 */
public class ListMyTasks extends GenericActivity implements ListMyTasksFunction {
	TaskContainer tc;

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_list_my_tasks);

		tc = (TaskContainer) findViewById(R.id.task_list);

		DutyController.getController().listMyTasks(this);
	}

	@Override
	public void listMyTasksFail() {
		Toast.makeText(this, DisplayStrings.LIST_MY_TASKS_FAIL, Toast.LENGTH_LONG).show();
	}

	@Override
	public void listMyTasksSuccess(Task[] tasks) {
		for (Task t : tasks) {
			tc.addTask(t);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DutyView.EDIT_DUTY && resultCode == RESULT_OK) {
			Duty duty = data.getExtras().getParcelable("Duty");
			boolean toRemove = data.getExtras().getBoolean("toRemove");

			if (toRemove) {
				tc.removeTask(duty);
			}
			else {
				tc.modifyTask(duty);
			}
		}
		else if (requestCode == DutyView.VIEW_DUTY && resultCode == RESULT_OK) {
			Duty duty = data.getExtras().getParcelable("Duty");
			tc.removeTask(duty);
		}
	}
}
