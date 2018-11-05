/*
 * Copyright sablintolya@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ma1uta.matrix.support.jsonb;

import static io.github.ma1uta.matrix.event.Event.MessageType.AUDIO;
import static io.github.ma1uta.matrix.event.Event.MessageType.EMOTE;
import static io.github.ma1uta.matrix.event.Event.MessageType.FILE;
import static io.github.ma1uta.matrix.event.Event.MessageType.IMAGE;
import static io.github.ma1uta.matrix.event.Event.MessageType.LOCATION;
import static io.github.ma1uta.matrix.event.Event.MessageType.NOTICE;
import static io.github.ma1uta.matrix.event.Event.MessageType.TEXT;
import static io.github.ma1uta.matrix.event.Event.MessageType.VIDEO;

import io.github.ma1uta.matrix.event.content.RoomMessageContent;
import io.github.ma1uta.matrix.event.message.Audio;
import io.github.ma1uta.matrix.event.message.Emote;
import io.github.ma1uta.matrix.event.message.File;
import io.github.ma1uta.matrix.event.message.Image;
import io.github.ma1uta.matrix.event.message.Location;
import io.github.ma1uta.matrix.event.message.Notice;
import io.github.ma1uta.matrix.event.message.RawMessageContent;
import io.github.ma1uta.matrix.event.message.Text;
import io.github.ma1uta.matrix.event.message.Video;

import java.lang.reflect.Type;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;

/**
 * JSON-B deserializer of the Room Messages.
 */
public class RoomMessageDeserializer implements JsonbDeserializer<RoomMessageContent> {

    private Jsonb jsonb = JsonbBuilder.create();

    protected Jsonb jsonb() {
        return jsonb;
    }

    @Override
    public RoomMessageContent deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
        JsonObject object = parser.getObject();
        if (object.get("msgtype") == null || object.isNull("msgtype")) {
            return parse(object, null, ctx);
        }
        String msgtype = object.getString("msgtype");
        String content = object.toString();

        switch (msgtype) {
            case AUDIO:
                return jsonb().fromJson(content, Audio.class);
            case EMOTE:
                return jsonb().fromJson(content, Emote.class);
            case FILE:
                return jsonb().fromJson(content, File.class);
            case IMAGE:
                return jsonb().fromJson(content, Image.class);
            case LOCATION:
                return jsonb().fromJson(content, Location.class);
            case NOTICE:
                return jsonb().fromJson(content, Notice.class);
            case TEXT:
                return jsonb().fromJson(content, Text.class);
            case VIDEO:
                return jsonb().fromJson(content, Video.class);
            default:
                return parse(object, msgtype, ctx);
        }
    }

    protected RawMessageContent parse(JsonObject object, String type, DeserializationContext ctx) {
        return new RawMessageContent(object, type);
    }
}