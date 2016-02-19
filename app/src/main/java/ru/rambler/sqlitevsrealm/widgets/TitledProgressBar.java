package ru.rambler.sqlitevsrealm.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.rambler.sqlitevsrealm.R;

public class TitledProgressBar extends FrameLayout {
    private ProgressBar progressBar;
    private TextView titleView;

    public TitledProgressBar(Context context) {
        super(context);
        init();
    }

    public TitledProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitledProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.titled_progess_bar, this);
        titleView = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void setTitle(String title) {
        this.titleView.setText(title);
    }

    public void setMax(int max) {
        progressBar.setMax(max);
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }
}
