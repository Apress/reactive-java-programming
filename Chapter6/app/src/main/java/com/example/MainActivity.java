package com.example;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    private EditText mEditText;

    private View mButton;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);

        // simple click bind
        RxView.clicks(mButton)
                .subscribe(click -> mTextView.setText("Button clicked!"));

        final int[] clickCount = {0};
        // react only to the first click every 300 milliseconds
        RxView.clicks(mButton)
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(click -> mTextView.setText("throttleFirst example, click count="+ (clickCount[0]++)));

        // simple EditText bind
        RxTextView.afterTextChangeEvents(mEditText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent tvChangeEvent) {
                        CharSequence text = tvChangeEvent.view().getText();
                        mTextView.setText(text);
                    }
                });

        // Notify EditText changes every 1 second
        RxTextView.afterTextChangeEvents(mEditText)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent tvChangeEvent) {
                        CharSequence text = tvChangeEvent.view().getText();
                        mTextView.setText(text);
                    }
                });


        Observable.timer(2, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycle.bindUntilEvent(lifecycle(),
                        ActivityEvent.DESTROY))
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long item) {
                        mTextView.setText("Timeout!");
                    }
                });

    }

}
