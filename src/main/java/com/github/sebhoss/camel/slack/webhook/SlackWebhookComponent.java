/*
 * Copyright © 2014 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.camel.slack.webhook;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.PropertyInject;
import org.apache.camel.impl.DefaultComponent;

/**
 * Provides a slack-webhook consumer
 */
public final class SlackWebhookComponent extends DefaultComponent {

    @PropertyInject(value = "slack.hook", defaultValue = "https://hooks.slack.com/services/YOUR_TOKEN_HERE")
    private String slackHook;

    @Override
    protected Endpoint createEndpoint(final String uri, final String channel, final Map<String, Object> parameters)
            throws Exception {
        return new SlackWebhookEndpoint(slackHook, channel);
    }

}
