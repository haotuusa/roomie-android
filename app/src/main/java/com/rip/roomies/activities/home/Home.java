package com.rip.roomies.activities.home;

import android.graphics.Point;
import android.view.Display;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.github.nkzawa.socketio.client.Socket;
import com.rip.roomies.R;
import com.rip.roomies.activities.GenericActivity;
import com.rip.roomies.activities.bills.Bills;
import com.rip.roomies.activities.bulletin.AddBulletin;
import com.rip.roomies.activities.bulletin.ModifyBulletin;
import com.rip.roomies.activities.duties.ListAllDuties;
import com.rip.roomies.activities.duties.ListMyDuties;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Images;
import com.rip.roomies.activities.goods.ListAllGoods;
import com.rip.roomies.controllers.HomeController;
import com.rip.roomies.models.Bulletin;
import com.rip.roomies.models.Bill;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.views.BulletinContainer;
import com.rip.roomies.server.ServerListener;
import com.rip.roomies.server.ServerRequest;

import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Home extends GenericActivity {
	private static final Logger log = Logger.getLogger(Home.class.getName());
	private static final double IMAGE_WIDTH_RATIO = 3.0 / 10;
	private static final double IMAGE_HEIGHT_RATIO = 2.0 / 25;
	private Socket mSocket;
	private User user;
	private CharSequence first_name;
	private BulletinContainer container;
	private Bulletin editBull;
	private TextView aBullCont;

	private final int RESULT_CODE_MODIFY_BULLETIN = 1;
	private final int RESULT_CODE_ADD_BULLETIN = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		TextView dutiesScreen = (TextView) findViewById(R.id.home_overallduties);
		TextView goodsScreen = (TextView) findViewById(R.id.home_shareditem);
		TextView billScreen = (TextView) findViewById(R.id.home_IOU);
		TextView username = (TextView) findViewById(R.id.home_username);

		user = User.getActiveUser();
		first_name = user.getFirstName();
		username.setText(" " + first_name + "!");

		Button bulletinAddButton = (Button) findViewById(R.id.bulletin_addbtn);
		container = (BulletinContainer) findViewById(R.id.bulletin_container);

		final Activity self = this;

		billScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, Bills.class));
			}
		});

		dutiesScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, ListAllDuties.class));
			}
		});

		setBalance(billScreen);
		HomeController.populateBulletins(container);

		goodsScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(self, ListAllGoods.class));
			}
		});

		TextView toMyDuties = (TextView) findViewById(R.id.to_view_my_duties);
		toMyDuties.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(self, ListMyDuties.class));
			}
		});

		bulletinAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(self, AddBulletin.class);
				startActivityForResult(i, RESULT_CODE_ADD_BULLETIN);
			}
		});

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		ImageView logo = (ImageView) findViewById(R.id.home_appname);
		logo.setImageBitmap(Images.getScaledDownBitmap(getResources(), R.mipmap.logowhite,
				(int) (size.x * IMAGE_WIDTH_RATIO), (int) (size.y * IMAGE_HEIGHT_RATIO)));


		//listening to all the notification
		try {
			ServerRequest.subscribToRoom(Group.getActiveGroup().getId());
			ServerRequest.subscribToMyTopic(User.getActiveUser().getId());

			ServerListener.activateCompleteDuty(self);
			ServerListener.activateRemindDuty(self);
			ServerListener.activateRemindBill(self);
//			ServerListener.activateCompleteCommonGood(self);
//			ServerListener.activateRemindCommonGood(self);
		}
		catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onBackPressed() {
		// This does nothing
	}

	private void setBalance(final TextView billScreen) {
		// Create and run a new thread
		new AsyncTask<Void, Void, CharSequence>() {
			@Override
			protected CharSequence doInBackground(Void... v) {
				return Bill.getNegativeBalance();
			}
			@Override
			protected void onPostExecute(CharSequence result) {
				billScreen.setText(result);
			}
		}.execute();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);

		if(resultCode == RESULT_CODE_MODIFY_BULLETIN) {
			String updContent = data.getStringExtra("Key_New_Content");

			editBull.setContent(updContent);
			aBullCont.setText(updContent);

			HomeController.getController().modifyBulletin(editBull);

		}
		else if(resultCode == RESULT_CODE_ADD_BULLETIN) {
			String content = data.getStringExtra("Key_New_Content");

			HomeController.getController().createBulletin(content, container);
		}
	}

	public void toEditBillScreen(TextView content, Bulletin editBull) {
		aBullCont = content;
		this.editBull = editBull;

		log.info(String.format(Locale.US, InfoStrings.SWITCH_ACTIVITY,
				ModifyBulletin.class.getSimpleName()));

		Intent i = new Intent(getApplicationContext(), ModifyBulletin.class);
		i.putExtra("Orig_Key_Content", editBull.getContent());
		startActivityForResult(i, RESULT_CODE_MODIFY_BULLETIN);
	}
}
