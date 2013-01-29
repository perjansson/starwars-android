package nu.pich.android.starwars;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

public class ViewItemListActivity extends Activity {

	public static final String SELECTION_FARM_ANIMALS = "FARM_ANIMALS";
	public static final String SELECTION_VEHICLES = "VEHICLES";
	public static final String SELECTION_GUESS_ITEM = "GUESS_ITEM";
	private static final int MENU_ITEM_VEHICLES = 0;
	private static final int MENU_ITEM_FARM_ANIMALS = 1;
	private static final int MENU_ITEM_GUESS_ITEM = 2;
	private String currentSelection;

	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;

	private static int lock = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.typelist);

		// See if any incoming selection exists in intent
		String itemType = getIntent().getStringExtra(
				"nu.pich.kidssounds.itemtype");

		// Set selection
		if (itemType != null && itemType.equals(SELECTION_VEHICLES)) {
			currentSelection = SELECTION_VEHICLES;
		} else {
			currentSelection = SELECTION_FARM_ANIMALS;

		}

		// ViewFlipper
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
		slideLeftOut = AnimationUtils
				.loadAnimation(this, R.anim.slide_left_out);
		slideRightIn = AnimationUtils
				.loadAnimation(this, R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_out);

		// Animal
		GridView animalGridview = (GridView) findViewById(R.id.animalGridview);
		animalGridview.setAdapter(new StarWarsOneAdapter(this));
		animalGridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				performTouch(v, position);
			}
		});

		// Vehicle
		GridView autoGridview = (GridView) findViewById(R.id.autoGridview);
		autoGridview.setAdapter(new StarWarsTwoAdapter(this));
		autoGridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				performTouch(v, position);
			}
		});

		// Set current view depending on selection
		if (currentSelection == SELECTION_VEHICLES) {
			viewFlipper.setInAnimation(slideLeftIn);
			viewFlipper.setOutAnimation(slideLeftOut);
			viewFlipper.showPrevious();
		}

	}

	private void performTouch(View v, int position) {

		// Check lock
		if (lock != 1) {

			// Set lock
			lock = 1;

			// Animation
			Animation shake = AnimationUtils.loadAnimation(
					ViewItemListActivity.this, R.anim.shake);
			v.startAnimation(shake);

			// Start task to view item
			new StartViewItemTask().execute(position);

		}

	}

	public class StartViewItemTask extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}

			return params[0];

		}

		@Override
		protected void onPostExecute(Integer position) {
			Intent intent = new Intent(ViewItemListActivity.this,
					ViewItemActivity.class);
			intent.putExtra("nu.pich.kidssounds.typeposition", position);
			intent.putExtra("nu.pich.kidssounds.itemtype", currentSelection);
			startActivity(intent);
		}

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();

		// Show correct itemlist menu item
		if (currentSelection.equals(SELECTION_FARM_ANIMALS)) {
			menu.add(Menu.NONE, MENU_ITEM_VEHICLES, 0, null);
			menu.findItem(MENU_ITEM_VEHICLES).setIcon(R.drawable.ic_menu_yoda);

		} else if (currentSelection.equals(SELECTION_VEHICLES)) {
			menu.add(Menu.NONE, MENU_ITEM_FARM_ANIMALS, 1, null);
			menu.findItem(MENU_ITEM_FARM_ANIMALS).setIcon(
					R.drawable.ic_menu_c3po);

		}

		// Show guess menu item
		menu.add(Menu.NONE, MENU_ITEM_GUESS_ITEM, 2, null);
		menu.findItem(MENU_ITEM_GUESS_ITEM).setIcon(R.drawable.ic_menu_question);

		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case MENU_ITEM_FARM_ANIMALS:
			currentSelection = SELECTION_FARM_ANIMALS;
			viewFlipper.setInAnimation(slideRightIn);
			viewFlipper.setOutAnimation(slideRightOut);
			viewFlipper.showNext();
			return true;
		case MENU_ITEM_VEHICLES:
			currentSelection = SELECTION_VEHICLES;
			viewFlipper.setInAnimation(slideLeftIn);
			viewFlipper.setOutAnimation(slideLeftOut);
			viewFlipper.showPrevious();
			return true;
		case MENU_ITEM_GUESS_ITEM:
			currentSelection = SELECTION_GUESS_ITEM;
			Intent intent = new Intent(this, GuessItemActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static int getLock() {
		return lock;
	}

	public static void setLock(int lock) {
		ViewItemListActivity.lock = lock;
	}

}