package com.rip.roomies.activities.duties;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.events.duties.AddRotationListener;
import com.rip.roomies.events.duties.CreateDutyListener;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.views.UserContainer;

import java.util.logging.Logger;

/**
 * Created by Kanurame on 5/19/2016.
 */
public class CreateDuty extends GenericActivity {
	private static final Logger log = Logger.getLogger(CreateDuty.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Button addDuty;
		Button addUser;
		EditText dutyName;
		EditText desc;
		UserContainer users;
		Spinner allUsers;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_duty);

		/* Linking xml objects to java */
		dutyName = (EditText) findViewById(R.id.duty_name);
		desc = (EditText) findViewById(R.id.description);
		allUsers = (Spinner) findViewById(R.id.group_users_spinner);
		addUser = (Button) findViewById(R.id.add_user_btn);
		users = (UserContainer) findViewById(R.id.users_container);
		addDuty = (Button) findViewById(R.id.add_duty_btn);

		ArrayAdapter<User> userArrayAdapter =
				new ArrayAdapter<User>(this, android.R.layout.simple_spinner_dropdown_item);

		for(User u : Group.getActiveGroup().getMembers()) {
			userArrayAdapter.add(u);
		}

		allUsers.setAdapter(userArrayAdapter);

		addDuty.setOnClickListener(new CreateDutyListener(this, dutyName, desc, users));
		addUser.setOnClickListener(new AddRotationListener(this, users, allUsers));

	}

	@Override
	public void onBackPressed() {
		// This is supposed to do nothing
	}
}
