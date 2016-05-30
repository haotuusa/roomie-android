package com.rip.roomies.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rip.roomies.R;
import com.rip.roomies.activities.goods.ModifyGood;
import com.rip.roomies.activities.goods.ViewGood;
import com.rip.roomies.models.Good;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * This class is a displayable view that represents a Good object. It will display
 * any necessary information as well as style once implemented.
 */
public class GoodView extends TaskView {
	private static final Logger log = Logger.getLogger(GoodView.class.getName());

	public static final int EDIT_GOOD = 1;
	public static final int VIEW_GOOD = 2;
	public static final int ADD_GOOD = 3;

	private Good good;

	/**
	 * @see android.view.View(Context)
	 */
	public GoodView(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet )
	 */
	public GoodView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public GoodView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Get the Good object that this class represents
	 *
	 * @return The Good object in question
	 */
	public Good getGood() {
		return good;
	}

	/**
	 * Set the good of this object whose information this view will display
	 *
	 * @param good The good object to display
	 */
	public void setGood(Good good) {
		this.good = good;
		setupLayout();
	}

	/**
	 * Sets up the layout for this GoodView.
	 */
	protected void setupLayout() {
		log.info(String.format(InfoStrings.VIEW_SETUP, GoodView.class.getSimpleName()));

		removeAllViews();

		LinearLayout.LayoutParams w = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
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
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f));

		name.setTextColor(Color.parseColor("#007EE5"));
		description.setTextColor(Color.parseColor("#007EE5"));
		assignee.setTextColor(Color.parseColor("#007EE5"));

		innerLayout.setOrientation(LinearLayout.VERTICAL);
		innerLayout.setPadding(50, 50, 50, 50);

		name.setText(good.getName());
		description.setText(good.getDescription());
		String fullName = good.getAssignee().getFirstName() + " " + good.getAssignee().getLastName();
		assignee.setText(fullName);

		innerLayout.addView(name);
		innerLayout.addView(description);
		innerLayout.addView(assignee);

		viewBtn.setText("View");
		viewBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
		viewBtn.setBackground(getResources().getDrawable(R.drawable.rec_border));
		viewBtn.setPadding(50, 50, 50 , 50);
		LinearLayout.LayoutParams v = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		v.gravity = Gravity.CENTER_VERTICAL;
		v.setMargins(10, 50, 10, 50);
		viewBtn.setLayoutParams(v);
/*		viewBtn.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1.0f));*/
		viewBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ViewGood.class.getSimpleName()));

				Intent i = new Intent(getContext(), ViewGood.class);
				i.putExtra("Good", good);
				((Activity) getContext()).startActivityForResult(i, VIEW_GOOD);
			}
		});



		editBtn.setText("Edit");
		editBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
		editBtn.setBackground(getResources().getDrawable(R.drawable.rec_border));
		editBtn.setPadding(50, 50, 50 , 50);
/*		editBtn.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1.0f)); */
		LinearLayout.LayoutParams p = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		p.gravity = Gravity.CENTER_VERTICAL;
		p.setMargins(10, 50, 10, 50);
		editBtn.setLayoutParams(p);
		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
						ModifyGood.class.getSimpleName()));

				Intent i = new Intent(getContext(), ModifyGood.class);
				i.putExtra("Good", good);
				((Activity) getContext()).startActivityForResult(i, EDIT_GOOD);
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