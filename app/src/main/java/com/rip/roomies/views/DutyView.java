package com.rip.roomies.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.activities.duties.ModifyDuty;
import com.rip.roomies.activities.duties.ViewDuty;
import com.rip.roomies.models.Duty;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class is a displayable view that represents a Duty object. It will display
 * any necessary information as well as style once implemented.
 */
public class DutyView extends LinearLayout {
	private static final Logger log = Logger.getLogger(DutyView.class.getName());
	public static final int EDIT_DUTY = 1;
	public static final int VIEW_DUTY = 2;
	public static final int ADD_DUTY = 3;

	private Duty duty;

	/**
	 * @see android.view.View(Context)
	 */
	public DutyView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public DutyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public DutyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Duty object that this class represents
	 *
	 * @return The Duty object in question
	 */
	public Duty getDuty() {
		return duty;
	}

	/**
	 * Set the duty of this object whose information this view will display
	 *
	 * @param duty The duty object to display
	 */
	public void setDuty(Duty duty) {
		this.duty = duty;
		setupLayout();
	}

	/**
	 * Sets up the layout for this DutyView.
	 */
	private void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, DutyView.class.getSimpleName()));

		LinearLayout.LayoutParams w = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setLayoutParams(w);
		setOrientation(LinearLayout.VERTICAL);

/*
		setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		setOrientation(LinearLayout.HORIZONTAL);
*/
		TextView name = new TextView(getContext());
		TextView description = new TextView(getContext());
		TextView assignee = new TextView(getContext());
		Button viewBtn = new Button(getContext());
		Button editBtn = new Button(getContext());
		LinearLayout innerLayout = new LinearLayout(getContext());
		innerLayout.setLayoutParams(new LayoutParams(
				0,
				LayoutParams.MATCH_PARENT, 1.0f));

		name.setTextColor(Color.parseColor("#007EE5"));
		description.setTextColor(Color.parseColor("#007EE5"));
		assignee.setTextColor(Color.parseColor("#007EE5"));

		innerLayout.setOrientation(LinearLayout.VERTICAL);
		innerLayout.setPadding(50, 50, 50, 50);

		name.setText(duty.getName());
		description.setText(duty.getDescription());
		String fullName = duty.getAssignee().getFirstName() + " " + duty.getAssignee().getLastName();
		assignee.setText(fullName);

		innerLayout.addView(name);
		innerLayout.addView(description);
		innerLayout.addView(assignee);

		viewBtn.setText("View");
		LinearLayout.LayoutParams v = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		v.setMargins(0, 50, 0, 50);
		viewBtn.setLayoutParams(v);
/*		viewBtn.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1.0f));*/
		viewBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ViewDuty.class.getSimpleName()));

				Intent i = new Intent(getContext(), ViewDuty.class);
				i.putExtra("Duty", duty);
				((Activity) getContext()).startActivityForResult(i, VIEW_DUTY);
			}
		});



		editBtn.setText("Edit");
/*		editBtn.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1.0f)); */
		LinearLayout.LayoutParams p = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		p.gravity = Gravity.CENTER_VERTICAL;
		p.setMargins(0, 50, 0, 50);
		editBtn.setLayoutParams(p);
		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ModifyDuty.class.getSimpleName()));

				Intent i = new Intent(getContext(), ModifyDuty.class);
				i.putExtra("Duty", duty);
				((Activity) getContext()).startActivityForResult(i, EDIT_DUTY);
			}
		});

		LinearLayout hline = new LinearLayout(getContext());
		hline.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
		hline.setBackgroundColor(Color.BLACK);

		LinearLayout outerLayout = new LinearLayout(getContext());
		outerLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		outerLayout.setOrientation(LinearLayout.HORIZONTAL);

		outerLayout.addView(innerLayout);
		outerLayout.addView(viewBtn);
		outerLayout.addView(editBtn);

		addView(outerLayout);
		addView(hline);
	}
}
