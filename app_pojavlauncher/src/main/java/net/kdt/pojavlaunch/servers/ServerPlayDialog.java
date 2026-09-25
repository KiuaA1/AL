package net.kdt.pojavlaunch.servers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import net.kdt.pojavlaunch.R;

/**
 * The small "play or save?" popup that appears when a server's PLAY button is tapped.
 *
 * <p>Kept intentionally simple: one short sentence and two buttons. Partner servers additionally
 * get their banner artwork.
 */
public final class ServerPlayDialog {

    public interface Action {
        void onPlayNow(ServerEntry entry);
        void onSaveOnly(ServerEntry entry);
    }

    private ServerPlayDialog() {}

    public static void show(Context ctx, ServerEntry entry, Action action) {
        if (ctx == null || entry == null) return;

        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_server_play, null, false);
        AlertDialog dialog = new AlertDialog.Builder(ctx).setView(view).create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        final FeaturedServers.Item item = FeaturedServers.find(entry.address);
        boolean partner = entry.pinned || item != null;

        CardView bannerCard = view.findViewById(R.id.spd_banner_card);
        TextView title = view.findViewById(R.id.spd_title);
        TextView address = view.findViewById(R.id.spd_address);
        TextView message = view.findViewById(R.id.spd_message);
        TextView btnSave = view.findViewById(R.id.spd_btn_save);
        TextView btnPlay = view.findViewById(R.id.spd_btn_play);

    }



    private static void animateIn(View v, long delay) {
        if (v == null || v.getVisibility() != View.VISIBLE) return;
        v.setAlpha(0f);
        v.setTranslationY(14f);
        v.animate().alpha(1f).translationY(0f)
                .setStartDelay(delay)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(260).start();
    }

    static void press(View v) {
        v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(70)
                .withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(150).start())
                .start();
    }
}
