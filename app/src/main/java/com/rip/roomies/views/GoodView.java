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
import com.rip.roomies.activities.goods.ListAllGoods;
import com.rip.roomies.activities.goods.ModifyGood;
import com.rip.roomies.events.goods.CompleteGoodListener;
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
	public static final int COMPLETE_GOOD=4;

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
	protected void setupLayout(){
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
		Button completebtn = new Button(getContext());
		Button editBtn = new Button(getContext());
		LinearLayout innerLayout = new LinearLayout(getContext());
		innerLayout.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f));

		name.setTextColor(getResources().getColor(R.color.colorPrimary));
		description.setTextColor(getResources().getColor(R.color.black_overlay));
		assignee.setTextColor(Color.BLACK);

		name.setTextSize(25);
		description.setTextSize(15);
		assignee.setTextSize(15);

		innerLayout.setOrientation(LinearLayout.VERTICAL);
		innerLayout.setPadding(25, 25, 25, 25);

		name.setText(good.getName());
		description.setText(good.getDescription());
		String fullName = good.getAssignee().getFirstName() + " " + good.getAssignee().getLastName();
		assignee.setText(fullName);

		innerLayout.addView(name);
		innerLayout.addView(assignee);
		innerLayout.addView(description);

		editBtn.setText("Edit");
		editBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
		editBtn.setBackground(getResources().getDrawable(R.drawable.rec_border));
		editBtn.setPadding(20, 20, 20 , 20);
/*		editBtn.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1.0f)); */
		LinearLayout.LayoutParams p = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		p.gravity = Gravity.CENTER;
		p.setMargins(10, 20, 10, 20);
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

		completebtn.setText("Complete");
		completebtn.setTextColor(getResources().getColor(R.color.colorPrimary));
		completebtn.setBackground(getResources().getDrawable(R.drawable.rec_border));
		completebtn.setPadding(20, 20, 20 , 20);
		LinearLayout.LayoutParams v = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		v.gravity = Gravity.CENTER;
		v.setMargins(10, 20, 10, 20);
		completebtn.setLayoutParams(v);
/*		viewBtn.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1.0f));*/

		completebtn.setOnClickListener(new CompleteGoodListener((ListAllGoods) getContext(),
				this, good));

		LinearLayout hline = new LinearLayout(getContext());
		hline.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
		hline.setBackgroundColor(Color.BLACK);

		LinearLayout outerLayout = new LinearLayout(getContext());
		outerLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		outerLayout.setOrientation(LinearLayout.HORIZONTAL);

		outerLayout.addView(innerLayout);
		outerLayout.addView(editBtn);
		outerLayout.addView(completebtn);

		addView(outerLayout);
		addView(hline);
	}
}