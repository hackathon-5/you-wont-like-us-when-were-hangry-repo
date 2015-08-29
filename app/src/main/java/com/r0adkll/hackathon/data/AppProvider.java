/*
 * Copyright © 52inc 2015.
 * All rights reserved.
 */

package com.r0adkll.hackathon.data;

import com.r0adkll.hackathon.BuildConfig;

import ollie.Ollie;
import ollie.OllieProvider;

/**
 * The database provider to enable loaders for Ollie
 *
 */
public class AppProvider extends OllieProvider {
    @Override
    protected String getDatabaseName() {
        return "hackathon15.db";
    }

    @Override
    protected int getDatabaseVersion() {
        return 1;
    }

    @Override
    protected Ollie.LogLevel getLogLevel() {
        return BuildConfig.DEBUG ?
                Ollie.LogLevel.FULL :
                Ollie.LogLevel.NONE;
    }

    @Override
    protected String getAuthority() {
        return BuildConfig.APPLICATION_ID;
    }
}
