package nu.pich.android.starwars;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewItemActivity extends Activity {

	private LinearLayout itemLayout;
	private Animation fadeAnimation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Inflate layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type);
		itemLayout = (LinearLayout) findViewById(R.id.itemLayout);

		// Load animation
		fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

		// Load item from info in intent
		Integer itemPosition = getIntent().getIntExtra(
				"nu.pich.kidssounds.typeposition", -1);
		String itemType = getIntent().getStringExtra(
				"nu.pich.kidssounds.itemtype");
		Item item = null;
		if (itemType.equals(ViewItemListActivity.SELECTION_FARM_ANIMALS)) {
			item = StarWarsOneAdapter.getItemAtPosistion(itemPosition);
			itemLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

		} else if (itemType.equals(ViewItemListActivity.SELECTION_VEHICLES)) {
			item = StarWarsTwoAdapter.getItemAtPosistion(itemPosition);
			itemLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}

		ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
		itemImage.setImageResource(item.getDrawable_large());
		
		TextView itemName = (TextView) findViewById(R.id.itemName);
		itemName.setText(item.getName());

		// Apply animation
		fadeAnimation.setFillAfter(true);
		fadeAnimation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				// Wait... and return to item list activity
				new ReturnToViewItemListTask().execute();

			}
		});

		itemImage.startAnimation(fadeAnimation);

		// Play item sound
		if (item.getSound() != null) {
			MediaPlayer mp = MediaPlayer.create(this, item.getSound());
			if (mp != null) {
				mp.start();
			} else {
				Toast.makeText(this,
						"Sorry, sound could not be played on this phone",
						Toast.LENGTH_SHORT).show();
			}
		}

		// Reset lock
		ViewItemListActivity.setLock(0);

	}

	public class ReturnToViewItemListTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {

			}

			return null;

		}

		@Override
		protected void onPostExecute(Void arg0) {
			finish();
		}

	}

}