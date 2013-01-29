package nu.pich.android.starwars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

public class ViewSplashActivity extends Activity {

	private Animation fadeAnimation;
	private Animation fadeOutAnimation;
	private LinearLayout rotateSplashLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createUserInterface();
	}

	private void createUserInterface() {

		// Inflate layout
		setContentView(R.layout.splash);

		// Load animations
		fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash);
		fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out_splash);

		// Get views
		rotateSplashLayout = (LinearLayout) findViewById(R.id.rotateSplashLayout);

		// Apply first animation
		fadeAnimation.setFillAfter(true);
		fadeAnimation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				// Apply second animation
				applyRotation(0, 360);
			}
		});

		rotateSplashLayout.startAnimation(fadeAnimation);

	}

	private void applyRotation(float start, float end) {
		// Find the center of the container
		final float centerX = rotateSplashLayout.getWidth() / 2.0f;
		final float centerY = rotateSplashLayout.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
				centerX, centerY, 0f, true);
		rotation.setDuration(1500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView());

		rotateSplashLayout.startAnimation(rotation);
	}

	private final class DisplayNextView implements Animation.AnimationListener {

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {

			// Apply third animation
			fadeOutAnimation.setFillAfter(true);
			fadeOutAnimation.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {

				}

				public void onAnimationRepeat(Animation animation) {

				}

				public void onAnimationEnd(Animation animation) {
					// Start application...
					Intent intent = new Intent(ViewSplashActivity.this,
							ViewItemListActivity.class);
					startActivity(intent);
					finish();
				}
			});

			rotateSplashLayout.startAnimation(fadeOutAnimation);
		}

		public void onAnimationRepeat(Animation animation) {
		}

	}
}
