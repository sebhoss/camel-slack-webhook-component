/*
 * Copyright © 2014 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.camel.slack.webhook;

import static org.apache.http.client.fluent.Request.Post;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.client.fluent.Executor;
import org.json.simple.JSONObject;

final class SlackWebhookProcessor implements Processor {

    private final String hook;
    private final String channel;
    private final String iconUrl;
    private final String iconEmoji;
    private final String name;

    SlackWebhookProcessor(final String hook, final String channel, final String iconUrl, final String iconEmoji,
            final String name) {
        this.hook = hook;
        this.channel = channel;
        this.iconUrl = iconUrl;
        this.iconEmoji = iconEmoji;
        this.name = name;
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        final String message = exchange.getIn().getBody(String.class);
        final String payload = payload(defaultName(exchange), message);
        final Executor executor = Executor.newInstance();

        executor.execute(Post(hook).bodyString(payload, APPLICATION_JSON));
    }

    private static String defaultName(final Exchange exchange) {
        final String contextName = exchange.getContext().getName();
        final String routeId = exchange.getFromRouteId();

        return contextName + "-" + routeId; //$NON-NLS-1$
    }

    private String payload(final String defaultName, final String message) {
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        builder.put("username", MoreObjects.firstNonNull(name, defaultName)); //$NON-NLS-1$
        builder.put("channel", channel); //$NON-NLS-1$

        if (iconUrl != null && !iconUrl.isEmpty()) {
            builder.put("icon_url", iconUrl); //$NON-NLS-1$
        }
        if (iconEmoji != null && !iconEmoji.isEmpty()) {
            builder.put("icon_emoji", iconEmoji); //$NON-NLS-1$
        }
        builder.put("text", message); //$NON-NLS-1$

        return JSONObject.toJSONString(builder.build());
    }

}
