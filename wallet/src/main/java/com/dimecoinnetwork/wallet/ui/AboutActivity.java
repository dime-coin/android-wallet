/*
 * Copyright 2011-2014 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dimecoinnetwork.wallet.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.MenuItem;

import com.google.bitcoin.core.VersionMessage;

import com.dimecoinnetwork.wallet.Constants;
import com.dimecoinnetwork.wallet.WalletApplication;
import com.dimecoinnetwork.wallet.R;

/**
 * @author Andreas Schildbach
 */
public final class AboutActivity extends PreferenceActivity
{
	private static final String KEY_ABOUT_VERSION = "about_version";
	private static final String KEY_ABOUT_LICENSE = "about_license";
	private static final String KEY_ABOUT_SOURCE = "about_source";
	private static final String KEY_ABOUT_MARKET_APP = "about_market_app";
    private static final String KEY_ABOUT_CREDITS_WEBSITE = "about_credits_website";
 	private static final String KEY_ABOUT_CREDITS_BITCOINJ = "about_credits_bitcoinj";
	private static final String KEY_ABOUT_CREDITS_ZXING = "about_credits_zxing";

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.about);

		findPreference(KEY_ABOUT_VERSION).setSummary(((WalletApplication) getApplication()).packageInfo().versionName);
		findPreference(KEY_ABOUT_LICENSE).setSummary(Constants.LICENSE_URL);
		findPreference(KEY_ABOUT_SOURCE).setSummary(Constants.FORKED_FROM_SOURCE +Constants.SOURCE_URL);

		findPreference(KEY_ABOUT_CREDITS_BITCOINJ).setTitle(getString(R.string.about_credits_bitcoinj_title, VersionMessage.BITCOINJ_VERSION));
		findPreference(KEY_ABOUT_CREDITS_BITCOINJ).setSummary(Constants.FORKED_FROM_SOURCE_BITCOINJ+Constants.CREDITS_BITCOINJ_URL);
		findPreference(KEY_ABOUT_CREDITS_ZXING).setSummary(Constants.CREDITS_ZXING_URL);

		findPreference(KEY_ABOUT_MARKET_APP).setSummary(String.format(Constants.MARKET_APP_URL, getPackageName()));
        findPreference(KEY_ABOUT_CREDITS_WEBSITE).setSummary(Constants.CREDITS_WEBSITE_URL);

	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPreferenceTreeClick(final PreferenceScreen preferenceScreen, final Preference preference)
	{
		final String key = preference.getKey();
		if (KEY_ABOUT_LICENSE.equals(key))
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.LICENSE_URL)));
			finish();
		}
		else if (KEY_ABOUT_SOURCE.equals(key))
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.SOURCE_URL)));
			finish();
		}
		else if (KEY_ABOUT_MARKET_APP.equals(key))
		{
			final Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constants.MARKET_APP_URL, getPackageName())));
			if (getPackageManager().resolveActivity(marketIntent, 0) != null)
				startActivity(marketIntent);
			else
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constants.WEBMARKET_APP_URL, getPackageName()))));
			finish();
		}
        else if (KEY_ABOUT_CREDITS_WEBSITE.equals(key))
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CREDITS_WEBSITE_URL)));
            finish();
        }
		else if (KEY_ABOUT_CREDITS_BITCOINJ.equals(key))
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CREDITS_BITCOINJ_URL)));
			finish();
		}
		else if (KEY_ABOUT_CREDITS_ZXING.equals(key))
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CREDITS_ZXING_URL)));
			finish();
		}
		return false;
	}
}
