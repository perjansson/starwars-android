package nu.pich.android.starwars;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class StarWarsTwoAdapter extends BaseAdapter {

	private Context mContext;

	public StarWarsTwoAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return items.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
			// attributes

			WindowManager windowManager = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			int height = windowManager.getDefaultDisplay().getHeight();
			int width = windowManager.getDefaultDisplay().getWidth();
			int calculatedHeight = new Double(height / 4.8).intValue();
			int calculatedWidth = new Double(width / 3.0).intValue();

			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(
					calculatedWidth, calculatedHeight));
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setPadding(16, 16, 16, 16);

		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(items[position].getDrawable_small());
		return imageView;
	}

	public static Item getItemAtPosistion(int position) {
		return items[position];
	}

	// References to Auto images
	private static Item[] items = {
			new Item(13, R.drawable.jabbathehutt_small,
					R.drawable.jabbathehutt_large, R.raw.jabbathehutt,
					"Jabba the Hutt"),
			new Item(14, R.drawable.jarjarbinks_small,
					R.drawable.jarjarbinks_large, R.raw.jarjarbinks,
					"Jar Jar Binks"),
			new Item(15, R.drawable.leia_small, R.drawable.leia_large,
					R.raw.leia, "Princess Leia"),
			new Item(16, R.drawable.lukeskywalker_small,
					R.drawable.lukeskywalker_large, R.raw.lukeskywalker,
					"Luke Skywalker"),
			new Item(17, R.drawable.obiwankenobi_small,
					R.drawable.obiwankenobi_large, R.raw.obiwankenobi,
					"Obi Wan Kenobi"),
			new Item(18, R.drawable.r2d2_small, R.drawable.r2d2_large,
					R.raw.r2d2, "R2D2"),
			new Item(19, R.drawable.yoda_small, R.drawable.yoda_large,
					R.raw.yoda, "Yoda") };

	public static Item[] getItems() {
		return items;
	}

	public static void setItems(Item[] items) {
		StarWarsTwoAdapter.items = items;
	}
};