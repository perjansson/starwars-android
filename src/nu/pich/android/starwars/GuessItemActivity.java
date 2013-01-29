package nu.pich.android.starwars;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

public class GuessItemActivity extends Activity {

	GuessItemActivity thisActivity = null;

	public static final String SELECTION_FARM_ANIMALS = "FARM_ANIMALS";
	public static final String SELECTION_VEHICLES = "VEHICLES";

	private static final int MENU_ITEM_VEHICLES = 0;
	private static final int MENU_ITEM_FARM_ANIMALS = 1;
	private String currentSelection;

	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;

	private LinearLayout guessItemLayout;
	private TextView guessItemText;

	// Random items
	private Item randomItem = null;
	private boolean randomItemAnFarmAnimal;

	private static int lock = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.guessitem);

		thisActivity = this;

		// ViewFlipper
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);

		// Guess text
		guessItemLayout = (LinearLayout) findViewById(R.id.guessItemLayout);
		guessItemText = (TextView) findViewById(R.id.guessItemText);
		guessItemLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Text color
				guessItemText.setTextColor(Color.parseColor("#FFFFFF"));

				// Animation
				// Animation shake = AnimationUtils.loadAnimation(
				// GuessItemActivity.this, R.anim.shake);
				// v.startAnimation(shake);

				// Get random item
				randomItem = getRandomItem();

				new StartGuessItemTask().execute();

				// Populate gridview with items for correct item type
				GridView randomItemview = (GridView) findViewById(R.id.randomItemGridview);
				if (randomItemAnFarmAnimal) {
					randomItemview.setAdapter(new StarWarsOneAdapter(v
							.getContext()));
				} else {
					randomItemview.setAdapter(new StarWarsTwoAdapter(v
							.getContext()));
				}
				randomItemview
						.setOnItemClickListener(new OnItemClickListener() {
							public void onItemClick(AdapterView<?> parent,
									View v, int position, long id) {
								// Check if correct answer i seleted. Play sound
								// and show toast, yes or no, depending on
								// answer. Flip view back if correct answer
								performTouch(v, position);
							}
						});

				// Flip view
				viewFlipper.setInAnimation(slideRightIn);
				viewFlipper.setOutAnimation(slideRightOut);
				viewFlipper.showNext();

			}

		});

		// Reset lock
		lock = 0;

	}

	private void performTouch(View v, int position) {

		// Check lock
		if (lock != 1) {

			// Set lock
			lock = 1;

			// Animation
			Animation shake = AnimationUtils.loadAnimation(
					GuessItemActivity.this, R.anim.shake);
			v.startAnimation(shake);

			// Check if answer is correct
			Item choosenItem = null;
			if (randomItemAnFarmAnimal) {
				choosenItem = StarWarsOneAdapter.getItemAtPosistion(position);
			} else {
				choosenItem = StarWarsTwoAdapter.getItemAtPosistion(position);
			}
			boolean correct = randomItem.getId().equals(choosenItem.getId());

			// If correct, play sound, show tost and flip view
			if (correct) {
				Toast.makeText(v.getContext(), getString(R.string.match_yes),
						Toast.LENGTH_SHORT).show();
				MediaPlayer mp = MediaPlayer.create(v.getContext(),
						R.raw.kidscheer);
				if (mp != null) {
					mp.start();
				}
				new ReturnToGuessItemTask().execute();

			} else {
				Toast.makeText(v.getContext(), getString(R.string.match_no),
						Toast.LENGTH_SHORT).show();
				MediaPlayer mp = MediaPlayer.create(v.getContext(), R.raw.ohno);
				if (mp != null) {
					mp.start();
				}
				new WaitAfterWrongGuessTask().execute();
			}

		}

	}

	/**
	 * Returns a random item
	 * 
	 * @return item
	 */
	private Item getRandomItem() {

		Item[] farmAnimalItems = StarWarsOneAdapter.getItems();
		Item[] vehicleItems = StarWarsTwoAdapter.getItems();

		Random random = new Random();
		int farmAnimalOrVehicle = random.nextInt(2);

		// Farm animals if 0
		if (farmAnimalOrVehicle == 0) {
			randomItemAnFarmAnimal = true;
			int farmAnimalNumber = random.nextInt(farmAnimalItems.length);
			randomItem = StarWarsOneAdapter
					.getItemAtPosistion(farmAnimalNumber);

		} else {
			randomItemAnFarmAnimal = false;
			int vechileNumber = random.nextInt(vehicleItems.length);
			randomItem = StarWarsTwoAdapter.getItemAtPosistion(vechileNumber);

		}

		return randomItem;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();

		menu.add(Menu.NONE, MENU_ITEM_FARM_ANIMALS, 0, null);
		menu.findItem(MENU_ITEM_FARM_ANIMALS).setIcon(R.drawable.ic_menu_c3po);

		menu.add(Menu.NONE, MENU_ITEM_VEHICLES, 0, null);
		menu.findItem(MENU_ITEM_VEHICLES).setIcon(R.drawable.ic_menu_yoda);

		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, ViewItemListActivity.class);
		// Handle item selection
		switch (item.getItemId()) {
		case MENU_ITEM_FARM_ANIMALS:
			currentSelection = SELECTION_FARM_ANIMALS;
			intent.putExtra("nu.pich.kidssounds.itemtype", currentSelection);
			startActivity(intent);
			return true;
		case MENU_ITEM_VEHICLES:
			currentSelection = SELECTION_VEHICLES;
			intent.putExtra("nu.pich.kidssounds.itemtype", currentSelection);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public class StartGuessItemTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {

			boolean playedOk = true;

			// Play sound
			if (randomItem.getSound() != null) {
				MediaPlayer mp = MediaPlayer.create(thisActivity,
						randomItem.getSound());
				if (mp != null) {
					mp.start();
				} else {
					playedOk = false;
				}
			}

			return playedOk;

		}

		@Override
		protected void onPostExecute(Boolean playedOk) {
			if (!playedOk) {
				Toast.makeText(thisActivity,
						"Temporary error, could not play sound",
						Toast.LENGTH_SHORT);

			}
		}

	}

	public class ReturnToGuessItemTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {

			}

			return null;

		}

		@Override
		protected void onPostExecute(Void arg0) {

			// Text color
			guessItemText.setTextColor(Color.parseColor("#FFFFFF"));

			// Flip view
			viewFlipper.setInAnimation(slideLeftIn);
			viewFlipper.setOutAnimation(slideLeftOut);
			viewFlipper.showNext();

			// Reset lock
			lock = 0;
		}

	}

	public class WaitAfterWrongGuessTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}

			return null;

		}

		@Override
		protected void onPostExecute(Void arg0) {
			// Reset lock
			lock = 0;
		}

	}

}