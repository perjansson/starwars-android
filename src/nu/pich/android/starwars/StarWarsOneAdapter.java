package nu.pich.android.starwars;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class StarWarsOneAdapter extends BaseAdapter {

	private Context mContext;

	public StarWarsOneAdapter(Context c) {
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
		if (convertView == null) {

			// Calculate image size
			WindowManager windowManager = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			int height = windowManager.getDefaultDisplay().getHeight();
			int width = windowManager.getDefaultDisplay().getWidth();
			int calculatedHeight = new Double(height / 4.8).intValue();
			int calculatedWidth = new Double(width / 3.0).intValue();

			// Create image view
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

	// References to animal images
	private static Item[] items = {
			new Item(1, R.drawable.anakin_small, R.drawable.anakin_large,
					R.raw.anakin, "Anakin Skywalker"),
			new Item(2, R.drawable.bobafett_small, R.drawable.bobafett_large,
					R.raw.bobafett, "Boba Fett"),
			new Item(3, R.drawable.c3po_small, R.drawable.c3po_large,
					R.raw.c3po, "C3PO"),
			new Item(4, R.drawable.chewbacca_small, R.drawable.chewbacca_large,
					R.raw.chewbacca, "Chewbacca"),
			new Item(5, R.drawable.darthvader_small,
					R.drawable.darthvader_large, R.raw.darthvader,
					"Darth Vader"),
			new Item(6, R.drawable.ewoks_small, R.drawable.ewoks_large,
					R.raw.ewoks, "Ewoks"),
			new Item(7, R.drawable.hansolo_small, R.drawable.hansolo_large,
					R.raw.hansolo, "Han Solo") };

	public static Item[] getItems() {
		return items;
	}

	public static void setItems(Item[] items) {
		StarWarsOneAdapter.items = items;
	}
};