package com.example.flyup;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;

import static android.content.Context.MODE_PRIVATE;


public class Settings extends PreferenceFragmentCompat {
    private AudioManager audioManager;
    private SeekBarPreference mSeekBarPreference;
    private PreferenceCategory BasicSettingCategory;
    private SharedPreferences mSharedPreferences;
    private ListPreference mListPreference;


    private final static String TAG = Settings.class.getSimpleName();
    private String value;


    @Override
    public void onPause() {
        super.onPause();
         //getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*
        if (mListPreference.getSummary() == getString(R.string.Français))
        {
            BasicSettingCategory.setTitle(R.string.Bases);
            mSeekBarPreference.setTitle(R.string.Son);
            mListPreference.setTitle(R.string.langue);
        }
        if(mListPreference.getSummary() == getString(R.string.Português))
        {
            BasicSettingCategory.setTitle(R.string.Config);
            mSeekBarPreference.setTitle(R.string.Som);
            mListPreference.setTitle(R.string.idioma);
        }
        if(mListPreference.getSummary() == getString(R.string.english))
        {
            BasicSettingCategory.setTitle(R.string.basics);
            mSeekBarPreference.setTitle(R.string.sound);
            mListPreference.setTitle(R.string.language);
        }
*/

    }

    @Override
    public void onResume() {
        super.onResume();
        //mPlayer.reset();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        //view.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.on_road));
        return view;
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_game, rootKey);
        BasicSettingCategory = findPreference(getString(R.string.settings));
        mListPreference = findPreference(getString(R.string.language));
        mSeekBarPreference = findPreference(getString(R.string.sound));
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mSeekBarPreference.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mSeekBarPreference.setShowSeekBarValue(true);

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();




        mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                 value = newValue.toString();



                int index = mListPreference.findIndexOfValue(value);
                mListPreference.setSummary(value);
                //mListPreference.setEntries();
                if (value == getString(R.string.Français))
                {
                    Toast.makeText(getActivity(),String.valueOf(value),Toast.LENGTH_SHORT).show();
                    BasicSettingCategory.setTitle(R.string.Bases);
                    mSeekBarPreference.setTitle(R.string.Son);
                    mListPreference.setTitle(R.string.langue);
                }
                if(value == getString(R.string.Português))
                {
                    Toast.makeText(getActivity(),String.valueOf(value),Toast.LENGTH_SHORT).show();
                    BasicSettingCategory.setTitle(R.string.Config);
                    mSeekBarPreference.setTitle(R.string.Som);
                    mListPreference.setTitle(R.string.idioma);
                }
                if(value == getString(R.string.english))
                    {
                        Toast.makeText(getActivity(),String.valueOf(value),Toast.LENGTH_SHORT).show();
                        BasicSettingCategory.setTitle(R.string.basics);
                        mSeekBarPreference.setTitle(R.string.sound);
                        mListPreference.setTitle(R.string.language);
                }

                //mListPreference.setValueIndex(index);
                //mListPreference.setValue(value);
                   // mListPreference.setSummary(mListPreference.getEntry());
                    return true;
            }
        });
        mSeekBarPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue instanceof Integer) {
                    Integer newValueInt;
                    try {
                        newValueInt = (Integer) newValue;
                    } catch (NumberFormatException nfe) {
                        Log.e(TAG, "SeekBarPreference is a Integer, but it caused a NumberFormatException");
                        Toast.makeText(getActivity(),
                                "SeekBarPreference is a Integer, but it caused a NumberFormatException",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newValueInt, 0);
                    return true;
                } else {
                    String objType = newValue.getClass().getName();
                    Log.e(TAG, "SeekBarPreference is not a Integer, it is " + objType);
                    Toast.makeText(getActivity(),
                            "SeekBarPreference is not a Integer, it is " + objType,
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
        });
        preferences.edit().putString(mListPreference.getKey(),mListPreference.getValue());

       // String Sound = preferences.getString(mSeekBarPreference.getKey(), "Nope!");

        String language = preferences.getString(mListPreference.getKey(), "English");
        if(language == getString(R.string.Français))
        {

            mListPreference.setTitle(getString(R.string.langue));
            BasicSettingCategory.setTitle(R.string.Bases);
            preferences.edit().putString(BasicSettingCategory.getKey(),getString(R.string.Bases));
            String Category = preferences.getString(BasicSettingCategory.getKey(), getString(R.string.Bases));
            BasicSettingCategory.setTitle(Category);

           // mSeekBarPreference.setTitle(R.string.Son);
        }
        if(language == getString(R.string.english))
        {
            mListPreference.setTitle(getString(R.string.language));
            BasicSettingCategory.setTitle(R.string.basics);
            mSeekBarPreference.setTitle(R.string.sound);
        }
        if(language == getString(R.string.Português))
        {
            mListPreference.setTitle(getString(R.string.idioma));
            BasicSettingCategory.setTitle(R.string.Config);
            mSeekBarPreference.setTitle(R.string.Som);
        }

        Toast.makeText(getActivity(),language,Toast.LENGTH_SHORT).show();

    }

}