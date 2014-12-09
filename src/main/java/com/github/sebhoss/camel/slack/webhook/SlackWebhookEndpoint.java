/*
 * Copyright © 2014 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.camel.slack.webhook;

import org.apache.camel.Processor;
import org.apache.camel.impl.ProcessorEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

@UriEndpoint(scheme = SlackWebhookEndpoint.SLACK_WEBHOOK)
final class SlackWebhookEndpoint extends ProcessorEndpoint {

    /**
     * The camel URI scheme for the slack webhook
     */
    public static final String SLACK_WEBHOOK = "slack-webhook"; //$NON-NLS-1$

    @UriParam
    private String             iconUrl;

    @UriParam
    private String             iconEmoji;

    @UriParam
    private String             name;

    @UriParam
    private String             hook;

    private final String       channel;

    SlackWebhookEndpoint(final String hook, final String channel) {
        this.hook = hook;
        this.channel = channel;
    }

    @Override
    protected Processor createProcessor() throws Exception {
        return new SlackWebhookProcessor(hook, channel, iconUrl, iconEmoji, name);
    }

    @Override
    protected String createEndpointUri() {
        return SLACK_WEBHOOK + ":" + channel; //$NON-NLS-1$
    }

    /**
     * @return The URL to the user's icon
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * @param iconUrl
     *            The URL to the user's icon
     */
    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * @return The name of the user's emoji icon
     */
    public String getIconEmoji() {
        return iconEmoji;
    }

    /**
     * @param iconEmoji
     *            The name of the user's emoji icon
     */
    public void setIconEmoji(final String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    /**
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name of the user
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The slack webhook endpoint to use
     */
    public String getHook() {
        return hook;
    }

    /**
     * @param hook
     *            The slack webhook endpoint to use
     */
    public void setHook(final String hook) {
        this.hook = hook;
    }

}
