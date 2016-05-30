package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.models.DutyLog;
import com.rip.roomies.util.InfoStrings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * Created by Tony Phan on 5/29/2016.
 */
public class DutyLogView extends LinearLayout {

	private static final Logger log = Logger.getLogger(DutyLogView.class.getName());

	private DutyLog dutyLog;

	/**
	 * @see android.view.View( Context )
	 */
	public DutyLogView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public DutyLogView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public DutyLogView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Duty object that this class represents
	 *
	 * @return The Duty object in question
	 */
	public DutyLog getDutyLog() {
		return dutyLog;
	}

	/**
	 * Set the duty of this object whose information this view will display
	 *
	 * @param dutyLog The duty object to display
	 */
	public void setDutyLog(DutyLog dutyLog) {
		this.dutyLog = dutyLog;
		setupLayout();
	}

	/**
	 * Sets up the layout for this DutyView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, DutyLogView.class.getSimpleName()));

		setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);

		TextView name = new TextView(getContext());
		TextView description = new TextView(getContext());
		TextView assignee = new TextView(getContext());
		TextView completeDate = new TextView(getContext());
		LinearLayout innerLayout = new LinearLayout(getContext());

		innerLayout.setOrientation(LinearLayout.VERTICAL);

		name.setText(dutyLog.getName());
		description.setText(dutyLog.getDescription());

		String fullName = dutyLog.getAssignee().getFirstName() + " " + dutyLog.getAssignee().getLastName();
		assignee.setText(fullName);

		DateFormat compDate = new SimpleDateFormat("MM/dd/yyyy");
		String text = compDate.format(dutyLog.getCompletion());
		completeDate.setText(text);

		innerLayout.addView(name);
		innerLayout.addView(description);
		innerLayout.addView(assignee);
		innerLayout.addView(completeDate);

		addView(innerLayout);
	}
}