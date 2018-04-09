package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.*;

/**
 * Data
 *
 * @author bubai
 * @date 2018/3/23
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private Attachment attachment;

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Attachment {
        private String name;
        private String channel;
        private String data;
        private String type;
        private String hash;

        @JsonProperty(value = "fileName")
        public String getName() {
            return name;
        }

        @JsonProperty(value = "name")
        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty(value = "clientAccount")
        public String getChannel() {
            return channel;
        }

        @JsonProperty(value = "channel")
        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @JsonProperty(value = "fileType")
        public String getType() {
            return type;
        }

        @JsonProperty(value = "type")
        public void setType(String type) {
            this.type = type;
        }
    }
}
