package com.rip.roomies.activities.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.controllers.GoodController;
import com.rip.roomies.functions.ListAllGoodsFunction;
import com.rip.roomies.models.Good;
import com.rip.roomies.util.DisplayStrings;

import java.util.logging.Logger;

/**
 * Created by johndoney on 5/30/16.
 */
public class ListAllGoods extends GenericActivity implements ListAllGoodsFunction {
	private static final Logger log = Logger.getLogger(ListAllGoods.class.getName());
	GoodContainer gc;

	@Override
	public void onCreate(Bundle savedInstance) {
		Button addGood;

		super.onCreate(savedInstance);
		setContentView(R.layout.activity_list_all_duties);

		/* Linking xml objects to java */
		gc = (GoodContainer) findViewById(R.id.good_list);
		addGood = (Button) findViewById(R.id.good_addbtn);

		final Activity self = this;

		GoodController.getController().listAllGoods(this);

		addGood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(new Intent(self, CreateGood.class), GoodView.ADD_GOOD);
			}
		});
	}

	/** @inheritDoc **/
	@Override
	public void listAllGoodsFail() {
		Toast.makeText(this, DisplayStrings.LIST_ALL_GOODS_FAIL, Toast.LENGTH_LONG).show();
	}

	/** @inheritDoc **/
	@Override
	public void listAllGoodsSuccess(Good[] goods) {
		for (Good g : goods) {
			gc.addGood(g);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GoodView.EDIT_GOOD && resultCode == RESULT_OK) {
			Good good = data.getExtras().getParcelable("Good");
			boolean toRemove = data.getExtras().getBoolean("toRemove");

			if (toRemove) {
				gc.removeGood(good);
			}
			else {
				gc.modifyGood(good);
			}
		}
		else if (requestCode == GoodView.VIEW_GOOD && resultCode == RESULT_OK) {
			Good good = data.getExtras().getParcelable("Good");
			gc.modifyGood(good);
		}
		else if (requestCode == GoodView.ADD_GOOD && resultCode == RESULT_OK) {
			Good good = data.getExtras().getParcelable("Good");
			gc.addGood(good);
		}
	}
}
